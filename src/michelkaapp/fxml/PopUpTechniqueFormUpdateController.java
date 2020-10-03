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
import javafx.scene.control.Alert.AlertType;
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
import michelkaapp.objets.CuirChevelu;
import michelkaapp.objets.Densite;
import michelkaapp.objets.EtatCheveux;
import michelkaapp.objets.Profession;
import michelkaapp.objets.TextureCheveux;
import michelkaapp.objets.TypeCheveux;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class PopUpTechniqueFormUpdateController implements Initializable {

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
    private TextField professionField;
    @FXML
    private ComboBox<Profession> professionComboBox;
    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private ComboBox<String> carteFideliteComboBox;
    @FXML
    private ComboBox<CuirChevelu> cuirCheveluComboBox;
    @FXML
    private ComboBox<TypeCheveux> typeCheveuxComboBox;
    @FXML
    private ComboBox<Densite> densiteCheveuxComboBox;
    @FXML
    private ComboBox<EtatCheveux> etatCheveuxComboBox;
    @FXML
    private ComboBox<TextureCheveux> textureCheveuxCombox;
    @FXML
    private Button btn_valider_prestation;
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

    private Stage stage;
    private Parent root;
    private ObservableList<Profession> listProfession = null;
    private ObservableList<Densite> listDensite = null;
    private ObservableList<CuirChevelu> listeCuirChevelu = null;
    private ObservableList<TypeCheveux> listTypeCheveux = null;
    private ObservableList<EtatCheveux> listEtatCheveux = null;
    private ObservableList<TextureCheveux> listTextureCheveux = null;
    private ObservableList<String> listJour = FXCollections.observableArrayList(
            "",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    );

    private ObservableList<String> listMois = FXCollections.observableArrayList("",
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre",
            "Octobre", "Novembre", "Décembre"
    );

    private CuirChevelu cuirChevelu = null;
    private Densite densite = null;
    private TypeCheveux typeCheveux = null;
    private TextureCheveux textureCheveux = null;
    private EtatCheveux etatCheveux = null;
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
    private String mois = null;
    private Client client = null;
    private Carte carte = null;

    private String reference_carte = null;
    private String precise_profession = null;
    private boolean avoir_carte = false;
    private boolean a_donner_precision = false;
    private boolean est_proprietaire = false;
    private boolean professionIsOK = false;
    private boolean isUpdateClient = false;
    private int nombre_enfant = 0;
    private int ancienCarte = 0;
    @FXML
    private CheckBox Encouple;
    @FXML
    private TextField nombreEnfant;
    private boolean init = false;
    private int couple = 0;
    private List<Profession> pfiltres = null;
    private List<Profession> listP = null;
    private String textKey = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isUpdateClient = (Utilis.UPDATE_CLIENT_TECHNIQUE == true);
        Utilis.UPDATE_CLIENT_TECHNIQUE = false;
        if (Utilis.clientActuel != null) {
            isUpdateClient = true;
        }
        carteFideliteField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                if (!Utilis.isNumber(newValue)) {
                    carteFideliteField.setStyle("-fx-background-color: #e8b496");
                } else {
                    carteFideliteField.setStyle(null);
                }
            }
            if (newValue.isEmpty()) {
                carteFideliteField.setStyle(null);
            }
        });
        telBureauField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                if (!Utilis.isNumber(newValue)) {
                    telBureauField.setStyle("-fx-background-color: #e8b496");
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
                    telDomicileField.setStyle("-fx-background-color: #e8b496");
                } else {
                    telDomicileField.setStyle(null);
                }
            }
            if (newValue == null || newValue.isEmpty()) {
                telDomicileField.setStyle(null);
            }
        });
        portable1Field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                if (!Utilis.isNumber(newValue)) {
                    portable1Field.setStyle("-fx-background-color: #e8b496");
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
                    portable2Field.setStyle("-fx-background-color: #e8b496");
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
                    emailField.setStyle("-fx-background-color: #e8b496");
                } else {
                    emailField.setStyle(null);
                }
            }
            if (nv.isEmpty()) {
                emailField.setStyle(null);
            }
        });
        nombreEnfant.textProperty().addListener((obs, old, nv) -> {
            if (nv != null && !nv.isEmpty() && Utilis.isNumber(nv)) {
                nombre_enfant = Integer.parseInt(nv);
            }
        });

        initializeComboBoxesActions();
        init = true;
        if (isUpdateClient) {
            try {
                client = (Client) Utilis.clientActuel.clone();
                if (client != null) {
                    PrepareFormulaireUpdate(client);
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PopUpTechniqueFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Etes vous sûr de quitter sans enregistrer les modifications saisies?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage = (Stage) btn_annuler.getScene().getWindow();
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();
            } else {
                alert.close();
            }
        }
        if (event.getSource() == btn_enregistrer) {
            initForm();
            if (email != null && !email.isEmpty() && !Utilis.isAEmailAdress(email)) {
                Alert errorMail = new Alert(Alert.AlertType.ERROR, Utilis.MAIL_ERROR, ButtonType.CANCEL);
                errorMail.show();
                return;
            }
            if (formIsOk()) {
                if ((avoir_carte && reference_carte == null)) {
                    Alert ErrorForm = new Alert(AlertType.ERROR, Utilis.ErrorForm, ButtonType.OK);
                    ErrorForm.show();
                } else {
                    creerClient();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText(Utilis.DialogConfirmationAjoutFicheTechnique);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        boolean teste = true;
                        if (a_donner_precision) {
                            teste = insertAvecNouvelleProfession();
                        } else if (profession == null) {
                            teste = true;
                        }
                        if (avoir_carte) {
                            teste = insertAvecCarte();
                        }
                        if (teste) {
                            client.setCarte(carte);
                            teste = Utilis.driver.updateClient(client);
                           
                        }
                        if (teste) {
                            Alert info = new Alert(Alert.AlertType.INFORMATION,
                                    Utilis.EnregistrementSuccee, ButtonType.CLOSE);
                            info.show();
                            stage = (Stage) btn_enregistrer.getScene().getWindow();
                            Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                            Utilis.CLIENT_IS_UPDATE = true;
                            stage.close();
                        } else {
                            Alert error = new Alert(AlertType.ERROR, Utilis.ErrorEnregistrementDB, ButtonType.CLOSE);
                            error.show();
                        }
                    }
                }

            } else {
                Alert ErrorForm = new Alert(AlertType.ERROR, Utilis.ErrorForm, ButtonType.OK);
                ErrorForm.show();
            }
        }
        if (event.getSource() == btn_valider_prestation) {
            initForm();
            if (email != null && !email.isEmpty() && !Utilis.isAEmailAdress(email)) {
                Alert errorMail = new Alert(Alert.AlertType.ERROR, Utilis.MAIL_ERROR, ButtonType.CANCEL);
                errorMail.show();
                return;
            }
            if (formIsOk()) {
                if ((avoir_carte && reference_carte == null)) {
                    Alert ErrorForm = new Alert(AlertType.ERROR, Utilis.ErrorForm, ButtonType.OK);
                    ErrorForm.show();
                } else {
                    creerClient();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText(Utilis.DialogConfirmationAjoutFicheTechnique);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        boolean teste = true;
                        if (a_donner_precision) {
                            teste = insertAvecNouvelleProfession();
                        }
                        if (avoir_carte) {
                            teste = insertAvecCarte();
                        }
                        if (teste) {
                            client.setCarte(carte);
                            teste = Utilis.driver.updateClient(client);
                        }
                        if (teste) {
                            Alert info = new Alert(Alert.AlertType.INFORMATION,
                                    Utilis.EnregistrementSuccee, ButtonType.OK);
                            Optional<ButtonType> res = info.showAndWait();
                            if (res.get() == ButtonType.OK) {
                                stage = (Stage) btn_enregistrer.getScene().getWindow();
                                Stage prestation = new Stage();
                                prestation.setResizable(false);
                                if (isUpdateClient) {
                                    Utilis.clientActuel = client = Utilis.driver.selectClientById(client.getId());
                                } else {
                                    Utilis.clientActuel = client = Utilis.driver.lastInsertClient();
                                }
                                Parent prestationRoot = FXMLLoader.load(getClass().getResource("PopPupPrestationFormFromFiche.fxml"));
                                prestation.setScene(new Scene(prestationRoot));
                                Utilis.CLIENT_IS_UPDATE = true;
                                prestation.show();
                                stage.close();
                            }

                        } else {
                            Alert error = new Alert(AlertType.ERROR, Utilis.ErrorEnregistrementDB, ButtonType.CLOSE);
                            error.show();
                        }
                    }
                }

            } else {
                Alert ErrorForm = new Alert(AlertType.ERROR, Utilis.ErrorForm, ButtonType.OK);
                ErrorForm.show();
            }
        }
    }

    private void initializeComboBoxesActions() {
        if (MichelKaApp.connexionIsOK) {
            List<Densite> listD = Utilis.driver.listDensite();
            List<EtatCheveux> listE = Utilis.driver.listEtatCheveux();
            List<TypeCheveux> listT = Utilis.driver.listTypeCheveux();
            List<CuirChevelu> listC = Utilis.driver.listCuirCheveux();
            List<TextureCheveux> listX = Utilis.driver.listTextureCheveux();
            listP = Utilis.driver.listProfession();
            if (listD != null) {
                listDensite = FXCollections.observableArrayList(listD);
                densiteCheveuxComboBox.getItems().addAll(listDensite);
            }
            if (listE != null) {
                listEtatCheveux = FXCollections.observableArrayList(listE);
                etatCheveuxComboBox.getItems().addAll(listEtatCheveux);
            }
            if (listT != null) {
                listTypeCheveux = FXCollections.observableArrayList(listT);
                typeCheveuxComboBox.getItems().addAll(listTypeCheveux);
            }
            if (listC != null) {
                listeCuirChevelu = FXCollections.observableArrayList(listC);
                cuirCheveluComboBox.setItems(listeCuirChevelu);
            }
            if (listX != null) {
                listTextureCheveux = FXCollections.observableArrayList(listX);
                textureCheveuxCombox.setItems(listTextureCheveux);
            }
            if (listP != null) {
                listProfession = FXCollections.observableArrayList(listP);
                professionComboBox.setItems(listProfession);
            }
        }

        listMoisComboBox.getItems().addAll(listMois);
        listJourComboBox.getItems().addAll(listJour);

        listJourComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            try {
                jour = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
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
                    Alert alert = new Alert(AlertType.ERROR);
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
        profession = new Profession(0, "Autre");
        professionComboBox.getItems().add(profession);
        professionComboBox.valueProperty().addListener((observableList, oldValue, newValue) -> {
            profession = newValue;
            if (newValue != null && init) {
                if (newValue.toString() != null && newValue.toString().equals("Autre")) {
                    professionField.setDisable(false);
                    professionField.setPromptText("Veuillez précisez");
                    a_donner_precision = true;
                } else {
                    professionField.setDisable(true);
                    professionField.setPromptText("");
                    a_donner_precision = false;
                }
            }
        });

        carteFideliteComboBox.getItems().addAll("Non", "Oui (Carte existant)", "Oui (Nouvelle Carte)");
        carteFideliteComboBox.setValue("Non");
        carteFideliteComboBox.valueProperty().addListener((observablelist, oldValue, newValue) -> {
            if (newValue.toString().contains("Carte")) {
                carteFideliteField.setDisable(false);
                carteFideliteField.setPromptText("Réference de carte");
                avoir_carte = true;
                if (newValue.toString().contains("Nouvelle")) {
                    est_proprietaire = true;
                    Encouple.setDisable(false);
                    nombreEnfant.setDisable(false);
                } else {
                    est_proprietaire = false;
                    Encouple.setDisable(true);
                    nombreEnfant.setDisable(true);
                }
            } else {
                carteFideliteField.setDisable(true);
                carteFideliteField.setPromptText("");
                avoir_carte = false;
                est_proprietaire = false;
                Encouple.setDisable(true);
                nombreEnfant.setDisable(true);
            }
        });
        densiteCheveuxComboBox.valueProperty().addListener((list, old, nv) -> {
            densite = nv;
        });
        cuirCheveluComboBox.valueProperty().addListener((list, old, nv) -> {
            cuirChevelu = nv;
        });
        typeCheveuxComboBox.valueProperty().addListener((list, old, nv) -> {
            typeCheveux = nv;
        });
        textureCheveuxCombox.valueProperty().addListener((list, old, nv) -> {
            textureCheveux = nv;
        });
        etatCheveuxComboBox.valueProperty().addListener((list, old, nv) -> {
            etatCheveux = nv;
        });
        professionComboBox.valueProperty().addListener((list, old, nv) -> {
            profession = nv;
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
        if (avoir_carte) {
            reference_carte = carteFideliteField.getText();
            carte = new Carte();
            try {
                carte.setReference(Integer.parseInt(reference_carte));
            } catch (NumberFormatException e) {
                Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
                Alert errorNumber = new Alert(AlertType.ERROR, Utilis.ErrorNumber, ButtonType.CANCEL);
                errorNumber.show();
            }
        } else {
            reference_carte = null;
        }
        if (a_donner_precision) {
            precise_profession = professionField.getText();
            if (precise_profession != null && !precise_profession.isEmpty()) {
                profession = new Profession(0, precise_profession);
            }
        } else {
            precise_profession = null;
        }
        System.out.println("ININT propr :" + avoir_carte + " avoir" + est_proprietaire);
    }

    private boolean formIsOk() {
        return !nom.isEmpty() && !prenom.isEmpty() && jour > 0 && mois != null && !mois.isEmpty();
    }

    private boolean professionAndCarteTeste() {
        if (!(a_donner_precision && precise_profession != null)) {
            return false;
        }
        if (!(avoir_carte && reference_carte != null)) {
            return false;
        }
        return true;
    }

    private void viderForm() {
        cuirChevelu = null;
        densite = null;
        typeCheveux = null;
        textureCheveux = null;
        etatCheveux = null;
        profession = null;
        adresse = null;
        email = null;
        tel_domicile = null;
        tel_bureau = null;
        tel_mobile_2 = null;
        tel_mobile_1 = null;
        nom = null;
        prenom = null;
        jour = 0;
        mois = null;
        client = null;
        carte = null;
        reference_carte = null;
        precise_profession = null;
        avoir_carte = false;
        a_donner_precision = false;
        est_proprietaire = false;
        professionIsOK = false;

        professionComboBox.setPromptText("Choisir...");
        densiteCheveuxComboBox.setPromptText("Choisir...");
        etatCheveuxComboBox.setPromptText("Choisir...");
        textureCheveuxCombox.setPromptText("Choisir...");
        cuirCheveluComboBox.setPromptText("Choisir...");
        typeCheveuxComboBox.setPromptText("Choisir...");
    }

    private void creerClient() {
        if (!isUpdateClient) {
            client = new Client();
        }
        if (Encouple.isSelected()) {
            couple = 2;
        }
        if (carte != null) {
            carte.setFamilleNombre(couple + nombre_enfant);
            carte.setCouple(Encouple.isSelected());
        }
        System.out.println("le carte est null" + carte + " = ==  = ==  ==  = = ");
        client.setAdresse(adresse);
        client.setAnniversaire(mois + "-" + jour);
        client.setCuirChevelu(cuirChevelu);
        client.setTelBureau(tel_bureau);
        client.setEmail(email);
        client.setMobile1(tel_mobile_1);
        client.setMobile2(tel_mobile_2);
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setTelBureau(tel_bureau);
        client.setDensiteCheveux(densite);
        client.setTextureCheveux(textureCheveux);
        client.setTypeCheveux(typeCheveux);
        client.setEtatCheveux(etatCheveux);
        client.setTelDomicile(tel_domicile);
        if (client.getCarte() == null || client.getCarte().getReference() == 0) {
            client.setCarte(carte);
        }
        client.setProfession(profession);
        client.setEstTechnique(true);
        System.out.println("carte:" + avoir_carte + " -->" + est_proprietaire);
    }

    private boolean insertAvecNouvelleProfession() {
        boolean teste = false;
        if (Utilis.driver.insertProfession(profession)) {
            Profession lp = Utilis.driver.lastProfession();
            if (lp != null) {
                profession = lp;
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
        String ErrorForm = "";
        carte = Utilis.driver.trouverCarte(Integer.parseInt(reference_carte));
        System.out.println(carte.getReference());
        carte.setCouple(Encouple.isSelected());
        boolean teste = true;
        if (carte == null) {
            teste = false;
            ErrorForm = "La carte n'existe pas!";
            Alert errorAlert = new Alert(AlertType.ERROR, ErrorForm, ButtonType.CANCEL);
            errorAlert.show();
            return teste;
        }
        if (carte.getEtatCarte() == 0 && !est_proprietaire) {
            Alert info = new Alert(Alert.AlertType.CONFIRMATION);
            info.setContentText("La carte est non active et sans proprÃ¨taire. Voulez vous l'attribuer Ã  ce client?");
            Optional<ButtonType> result = info.showAndWait();
            if (result.get() == ButtonType.OK) {
                est_proprietaire = true;
                client.setEst_proprietaire_carte(est_proprietaire);
                client.setUtiliseCarte(true);
                carte.setEtatCarte(1);
                carte.setNombreUtilisateurCarte(1);
                if (Encouple.isSelected()) {
                    carte.setFamilleNombre(nombre_enfant + 2);
                } else {
                    carte.setFamilleNombre(nombre_enfant + 1);
                }
                carte.setNombreUtilisateurCarte(1);
                carte.setFamilleNombre(carte.getFamilleNombre() + nombre_enfant);
                return Utilis.driver.attribuerCarte(carte);
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION, "Veuillez choisir une autre carte disponible.", ButtonType.OK);
                error.show();
                return false;
            }
        } else if (carte.getEtatCarte() == 0 && est_proprietaire) {
            client.setEst_proprietaire_carte(est_proprietaire);
            client.setUtiliseCarte(true);
            carte.setEtatCarte(1);
            carte.setNombreUtilisateurCarte(1);
            if (Encouple.isSelected()) {
                carte.setFamilleNombre(nombre_enfant + 2);
            } else {
                carte.setFamilleNombre(nombre_enfant + 1);
            }
            carte.setNombreUtilisateurCarte(1);
            carte.setFamilleNombre(carte.getFamilleNombre() + nombre_enfant);
            return Utilis.driver.attribuerCarte(carte);
        } else if (carte.getEtatCarte() == 1 && est_proprietaire) {
            ErrorForm = "Cette carte a déjâ  un propriÃ¨taire";
            teste = false;
        } else if (carte.getEtatCarte() == 1 && !est_proprietaire) {
            if (carte.getFamilleNombre() > carte.getNombreUtilisateurCarte()) {
                if (Utilis.driver.updateCarte(carte.getReference(), "nombre_utilisateur_carte", carte.getNombreUtilisateurCarte() + 1)) {
                    client.setCarte(carte);
                    teste = true;
                    client.setEst_proprietaire_carte(false);
                    client.setUtiliseCarte(true);
                }
            } else {
                teste = false;
                ErrorForm = "Cette carte a atteint la limite d'utilisateur autorisé.";
            }
        } else if (carte.getEtatCarte() == 2) {
            Alert confir = new Alert(AlertType.CONFIRMATION);
            confir.setContentText("Cette carte est bloquée. Voulez vous la réactiver?");
            Optional<ButtonType> result = confir.showAndWait();
            if (result.get() == ButtonType.OK) {
                Utilis.driver.updateCarte(client.getCarte().getReference(), "etat_carte", 1);
                teste = true;
            } else {
                teste = false;
                ErrorForm = "Vous n'avez pas autoriser le déblocage de carte.";
            }
        } else if (carte.getEtatCarte() == 3) {
            teste = false;
            ErrorForm = "La carte a expiré depuis la date du " + carte.getDateExpiration();
        } else {
            teste = false;
            ErrorForm = "La carte a été déclarer comme perdu.";
        }
        if (teste == false) {
            Alert errorAlert = new Alert(AlertType.ERROR, ErrorForm, ButtonType.CANCEL);
            errorAlert.show();
        }
        if (teste && carte != null) {
            client.setCarte(carte);
        }
        return teste;
    }

    private void PrepareFormulaireUpdate(Client clientTech) {
        nomField.setText(client.getNom());
        prenomField.setText(client.getPrenom());
        portable1Field.setText(client.getMobile1());
        portable2Field.setText(client.getMobile2());
        telBureauField.setText(client.getTelBureau());
        telDomicileField.setText(client.getTelDomicile());
        emailField.setText(client.getEmail());
        adresseField.setText(client.getAdresse());
        String date[] = client.getAnniversaire().split("-");
        listJourComboBox.setValue(date[1]);
        listMoisComboBox.setValue(date[0]);
        est_proprietaire = client.isEst_proprietaire_carte();
        avoir_carte = client.isUtiliseCarte();
        if (client.isUtiliseCarte()) {
            carteFideliteField.setText("" + client.getCarte().getReference());
            carteFideliteField.setDisable(true);
            carteFideliteComboBox.setValue("Oui");
            carteFideliteComboBox.setDisable(true);
            client.setCarte(Utilis.driver.trouverCarte(client));
            if (client.getCarte() != null) {
                Encouple.setSelected(client.getCarte().isCouple());
                carte = client.getCarte();
            }
            if (client.getCarte() != null && client.getCarte().isCouple()) {
                nombreEnfant.setText(client.getCarte().getFamilleNombre() - 2 + "");
            } else {
                nombreEnfant.setText(client.getCarte().getFamilleNombre() - 1 + "");
            }
        }

        if (client.getProfession() != null) {
            professionComboBox.setValue(client.getProfession());
        }
        if (client.getTypeCheveux() != null) {
            typeCheveuxComboBox.setValue(client.getTypeCheveux());
        }
        if (client.getDensiteCheveux() != null) {
            densiteCheveuxComboBox.setValue(client.getDensiteCheveux());
        }
        if (client.getTextureCheveux() != null) {
            textureCheveuxCombox.setValue(client.getTextureCheveux());
        }
        if (client.getEtatCheveux() != null) {
            etatCheveuxComboBox.setValue(client.getEtatCheveux());
        }
        if (client.getCuirChevelu() != null) {
            cuirCheveluComboBox.setValue(client.getCuirChevelu());
        }
        est_proprietaire = client.isEst_proprietaire_carte();
        System.out.println("Client est proprietaire de la carte:" + client.isEst_proprietaire_carte() + " et il l'utilise" + client.isUtiliseCarte());
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
