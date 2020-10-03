/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import michelkaapp.objets.DetailInventaire;
import michelkaapp.objets.Inventaire;
import michelkaapp.objets.Produit;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class InventaireController implements Initializable {

    @FXML
    private AnchorPane pane_inventaire;
    @FXML
    private Button btn_nouvel_inventaire;
    @FXML
    private Button btn_detail;
    @FXML
    private Button btn_quitter;
    @FXML
    private AnchorPane pane_nouvel;
    @FXML
    private AnchorPane form_inventaire;
    @FXML
    private Button btn_quitterInventaire;
    @FXML
    private Button btn_enregistre_Inventaire;
    @FXML
    private Button btn_retour_list;
    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane pane_tab_detail;
    @FXML
    private Label titre_detail;
    @FXML
    private TableView<?> tbv_details;
    @FXML
    private Button btn_extraire;
    @FXML
    private Button btn_quitter_detail;
    @FXML
    private VBox vboc;
    @FXML
    private ImageView imv_add;

    private List<Produit> produitsEnAttentes = null;
    private ObservableList<Produit> obsProduits = null;
    private Produit ProduitEnInventaire = null;
    private Inventaire inventaire = null;
    private DetailInventaire detailInventaire = null;
    private List<DetailInventaire> listProdduitEnInventaire;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Produit> listp = Utilis.driver.listProduit("WHERE produit.supprime = 0");
        if (listp != null) {
            obsProduits = FXCollections.observableArrayList(listp);
            produitsEnAttentes = new ArrayList<>();
            listProdduitEnInventaire = new ArrayList<>();
        }
        imv_add.setOnMouseClicked(event -> {
            if (ProduitEnInventaire != null) {
                listProdduitEnInventaire.add(detailInventaire);
                if (obsProduits.size() == 1) {
                    obsProduits.clear();
                } else {
                    obsProduits.remove(removeFromList(obsProduits, ProduitEnInventaire));
                }
                int len = vboc.getChildren().size();
                HBox hb = (HBox) vboc.getChildren().get(len - 1);
                int len1 = hb.getChildren().size();
                for (int i = 0; i < len1 - 1; i++) {
                    hb.getChildren().get(i).setDisable(true);
                }

            }
            if (!obsProduits.isEmpty()) {
                vboc.getChildren().add(faireNouvelleInventaire());
            }
        });

    }

    @FXML
    private void handlerActionButton(ActionEvent event) {
        if (event.getSource() == btn_quitter) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_quitter.getScene().getWindow();
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();
            } else {
                alert.close();
            }
        } else if (event.getSource() == btn_detail) {
            pane_inventaire.setVisible(false);
            pane_nouvel.setVisible(false);
            pane_tab_detail.setVisible(true);
        } else if (event.getSource() == btn_nouvel_inventaire) {
            pane_inventaire.setVisible(false);
            pane_tab_detail.setVisible(false);
            pane_nouvel.setVisible(true);
        } else if (event.getSource() == btn_enregistre_Inventaire) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.QuestionValideForm);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert info = new Alert(Alert.AlertType.INFORMATION, "L'inventaire "
                        + "a été enregistrer avec succé", ButtonType.OK);
                info.show();
                pane_inventaire.setVisible(true);
                pane_tab_detail.setVisible(false);
                pane_nouvel.setVisible(false);
            }
        } else if (event.getSource() == btn_quitter_detail) {
            pane_inventaire.setVisible(true);
            pane_tab_detail.setVisible(false);
            pane_nouvel.setVisible(false);
        } else if (event.getSource() == btn_quitterInventaire) {
            pane_inventaire.setVisible(true);
            pane_tab_detail.setVisible(false);
            pane_nouvel.setVisible(false);
        }
    }

    private HBox faireNouvelleInventaire() {

        detailInventaire = new DetailInventaire();
        inventaire = new Inventaire();
        HBox hbox = new HBox();
        ComboBox<Produit> comboBox = new ComboBox();
        comboBox.setPromptText("Choisir produit...");
        comboBox.setItems(obsProduits);

        TextField real = new TextField();
        real.setPromptText("Réal");
        real.textProperty().addListener((obs, old, nv) -> {
            if (Utilis.isNumber(nv)) {
                if (ProduitEnInventaire != null) {
                    if (ProduitEnInventaire.isUtilisation()) {
                        detailInventaire.setQuantiteRealService(Integer.parseInt(nv));
                    } else {
                        detailInventaire.setQuantiteRealVente(Integer.parseInt(nv));
                    }
                }
            }
        });

        TextField theorique = new TextField();
        theorique.setPromptText("Théorique");
        theorique.setDisable(true);

        TextField comment = new TextField();
        comment.setPromptText("Commentaire");
        comment.textProperty().addListener((obs, old, nv) -> {
            if (ProduitEnInventaire != null) {
                detailInventaire.setCommentaire(nv);
            }
        });

        ImageView delete = new ImageView();
        delete.setFitWidth(25);
        delete.setFitHeight(25);
        Image image = new Image(getClass().getResourceAsStream("effacer.png"));
        delete.setImage(image);
        delete.setOnMouseClicked(event -> {
            vboc.getChildren().remove(hbox);
            if (ProduitEnInventaire != null) {
                listProdduitEnInventaire.remove(removeFromList(listProdduitEnInventaire, comboBox.getSelectionModel().getSelectedItem()));
            }
        });

        comboBox.valueProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                ProduitEnInventaire = nv;
                if (ProduitEnInventaire.isUtilisation()) {
                    theorique.setText(Utilis.separatorNumber(ProduitEnInventaire.getStockService()));
                } else {
                    theorique.setText(Utilis.separatorNumber(ProduitEnInventaire.getStockVente()));
                }
                detailInventaire.setProduit(ProduitEnInventaire);
            }
        });

        hbox.getChildren().add(comboBox);
        hbox.getChildren().add(real);
        hbox.getChildren().add(theorique);
        hbox.getChildren().add(comment);
        hbox.getChildren().add(delete);

        return hbox;
    }

    private int removeFromList(List<DetailInventaire> listdp, Produit prod) {
        int index = -1;
        int len = listdp.size();
        if (len == 1) {
            return 0;
        }
        for (int i = 0; i < len; i++) {
            if (prod.getId() == listdp.get(i).getId()) {
                index = i;
                obsProduits.add(prod);
                break;
            }
        }
        return index;
    }

    private int removeFromList(ObservableList<Produit> obLp, Produit prod) {
        int index = -1;
        int len = obLp.size();
        for (int i = 0; i < len; i++) {
            if (prod.getId() == obLp.get(i).getId()) {
                index = 1;
                break;
            }
        }
        return index;
    }
}
