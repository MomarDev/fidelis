/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import michelkaapp.objets.Parametre;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class ParametresController implements Initializable {

    @FXML
    private SplitPane pane_center;
    @FXML
    private AnchorPane pane_gauche;
    @FXML
    private Button btn_fiche;
    @FXML
    private Button btn_carte;
    @FXML
    private Button btn_payement;
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
    private AnchorPane pane_border;

    private Stage stage;
    private Parent root;
    @FXML
    private HBox admin_bar;
    @FXML
    private VBox vbox_content;

    private String valeur = "";
    private boolean update = false;
    @FXML
    private Button logout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.IsAdmin()) {
            admin_bar.setVisible(true);
        }
        if (Utilis.PARAMETRES_APPLICATION == null || Utilis.PARAMETRES_APPLICATION.isEmpty()) {
            Utilis.PARAMETRES_APPLICATION = Utilis.driver.listParametres();
        }
        vbox_content.getChildren().add(new Separator(Orientation.HORIZONTAL));
        Utilis.PARAMETRES_APPLICATION.forEach((paramtre) -> {
            if (paramtre.isAffiche()) {
                vbox_content.getChildren().add(faireParametre(paramtre));
            }
            vbox_content.getChildren().add(new Separator(Orientation.HORIZONTAL));
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
        
        
        logout.setOnMouseClicked(event -> {
            Alert alertLogout = new Alert(Alert.AlertType.CONFIRMATION);
            alertLogout.setContentText("Etes vous sure de vouloir vous déconnecter?");
            Optional<ButtonType> result = alertLogout.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Logout: " + Utilis.LOGOUT.logout(((Stage) logout.getScene().getWindow())));
            }
        });
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


    @FXML
    private void buttonListener(ActionEvent event) throws IOException {
        if (event.getSource() == btn_users) {
            stage = (Stage) btn_users.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Utilisateurs.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        if (event.getSource() == btn_traces) {
            stage = (Stage) btn_traces.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Traces.fxml"));
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
    }

    private HBox faireParametre(Parametre parametre) {
        HBox parametreHBox = new HBox();
        parametreHBox.setAlignment(Pos.CENTER);
        parametreHBox.setSpacing(10);
        parametreHBox.setPrefWidth(600);

        Label labelCle = new Label(parametre.getCle());
        labelCle.setPrefWidth(200);

        TextField textFieldValue = new TextField(parametre.getValeur());
        textFieldValue.setDisable(true);
        textFieldValue.setPrefWidth(200);
        textFieldValue.setSnapToPixel(false);
        textFieldValue.textProperty().addListener((obs, old, nv) -> {
            update = true;
            if (nv != null && !nv.isEmpty()) {
                valeur = nv;
                if (Utilis.isNumber(parametre.getValeur())) {
                    if (!Utilis.isNumber(nv)) {
                        textFieldValue.setStyle("-fx-background-color:#fcda00");
                    } else {
                        textFieldValue.setStyle(null);
                    }
                } else {
                    textFieldValue.setStyle(null);
                }
            } else {
                valeur = "";
            }
        });
        boolean isRestor = parametre.getCle().equals(Utilis.PARA_SOURCE_PATH);
        Button buttonModifier = new Button("Modifier");
        buttonModifier.setPrefWidth(100);
        buttonModifier.setOnMouseClicked(event -> {
            if (textFieldValue.isDisabled()) {
                textFieldValue.setDisable(false);
                buttonModifier.setText("Valider");
                buttonModifier.setStyle("-fx-background-color: linear-gradient(#61a2b1, #009688)");
                if (isRestor) {
//                    textFieldValue.setDisable(true);
                    DirectoryChooser choose = new DirectoryChooser();
                    choose.setInitialDirectory(new File(parametre.getValeur().isEmpty() ? System.getProperty("user.dir") : parametre.getValeur()));
                    choose.setTitle("Source DB");
                    File selectedFile = choose.showDialog((Stage) buttonModifier.getScene().getWindow());
                    textFieldValue.setText(selectedFile == null ? System.getProperty("user.dir") : selectedFile.getAbsolutePath());
                    if (!System.getProperty("user.dir").equals(textFieldValue.getText()) && textFieldValue.getText().contains("michelekadb")) {
                        parametre.setValeur(textFieldValue.getText());
                        Utilis.driver.updateParametre(parametre);
                    }
                }
            } else {
                if (update && !parametre.getValeur().equals(valeur)) {
                    parametre.setValeur(valeur);
                    Utilis.driver.updateParametre(parametre);
                }
                textFieldValue.setDisable(true);
                buttonModifier.setStyle(null);
                buttonModifier.setText("Modifier");
            }

            update = false;
        });

        Text textAreaCommentaire = new Text(parametre.getCommentaire());
        textAreaCommentaire.setTextAlignment(TextAlignment.CENTER);
        BorderPane pane = new BorderPane(textAreaCommentaire);
        pane.setPrefWidth(350);
//        pane.setAlignment(pane, Pos.CENTER);
        parametreHBox.getChildren().add(labelCle);
        parametreHBox.getChildren().add(new Separator(Orientation.VERTICAL));

        parametreHBox.getChildren().add(textFieldValue);
        parametreHBox.getChildren().add(new Separator(Orientation.VERTICAL));
        parametreHBox.getChildren().add(buttonModifier);
        parametreHBox.getChildren().add(new Separator(Orientation.VERTICAL));
        parametreHBox.getChildren().add(pane);
        return parametreHBox;
    }
}
