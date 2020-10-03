package michelkaapp.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import michelkaapp.objets.Product;
import michelkaapp.objets.Produit;
import michelkaapp.objets.ProduitAchete;
import michelkaapp.utilis.Utilis;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class ListAchatProduitControl implements Initializable {
    @FXML
    private TableView<Product> table_view1;
    @FXML
    private TableColumn<Product,String> produit_col;
    @FXML
    private TableColumn<Product,Integer> prixPro_col;
    @FXML
    private TableColumn<Product,Integer> quantite_col;
    
    private ObservableList<Product> listProduit;

    private int prix_total;
    private List<Product> choixProduit;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        choixProduit = Utilis.panier.getProduits();

        if(choixProduit==null || Utilis.panier.getPrestations()==null) {
        	Alert alertVide = new Alert(Alert.AlertType.ERROR, Utilis.ErrorPanier, ButtonType.OK);
            alertVide.show();
        }
        
        listProduit = FXCollections.observableArrayList(choixProduit);
        table_view1.setItems(listProduit);
        produit_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixPro_col.setCellValueFactory(new PropertyValueFactory<>("prixU"));
        quantite_col.setCellValueFactory(new PropertyValueFactory<>("quantite"));

    }

    @FXML
    private void handlerButton(){

    }
}
