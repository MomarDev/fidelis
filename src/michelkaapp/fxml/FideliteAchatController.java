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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Achat;
import michelkaapp.objets.Client;
import michelkaapp.objets.Produit;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class FideliteAchatController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane border_pane;
    @FXML
    private Button btn_add_achat;

    private Stage stage;
    private Parent root;
    @FXML
    private AnchorPane info_border;
    @FXML
    private Label nom_prenom_point;
    @FXML
    private Label reference_carte_point;

    private Client client = null;
    @FXML
    private TableView<Achat> table_achat;
    @FXML
    private TableColumn<Achat, String> date_achat_col;
//    private TableColumn<Achat, String> montant_achat_col;
    @FXML
    private TableColumn<Achat, List<Produit>> produits_achete;
    @FXML
    private Label total_point_achat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.clientActuel != null) {
            try {
                client = (Client) Utilis.clientActuel.clone();
                nom_prenom_point.setText(client.getNom() + " " + client.getPrenom());
                reference_carte_point.setText(client.getCarte().getReference() + "");
                total_point_achat.setText("Total: " + this.client.getCarte().getNptsFideliteAchat());
            } catch (CloneNotSupportedException ex) {
                Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                Logger.getLogger(FideliteAchatController.class.getName()).log(Level.SEVERE, null, ex);
            }
            faireAchat();
        }
    }

    @FXML
    private void handlerButton(ActionEvent event) throws IOException {
        if (event.getSource() == btn_add_achat) {
            Stage stageAchatForm = new Stage();
            stageAchatForm.setResizable(false);
            Parent rootAchatForm = FXMLLoader.load(getClass().getResource("FicheAchat.fxml"));
            stageAchatForm.setScene(new Scene(rootAchatForm));
            stage = (Stage) btn_add_achat.getScene().getWindow();
            stage.close();
            stageAchatForm.show();
            Utilis.Carte_IS_UPDATE = true;
        }
    }

    private void faireAchat() {
        List<Achat> achatsClient = Utilis.driver.listAchatClient(client.getId());
        if (achatsClient != null) {
            int len = achatsClient.size();
            for (int i = 0; i < len; i++) {
                achatsClient.get(i).setProduits(Utilis.driver.listProduits(achatsClient.get(i).getId()));
            }
            table_achat.setItems(FXCollections.observableArrayList(achatsClient));
            date_achat_col.setCellValueFactory(new PropertyValueFactory<>("date"));
            produits_achete.setCellValueFactory(new PropertyValueFactory<>("produits"));
            
          
           
        }
    }
}
