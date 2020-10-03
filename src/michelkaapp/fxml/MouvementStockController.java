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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Produit;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class MouvementStockController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private Button btn_quitter;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Label titre;
    @FXML
    private TextField code_barres;
    @FXML
    private Label reponse;
    @FXML
    private RadioButton rd_vente;
    @FXML
    private ToggleGroup gp_motif;
    @FXML
    private RadioButton rd_service;
    @FXML
    private RadioButton rd_autre;
    @FXML
    private TextField fld_autre;

    private String text_autre = "achat";
    private int qt = 1;
    @FXML
    private TextField fld_qt;
    private int restant = 0;

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
        btn_enregistrer.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                destocker();
            }
        });
        fld_autre.textProperty().addListener((obs, old, nv) -> {
            text_autre = nv;
        });
        gp_motif.selectedToggleProperty().addListener((obs, old, nv) -> {
            if (nv == rd_autre) {
                fld_autre.setDisable(false);
            } else {
                fld_autre.setDisable(true);
            }
            if (nv == rd_vente) {
                text_autre = "achat";
            }
            if (nv == rd_service) {
                text_autre = "service";
            }
        });
        fld_qt.textProperty().addListener((obs, old, nv) -> {
            if (nv != null && !nv.isEmpty()) {
                if (Utilis.isNumber(nv)) {
                    qt = Integer.parseInt(nv);
                }
            } else {
                qt = 1;
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
                Utilis.PRODUIT_IS_UPDATE = true;
                stage.close();
            } else {
                alert.close();
            }
        }
        if (event.getSource() == btn_enregistrer) {
            destocker();
        }
    }

    @FXML
    private void keyEventListener(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
//            destocker();
            Utilis.runFocusOnRecherche(btn_enregistrer);
        }
    }

    private void destocker() {
        String code = code_barres.getText();
        if (!code.isEmpty()) {
            if (Utilis.isNumber(code)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sure de voulois supprimer un élément dans le stock.");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Produit produit = Utilis.driver.selectProduitById("code_bar_produit", code);
                    if (produit != null) {
                        boolean teste = false;
                        if (produit.getStockService() > 0) {
                            if (produit.getStockService() >= qt) {
                                produit.setStockService(produit.getStockService() - qt);
                                teste = Utilis.driver.updateProduit("qt_stock_service", produit.getStockService() + "", produit.getId());
                                restant = produit.getStockService();
                            }
//                            teste = Utilis.driver.destocker(code, "qt_stock_service");
                        }
                        if (produit.getStockVente()> 0) {
                            if (produit.getStockVente() >= qt) {
                                produit.setStockVente(produit.getStockVente() - qt);
                                teste = Utilis.driver.updateProduit("qt_stock_vente", produit.getStockVente() + "", produit.getId());
                                restant = produit.getStockVente();
                            }
//                            teste = Utilis.driver.destocker(code, "qt_stock_vente");
                        }
                        if (teste) {
                            Utilis.driver.mouvementStock(produit.getId(), "destockage", qt, text_autre);
                            reponse.setText("retiré du stock: " + qt + ", restante: " + restant);
                            code_barres.setStyle(null);
                            code_barres.setText("");
                        } else {
                            Alert alertError = new Alert(Alert.AlertType.ERROR, "La quantité de stock est insuffissante...", ButtonType.CANCEL);
                            alertError.show();
                            code_barres.setStyle(null);
                            code_barres.setText("");
                        }
                    } else {
                        Alert alertErrorSupp = new Alert(Alert.AlertType.ERROR, "Veuillez verifier que le produit existe dans le stock.");
                        alertErrorSupp.show();
                    }
                }
            } else {
                code_barres.setStyle("-fx-background-color:#ea6060");
            }

        } else {
            code_barres.setStyle("-fx-background-color:#ea6060");
        }
        code_barres.requestFocus();
        fld_autre.setText(null);
//        text_autre = "autre";
        fld_qt.setText("1");
        qt = 1;
    }
}
