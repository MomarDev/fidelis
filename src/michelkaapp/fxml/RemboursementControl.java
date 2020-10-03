package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import michelkaapp.objets.Restant;
import michelkaapp.objets.User;
import michelkaapp.utilis.PasswordInputDialog;
import michelkaapp.utilis.Utilis;

public class RemboursementControl implements Initializable {
	
	@FXML
	private Button btn_save;
	@FXML
	private Button btn_quitter;
 	@FXML
	private Label nomCli;
	@FXML
	private ComboBox<String> comboMode;
	@FXML
	private Label montant;
	@FXML
	private ComboBox<String> comboOp;
	
	private Parent root;
	private Stage stage;
	private User user;
	private List<User> listOperateur;
	private List<String> nomOperateur = new ArrayList<String>();


	SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    Date today = new Date();
    
    private ObservableList<String> mode = FXCollections.observableArrayList(
            "Carte Bancaire", "Chéque",  "Espéce"
    );
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 listOperateur = Utilis.driver.listUser();
		 
		 for (User op : listOperateur) {
	            nomOperateur.add(op.getUsername());
	        }

	        comboOp.getItems().addAll(nomOperateur);

		 montant.setText(Utilis.separatorNumber(RestantTotalControl.rest)+" FCFA");
		 nomCli.setText(RestantTotalControl.name);
		
		// TODO Auto-generated method stub

		
			montant.textProperty().addListener((obs, old, nv) -> {
          	
			if (!nv.isEmpty() && !Utilis.isNumber(nv)) {
                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
                alertErrorNumber.show();
			}
			
			ModePayementControl.net=Integer.parseInt(montant.getText());
     		
         });
			
			
		

		comboMode.getItems().addAll(mode);
	}
	
	@FXML
	private void handlerButton(ActionEvent event) {
		
		if(event.getSource()==btn_quitter) {
			Alert alert = new Alert(AlertType.CONFIRMATION,Utilis.Quitter);
			Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_quitter.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
			
		}
		
		
		if(event.getSource()==btn_save) {
			 if (comboOp.getValue()!=null){

	                String userOp = (String) comboOp.getValue();
	                PasswordInputDialog passwordDialog = new PasswordInputDialog();
	                passwordDialog.setHeaderText("Confirmer avec votre mot de Passe");
	                passwordDialog.setContentText("Mot de Passe");
	                
	                System.out.println(user);
	                Optional<String> result = passwordDialog.showAndWait();
	                if(result.isPresent()) {
	                    System.out.println(result);
	                    user = Utilis.driver.getUserPasseword(result.get());
	                    if (user!=null) {

	                    	if(Utilis.driver.selectRestant(RestantTotalControl.tel)!=null){
								Utilis.driver.deleteEncaisser(RestantTotalControl.rest,Utilis.driver.getLastOpenCaisse());
							}
							Utilis.driver.deleteRestant(RestantTotalControl.tel);
	                    	Utilis.driver.insertEncaisser(RestantTotalControl.rest, 0, "remboursement");
	                        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
		                        alert.setContentText("Remboursement enregistré avec succés!!");
		                        alert.showAndWait(); 
		                        Stage stage = (Stage) btn_save.getScene().getWindow();
		                        stage.close();
		                      
	                       
	                    }

	                    else {
	                        Alert alert = new Alert(Alert.AlertType.ERROR);
	                        alert.setContentText("Mot de passe incorrect");
	                        alert.showAndWait();
	                    }

	                }


	            }else {
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setHeaderText("Information");
	                alert.setContentText("Veuillez choisir votre nom avant de valider");
	                alert.showAndWait();
	            }
		}
		
	}
	
	

}
