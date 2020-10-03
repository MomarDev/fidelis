package michelkaapp.fxml;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import michelkaapp.objets.Carte;
import michelkaapp.objets.Client;
import michelkaapp.objets.Product;
import michelkaapp.objets.Restant;
import michelkaapp.objets.Tarif;
import michelkaapp.objets.User;
import michelkaapp.objets.Visite;
import michelkaapp.utilis.PasswordInputDialog;
import michelkaapp.utilis.Utilis;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModePayementControl implements Initializable {
    @FXML
    private Button btn_payer;
    @FXML
    private Button btn_retour;
    @FXML
    private Label prixInit;
    @FXML
    private Label prixTotal;
    @FXML
    private TextField remise;
    @FXML
    private ComboBox operateur;
    @FXML
    private CheckBox c1;
    @FXML
    private CheckBox c2;
    @FXML
    private CheckBox c3;
    @FXML
    private CheckBox c4;
    @FXML
    private AnchorPane pan_general;
    @FXML
    private TextField carte;
    @FXML
	private Button euro;
    @FXML
    private TextField textCarte;
    @FXML
    private TextField textCheque;
    @FXML
    private TextField textEspece;
    @FXML
    private TextField textAttente;

    int selected=0;
    private int paie =0;
    public static boolean cli=false;
    public static User user;
    private Carte carteCli;
    public static Client client;
    public static int net=0;
    private Parent root;
    private Stage stage;
    private int prixInitial = 0;
    private int prixReInitial = 0;
    private List<Tarif> choixAchat;
    private List<Product> choixProduit;
    
    public static int maner;
    public static int restant;
    public static String mode;
    private List<User> listOperateur;
    private List<String> nomOperateur = new ArrayList<String>();
    public static int varCard;
    public static int varCheque;
    public static int varEspece;
    public static int tva;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	
		textCarte.setVisible(false);
		textCheque.setVisible(false);
		textEspece.setVisible(false);
		textAttente.setVisible(false);
    	restant=0;
    	maner=0;
        varCard=0;
        varCheque=0;
        varEspece=0;
    	tva=0;
    	listOperateur = Utilis.driver.listUser();
    	
    	for (User op : listOperateur) {
			nomOperateur.add(op.getUsername());
		}
    	
    	operateur.getItems().addAll(nomOperateur);
        
        choixAchat = ChoixAchatPrestationControl.selectedForPrestation;
        choixProduit = PayementController.selectedForProduit;

        
        for (Tarif tarif : choixAchat) {
            prixInitial = tarif.getPrix()+ prixInitial;

        }

        for (Product product : choixProduit) {
            prixInitial = product.getPrixTotal()+ prixInitial;

        }
        prixReInitial=prixInitial;
        prixInit.setText(Utilis.separatorNumber(prixInitial)+" FCFA");
        prixTotal.setText(Utilis.separatorNumber(prixInitial) +" FCFA");
        net=prixInitial;
        
        client =null;
        if (!ValidationChequeCadeauControl.cheque.isEmpty()) {
			Label cadeau = new Label();
			cadeau.setText(ValidationChequeCadeauControl.cheque.get(ValidationChequeCadeauControl.cheque.size()-1).getDetail());
			cadeau.setLayoutX(158.0);
			cadeau.setLayoutY(139.0);
			pan_general.getChildren().add(cadeau);
			prixInitial=prixInitial-ValidationChequeCadeauControl.cheque.get(ValidationChequeCadeauControl.cheque.size()-1).getPrix();
			prixTotal.setText(Utilis.separatorNumber(prixInitial) +" FCFA");
	        net=prixInitial;
	        prixReInitial=prixInitial;
		}
        Label benefice = new Label();
            benefice.setLayoutX(365.0);
            benefice.setLayoutY(106.0);
        carte.textProperty().addListener((obs, old, nv) -> {
            
            prixInitial=prixReInitial;
            net= prixReInitial;
            prixTotal.setText(Utilis.separatorNumber(prixInitial) +" FCFA");
        	if (nv != null && !nv.isEmpty() && Utilis.isNumber(nv)) {
        		client = Utilis.driver.rechercheClientFormCarte(Integer.parseInt(carte.getText()));
        		if(client!=null) {
        			
        			
        	                carteCli = Utilis.driver.trouverCarte(client);
        	                List<Visite> visites = Utilis.driver.listVisites(carteCli.getReference());
        	                int size = visites == null ? 0 : visites.size();
        	                if (size >= Integer.parseInt(Utilis.trouverParametre("FIDELITE_VISITE").getValeur()) && carteCli.getNptPrestation() > 0) {
        	                    Stage impStage = new Stage();
        	                    impStage.setResizable(false);
        	                    FXMLLoader impRoot = null;
        	                    VBox pane = null;
        	                    try {
        	                    	  
        	                        impRoot = new FXMLLoader(getClass().getResource("ImpressionFidelite.fxml"));
        	                        pane = impRoot.load();
        	                        ImpressionFideliteController controller = (ImpressionFideliteController) impRoot.getController();
        	                        controller.newInstence(client, "nombre_fidelite_prestation", true);
        	                       
        	                    } catch (IOException ex) {
        	                        Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
        	                    }
        	                    benefice.setText(Utilis.separatorNumber(client.getCarte().getNptPrestation())+ " FCFA");
        	                    cli=true;
        	                    impStage.setScene(new Scene(pane));
        	                    impStage.show();
        	                }else {
        	                	Stage impStage = new Stage();
        	                	VBox pane = null;
        	                	try {
        	                		
            	                    impStage.setResizable(false);
        	        				FXMLLoader impRoot = new FXMLLoader(getClass().getResource("NiveauVisite.fxml"));
        	        			    pane =impRoot.load();
        	        			    NiveauVisiteControl controller = (NiveauVisiteControl) impRoot.getController();
        	                        controller.newInstence(client, "nombre_fidelite_prestation");
        	                        
        	        	            
        	        			} catch (IOException ex) {
        	                        Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);

        	        			}
        	                	cli=false;
        	                	 impStage.setScene(new Scene(pane));
         	                    impStage.show();
        	                	//client =null;
        	                }
        	                
        	                if(cli) {
        	                	prixTotal.setText(Utilis.separatorNumber(prixInitial-client.getCarte().getNptPrestation())+ " FCFA");
            	                
             	               prixInitial = prixInitial-client.getCarte().getNptPrestation();
             	               net=prixInitial;
        	                }
        	            
        	                
        		}
        		    
        	}
            
        });
        
        pan_general.getChildren().add(benefice);
        
        
        
        remise.textProperty().addListener((obs, old, nv) -> {
            if (!nv.isEmpty() && !Utilis.isNumber(nv)) {
                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
                alertErrorNumber.show();
            } else {
           	 int r = prixInitial*Integer.parseInt(remise.getText()) / 100;
           	 net=prixInitial-r;
           	 prixTotal.setText(Utilis.separatorNumber(net)+ " FCFA");
           	 tva=Integer.parseInt(remise.getText());
            }
            

        });

    }

    @FXML
    private void handlerButton(ActionEvent event){
		if (event.getSource()== euro){
			try {
				root = FXMLLoader.load(getClass().getResource("EquivalentEuro.fxml"));
				stage = (Stage) btn_retour.getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        if (event.getSource()== btn_payer){
        	
        	if(c1.isSelected() || c2.isSelected() || c3.isSelected() || c4.isSelected()) {
        		if (c1.isSelected()) {
					mode="Carte bancaire";
				}else if(c2.isSelected()){
					mode="Chéque";
				}else if(c3.isSelected()){
					mode="Espèce";
				}else if(c4.isSelected()){
					mode="Attente";
					restant=net;
				}
        		
        		if(maner!=0) {
        			if((textCarte.getText().isEmpty()&& textCheque.getText().isEmpty()&& textEspece.getText().isEmpty())||
        					(textCarte.getText().contains("-")|| textCheque.getText().contains("-")|| textEspece.getText().contains("-")|| textAttente.getText().contains("-"))) {
        				Alert alert = new Alert(AlertType.INFORMATION);
                		alert.setHeaderText("Information");
                		alert.setContentText("Veuillez vérifier les valeurs saisies!");
                		alert.showAndWait();
                		
        			}else {
            			
        				if(!Utilis.isNumber(textCheque.getText()) && !Utilis.isNumber(textEspece.getText())) {
        					
        				}
            			if (operateur.getValue()!=null) {
                    		
                    		String userOp = (String) operateur.getValue();
                    		PasswordInputDialog passwordDialog = new PasswordInputDialog();
                    		passwordDialog.setHeaderText("Confirmer avec votre mot de Passe");
                    		passwordDialog.setContentText("Mot de Passe");
                    		  user = Utilis.driver.getUserPasseword(userOp);
                    		 System.out.println(user);
                    		Optional<String> result = passwordDialog.showAndWait();
                    		if(result.isPresent()) {
                    			System.out.println(result);
                    			if (result.get().equals(user.getPassword())) {
                    				
                    				
                    				if(mode.equals("Attente") || maner==3 || maner==5 || maner==6 ) {
                    					if(client==null) {
                    						try {
                                                root = FXMLLoader.load(getClass().getResource("Dette.fxml"));
                                                stage = (Stage) btn_payer.getScene().getWindow();
                                                Scene scene = new Scene(root);
                                                stage.setScene(scene);
                                                stage.show();

                                            } catch (IOException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                    					}else {
                    						
                    						
                    						Restant restant= new Restant();
                    						restant.setNomCli(client.getNom());
                    						restant.setPrenomCli(client.getPrenom());
                    						restant.setTelephoneCli(client.getTelDomicile());
                    						restant.setCommentaire("Numéro de carte: "+client.getCarte().getReference());
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
                    						Utilis.driver.insertRestant(restant);
                    					}
                    					
                    					
                                		
                                		
                                		
                                	}
                    				
                    				
                    					 try {

                                         	
                                         	
                                         	
                                             root = FXMLLoader.load(getClass().getResource("facture.fxml"));
                                             stage = (Stage) btn_payer.getScene().getWindow();
                                             Scene scene = new Scene(root);
                                             stage.setScene(scene);
                                             stage.show();

                                         } catch (IOException e) {
                                             // TODO Auto-generated catch block
                                             e.printStackTrace();
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
        		}else {
        			
        			
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
                				
                				
                				if(mode.equals("Attente") || maner==3 || maner==5 || maner==6 ) {
                					if(client==null) {
                						try {
                                            root = FXMLLoader.load(getClass().getResource("Dette.fxml"));
                                            stage = (Stage) btn_payer.getScene().getWindow();
                                            Scene scene = new Scene(root);
                                            stage.setScene(scene);
                                            stage.show();

                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                					}else {
                						
                						
                						Restant restant= new Restant();
                						restant.setNomCli(client.getNom());
                						restant.setPrenomCli(client.getPrenom());
                						restant.setTelephoneCli(client.getTelDomicile());
                						restant.setCommentaire("Numéro de carte: "+client.getCarte().getReference());
                						
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
                						
                						Utilis.driver.insertRestant(restant);
                					}
                					
                					
                            		
                            		
                            		
                            	}
                				
                				
                					 try {

                                     	
                                     	
                                     	
                                         root = FXMLLoader.load(getClass().getResource("facture.fxml"));
                                         stage = (Stage) btn_payer.getScene().getWindow();
                                         Scene scene = new Scene(root);
                                         stage.setScene(scene);
                                         stage.show();

                                     } catch (IOException e) {
                                         // TODO Auto-generated catch block
                                         e.printStackTrace();
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

        	else {
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setHeaderText("Information");
        		alert.setContentText("Veuillez choisir le mode paiement");
        		alert.showAndWait();
        	}
        	
        	
        

        } else if (event.getSource()== btn_retour){
            try {
                root = FXMLLoader.load(getClass().getResource("Panier.fxml"));
                stage = (Stage) btn_retour.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    @FXML
    private void checkPaid(ActionEvent event) {
    	
    	
    	if(c1.isSelected()&&c2.isSelected()&&c4.isSelected() ||c1.isSelected()&&c2.isSelected()&&c3.isSelected()) {
    		
            		Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR," Vous ne pouvez pas choisir trois mode de paiement différents", ButtonType.OK);
                    alertErrorNumber.show();
            	    c3.setSelected(false);
            	    c4.setSelected(false);
            	    
    	}
    	if(c3.isSelected()&&c2.isSelected()&&c4.isSelected()||c3.isSelected()&&c1.isSelected()&&c4.isSelected()) {
    		
    		Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, " Vous ne pouvez pas choisir trois mode de paiement différents", ButtonType.OK);
            alertErrorNumber.show();
    	    c4.setSelected(false);
}
    	

    	     
             
    		if((c1.isSelected() && c2.isSelected())) {
    			
        		
    			
        		maner=1;
        		textCarte.setVisible(true);
        		
        		textCarte.textProperty().addListener((obs, old, nv) -> {
        			if (!nv.isEmpty() && Utilis.isChaine(nv)) {
    	                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNombre, ButtonType.OK);
    	                alertErrorNumber.show();
    	            }else {
    	            	 //convert.setText(Utilis.separatorNumber(Integer.parseInt(fond.getText()))+ " FCFA");
                		varCard=Integer.parseInt(textCarte.getText());
                		textCheque.setText(Utilis.separatorNumber(net-varCard)+ " FCFA");
    	            }
               	
               	 
            		
                });
        		
        		textCheque.setVisible(true);
        		
        		textCheque.setEditable(false);
        		textEspece.setVisible(false);
        		textAttente.setVisible(false);
        		
        		
        		
    		}else {
    			  maner=0;
    			textCheque.setVisible(false);
    			textCarte.setVisible(false);
    			//pan_general.getChildren().remove(pan_general.getChildren().size()-1);
    			
    		}
    		if(c1.isSelected()&&c3.isSelected()) {
    			maner=2;
    			textCarte.setVisible(true);
    			
    			textCarte.textProperty().addListener((obs, old, nv) -> {
    				
    				if (!nv.isEmpty() && Utilis.isChaine(nv)) {
    	                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNombre, ButtonType.OK);
    	                alertErrorNumber.show();
    	            }else {

                    	  //convert.setText(Utilis.separatorNumber(Integer.parseInt(fond.getText()))+ " FCFA");
                 		varCard=Integer.parseInt(textCarte.getText());
                 		textEspece.setText(Utilis.separatorNumber(net-varCard)+ " FCFA");
    	            }
               		
                   });
    			textEspece.setVisible(true);
    			
    			textEspece.setEditable(false);
    			textCheque.setVisible(false);
    			textAttente.setVisible(false);
        		
    		}else {
    			
    			textEspece.setVisible(false);
    		}
    	
    		if(c1.isSelected()&&c4.isSelected()) {
    			maner=3;
    			textCarte.setVisible(true);
    			
    			textCarte.textProperty().addListener((obs, old, nv) -> {
    				if (!nv.isEmpty() && Utilis.isChaine(nv)) {
    	                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNombre, ButtonType.OK);
    	                alertErrorNumber.show();
    	            }else {
    	            	
    	            	 //convert.setText(Utilis.separatorNumber(Integer.parseInt(fond.getText()))+ " FCFA");
                   		varCard=Integer.parseInt(textCarte.getText());
                   		textAttente.setText(Utilis.separatorNumber(net-varCard)+ " FCFA");
    	            }
                  	
                  	 
               		
                   });
    			textAttente.setVisible(true);
    			
    			textAttente.setEditable(false);
    			textCheque.setVisible(false);
    			textEspece.setVisible(false);
        		
    		}else {
    			
    			textAttente.setVisible(false);
    			//
    		}
    		
    		if(c2.isSelected()&&c3.isSelected()) {
    			maner=4;
    			textCarte.setVisible(false);
    			textAttente.setVisible(false);
    			textCheque.setVisible(true);
    			textCheque.setEditable(true);
    			
    			textCheque.textProperty().addListener((obs, old, nv) -> {
    				if (!nv.isEmpty() && Utilis.isChaine(nv)) {
    	                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNombre, ButtonType.OK);
    	                alertErrorNumber.show();
    	            }else {
    	            	//convert.setText(Utilis.separatorNumber(Integer.parseInt(fond.getText()))+ " FCFA");
                   		varCheque=Integer.parseInt(textCheque.getText());
                   		textEspece.setText(Utilis.separatorNumber(net-varCheque)+ " FCFA");
    	            	
    	            }
                  	
                  	  
               		
                   });
    			textEspece.setVisible(true);
    			
    			textEspece.setEditable(false);
        		
    		}else {
    			
    			//
    		}
    		
    		
    		if(c2.isSelected()&&c4.isSelected()) {
    			maner=5;
    			textCarte.setVisible(false);
    			
    			
    			textCheque.setVisible(true);
    			textCheque.setEditable(true);
    			
    			textCheque.textProperty().addListener((obs, old, nv) -> {
    				if (!nv.isEmpty() && Utilis.isChaine(nv)) {
    	                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNombre, ButtonType.OK);
    	                alertErrorNumber.show();
    	            }else {

                   	  //convert.setText(Utilis.separatorNumber(Integer.parseInt(fond.getText()))+ " FCFA");
                		varCheque=Integer.parseInt(textCheque.getText());
                		textAttente.setText(Utilis.separatorNumber(net-varCheque)+ " FCFA");
    	            	
    	            }
                 	 
              		
                  });
    			textAttente.setVisible(true);
    			
    			textEspece.setVisible(false);
    			textAttente.setEditable(false);
        		
    		}else {
    			
    			//
    		}
    		
    		if(c3.isSelected()&&c4.isSelected()) {
    			maner=6;
    			textCarte.setVisible(false);
    			
    			
    			textCheque.setVisible(false);
    			textEspece.setVisible(true);
    			textEspece.setEditable(true);
    			
    			textEspece.textProperty().addListener((obs, old, nv) -> {
    				if (!nv.isEmpty() && Utilis.isChaine(nv)) {
    	                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNombre, ButtonType.OK);
    	                alertErrorNumber.show();
    	            }else {
    	            	
    	            	//convert.setText(Utilis.separatorNumber(Integer.parseInt(fond.getText()))+ " FCFA");
                 		varEspece=Integer.parseInt(textEspece.getText());
                 		textAttente.setText(Utilis.separatorNumber(net-varEspece)+ " FCFA");
    	            }
                	
                	  
             		
                 });
    			
    			textAttente.setVisible(true);
    			
    			textAttente.setEditable(false);
        		
    		}else {
    			
    			//
    		}
    		
    	    }
   
    }
