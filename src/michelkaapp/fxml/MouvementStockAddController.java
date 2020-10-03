/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Produit;
import michelkaapp.objets.Trace;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class MouvementStockAddController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private TextField code_barres;
    @FXML
    private Button btn_quitter;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Label titre;
    @FXML
    private TextField quantite_produit;
    @FXML
    private Label label_effectuer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        code_barres.textProperty().addListener((ob, old, nv) -> {
            if (nv != null && !nv.isEmpty() && !Utilis.isNumber(nv)) {
                code_barres.setStyle("-fx-background-color:#ddaa96");
            } else {
                code_barres.setStyle(null);
            }
        });
        quantite_produit.textProperty().addListener((ob, old, nv) -> {
            if (nv != null && !nv.isEmpty() && !Utilis.isNumber(nv)) {
                quantite_produit.setStyle("-fx-background-color:#ddaa96");
            } else {
                quantite_produit.setStyle(null);
            }
        });
    }

    @FXML
    private void handlerButton(ActionEvent event) {
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
        } else if (event.getSource() == btn_enregistrer) {
            String codeString = code_barres.getText();
            String quantString = quantite_produit.getText();

            if (codeString != null && quantString != null && !Utilis.isNumber(codeString) || !Utilis.isNumber(quantString)) {
                Alert errorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
                errorNumber.show();
                return;
            }
            Produit produit = Utilis.driver.selectProduitById("code_bar_produit", codeString);
            if (produit == null) {
                Alert errorNulValue = new Alert(Alert.AlertType.ERROR, "Ce produit n'est pas enregistrer.", ButtonType.OK);
                errorNulValue.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sure de vouloir ajouter " + quantString + " au de stock de " + produit.getNom());
                Optional<ButtonType> rs = alert.showAndWait();
                if (rs.get() == ButtonType.OK) {
                    int qt = Integer.parseInt(quantString);
                    boolean teste = false;
                    int quantite = 0;
                    if (produit.isUtilisation()) {
                        produit.setStockService(produit.getStockService() + qt);
                        quantite = produit.getStockService();
                        teste = Utilis.driver.updateProduit("qt_stock_service", produit.getStockService() + "", produit.getId());
                        Utilis.driver.insertTrace(new Trace(0, "ajout stock", produit.getCode() + "/" + qt, teste ? "effectué" : "echec", null, Utilis.user));
                        if (teste) {
                            Utilis.driver.mouvementStock(produit.getId(), "stockage", qt, "ajout stock");
                        }
                        produit.setStock(produit.getStockService());
                    } else {
                        produit.setStockVente(produit.getStockVente() + qt);
                        produit.setStock(produit.getStockVente());
                        quantite = produit.getStockVente();
                        teste = Utilis.driver.updateProduit("qt_stock_vente", produit.getStockVente() + "", produit.getId());
                        Utilis.driver.insertTrace(new Trace(0, "ajout stock", produit.getCode() + "/" + qt, teste ? "effectué" : "echec", null, Utilis.user));
                        if (teste) {
                            Utilis.driver.mouvementStock(produit.getId(), "stockage", qt, "ajout stock");
                        }
                    }
                    if (teste) {
                        label_effectuer.setText(quantString + " a été ajouter au stock de " + produit.getNom() + ". Quantité actuelle : " + produit.getStock());
                        code_barres.setStyle(null);
                        code_barres.setText(null);
                        quantite_produit.setText(null);
                        quantite_produit.setStyle(null);
                        Utilis.PRODUIT_IS_UPDATE = true;
                    } else {
                        Alert errorDB = new Alert(Alert.AlertType.ERROR, Utilis.ErrorEnregistrementDB, ButtonType.OK);
                        errorDB.show();
                    }
                } else {
                    code_barres.setStyle(null);
                    code_barres.setText(null);
                    quantite_produit.setText(null);
                    quantite_produit.setStyle(null);
                    label_effectuer.setText(null);
                }

            }

        }
    }

    @FXML
    private void keyEventListener(KeyEvent event) {
    }

}
