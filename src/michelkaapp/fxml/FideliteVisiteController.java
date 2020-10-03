/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Client;
import michelkaapp.objets.Visite;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class FideliteVisiteController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane border_pane;
    @FXML
    private Button btn_add_visite;
    @FXML
    private AnchorPane info_border;

    private Stage stage;
    @FXML
    private TableView<Visite> tableView;
    @FXML
    private TableColumn<Visite, Integer> date_col;
    @FXML
    private TableColumn<Visite, String> points_col;
    @FXML
    private Label total;
    @FXML
    private Label nomProprietaireCarte;
    @FXML
    private Label referenceCarte;

    private Client client = null;
    private Button btn_refresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        client = Utilis.clientActuel;
        if (client != null) {
            referenceCarte.setText(client.getCarte().getReference() + "");
            nomProprietaireCarte.setText(client.getNom() + " " + client.getPrenom());
            List<Visite> list = Utilis.driver.listVisites(client.getCarte().getReference());
            if (list != null) {
                ObservableList<Visite> obs = FXCollections.observableArrayList(list);
                tableView.setItems(obs);
                date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
                points_col.setCellValueFactory(new PropertyValueFactory<>("nombrePoints"));
                int tt = 0;
                int len = list.size();
                for (int i = 0; i < len; i++) {
                    tt += list.get(i).getNombrePoints();
                }
                total.setText("Total points visite : " + tt);
            }
            client.setCarte(Utilis.driver.trouverCarte(client.getCarte().getReference()));
        }
    }

    @FXML
    private void handlerButton(ActionEvent event) throws IOException {
        if (event.getSource() == btn_add_visite) {
            stage = (Stage) btn_add_visite.getScene().getWindow();
            Stage stageVisiteForm = new Stage();
            stageVisiteForm.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("FideliteVisiteForm.fxml"));
            stageVisiteForm.setScene(new Scene(root));
            stage.close();
            stageVisiteForm.show();
        } 
        Utilis.Carte_IS_UPDATE = true;
    }
}
