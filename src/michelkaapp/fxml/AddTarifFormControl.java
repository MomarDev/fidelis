package michelkaapp.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import michelkaapp.objets.Prestation;
import michelkaapp.objets.Tarif;
import michelkaapp.utilis.Utilis;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddTarifFormControl implements Initializable {
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Button btn_quitter;
    @FXML
    private ComboBox combo_sexe;
    @FXML
    private ComboBox combo_age;
    @FXML
    private TextField fdl_prix;
    @FXML
    private TextField prestation;
    @FXML
    private TextField fld_prestation;
    private String sex;
    private String age;
    private String prest;
    private String details;
    private int prix;
    private Tarif tarif= new Tarif();
    private ObservableList<String> listAge = FXCollections.observableArrayList(
            " ADULTE", " ADOS -20 ans",  " Garçon -12 ans", " BEBE -5 ANS",  " FILLETTE -12 ANS"
    );
    private ObservableList<String> sexe = FXCollections.observableArrayList("Homme","Femme");
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        combo_age.getItems().addAll(listAge);
        combo_sexe.getItems().addAll(sexe);
        
        fdl_prix.textProperty().addListener((obs, old, nv) -> {
        	if (!nv.isEmpty() && !Utilis.isNumber(nv)) {
                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
                alertErrorNumber.show();
            }
        });
        
       
    }

    @FXML
    private void handlerButtonAction(ActionEvent event) {
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

        	if(combo_sexe.getValue()!=null && combo_age.getValue()!=null && prestation.getText()!=null && fld_prestation.getText()!=null && fdl_prix.getText()!=null) {
        		sex=(String) combo_sexe.getValue();
        		age =(String) combo_age.getValue();
        		prest=prestation.getText();
        		details=fld_prestation.getText();
        		prix=Integer.parseInt(fdl_prix.getText());
        		
        		tarif.setSexe(sex);
        		tarif.setNom(age);
        		tarif.setPrestation(prest);
        		tarif.setDetail(details);
        		tarif.setPrix(prix);

                Stage stage = (Stage) btn_enregistrer.getScene().getWindow();
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();

        		Utilis.driver.insertTarif(tarif);
        		  Alert alert = new Alert(Alert.AlertType.INFORMATION," Prestation enregistrée avec succés!!!");
                  alert.showAndWait();
        	}else {
        		 Alert alert = new Alert(Alert.AlertType.ERROR," Veuillez renseigner les champs vides");
                 alert.showAndWait();
        	}
        		
        }
    }
}
