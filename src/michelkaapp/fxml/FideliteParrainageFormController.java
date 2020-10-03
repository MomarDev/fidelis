/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Carte;
import michelkaapp.objets.Client;
import michelkaapp.objets.Parrainage;
import michelkaapp.utilis.SendMail;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class FideliteParrainageFormController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane form_prestation;
    @FXML
    private Label form_titre;
    @FXML
    private AnchorPane info_border;
    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_enregistrer;

    private Stage stage;
    @FXML
    private TextField dateField;
    @FXML
    private TextField nom_parraine;
    @FXML
    private TextField prenom_parraine;
    @FXML
    private TextField telephone_parraine;
    @FXML
    private Label nom_proprietaire_carte;
    @FXML
    private Label reference_carte;

    private Client clientProprietaireCarte = null;
    private Parrainage parrainage = null;

    private String telephone;
    private String nom;
    private String prenom;
    private String date;
    private Client client = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientProprietaireCarte = Utilis.clientActuel;
        if (clientProprietaireCarte != null) {
            nom_proprietaire_carte.setText(clientProprietaireCarte.getNom() + " " + clientProprietaireCarte.getPrenom());
            reference_carte.setText(clientProprietaireCarte.getCarte().toString());
            client = clientProprietaireCarte;
        }
        dateField.setText(Utilis.dateFrenchFormat(Utilis.NOW_LOCAL_DATE()));
        telephone_parraine.textProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                if (!Utilis.isNumber(nv)) {
                    telephone_parraine.setText("");
                }
            }
        });
    }

    private void initForm() {
        telephone = telephone_parraine.getText();
        nom = nom_parraine.getText();
        prenom = prenom_parraine.getText();
        date = dateField.getText();
    }

    private boolean formIsOk() {
        return !telephone.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !telephone.isEmpty();
    }

    private void crerForm() {
        parrainage = new Parrainage();
        parrainage.setTelephone(telephone);
        parrainage.setDate(date);
        parrainage.setReference_carte(clientProprietaireCarte.getCarte().getReference());
        parrainage.setPrenomParraine(prenom);
        parrainage.setNomParraine(nom);
    }

    @FXML
    private void handlerButton(ActionEvent event) {
        if (event.getSource() == btn_enregistrer) {
            if (client.getEmail().isEmpty()) {
                Alert errorAlert = new Alert(AlertType.ERROR, "Ce client ne dispose pas d'adresse Email", ButtonType.OK);
                errorAlert.show();
            } else {
                initForm();
                stage = (Stage) btn_enregistrer.getScene().getWindow();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText(Utilis.QuestionValideForm);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (formIsOk()) {
                        crerForm();
                        if (Utilis.driver.insertParrainage(parrainage)) {
                            Alert info = new Alert(Alert.AlertType.INFORMATION, Utilis.EnregistrementSuccee, ButtonType.OK);
                            info.show();
                            if (clientProprietaireCarte.isUtiliseCarte()) {
                                Carte carte = Utilis.driver.trouverCarte(clientProprietaireCarte);
                                Utilis.driver.pointParrainage(carte.getReference());
                                int max = Integer.parseInt(Utilis.trouverParametre("MAX_FIDELITE_PARRAINAGE").getValeur());
                                if (carte.getNtpsParrainage() + 1 >= max) {
                                    client = Utilis.driver.rechercheClientFormCarte(carte.getReference());
                                    if (client != null && !client.getEmail().isEmpty() && Utilis.isAEmailAdress(client.getEmail())) {
                                        info.setOnHidden((v) -> {
                                            Alert veuxMail = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous autoriser l'envoie de mail parrainage au client?");
                                            Optional<ButtonType> res = veuxMail.showAndWait();
                                            if (res.get() == ButtonType.OK) {
                                                sendMailTask();
                                            }
                                        });

                                    } else {
                                        Alert errorAlert = new Alert(AlertType.ERROR, "Le mail pour la fidélisation de parrainage n'est pas envoyé!", ButtonType.OK);
                                        errorAlert.show();
                                    }
                                }
                            }
                        } else {
                            Utilis.LOGGER.log(Utilis.ErrorEnregistrementDB);
                            Alert errorForm = new Alert(Alert.AlertType.ERROR, Utilis.ErrorEnregistrementDB, ButtonType.CLOSE);
                            errorForm.show();
                        }
                        Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                        stage.close();
                    } else {
                        Alert error = new Alert(Alert.AlertType.ERROR, Utilis.ErrorForm,
                                ButtonType.OK);
                        error.show();
                    }

                } else {
                    alert.close();
                }
            }
        }
        if (event.getSource() == btn_annuler) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage = (Stage) btn_annuler.getScene().getWindow();
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();
            } else {
                alert.close();
            }
        }

    }

    private String faireMsg(String text) {
        return text.replace("[nom]", clientProprietaireCarte.getNom() + " " + clientProprietaireCarte.getPrenom()).replace("[nbp]", Utilis.trouverParametre("MAX_FIDELITE_PARRAINAGE").getValeur());
    }

    private void sendMailTask() {
        Task longMailTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if (Utilis.isConnectedToInternet()) {
                    SendMail mail = new SendMail();
                    String bstMail = Utilis.trouverParametre("MAIL_BST").getValeur();
                    String micheleMail = Utilis.trouverParametre("EMAIL_MICHELE_KA").getValeur();
                    mail.setUsername(Utilis.DefaultEmail);
                    mail.setDestinataires(new String[]{client.getEmail(), bstMail, micheleMail});
                    mail.setCorps(faireMsg(Utilis.trouverParametre("TEXT_MAIL_PARRAINAGE").getValeur()));
                    mail.setFichierJoint(null);
                    mail.setHostname(Utilis.trouverParametre("HOSTNAME_SMTP").getValeur());
                    mail.setPassword(Utilis.DefaultEmailPassword);
                    mail.setMailServer(Utilis.trouverParametre("TYPE_SERVER_MAIL").getValeur());
                    mail.setSujet("Fidélité de parrainage");

                    mail.send();
                    if (mail.isSeeded()) {
                        return true;
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Voulez-vous vérifier la connection internet?").show();
                    return false;
                }
                if (isCancelled()) {
                    return false;
                }
                return true;
            }
        };
        new Thread(longMailTask)
                .start();
        longMailTask.setOnSucceeded((Event event) -> {
            Alert alertmail = new Alert(AlertType.INFORMATION, "Un email informant le client de la bonus parrainage a été envoyé", ButtonType.OK);
            alertmail.show();
        });
    }
}
