/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Profile;
import michelkaapp.objets.User;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class UtilisateurUpdateController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane form;
    @FXML
    private RadioButton rd_admin;
    @FXML
    private RadioButton rd_operator;
    @FXML
    private Button btn_cancel;
    @FXML
    private Button btn_save;
    @FXML
    private Label form_title;

    @FXML
    private TextField login_fd;
    @FXML
    private PasswordField pwd_fd;
    @FXML
    private TextField nom_fd;
    @FXML
    private TextField prenom_fd;
    @FXML
    private TextField email_fd;
    @FXML
    private TextField telephone_fd;

    private User user;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String pwd;
    private String login;
    private Profile profil;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.SelectedUser != null) {
            try {
                user = (User) Utilis.SelectedUser.clone();
            } catch (CloneNotSupportedException ex) {
                Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                Logger.getLogger(UtilisateurUpdateController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (user != null) {
            if (user.getProfile().getId() == 1) {
                rd_admin.setSelected(true);
                rd_operator.setSelected(false);
            } else {
                rd_admin.setSelected(false);
                rd_operator.setSelected(true);
            }
            nom_fd.setText(user.getNom());
            prenom_fd.setText(user.getPrenom());
            telephone_fd.setText(user.getTelephone());
            email_fd.setText(user.getEmail());
            login_fd.setText(user.getUsername());
            profil = user.getProfile();

        }
    }

    private void initForm() {

        nom = nom_fd.getText();
        prenom = prenom_fd.getText();
        telephone = telephone_fd.getText();
        pwd = pwd_fd.getText();
        login = login_fd.getText();
        if (rd_admin.isSelected()) {
            profil.setId(1);
        } else {
            profil.setId(2);
        }
        email = email_fd.getText();
    }

    private boolean formIsOk() {
        return !nom.isEmpty() && !prenom.isEmpty() && !telephone.isEmpty() && !login.isEmpty();
    }

    private void creerForm() {
        user.setEmail(email);
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setProfile(profil);
        user.setTelephone(telephone);
        if (pwd != null && !pwd.isEmpty()) {
            user.setPassword(pwd);
        }
        user.setUsername(login);
    }

    @FXML
    private void buttonListener(ActionEvent event) {
        if (event.getSource() == btn_save) {
            initForm();
            if (formIsOk()) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText(Utilis.QuestionValideForm);
                Optional<ButtonType> resutl = confirm.showAndWait();
                if (resutl.get() == ButtonType.OK) {
                    Stage stage = (Stage) btn_save.getScene().getWindow();
                    creerForm();
                    if (Utilis.driver.updateUser(user)) {
                        Alert info = new Alert(Alert.AlertType.INFORMATION, Utilis.EnregistrementSuccee, ButtonType.OK);
                        Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                        info.show();
                        stage.close();
                    } else {
                        Alert errorForm = new Alert(Alert.AlertType.ERROR, Utilis.ErrorEnregistrementDB, ButtonType.CLOSE);
                        errorForm.show();
                    }
                }
            } else {
                Alert errorForm = new Alert(Alert.AlertType.ERROR, Utilis.ErrorForm, ButtonType.CLOSE);
                errorForm.show();
            }
        } else if (event.getSource() == btn_cancel) {
            Alert info = new Alert(Alert.AlertType.CONFIRMATION);
            info.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = info.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_cancel.getScene().getWindow();
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();
            } else {
                info.close();
            }
        }

    }

}
