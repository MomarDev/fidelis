package michelkaapp.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import michelkaapp.objets.Restant;
import michelkaapp.utilis.Utilis;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetteControl implements Initializable {
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField telephone;
    @FXML
    private TextArea commentaire;
    @FXML
    private Button btn_save;
    @FXML
    private Button btn_quitter;

    private Parent root;
    private Stage stage;
    private Restant restant= new Restant();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nom.textProperty().addListener((obs, old, nv) -> {

            restant.setNomCli(nom.getText());
        });

        prenom.textProperty().addListener((obs, old, nv) -> {

            restant.setPrenomCli(prenom.getText());
        });

        telephone.textProperty().addListener((obs, old, nv) -> {

            restant.setTelephoneCli(telephone.getText());
        });

        commentaire.textProperty().addListener((obs, old, nv) -> {

            restant.setCommentaire(commentaire.getText());
        });


        if(ModePayementControl.maner==0) {
        	restant.setRestant(ModePayementControl.restant);
        }
        if(ModePayementControl.maner==3) {
        	restant.setRestant(ModePayementControl.net-ModePayementControl.varCard);
        }
        
        if(ModePayementControl.maner==5) {
        	restant.setRestant(ModePayementControl.net-ModePayementControl.varCheque);
        }
        
        if(ModePayementControl.maner==6) {
        	restant.setRestant(ModePayementControl.net-ModePayementControl.varEspece);
        }
    }

    @FXML
    private void handlerButton(ActionEvent event){
        if (event.getSource()== btn_quitter){

            try {
                root = FXMLLoader.load(getClass().getResource("ModePayement.fxml"));
                stage = (Stage) btn_quitter.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (event.getSource()==btn_save){
            Utilis.driver.insertRestant(restant);
            try {
                root = FXMLLoader.load(getClass().getResource("facture.fxml"));
                stage = (Stage) btn_save.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
