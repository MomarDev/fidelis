/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import michelkaapp.MichelKaApp;
import michelkaapp.objets.Profile;
import michelkaapp.objets.User;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class UtilisateursController implements Initializable {

    @FXML
    private SplitPane pane_center;
    @FXML
    private AnchorPane pane_gauche;
    @FXML
    private Button btn_fiche;
    @FXML
    private Button btn_carte;
    @FXML
    private Button btn_service;
    @FXML
    private Button btn_produit;
    @FXML
    private Button btn_users;
    @FXML
    private Button btn_setting;
    @FXML
    private Button btn_traces;
    @FXML
    private AnchorPane pane_central;
    @FXML
    private Label titre_menu;
    @FXML
    private Tab pane_user;
    @FXML
    private AnchorPane pane_border;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private ToggleGroup rolesToggleGroupe;
    @FXML
    private Label result_carte;

    private Stage stage;
    private Parent root;
    @FXML
    private Tab pane_add_user;
    @FXML
    private Button btn_enregistrer_user;
    @FXML
    private Button btn_annuler_user;
    @FXML
    private Button btn_payement;
    @FXML
    private TextField fld_email;
    @FXML
    private RadioButton rdBtn_admin;
    @FXML
    private RadioButton rdBtn_operator;
    @FXML
    private TextField fld_telephone;
    @FXML
    private TableView<User> table_view_user;
    @FXML
    private TableColumn<User, String> nom_column;
    @FXML
    private TableColumn<User, String> prenom_column;
    @FXML
    private TableColumn<User, String> login_column;
    @FXML
    private TableColumn<User, String> email_column;
    @FXML
    private TableColumn<User, Profile> profil_column;
    @FXML
    private TableColumn<User, String> password_column;
    @FXML
    private TableColumn<User, String> telephone_column;

    private String nom = "";
    private String prenom = "";
    private String login = "";
    private String password = "";
    private String telephone = "";
    private Profile profile = null;
    private String email = "";
    private ObservableList<User> listUser = null;

    private User currentUser = null;
    private static int click = 0;
    @FXML
    private HBox admin_bar;
    @FXML
    private Button refresh;
    @FXML
    private Button logout;
    List<User> list;
    @FXML
    private TabPane tabpane;
    @FXML
    private PasswordField pwd_r;
    @FXML
    private Button btn_delete_usr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        profile = new Profile();
        if (Utilis.IsAdmin()) {
            admin_bar.setVisible(true);
        }
        logout.setOnMouseClicked(event -> {
            Alert alertLogout = new Alert(Alert.AlertType.CONFIRMATION);
            alertLogout.setContentText("Etes vous sure de vouloir vous déconnecter?");
            Optional<ButtonType> result = alertLogout.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Logout: " + Utilis.LOGOUT.logout(((Stage) logout.getScene().getWindow())));
            }
        });
        
        if(Utilis.entrainPayer) {
        	btn_payement.setDisable(true);
        }
        btn_payement.setOnMouseClicked(event -> {
            
        	 try {
        		 
				afficheFenetresSecondaire("Payement.fxml");
				btn_payement.setDisable(true);
				Utilis.entrainPayer=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        });
        
        profile.setId(1);
        if (MichelKaApp.connexionIsOK) {
            tableInit();
        }
        btn_delete_usr.setOnMouseClicked(event -> {
            if (Utilis.SelectedUser != null && Utilis.SelectedUser.getId() != Utilis.user.getId()) {
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setContentText("Opération irréversible: êtes vous sure de vouloir d'appliquer?");
                Optional<ButtonType> result = conf.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (Utilis.driver.deleteUser(Utilis.SelectedUser)) {
                        tableInit();
                        Alert AUser = new Alert(Alert.AlertType.INFORMATION, "L'utilisateur " + Utilis.SelectedUser.getUsername() + " est supprimé.", ButtonType.OK);
                        AUser.show();
                    }
                }
            } else {
                Alert AUser = new Alert(Alert.AlertType.INFORMATION, "L'utilisateur courant " + Utilis.SelectedUser.getUsername() + " ne peut pas actuellement être supprimer.", ButtonType.OK);
                AUser.show();
            }
        });
    }

    @FXML
    private void buttonListener(ActionEvent event) throws IOException {

        if (event.getSource() == btn_traces) {
            stage = (Stage) btn_traces.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Traces.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        if (event.getSource() == btn_setting) {
            stage = (Stage) btn_setting.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Parametres.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        if (event.getSource() == btn_service) {
            root = FXMLLoader.load(getClass().getResource("StockService.fxml"));
            stage = (Stage) btn_service.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (event.getSource() == btn_produit) {
            root = FXMLLoader.load(getClass().getResource("StockProduit.fxml"));
            stage = (Stage) btn_produit.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (event.getSource() == btn_carte) {
            root = FXMLLoader.load(getClass().getResource("Cartes.fxml"));
            stage = (Stage) btn_carte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (event.getSource() == btn_fiche) {
            root = FXMLLoader.load(getClass().getResource("FicheTechnique.fxml"));
            stage = (Stage) btn_fiche.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (event.getSource() == btn_enregistrer_user) {
            miseEnform();
            if (!Utilis.isAEmailAdress(email)) {
                Alert errorMail = new Alert(Alert.AlertType.ERROR, Utilis.MAIL_ERROR, ButtonType.CANCEL);
                errorMail.show();
                return;
            }
            if (formIsOkk() && passwordTeste()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Etes vous sÃ»r de vouloir ajouter un nouvel utilisateur?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    User user = new User();
                    user.setNom(nom);
                    user.setPassword(password);
                    user.setPrenom(prenom);
                    user.setEmail(email);
                    user.setTelephone(telephone);
                    user.setProfile(profile);
                    user.setUsername(login);
                    if (Utilis.driver.insertUser(user)) {
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setContentText("L'utilisateur Ã  été  ajouté avec succés");
                        info.show();
                        videForm();
                        if (refresh.isDisabled()) {
                            refresh.setDisable(false);
                        } else {
                            refresh.setDisable(true);
                        }
                        tableInit();
                        tabpane.getSelectionModel().select(pane_user);
                    } else {
                        Alert errSQl = new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de l'enregistrement de l'utilisateur", ButtonType.OK);
                        errSQl.show();
                    }

                }
            }
            else{
            Alert errSQl = new Alert(Alert.AlertType.ERROR, "Veuillez renseigner correctement les attributs de l'utilisateur", ButtonType.OK);
                        errSQl.show();
            }

        } else if (event.getSource() == rdBtn_admin) {
            profile = new Profile();
            profile.setId(1);
        } else if (event.getSource() == rdBtn_operator) {
            profile = new Profile();
            profile.setId(2);
        } else if (event.getSource() == refresh) {
            tableInit();
            refresh.setDisable(true);
        }

    }

    private boolean formIsOkk() {
        return !email.isEmpty()
                && !password.isEmpty() && !telephone.isEmpty() && profile != null && !login.isEmpty() && !nom.isEmpty() && !prenom.isEmpty();
    }

    private void miseEnform() {
        login = usernameTextField.getText();
        nom = nameTextField.getText();
        prenom = lastnameTextField.getText();
        email = fld_email.getText();
        telephone = fld_telephone.getText();
        password = passwordTextField.getText();
        if (rdBtn_admin.isSelected()) {
            profile = new Profile();
            profile.setId(1);
        } else if (rdBtn_operator.isSelected()) {
            profile = new Profile();
            profile.setId(2);
        }
    }

    private void afficheFenetresSecondaire(String fxmlFile) throws IOException {
        if (Utilis.Max_DEFAULT < Utilis.DEFINED_FENETRE) {
            Utilis.Max_DEFAULT = Utilis.Max_DEFAULT + 1;
            Stage smoleFenetre = new Stage();
            smoleFenetre.setResizable(false);
            if(smoleFenetre.getOnCloseRequest()!=null) {
            	Utilis.entrainPayer=false;
            	
            }
            Parent smoleRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
            smoleFenetre.setScene(new Scene(smoleRoot));
            smoleFenetre.setOnCloseRequest(event -> {
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                Utilis.entrainPayer=false;
                btn_payement.setDisable(false);
               
            });
            smoleFenetre.setOnHidden((o) -> {
                --Utilis.Max_DEFAULT;
               
               
            });
            smoleFenetre.show();
        } else {
            Utilis.LOGGER.log(Utilis.errorFenetres);
            Alert errorFenetre = new Alert(Alert.AlertType.ERROR, Utilis.errorFenetres, ButtonType.OK);
            errorFenetre.show();
        }
    }

    public void tableInit() {
        list = Utilis.driver.listUser();
        listUser = FXCollections.observableArrayList(list);
        table_view_user.setItems(listUser);
        table_view_user.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Utilis.SelectedUser = row.getItem();
                    try {
                        afficheFenetresSecondaire("UtilisateurUpdate.fxml");
                    } catch (IOException ex) {
                        Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                        Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });
        nom_column.setCellValueFactory(new PropertyValueFactory<>("nom"));
        login_column.setCellValueFactory(new PropertyValueFactory<>("username"));
        email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        profil_column.setCellValueFactory(new PropertyValueFactory<>("profile"));
        password_column.setCellValueFactory(new PropertyValueFactory<>("password"));
        telephone_column.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        prenom_column.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        table_view_user.getSelectionModel().selectedItemProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                Utilis.SelectedUser = currentUser = nv;
                btn_delete_usr.setDisable(false);
            } else {
                btn_delete_usr.setDisable(true);
            }
            System.out.print(click);
        });
    }

    private void videForm() {
        nom = "";
        prenom = "";
        login = "";
        password = "";
        telephone = "";
        profile = null;
        email = "";
        usernameTextField.setText("");
        nameTextField.setText("");
        lastnameTextField.setText("");
        fld_email.setText("");
        fld_telephone.setText("");
        passwordTextField.setText("");
        pwd_r.setText("");
    }

    private boolean passwordTeste() {
        password = passwordTextField.getText();
        String sc_pwd = pwd_r.getText();

        if (!sc_pwd.isEmpty() && password.toLowerCase().equals(sc_pwd)) {
            return true;
        }
        Alert pwdAlert = new Alert(Alert.AlertType.ERROR, "Veuillez indiquer les mÃªmes mots de passes.", ButtonType.CLOSE);
        pwdAlert.show();
        return false;
    }
}
