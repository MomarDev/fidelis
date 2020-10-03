package michelkaapp.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import michelkaapp.objets.Trace;
import michelkaapp.objets.User;
import michelkaapp.utilis.PasswordInputDialog;
import michelkaapp.utilis.Utilis;


import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class CaisseControl implements Initializable {
    @FXML
    private Button btn_ouvrir;
    @FXML
    private Button btn_quitter;
    @FXML
    private ComboBox operateur;
    @FXML
    private Label date;
    @FXML
    private TextField fond;
    @FXML
    private TextArea commentaire;
    @FXML
    private Label convert;
    public static String funds=null;
    public static String com= null;
    public static User user;
    private Parent root;
    private Stage stage;
    private List<User> listOperateur;
    private Alert alertF;
    private List<String> nomOperateur = new ArrayList<String>();
    private SimpleDateFormat format = new SimpleDateFormat("EEEE, d MMM yyyy");
    private Date today = new Date();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	 funds=null;
         com= null;
         fond.textProperty().addListener((obs, old, nv) -> {
        	 if (!nv.isEmpty() && !Utilis.isNumber(nv)) {
                 Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNombre, ButtonType.OK);
                 alertErrorNumber.show();
             }
        	 convert.setText(Utilis.separatorNumber(Integer.parseInt(fond.getText()))+ " FCFA");
     		funds=fond.getText();
     		
         });
    	commentaire.textProperty().addListener((obs, old, nv) -> {
    		
    		com=commentaire.getText();
        });
       
    	
    	
    	
        date.setText(format.format(today));
        listOperateur = Utilis.driver.listUser();
    	
    	for (User op : listOperateur) {
			nomOperateur.add(op.getUsername());
		}
    	
    	operateur.getItems().addAll(nomOperateur);

    }

    @FXML
    private void handlerButton(ActionEvent event){

        if (event.getSource()==btn_ouvrir){
        	if(funds==null) {
        		 Alert alert = new Alert(Alert.AlertType.ERROR,"Veuillez renseigner le fonds de caisse svp!!");
        		 alert.showAndWait();
        	}
        	else {
        		
        		
        		if (operateur.getValue()!=null) {
            		
            		String userOp = (String) operateur.getValue();
            		PasswordInputDialog passwordDialog = new PasswordInputDialog();
            		passwordDialog.setHeaderText("Confirmer avec votre mot de Passe");
            		passwordDialog.setContentText("Mot de Passe");
            		
            		 System.out.println(user);
            		Optional<String> result = passwordDialog.showAndWait();
            		if(result.isPresent()) {
            			System.out.println(result);
            			  user = Utilis.driver.getUserPasseword(result.get());
            			if (user!=null) {
            				
            				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            	            alert.setContentText("Etes vous sûr de vouloir ouvrir la caisse ?");
            	            Optional<ButtonType> resultat = alert.showAndWait();
            	            if (resultat.get() == ButtonType.OK) {
            	            	
            	            	 alertF = new Alert(AlertType.INFORMATION,"La caisse est ouverte avec succés!!!");
            	                 alertF.setHeaderText("Ouverture Caisse");
            	                 alertF.show();
            				
            				try {
                                root = FXMLLoader.load(getClass().getResource("TicketOuverture.fxml"));
                                stage = (Stage) btn_ouvrir.getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                                stage.setOnCloseRequest(event1 -> {
                                    Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                                    Utilis.entrainPayer=false;
                                    Utilis.caisse=1;
                                    Trace trace = new Trace();
                                    trace.setAction("Caisse");
                                    trace.setObjet("Ouverture");
                                    trace.setUser(CaisseControl.user);
                                    
                                    
                                    
                                    trace.setValeur(fond.getText());
                                    Utilis.driver.insertTrace(trace);
                                   
                                });

                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
            				}else {
            					
            				}
    					}
            			
            			else {
                			Alert alert = new Alert(AlertType.ERROR);
                			alert.setContentText("Mot de passe incorrect");
                			alert.showAndWait();
                		}
            			
            		}
            		
            		
    				
            	}else {
            		Alert alert = new Alert(AlertType.INFORMATION);
            		alert.setHeaderText("Information");
            		alert.setContentText("Veuillez choisir votre nom avant de valider");
            		alert.showAndWait();
            	}	
        		
        		
        		
        		
        		
        	}
            
        }

        if (event.getSource() == btn_quitter) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

				try {
                    root = FXMLLoader.load(getClass().getResource("Payement.fxml"));
                    stage = (Stage) btn_quitter.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    stage.setOnCloseRequest(event1 -> {
                        Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                        Utilis.entrainPayer=false;
                        Utilis.caisse=1;
                       
                    });

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Stage stage = (Stage) btn_quitter.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        }

    }
}
