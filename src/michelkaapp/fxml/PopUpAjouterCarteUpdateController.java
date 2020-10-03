/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.MichelKaApp;
import michelkaapp.objets.Carte;
import michelkaapp.objets.Client;
import michelkaapp.objets.Profession;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class PopUpAjouterCarteUpdateController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private Label titre_popup;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField portable1Field;
    @FXML
    private TextField portable2Field;
    @FXML
    private TextField telBureauField;
    @FXML
    private TextField telDomicileField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField adresseField;
    @FXML
    private ComboBox<Profession> professionComboBox;
    @FXML
    private TextField professionField;
    @FXML
    private TextField carteFideliteField;
    @FXML
    private ComboBox<String> listJourComboBox;
    @FXML
    private ComboBox<String> listMoisComboBox;
    @FXML
    private Label label_obligatoire;
    @FXML
    private Label prenomObligartoire;
    @FXML
    private Label telObligatoiire;
    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Button btn_valider_prestation;

    private ObservableList<String> listJour = FXCollections.observableArrayList(
            "",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    );

    private ObservableList<String> listMois = FXCollections.observableArrayList("",
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre",
            "Octobre", "Novembre", "Décembre"
    );
    private ObservableList<Profession> listProfession = null;
    private Profession profession = null;
    private String adresse = null;
    private String email = null;
    private String tel_domicile = null;
    private String tel_bureau = null;
    private String tel_mobile_2 = null;
    private String tel_mobile_1 = null;
    private String nom = null;
    private String prenom = null;
    private int jour = 0;
    private String mois = "";
    private Client client = null;
    private Carte carte = null;

    private String precise_profession = null;
    private boolean a_donner_precision = false;
    private boolean est_proprietaire = false;
    private String reference_carte = null;
    private int nombreEnfant = 0;
    private int couple = 1;
    @FXML
    private CheckBox enCouple;
    @FXML
    private TextField nombre_enfants;
    private int ancienCarte = 0;
    private String msgError = "";
    private boolean init = false;
    @FXML
    private CheckBox technique;
    private boolean isUpdate = false;

    private List<Profession> pfiltres = null;
    private List<Profession> listP = null;
    private String textKey = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.clientActuel != null) {
            try {
                isUpdate = FicheTechniqueController.ISFROMTECHNIAUE == true;
                FicheTechniqueController.ISFROMTECHNIAUE = false;
                initializeComboBoxesActions();
                client = (Client) Utilis.clientActuel.clone();
                profession = Utilis.driver.selectProfession(client.getProfession().getId());
                if (profession != null) {
                    client.setProfession(profession);
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PopUpAjouterCarteController.class.getName()).log(Level.SEVERE, null, ex);
            }
            prepareClientUpdate();
        }
        carteFideliteField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                if (!Utilis.isNumber(newValue)) {
                    carteFideliteField.setStyle("-fx-background-color:red");
                } else {
                    carteFideliteField.setStyle(null);
                    if (client.getCarte().getReference() != Integer.parseInt(newValue) && Integer.parseInt(newValue) != 0) {
                        System.out.println(newValue);
                    }
                }
            }
            if (newValue.isEmpty()) {
                carteFideliteField.setStyle(null);
            }
        });
        telBureauField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                if (!Utilis.isNumber(newValue)) {
                    telBureauField.setStyle("-fx-background-color:red");
                } else {
                    telBureauField.setStyle(null);
                }
            }
            if (newValue.isEmpty()) {
                telBureauField.setStyle(null);
            }
        });
        telDomicileField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                if (!Utilis.isNumber(newValue)) {
                    telDomicileField.setStyle("-fx-background-color:red");
                } else {
                    telDomicileField.setStyle(null);
                }
            }
            if (newValue.isEmpty()) {
                telDomicileField.setStyle(null);
            }
        });
        portable1Field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                if (!Utilis.isNumber(newValue)) {
                    portable1Field.setStyle("-fx-background-color:red");
                } else {
                    portable1Field.setStyle(null);
                }

            }
            if (newValue.isEmpty()) {
                portable1Field.setStyle(null);
            }
        });
        portable2Field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                if (!Utilis.isNumber(newValue)) {
                    portable2Field.setStyle("-fx-background-color:red");
                } else {
                    portable2Field.setStyle(null);
                }
            }
            if (newValue.isEmpty()) {
                portable2Field.setStyle(null);
            }
        });
        emailField.textProperty().addListener((obs, old, nv) -> {
            if (nv != null && !nv.isEmpty()) {
                if (!Utilis.isAEmailAdress(nv)) {
                    emailField.setStyle("-fx-background-color:red");
                } else {
                    emailField.setStyle(null);
                }
            }
            if (nv.isEmpty()) {
                emailField.setStyle(null);
            }
        });
        nombre_enfants.textProperty().addListener((obs, old, nv) -> {
            if (nv != null && !nv.isEmpty() && Utilis.isNumber(nv)) {
                nombreEnfant = Integer.parseInt(nv);
            } else {
                nombre_enfants.setText("");
            }
        });
        initializeComboBoxesActions();
        init = true;
        professionComboBox.setOnKeyTyped(event -> {
            textKey += event.getCharacter();
            pfiltres = listFiltreProfession(textKey);
            listProfession = FXCollections.observableArrayList(pfiltres);
            professionComboBox.setItems(listProfession);
            professionComboBox.getSelectionModel().selectFirst();

            if (pfiltres == null || pfiltres.isEmpty()) {
                listProfession = FXCollections.observableArrayList(listP);
                professionComboBox.setItems(listProfession);
                professionComboBox.setValue(null);
                textKey = "";
            }
        });
        professionComboBox.setOnMouseClicked(event -> {
            listProfession = FXCollections.observableArrayList(listP);
            professionComboBox.setItems(listProfession);
            professionComboBox.setValue(profession);
        });
    }

    @FXML
    private void buttonCliked(ActionEvent event) throws IOException {
        if (event.getSource() == btn_annuler) {
            if (isModified()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Etes vous sûr de quitter sans enregistrer les modifications saisies?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Stage stage = (Stage) btn_annuler.getScene().getWindow();
                    Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                    stage.close();
                }
            } else {
                Stage stage = (Stage) btn_annuler.getScene().getWindow();
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();
            }
        }
        if (event.getSource() == btn_enregistrer) {
            enregister(false, (Stage) btn_enregistrer.getScene().getWindow());
        }
        if (event.getSource() == btn_valider_prestation) {
            enregister(true, (Stage) btn_enregistrer.getScene().getWindow());
        }
    }

    private boolean isModified() {
        if (true) {
            return true;
        }
        return (!nomField.getText().isEmpty() && !prenomField.getText().isEmpty()
                && !carteFideliteField.getText().isEmpty())
                && (jour > 0 && mois != null && !mois.isEmpty() && !reference_carte.isEmpty());
    }

    private void initializeComboBoxesActions() {
        listMoisComboBox.getItems().addAll(listMois);
        listJourComboBox.getItems().addAll(listJour);
        if (MichelKaApp.connexionIsOK) {
            listP = Utilis.driver.listProfession();
            if (listP != null) {
                ObservableList<Profession> listProfessions = FXCollections.observableArrayList(listP);
                professionComboBox.setItems(listProfessions);
            }
        }

        listJourComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            try {
                jour = Integer.parseInt(newValue);
            } catch (Exception e) {
                Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
                jour = 0;
            }
            if (jour >= 30) {
                mois = listMoisComboBox.getValue();
                if (mois.equals("Février")) {
                    listJourComboBox.setValue("29");
                }
            }
            if (jour == 31) {
                mois = listMoisComboBox.getValue();
                if (mois.equals("Avril") || mois.equals("Juin") || mois.equals("Septembre") || mois.equals("Novembre")) {
                    listMoisComboBox.setValue("Mai");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Veuillez reverifier la date s'il vous plait");
                    Optional<ButtonType> resulat = alert.showAndWait();
                    if (resulat.get() == ButtonType.OK) {
                        alert.close();
                    }
                }
            }
        });

        listMoisComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            mois = newValue;
            if (newValue.equals("Février") && jour >= 30) {
                listJourComboBox.setValue("29");
            }
            if ((newValue.equals("Avril") || newValue.equals("Juin") || newValue.equals("Septembre") || newValue.equals("Novembre"))
                    && jour == 31) {
                listJourComboBox.setValue("30");
            }
        });

        professionComboBox.getItems().addAll(new Profession(0, "Autre"));

        professionComboBox.valueProperty().addListener((observableList, oldValue, newValue) -> {
            if (newValue != null && init) {
                if (newValue.toString().equals("Autre")) {
                    professionField.setDisable(false);
                    professionField.setPromptText("Veuillez précisez");
                    a_donner_precision = true;

                } else {
                    a_donner_precision = false;
                    professionField.setDisable(true);
                    professionField.setPromptText("");
                }
            }
            profession = newValue;
        });
    }

    private void initForm() {
        nom = nomField.getText();
        prenom = prenomField.getText();
        adresse = adresseField.getText();
        email = emailField.getText();
        tel_domicile = telDomicileField.getText();
        tel_bureau = telBureauField.getText();
        tel_mobile_2 = portable2Field.getText();
        tel_mobile_1 = portable1Field.getText();
        reference_carte = carteFideliteField.getText();
        carte = new Carte();
        try {
            carte.setReference(Integer.parseInt(reference_carte));
        } catch (Exception e) {
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
            Alert errorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber + "Carte de reference obligatoire", ButtonType.CANCEL);
            errorNumber.show();
        }
        if (a_donner_precision) {
            precise_profession = professionField.getText();
            if (precise_profession != null && !precise_profession.isEmpty()) {
                profession = new Profession(0, precise_profession);
            }
        } else {
            precise_profession = null;
        }
        est_proprietaire = client.isEst_proprietaire_carte();
    }

    private boolean formIsOk() {
        return (!nom.isEmpty() && !prenom.isEmpty()) && (jour > 0 && mois != null && !mois.isEmpty() && !reference_carte.isEmpty());
    }

    private void creerClient() {
        if (enCouple.isSelected()) {
            couple = 2;
        }
        if (carte != null) {
            carte.setFamilleNombre(nombreEnfant + couple);
            carte.setCouple(enCouple.isSelected());
            nombreEnfant = carte.getFamilleNombre() - couple;
        }
        client.setAdresse(adresse);
        client.setAnniversaire(mois + "-" + jour);
        client.setTelBureau(tel_bureau);
        client.setEmail(email);
        client.setMobile1(tel_mobile_1);
        client.setMobile2(tel_mobile_2);
        client.setEst_proprietaire_carte(est_proprietaire);
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setTelBureau(tel_bureau);
        client.setUtiliseCarte(true);
        client.setCarte(carte);
        client.setProfession(profession);
        client.setTypeCheveux(null);
        client.setTextureCheveux(null);
        client.setCuirChevelu(null);
        client.setDensiteCheveux(null);
        client.setEtatCheveux(null);
        client.setTelDomicile(tel_domicile);
        nombreEnfant = Integer.parseInt(nombre_enfants.getText());
        client.setEstTechnique(technique.isSelected());
    }

    private boolean insertAvecNouvelleProfession() {
        boolean teste = false;
        String ErrorForm = "";
        if (Utilis.driver.insertProfession(profession)) {
            List<Profession> lp = Utilis.driver.listProfession();
            if (lp != null) {
                int index = lp.size();
                profession = lp.get(index - 1);
                client.setProfession(profession);
                teste = true;
            } else {
                teste = false;
            }
        } else {
            teste = false;
        }
        return teste;
    }

    private boolean insertAvecCarte() {
        carte = Utilis.driver.trouverCarte(client.getCarte().getReference());
        System.out.println(client.getCarte().getReference());
        boolean teste = true;
        if (carte == null) {
            msgError = "La carte n'existe pas!";
            teste = false;
        }
       
        if (carte != null) {
            System.out.println("etat de carte:" + carte.getEtatCarte());

            switch (carte.getEtatCarte()) {
                case 0:
                    carte.setCouple(enCouple.isSelected());
                    carte.setFamilleNombre(couple + nombreEnfant);
                    client.setUtiliseCarte(true);
                    est_proprietaire = true;
                    client.setEst_proprietaire_carte(est_proprietaire);
                    carte.setEtatCarte(1);
                    carte.setNombreUtilisateurCarte(1);
                    if (isUpdate) {
                        client.setEst_proprietaire_carte(true);
                    }
                    if (client.isEst_proprietaire_carte()) {
                        teste = Utilis.driver.attribuerCarte(carte);
                    }
                    break;
                case 1:
                    if (!client.isEst_proprietaire_carte()) {
                        est_proprietaire = false;
                        Alert info = new Alert(Alert.AlertType.CONFIRMATION, "La carte a déjâ été attribuer. Voulez-vous ajouter le client aux utilisateurs de carte ?");
                        Optional<ButtonType> result = info.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            System.out.println("Nombre membre de la famille est -->" + carte.getFamilleNombre());
                            if (carte.getNombreUtilisateurCarte() < carte.getFamilleNombre()) {
                                client.setCarte(carte);
                                client.setEst_proprietaire_carte(est_proprietaire);
                                client.setUtiliseCarte(true);
                                teste = Utilis.driver.updateCarte(carte.getReference(), "nombre_utilisateur_carte", carte.getNombreUtilisateurCarte() + 1);
                            } else {
                                msgError = "La carte a atteint le nombre d'utilisateur prédéfinie.";
                                return false;
                            }
                            if (!teste) {
                                msgError = Utilis.ErrorEnregistrementDB;
                            }
                        } else {
                            teste = false;
                            msgError = "Vous n'avez pas autorisé l'enregistrement du client au niveau de carte: " + carte.getReference();
                        }
                    }
                    break;
               
            }
        
        }
      /*  else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Une erreur inattendu est survenu lors de votre derniere manupulation...");
            alert.show();
        }*/
        return teste;
    }

    private void prepareClientUpdate() {
        nomField.setText(client.getNom());
        prenomField.setText(client.getPrenom());
        adresseField.setText(client.getAdresse());
        emailField.setText(client.getEmail());
        telDomicileField.setText(client.getTelDomicile());
        telBureauField.setText(client.getTelBureau());
        portable2Field.setText(client.getMobile2());
        portable1Field.setText(client.getMobile1());
        carteFideliteField.setText(client.getCarte().toString());
        String date[] = client.getAnniversaire().split("-");
        listJourComboBox.setValue(date[1]);
        listMoisComboBox.setValue(date[0]);
        professionComboBox.setValue(client.getProfession());
        System.out.println(client.getProfession());
        if (client.getCarte() != null && client.getCarte().getReference() != 0) {
            client.setCarte(Utilis.driver.trouverCarte(client));
            carte = client.getCarte();
            if (!client.isEst_proprietaire_carte()) {
                nombre_enfants.setDisable(true);
                enCouple.setDisable(true);
            }
            enCouple.setSelected(carte.isCouple());
            if (carte.isCouple()) {
                nombre_enfants.setText(carte.getFamilleNombre() - 2 + "");
            } else {
                nombre_enfants.setText(carte.getFamilleNombre() - 1 + "");
            }
            ancienCarte = client.getCarte().getReference();
        }
        est_proprietaire = client.isEst_proprietaire_carte();
        technique.setSelected(client.isEstTechnique());
    }

//     Pour changer de carte
    private boolean changerCarte(Carte nvCarte) {
        if (nvCarte.getReference() != ancienCarte && ancienCarte > 0) {
            Carte cv = Utilis.driver.trouverCarte(nvCarte.getReference());
            if (cv == null || cv.getEtatCarte() >= 2) {
                Alert erc = new Alert(Alert.AlertType.ERROR, "La carte n'existe pas ou elle est bloquée.");
                erc.show();
                return false;
            }
            Carte anCarte = Utilis.driver.trouverCarte(ancienCarte);
            if (anCarte != null) {
                Alert info = new Alert(Alert.AlertType.CONFIRMATION);
                info.setContentText("Etes vous sure de vouloir faire changer la carte fidelité: " + ancienCarte + " pour: " + nvCarte.getReference());
                Optional<ButtonType> result = info.showAndWait();
                if (result.get() == ButtonType.OK) {
                    anCarte.setReference(nvCarte.getReference());
                    anCarte.setEtatCarte(1);
                    client.setCarte(anCarte);
                    if (Utilis.driver.attribuerCarte(anCarte) && Utilis.driver.clonerCarte(anCarte) && Utilis.driver.updateCarte(ancienCarte, "etat_carte", 2) && Utilis.driver.tousChangerCarte(anCarte.getReference(), ancienCarte)) {
                        System.out.println(ancienCarte);
                        Alert changeCarteAlert = new Alert(Alert.AlertType.INFORMATION, "Le client a changer de carte avec succée.", ButtonType.FINISH);
                        changeCarteAlert.show();
                    }
                } else {
                    carte.setReference(ancienCarte);
                    client.setCarte(carte);
                }
            }
        }
        return true;
    }

    private void enregister(boolean avecPrestation, Stage stg) {
        initForm();
        boolean teste = true;
        if (formIsOk() && isModified()) {
            if (email != null && !email.isEmpty() && !Utilis.isAEmailAdress(email)) {
                Alert errorMail = new Alert(Alert.AlertType.ERROR, Utilis.MAIL_ERROR, ButtonType.CANCEL);
                errorMail.show();
                return;
            } else {
                Alert con = new Alert(Alert.AlertType.CONFIRMATION, Utilis.DialogConfirmationCarteAdd);
                Optional<ButtonType> rs = con.showAndWait();
                if (rs.get() == ButtonType.OK) {
                    creerClient();
                    if (a_donner_precision) {
                        teste = insertAvecNouvelleProfession();
                    }
                    if (teste) {
                        teste = insertAvecCarte();
                    }
                    if (!isUpdate) {
                        if (carte != null) {
                            carte.setCouple(enCouple.isSelected());
                            carte.setFamilleNombre(couple + nombreEnfant);
                        }
                    }
                    if (!teste) {
                        Alert errmsg = new Alert(Alert.AlertType.ERROR, msgError);
                        errmsg.show();
                        return;
                    }
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    teste = (Utilis.driver.updateCarteFamilleCouple(carte) && changerCarte(carte) && Utilis.driver.updateClient(client));
                    if (teste) {
                        info.setContentText(Utilis.EnregistrementSuccee);
                        info.show();
                        info.setOnHidden((v) -> {
                            if (avecPrestation) {
                                Utilis.clientActuel = client;
                                Stage stagePrestation = new Stage();
                                try {
                                    Parent prestationRoot = FXMLLoader.load(getClass().getResource("PopPupPrestationFormFromFiche.fxml"));
                                    stagePrestation.setScene(new Scene(prestationRoot));
                                    stagePrestation.setResizable(false);
                                    stagePrestation.show();
                                    stg.close();

                                } catch (IOException ex) {
                                    Logger.getLogger(PopUpAjouterCarteController.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                stg.close();
                            }
                        });
                    }
                }
            }
        } else {
            System.out.println("not ok");
        }
    }

    private List<Profession> listFiltreProfession(String key) {
        pfiltres = new ArrayList<>();
        listP.forEach(tv -> {
            if (tv.getProfession().substring(0, key.length()).toLowerCase().contains(key.toLowerCase())) {
                pfiltres.add(tv);
            }
        });
        return pfiltres;
    }
}
