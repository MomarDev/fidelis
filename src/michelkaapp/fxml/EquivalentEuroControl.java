package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import michelkaapp.objets.Parametre;
import michelkaapp.objets.User;
import michelkaapp.utilis.PasswordInputDialog;
import michelkaapp.utilis.Utilis;

public class EquivalentEuroControl implements Initializable{

	@FXML
	private Label unite_euro;

	@FXML
	private Label montantCfa;
	@FXML
	private Button btn_quitter;
	@FXML
	private Button btn_save;
	@FXML
	private ComboBox<String> comboOp;
	@FXML
	private ComboBox<String> comboMode;
	private User user;
	private List<User> listOperateur;
	private List<String> nomOperateur = new ArrayList<String>();
	private Parent root;
	private Stage stage;

	private ObservableList<String> mode = FXCollections.observableArrayList(
	            "Chéque",  "Espèce"
	    );
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 listOperateur = Utilis.driver.listUser();
		 
		 for (User op : listOperateur) {
	            nomOperateur.add(op.getUsername());
	        }

	        comboOp.getItems().addAll(nomOperateur);
		// TODO Auto-generated method stub
		
		comboMode.getItems().addAll(mode);

		unite_euro.setText(Utilis.trouverParametre("TAUX_EURO_EN_FCFA").getValeur()+" FCFA");

		montantCfa.setText(Utilis.separatorNumber(ModePayementControl.net/Integer.parseInt(Utilis.trouverParametre("TAUX_EURO_EN_FCFA").getValeur())) +" Euros");
     		

	}

	@FXML
	private void handlerButton(ActionEvent event) {
		if(event.getSource()==btn_quitter) {
			Alert alert = new Alert(AlertType.CONFIRMATION,Utilis.Quitter);
			Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
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
            } else {
                alert.close();
            }
			
		}
		
		
		if(event.getSource()==btn_save){

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

                        try {
                        	ModePayementControl.mode=comboMode.getValue();
                        	ModePayementControl.user=user;
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
