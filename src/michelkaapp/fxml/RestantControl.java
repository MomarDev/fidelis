package michelkaapp.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import michelkaapp.objets.Restant;
import michelkaapp.utilis.Utilis;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class RestantControl implements Initializable {
    @FXML
    private TableView table_view;
    @FXML
    private TableColumn col_nom;
    @FXML
    private TableColumn col_prenom;
    @FXML
    private TableColumn col_phone;
    @FXML
    private TableColumn col_comment;
    @FXML
    private TableColumn col_restant;
    @FXML
    private Button btn_fermer;
    private ObservableList<Restant> restants;
    private List<Restant> lesRestants;
    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    Date today = new Date();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lesRestants = Utilis.driver.listRestants(Utilis.driver.getLastOpenCaisse(),format.format(today));

        restants = FXCollections.observableArrayList(lesRestants);

        table_view.setItems(restants);

        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenomCli"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nomCli"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("telephoneCli"));
        col_comment.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
        col_restant.setCellValueFactory(new PropertyValueFactory<>("restant"));


    }

    @FXML
    private void handButton(ActionEvent event){

        if (event.getSource()==btn_fermer){
            Stage stage = (Stage) btn_fermer.getScene().getWindow();
            stage.close();
        }
    }
}
