package michelkaapp.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import michelkaapp.objets.Restant;
import michelkaapp.objets.Service;
import michelkaapp.utilis.Utilis;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestantTotalControl implements Initializable {
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
    private TableColumn col_payer;
    @FXML
    private Button btn_fermer;
    public  static int rest;
    public static String name;
    public static String tel;

    private ObservableList<Restant> restants;
    private List<Restant> lesRestants;
    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    Date today = new Date();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
        initTable();
        
        
        
        table_view.setRowFactory(tv -> {
            TableRow<Restant> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Utilis.RestantToUpdate = row.getItem();
                    try {
                        rest = row.getItem().getRestant();
                        name = row.getItem().getPrenomCli()+" "+row.getItem().getNomCli();
                        tel= row.getItem().getTelephoneCli();
                        Stage stageUpdateService = new Stage();
                        Parent updateRoot = FXMLLoader.load(getClass().getResource("Remboursement.fxml"));
                        stageUpdateService.setScene(new Scene(updateRoot));
                        stageUpdateService.setResizable(false);
                        stageUpdateService.setOnCloseRequest((o) -> {
                            --Utilis.Max_DEFAULT;
                        });
                        stageUpdateService.setOnHidden((o) -> {
                            --Utilis.Max_DEFAULT;
                            initTable();
                        });
                        stageUpdateService.show();
                    } catch (IOException ex) {
                        Logger.getLogger(StockServiceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });

    }
    
    private void initTable() {
    	lesRestants = Utilis.driver.listRestants();

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
