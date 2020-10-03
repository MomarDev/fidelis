/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import michelkaapp.MichelKaApp;
import michelkaapp.database.Driver;
import michelkaapp.objets.Parametre;
import michelkaapp.objets.Trace;
import michelkaapp.objets.User;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class LoginController implements Initializable {

    @FXML
    private TextField input_username;
    @FXML
    private Label welcome;
    @FXML
    private Button btn_login;
    @FXML
    private Label output_label;
    @FXML
    private Label salon_title;
    @FXML
    private Label salon_sous_titre;

//    Les variables non FXML
    private Stage stage;
    private Parent root;
    @FXML
    private AnchorPane root_pane;
    @FXML
    private HBox hbox;
    @FXML
    private PasswordField input_password;
    @FXML
    private AnchorPane pane_droite;

    @FXML
    public void loginEvent(ActionEvent event) throws IOException, SQLException {
        login();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        input_username.requestFocus();
        Utilis.driver = new Driver();
        if (Utilis.BASE_DE_DONNEE == null) {
            Utilis.driver.setNomBaseDonnee(Utilis.db_name);
            System.out.println("Connection via Default");
        } else {
            Utilis.driver.setHostname(Utilis.BASE_DE_DONNEE.getHostname());
            Utilis.driver.setNomBaseDonnee(Utilis.BASE_DE_DONNEE.getDbname());
            Utilis.driver.setUtilisateur(Utilis.BASE_DE_DONNEE.getUsername());
            Utilis.driver.setMotDePasse(Utilis.BASE_DE_DONNEE.getPassword());
            Utilis.driver.setPort(Integer.parseInt(Utilis.BASE_DE_DONNEE.getPort()));
            System.out.println("Connection via file");
        }
        MichelKaApp.connexionIsOK = Utilis.driver.CreateConnecion();
        Parametre parametre = Utilis.driver.parametre("MAX_FENETRE");
        if (parametre != null) {
            Utilis.DEFINED_FENETRE = Integer.parseInt(parametre.getValeur());
            Parametre parametreEmail = Utilis.driver.parametre("EMAIL");
            Utilis.DefaultEmail = parametreEmail.getValeur();
            Utilis.DefaultEmailPassword = parametreEmail.getSuplementaire();
        }
        input_username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("ENTER");
                if (input_password.getText().isEmpty() || input_username.getText().isEmpty()) {
                    output_label.setText("Veuillez remplir tous les champs s'il vous plait");
                } else {
                    try {
                        login();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        input_password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("ENTER");
                if (input_password.getText().isEmpty() || input_username.getText().isEmpty()) {
                    output_label.setText("Veuillez remplir tous les champs s'il vous plait");
                } else {
                    try {
                        login();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private void login() throws IOException {
        String pwd = input_password.getText();
        String usr = input_username.getText();
        if (pwd.isEmpty() || usr.isEmpty()) {
            output_label.setText("Veuillez vérifier les informations saisies s'il vous plait.");
        } else {
            User user = null;
            if (MichelKaApp.connexionIsOK) {
                user = Utilis.driver.authenfication(usr, pwd);
                Utilis.user = user;
            } else {
                Utilis.LOGGER.log("LOGIN CONTROLLER: Ne peut pas acceder à  la base de donnée.");
                Alert error = new Alert(Alert.AlertType.WARNING, "Ne peut pas acceder à  la base de donnée.", ButtonType.OK);
                error.show();
                return;
            }
            if (user != null) {
                MichelKaApp.connexionIsOK = true;
                Trace trace = new Trace();
                trace.setUser(user);
                trace.setAction("Login");
                trace.setObjet("Application");
                trace.setValeur(Utilis.Success_Values);
                boolean teste = Utilis.driver.insertTrace(trace);
                System.err.print(teste);
                root = FXMLLoader.load(getClass().getResource("HomeFade.fxml"));
                stage = (Stage) btn_login.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                output_label.setText("Erreur d'authenfication!");
                Utilis.Failed_Connexion++;
                if (Utilis.Failed_Connexion == Utilis.Max_Tentative_connexion_Failed) {
                    stage = (Stage) btn_login.getScene().getWindow();
                    Alert warring = new Alert(Alert.AlertType.WARNING, "Vous avez atteint le nombre de tentatives maximales autorisées! L'application va se fermer...", ButtonType.OK);
                    warring.setOnCloseRequest(new EventHandler<DialogEvent>() {
                        @Override
                        public void handle(DialogEvent event) {
                            warring.close();
                            stage.close();
                        }
                    });
                    warring.show();
                }
            }
        }
    }
}
