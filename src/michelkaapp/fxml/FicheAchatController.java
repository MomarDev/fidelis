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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import michelkaapp.objets.Achat;
import michelkaapp.objets.Carte;
import michelkaapp.objets.Client;
import michelkaapp.objets.Produit;
import michelkaapp.objets.ProduitAchete;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class FicheAchatController implements Initializable {

    @FXML
    private AnchorPane second_pane;

    private Stage stage;
    private Parent root;
    @FXML
    private AnchorPane pane_achat1;
    @FXML
    private Label prix_total_fd;
    @FXML
    private Label nom_client_achat;
    @FXML
    private Label reference_carte_achat;
    @FXML
    private Button btn_quitter_achat;
    @FXML
    private Button btn_enregistrer_achat;
    @FXML
    private VBox conteneur_ventes;
    @FXML
    private ImageView imv_add_achat;

    private Produit selectedProduit = null;
    private int quantiteAchate = 0;
    private List<ProduitAchete> paniers = null;
    private ObservableList<Produit> ob = null;
    private Client client = null;
    private Carte carte = null;
    private boolean listVide = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.clientActuel != null) {
            try {
                client = (Client) Utilis.clientActuel.clone();
                carte = client.getCarte();
                nom_client_achat.setText(client.getNom() + " " + client.getPrenom());
                reference_carte_achat.setText(carte.toString());

            } catch (CloneNotSupportedException ex) {
                Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                Logger.getLogger(SuiviMaisonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("is not ok");
        }
        List<Produit> list = Utilis.driver.listProduit("WHERE produit.supprime = 0 and utilisation_produit = 0 ");
        if (list != null) {
            ob = FXCollections.observableArrayList(list);
        }
        paniers = new ArrayList<>();
        conteneur_ventes.getChildren().add(faireProduitList());
        conteneur_ventes.getChildren().add(new Separator(Orientation.HORIZONTAL));
        imv_add_achat.setOnMouseClicked(event -> {
            try {
                if (selectedProduit != null) {
                    ProduitAchete panier = new ProduitAchete();
                    panier.setProduit(selectedProduit);
                    panier.setQuantite(quantiteAchate);
                    paniers.add(panier);
                    int len = conteneur_ventes.getChildren().size();
                    if (!paniers.isEmpty()) {
                        HBox h = (HBox) conteneur_ventes.getChildren().get(len - 2);
                        int len1 = h.getChildren().size();
                        for (int i = 0; i < len1 - 2; i++) {
                            h.getChildren().get(i).setDisable(true);
                        }
                    }
                }
                if ((selectedProduit != null || (paniers.isEmpty() && selectedProduit != null)) || listVide) {
                    conteneur_ventes.getChildren().add(faireProduitList());
                    conteneur_ventes.getChildren().add(new Separator(Orientation.HORIZONTAL));
                    listVide = false;
                }
                selectedProduit = null;
                System.out.println(paniers.size());

            } catch (Exception e) {
                e.printStackTrace();
                Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
                Alert error = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
                error.show();
            }
        });
    }

    private HBox faireProduitList() {
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setAlignment(Pos.CENTER_LEFT);
        Label prix = new Label("PT: ");
        prix.setPrefWidth(100);
        Label prixUnitaire = new Label("PU: ");
        prixUnitaire.setPrefWidth(100);

        TextField codebarres = new TextField();
        codebarres.setPromptText("Code Barres");
        codebarres.setPrefWidth(120);

        ComboBox<Produit> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(120);
        comboBox.setItems(ob);
        comboBox.setPromptText("Choisir produit...");

        codebarres.textProperty().addListener((obs, old, nv) -> {
            if (nv != null && !nv.isEmpty() && Utilis.isNumber(nv)) {
                selectedProduit = Utilis.driver.selectProduitById("code_bar_produit", nv);
                if (selectedProduit != null && !selectedProduit.getNom().toLowerCase().contains("divers")) {
                    comboBox.setValue(selectedProduit);
                }
            }
        });

        ImageView deleteView = new ImageView();
        Image v = new Image(getClass().getResourceAsStream("effacer.png"));
        deleteView.setImage(v);
        deleteView.setFitWidth(25);
        deleteView.setFitHeight(25);
        deleteView.setOnMouseClicked(event -> {
            int nombreFils = conteneur_ventes.getChildren().size();
            conteneur_ventes.getChildren().remove(nombreFils - 1);
            conteneur_ventes.getChildren().remove(hbox);
            if (!paniers.isEmpty()) {
                if (paniers.size() > 0) {
                    int find = findProduit(paniers, comboBox.getValue());
                    if (find != -1) {
                        paniers.remove(find);
                    }
                }
                System.out.println(paniers.size());
                prix_total_fd.setText("" + Utilis.separatorNumber(calculPrixTotal() + (quantiteAchate * selectedProduit.getPrix())));
            }
            if (paniers.isEmpty() || paniers.size() == 0) {
                selectedProduit = null;
                listVide = true;
                prix_total_fd.setText("0");
            }

        });

        Label labelQuantite = new Label("QT: ");
        labelQuantite.prefWidth(80);
        TextField qt = new TextField("1");
        qt.setPromptText("QT:");
        qt.setPrefWidth(30);
        qt.textProperty().addListener((obs, old, nv) -> {
            if (!nv.isEmpty() && !Utilis.isNumber(nv)) {
                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
                alertErrorNumber.show();
            } else {
                if (selectedProduit != null) {
                    prix.setText("PT: " + Utilis.separatorNumber((selectedProduit.getPrix() * (Integer.parseInt(nv)))) + " CFA");
                    //quantiteAchate = Integer.parseInt(nv);
                    quantiteAchate = Integer.parseUnsignedInt(nv);
                    Utilis.verifierQuantiteProduit(selectedProduit, quantiteAchate);
                    prix_total_fd.setText("" + Utilis.separatorNumber(calculPrixTotal() + (quantiteAchate * selectedProduit.getPrix())));
                }
            }
        });
        comboBox.valueProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                selectedProduit = nv;
                codebarres.setText(nv.getCode());
                if (nv.getNom().toLowerCase().contains("divers")) {
                    Alert alertDivers = Utilis.ProduitDivers(nv);
                    codebarres.setDisable(true);
                } else {
                    codebarres.setDisable(false);
                }
                quantiteAchate = Integer.parseInt(qt.getText());
                prix.setText("PT: " + Utilis.separatorNumber(nv.getPrix() * quantiteAchate) + " CFA");
                prixUnitaire.setText("PU: " + Utilis.separatorNumber(nv.getPrix()) + " CFA");
                prix_total_fd.setText("" + Utilis.separatorNumber(calculPrixTotal() + (quantiteAchate * selectedProduit.getPrix())));

            }
            System.out.println(nv);
        });

//        ajouter les fils
        Label lc = new Label("Code barres");
        lc.setPrefWidth(75);
        hbox.getChildren().add(lc);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(codebarres);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(comboBox);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(prixUnitaire);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(labelQuantite);
        hbox.getChildren().add(qt);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(prix);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(deleteView);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        return hbox;
    }

    private int findProduit(List<ProduitAchete> ps, Produit p) {
        if (paniers.isEmpty() || p == null) {
            return -1;
        }
        int len = paniers.size();
        int pos = -1;
        for (int i = 0; i < len; i++) {
            if (paniers.get(i).getProduit().getId() == p.getId()) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    private int calculPrixTotal() {
        int px = 0;
        int len = paniers.size();
        for (int i = 0; i < len; i++) {
            ProduitAchete panier = paniers.get(i);
            px += panier.getQuantite() * panier.getProduit().getPrix();
        }
        return px;
    }

    public int calculPrixTotalApresRemise(int totalAvantRemise, int remise) {
        return (totalAvantRemise) * (1 + (remise / 100));
    }

    @FXML
    private void handlerButtonAchat(ActionEvent event) {
        if (event.getSource() == btn_quitter_achat) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_quitter_achat.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        } else if (event.getSource() == btn_enregistrer_achat) {
//            On prends le dernier produit
            if (selectedProduit != null) {
                ProduitAchete panier = new ProduitAchete();
                panier.setProduit(selectedProduit);
                panier.setQuantite(quantiteAchate);
                paniers.add(panier);
//                produitsActuel.add(selectedProduit);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.QuestionValideForm);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_enregistrer_achat.getScene().getWindow();
                Achat achat = new Achat(0, null, client.getId(), 0, 0, 0, false);
                if (client.isUtiliseCarte()) {
                    carte = Utilis.driver.trouverCarte(client);
                }
                if (Utilis.driver.insertAchat(achat)) {
                    System.out.println("ok");
                    achat = Utilis.driver.dernierAchat(achat.getClient());
                    if (!paniers.isEmpty()) {
                        List<Integer> insertProduitUtiliseTeste = Utilis.driver.insertProduitAchete(paniers, achat.getId());
                        int montant_total = calculPrixTotal();
                        achat.setMontantAvantRemise(montant_total);
                        achat.setMontantApresRemise(montant_total);

                        if (Utilis.driver.updateAchat(achat)) {
                            Utilis.PRESTATION_IS_UPDATE = true;
                            Utilis.venteProduits(paniers);
//                            Utilis.regrouperProduitAcheter(paniers);
                            if (client.isUtiliseCarte() && !achat.isUtiliseFideite()) {
                                Alert pointAlert = Utilis.DonnerPointDialog("Montant Achat", "Achat", "Voulez-vous renseigner le montant de l'achat du client?", "nb_pts_fidelite_achat", carte);
                                if (!pointAlert.isShowing()) {
                                    System.out.println("ALERT");
                                    List<Achat> achats = Utilis.driver.listAchatClient(client.getId());
                                    int size = achats == null ? 0 : achats.size();
                                    // if (size > 0 && carte.getNptsFideliteAchat() > 0 && (size % Integer.parseInt(Utilis.trouverParametre("FIDELITE_ACHAT").getValeur()) == 0 || Utilis.driver.aUnAvoir(carte.getReference()))) {
                                    if (size > 0 && carte.getNptsFideliteAchat() > 0 && ((size % Integer.parseInt(Utilis.trouverParametre("FIDELITE_ACHAT").getValeur()) == 0) )) {

                                        System.out.println("size:" + size);

                                        System.out.println("Nfidelite achat:" + carte.getNptsFideliteAchat());
                                        System.out.println("size mod fide param:" + size % Integer.parseInt(Utilis.trouverParametre("FIDELITE_ACHAT").getValeur()));
                                        System.out.println("driver a un avoir:" + Utilis.driver.aUnAvoir(carte.getReference()));

                                        Stage impStage = new Stage();
                                        impStage.setResizable(false);
                                        FXMLLoader impRoot = null;
                                        VBox pane = null;
                                        try {
                                            System.out.println("Try");

                                            impRoot = new FXMLLoader(getClass().getResource("ImpressionFidelite.fxml"));
                                            pane = impRoot.load();
                                            ImpressionFideliteController controller = (ImpressionFideliteController) impRoot.getController();
                                            controller.newInstence(client, "nb_pts_fidelite_achat", false);
                                        } catch (IOException ex) {
                                            Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        impStage.setScene(new Scene(pane));
                                        impStage.show();
                                    }
                                }
                                System.out.println("PAs alerte");
                            }
                            Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
                            okAlert.setContentText("L'achat est bien effectué.");
                            okAlert.show();
                        }
                    } else {
                        Alert errorInsert = new Alert(Alert.AlertType.ERROR, Utilis.ErrorEnregistrementDB, ButtonType.OK);
                        errorInsert.show();
                        Utilis.LOGGER.log(Utilis.ErrorEnregistrementDB);
                        return;
                    }
                } else {
                    System.out.println("......");
                }
                stage.close();
            }
        }
    }
}
