/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import michelkaapp.objets.*;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class PayementController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane pane_achat;

    @FXML
    private Button remboursement;
    @FXML
    private Rectangle rect1;
    @FXML
    private Rectangle rect2;
    @FXML
    private AnchorPane caisse_ouverte;
    @FXML
    private Button btn_valider;
    @FXML
    private Button fermer_caisse;
    @FXML
    private Button ouvrir_caisse;

    @FXML
    private AnchorPane ouvre_caisse;
    @FXML
    private Circle rouge;
    @FXML
    private Circle vert;
   
    
    private Parent root;
    private Stage stage;
    @FXML
    private Button btn_suivant;
    @FXML
    private Button btn_retour;
    @FXML
    private ImageView imv_add_achat;
    @FXML
    private AnchorPane pane_conseil;
    @FXML
    private Button btn_quitter;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Button btn_achat;
    @FXML
    private Label non_client;
    @FXML
    private Button quit;
    int a=0;
    @FXML
    private RadioButton radioButton_homme;
    @FXML
    private RadioButton radioButton_femme;
    @FXML
    private RadioButton radioButton_prestation;
    @FXML
    private RadioButton radioButton_divers;
    @FXML
    private TextField code_barre;
    @FXML
    private TextField nom_produit;
    @FXML
    private TextField prix_produit;
    @FXML
    private TextField prix_total;
    @FXML
    private Label reference_carte;
    @FXML
    private TextArea conseil;
    public static Produit produit = new Produit();
    
    public static List<Tarif> t= null;
    public static List<Tarif> tc= null;
    private String conseil_texte;
    private Client client;
    private Carte carte;
    private SuiviMaison suiviMaison;
    private Achat achat;
    private String sexe;
    private String nomTarif;
    @FXML
    private Label nom_client_achat;
    @FXML
    private ComboBox<String> ageCombo;
    String test;
    @FXML
    private Label reference_carte_achat;
    @FXML
    private VBox conteneur_ventes;
    private ObservableList<Produit> ob = null;
    List<HBox> listCountableAchat = null;
    List<ProduitAchete> paniers = new ArrayList<ProduitAchete>();
    private static int count = 0;
    private ImageView imageView = null;
    @FXML
    private AnchorPane pan_prestation;
    @FXML
    private AnchorPane pan_divers;
    private List<Produit> produitsActuel = null;
    private Produit selectedProduit = null;
    private int quantiteAchate = 1;
    @FXML
    private Label prix_total_fd;
    
    public static List<Product> selectedForProduit= new ArrayList<>();
    
    private boolean listVide = false;
    public static User user;
    private List<Product> products = new ArrayList<Product>();

    private ObservableList<String> listAgeO = FXCollections.observableArrayList(
            " ADULTE", " ADOS -20 ans",  " Garçon -12 ans", " BEBE -5 ANS"
    );
    
    private ObservableList<String> listAgeF = FXCollections.observableArrayList(
            " ADULTE", " ADOS -20 ans",  " FILLETTE -12 ANS", " BEBE -5 ANS"
    );

    /**
     * Initializes the controller class.
     */
    @FXML
    private void actionAge(ActionEvent event) {
    	//ageCombo.getItems().addAll(listAgeF);
    	if (event.getSource()== radioButton_homme) {
    		sexe= radioButton_homme.getText();
    		if(ageCombo.getItems().isEmpty()) {
    			ageCombo.getItems().addAll(listAgeO);
    			
    		}
    		else if(!ageCombo.getItems().isEmpty() && ageCombo.getItems().contains(" FILLETTE -12 ANS")) {
    			ageCombo.getItems().removeAll(listAgeF);
    			ageCombo.getItems().addAll(listAgeO);
    		}
    		
		}else
    	if (event.getSource()== radioButton_femme) {
    		sexe= radioButton_femme.getText();
    		if(ageCombo.getItems().isEmpty()) {
    			ageCombo.getItems().addAll(listAgeF);
    		}
    		else if(!ageCombo.getItems().isEmpty() && ageCombo.getItems().contains(" Garçon -12 ans")) {
    			ageCombo.getItems().removeAll(listAgeO);
    			ageCombo.getItems().addAll(listAgeF);
    		}
		}
    }
    
    @FXML
    private void actionAchat(ActionEvent event) {
    	if (event.getSource()== radioButton_prestation) {
    		pan_prestation.setVisible(true);
    		
    		}else {
				pan_prestation.setVisible(false);
			}
    	if(event.getSource()== radioButton_divers) {
    	pan_divers.setVisible(true);
    	
    	}else {
    		pan_divers.setVisible(false);
    	}
    	
    }
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<Produit> list = Utilis.driver.listProduit("WHERE produit.supprime = 0 and utilisation_produit = 0 ");
    	if (list != null) {
            ob = FXCollections.observableArrayList(list);
        }
       
    	if(Utilis.caisse==0) {
    		fermerCaisse();
    	}else {
    		ouvrirCaisse();
    	}
    	
    	
       
    	
    	        
    	conteneur_ventes.getChildren().add(faireProduitList());
    	conteneur_ventes.getChildren().add(new Separator(Orientation.HORIZONTAL));
            //listVide = false;
           
    	Utilis.entrainPayer=false;
       
    }
    

     
    

    @FXML
    private void handlerButtonAchat(ActionEvent event) {
        if (event.getSource() == btn_quitter) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            	 Utilis.entrainPayer=false;
                Stage stage = (Stage) btn_quitter.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        }
        
        if(event.getSource()==remboursement) {
        	 Stage impStage = new Stage();
             impStage.setResizable(false);
             FXMLLoader impRoot = null;
             AnchorPane pane = null;
             try {
            	 Utilis.driver.listRestants();
                 impRoot = new FXMLLoader(getClass().getResource("RestantTotal.fxml"));
                 pane = impRoot.load();


             } catch (IOException ex) {
                 Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
             }

             impStage.setScene(new Scene(pane));
             impStage.show();
        }
        
                
        
        
        if (event.getSource() == quit) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            	 Utilis.entrainPayer=false;
                Stage stage = (Stage) quit.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        }
        
        if (event.getSource() == fermer_caisse) {
        	  Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Êtes vous sûre de vouloir fermer la caisse ?");
        	  Optional<ButtonType> result = alert.showAndWait();
        	  if(result.get()==ButtonType.OK) {
        		  try {


		                root = FXMLLoader.load(getClass().getResource("FermetureCaisse.fxml"));
		                stage = (Stage) btn_valider.getScene().getWindow();
		                Scene scene = new Scene(root);
		                stage.setScene(scene);
		                stage.show();
		                stage.centerOnScreen();

		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
        		   //fermerCaisse();
                   //Utilis.caisse=0;
        	  }
             
        }
        if (event.getSource() == ouvrir_caisse) {
        	Stage impStage = new Stage();
            impStage.setResizable(false);
            FXMLLoader impRoot = null;
            AnchorPane pane = null;
            try {
            	  
                impRoot = new FXMLLoader(getClass().getResource("Caisse.fxml"));
                pane = impRoot.load();
                
               
            } catch (IOException ex) {
                Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            impStage.setScene(new Scene(pane));
            impStage.initStyle(StageStyle.UTILITY);
            impStage.show();
            //ouvrir_caisse.setDisable(true);
            Stage stage = (Stage) ouvrir_caisse.getScene().getWindow();
            stage.close();
           
        }
        
        if (event.getSource()== btn_valider){
        		Alert alert = new Alert(AlertType.CONFIRMATION);
        		alert.setContentText("Voulez-vous confirmer l'achat de produit ");
        		Optional<ButtonType> result = alert.showAndWait();
        		if(result.get()== ButtonType.OK) {
        			 
          			try {


    			                root = FXMLLoader.load(getClass().getResource("Panier.fxml"));
    			                stage = (Stage) btn_valider.getScene().getWindow();
    			                Scene scene = new Scene(root);
    			                stage.setScene(scene);
    			                stage.show();
    			                stage.setOnCloseRequest(event1 -> {
                                    Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                                    Utilis.entrainPayer=false;
                                    Utilis.entrainPayer=false;

                                });

    			            } catch (IOException e) {
    			                // TODO Auto-generated catch block
    			                e.printStackTrace();
    			            }
        			
        		}
        	      
    					}
    					
        
        else if (event.getSource() == btn_suivant) {
        	if((ageCombo.getValue()==null)) {
        		Alert error = new Alert(Alert.AlertType.WARNING, "Veuillez d'abord selectionner le Sexe et la categorie d'age .");
                 error.show();
        	}else {
            try {
            	
            	nomTarif=ageCombo.getValue();
            	t = Utilis.driver.listTarifForfait(sexe, nomTarif);
            	tc = Utilis.driver.listTarifCarte(sexe, nomTarif);
            	    if(t==null && tc==null) {
            	    	Alert alertPress = new Alert(AlertType.INFORMATION,"Aucune enregistrement concernant le sexe et la categorie d'âge!! ");
            	    	alertPress.setTitle("Information");
            	    	alertPress.setHeaderText("Message d'information");
            	    	alertPress.showAndWait();
            	    }
            	     
            	    else {
            	    	root = FXMLLoader.load(getClass().getResource("ChoixAchatPrestation.fxml"));
                        stage = (Stage) btn_suivant.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        stage.setOnCloseRequest(event1 -> {
                            Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                            Utilis.entrainPayer=false;
                            Utilis.entrainPayer=false;

                        });
                        stage.centerOnScreen();
            	    }
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        }
   
       
    }
    private HBox faireProduitList() {
    	ProduitAchete panier = new ProduitAchete();
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setAlignment(Pos.CENTER_LEFT);
        Label prix = new Label("PT: ");
        prix.setPrefWidth(80);
        TextField prixUnitaire = new TextField();
        prixUnitaire.setPromptText("P U.");
        prixUnitaire.setPrefWidth(90);

        TextField codebarres = new TextField();
        TextField nomProduit = new TextField();
        nomProduit.setPromptText("Nom Produit");
        codebarres.setPromptText("Code Barres");
        codebarres.setPrefWidth(110);

        ComboBox<Produit> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(120);
        comboBox.setItems(ob);
           
           
           
        	codebarres.textProperty().addListener((obs, old, nv) -> {
                if (nv != null && !nv.isEmpty() && Utilis.isNumber(nv)) {
                    selectedProduit = Utilis.driver.selectProduitById("code_bar_produit", nv);
                    if (selectedProduit != null && !selectedProduit.getNom().toLowerCase().contains("divers")) {
                        nomProduit.setText(selectedProduit.getNom());
                        panier.setProduit(selectedProduit);
                        prixUnitaire.setText(Utilis.separatorNumber(selectedProduit.getPrix()));
                        prix.setText("PT: " + Utilis.separatorNumber(selectedProduit.getPrix() * 1) + " CFA");
                     
                        
                    }
                }
            });
           
        
        
        
        
             
             prixUnitaire.textProperty().addListener((obs2, old2, nv1) -> {
                 
            	         
                        if(!prixUnitaire.getText().isEmpty()) {
                        	if(codebarres.getText().isEmpty()) {
                        	 
                        	 quantiteAchate=1;
                        	 if (!prixUnitaire.getText().isEmpty() && !Utilis.isNumber(prixUnitaire.getText())) {
                                 Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
                                 alertErrorNumber.show();
                             }
                        	 else {
                        		 selectedProduit = new Produit();
                        		 prix.setText("PT: " + Utilis.separatorNumber(Integer.parseInt(prixUnitaire.getText()) * quantiteAchate) + " CFA");
                                 
                                 //selectedProduit.setNom(nomProduit.getText());
                        		 if(!selectedForProduit.isEmpty()) {
                        			 for (Product prop : selectedForProduit) {
										if(nomProduit.getText().equalsIgnoreCase(prop.getNom())) {
											selectedForProduit.remove(prop);
											 selectedProduit.setPrix(Integer.parseInt(prixUnitaire.getText()));
			                                 selectedProduit.setNom(nomProduit.getText());
										}else {
											 selectedProduit.setPrix(Integer.parseInt(prixUnitaire.getText()));
			                                 selectedProduit.setNom(nomProduit.getText());
										}
									}
                        		 }else {
									 selectedProduit.setPrix(Integer.parseInt(prixUnitaire.getText()));
	                                 selectedProduit.setNom(nomProduit.getText());
								}
                                 
                        	 }
                        	
                        }
                        }
                         
                     
                 
             });         
               
               
               
              
        
        
        ImageView ajoutView = new ImageView();
        ImageView delView = new ImageView();
        Image v = new Image(getClass().getResourceAsStream("addd.png"));
        Image v1 = new Image(getClass().getResourceAsStream("effacer.png"));
        
        delView.setImage(v1);
        delView.setFitWidth(25);
        delView.setFitHeight(25);
        
        ajoutView.setImage(v);
        ajoutView.setFitWidth(25);
        ajoutView.setFitHeight(25);
        ajoutView.setOnMouseClicked(event -> {
        
            try {
                if (selectedProduit != null || (nomProduit.getText()!=null && prixUnitaire!=null)) {
                   
                    panier.setProduit(selectedProduit);
                    panier.setQuantite(quantiteAchate);
                    paniers.add(panier);
                    Produit ver= new Produit();
                    ver = Utilis.driver.rechercheProduit(selectedProduit.getNom());
                    /*if(ver!=null) {
                    	Utilis.venteProduits(paniers);
                    }*/
                    
                    int len = pan_divers.getChildren().size();
                    if (!paniers.isEmpty()) {
                        Product p = new Product(selectedProduit.getNom(), selectedProduit.getPrix(), quantiteAchate, selectedProduit.getPrix() * quantiteAchate,Utilis.separatorNumber(selectedProduit.getPrix()),Utilis.separatorNumber(selectedProduit.getPrix()* quantiteAchate));
                        //ajoutproduit(p);
                        selectedForProduit.add(p);
                        Utilis.panier.setProduits(selectedForProduit);
                        HBox h = (HBox) conteneur_ventes.getChildren().get(len - 2);
                        int len1 = h.getChildren().size();
                        for (int i = 0; i < len1 - 2; i++) {
                            h.getChildren().get(i).setDisable(true);
                        }
                    }
                }
                if ((selectedProduit != null || (paniers.isEmpty() && selectedProduit != null)) || listVide) {
                	conteneur_ventes.getChildren().add(faireProduitList());
                	conteneur_ventes.getChildren().add(new Separator(Orientation.HORIZONTAL));
                    listVide = false;
                }
                selectedProduit = null;
                System.out.println(paniers.size());
               
             
            } catch (Exception e) {
                e.printStackTrace();
                Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
                Alert error = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
                error.show();
            }
        });

        
        delView.setOnMouseClicked(event -> {
            System.out.println(paniers);
            System.out.println("for delete 1 "+nomProduit.getText());
            if(selectedForProduit.isEmpty()|| paniers.isEmpty()) {
            	Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, "Impossible de supprimer", ButtonType.OK);
                alertErrorNumber.show();
            }
            else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer le produit?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.OK) {
            	int nombreFils = conteneur_ventes.getChildren().size();
                conteneur_ventes.getChildren().remove(nombreFils - 1);
                conteneur_ventes.getChildren().remove(hbox);
                hbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
                if (!paniers.isEmpty()) {
                    if (paniers.size() > 0) {
                    	System.out.println("for delete 2 "+nomProduit.getText());
                    	Produit p = new Produit();
                    	p.setNom(nomProduit.getText());
                    	p.setPrix(Integer.parseInt(prixUnitaire.getText()));
                        int find = findProduitGood(selectedForProduit, p);
                        selectedForProduit.remove(find);
                        
                    }
                    //hbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
                    System.out.println("for delete 3 "+nomProduit.getText());
                    System.out.println(paniers.size());
                    System.out.println("for delete 5 "+nomProduit.getText());
                    prix_total_fd.setText("" + Utilis.separatorNumber(calculPrixTotal() + (quantiteAchate * selectedProduit.getPrix())));
                }
                if (paniers.isEmpty() || paniers.size() == 0) {
                    selectedProduit = null;
                    listVide = true;
                    prix_total_fd.setText("0");
                }
            	
            }
            }
        	
        });
        
        
        Label labelQuantite = new Label("Qte     ");
        labelQuantite.prefWidth(100);
        TextField qt = new TextField("1");
        qt.setPromptText("QT:");
        qt.setPrefWidth(30);
        qt.textProperty().addListener((obs, old, nv) -> {
            if (!nv.isEmpty() && !Utilis.isNumber(nv)) {
                Alert alertErrorNumber = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
                alertErrorNumber.show();
            } else {
                if (selectedProduit != null) {
                	 
                    prix.setText("PT: " + Utilis.separatorNumber((selectedProduit.getPrix() * (Integer.parseInt(nv)))) + " CFA");
                    //quantiteAchate = Integer.parseInt(nv);
                    quantiteAchate = Integer.parseUnsignedInt(nv);
                    Produit ver= new Produit();
                    ver = Utilis.driver.rechercheProduit(selectedProduit.getNom());
                    if(ver!=null) {
                    	Utilis.verifierQuantiteProduit(selectedProduit, quantiteAchate);
                    	Utilis.venteProduits(paniers);
                    }
                    
                    prix_total_fd.setText("" + Utilis.separatorNumber(calculPrixTotal() + (quantiteAchate * selectedProduit.getPrix())));
                }
            }
            
            quantiteAchate = Integer.parseInt(qt.getText());
            prix.setText("PT: " + Utilis.separatorNumber(selectedProduit.getPrix() * quantiteAchate) + " CFA");
            
           
            Product produitV = new Product(selectedProduit.getNom(), selectedProduit.getPrix(), quantiteAchate, selectedProduit.getPrix() * quantiteAchate,Utilis.separatorNumber(selectedProduit.getPrix()),Utilis.separatorNumber(selectedProduit.getPrix()* quantiteAchate));
            products.add(produitV);
            Utilis.panier.setProduits(products);
        });
        
        
        

		 
	

//        ajouter les fils
        Label lc = new Label("  Code Barres");
        lc.setPrefWidth(85);
        
        hbox.getChildren().add(lc);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(codebarres);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(nomProduit);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(prixUnitaire);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(labelQuantite);
        hbox.getChildren().add(qt);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(prix);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(ajoutView);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        hbox.getChildren().add(delView);
        hbox.getChildren().add(new Separator(Orientation.VERTICAL));
        return hbox;
    }
    
        private int findProduit(List<ProduitAchete> ps, Produit p) {
        if (paniers.isEmpty() || p == null) {
            return -1;
        }
        int len = paniers.size();
        int pos = -1;
        for (int i = 0; i < len; i++) {
            if (paniers.get(i).getProduit().getNom().equals(p.getNom()) ) {
                pos = i;
                break;
            }
        }
        return pos;
    }
        
        private int findProduitGood(List<Product> ps, Produit p) {
            if (selectedForProduit.isEmpty() || p == null) {
                return -1;
            }
            int pos = -1;
            int i = 0;	
            	for (Product product : ps) {
            		if (product.getNom().equals(p.getNom()) ) {
                        pos = i;
                        break;
                    }i++;
				}
            	
            return pos;
        }
        

    private int calculPrixTotal() {
        int px = 0;
        int len = paniers.size();
        for (int i = 0; i < len; i++) {
            ProduitAchete panier = paniers.get(i);
            px += panier.getQuantite() * panier.getProduit().getPrix();
        }
        return px;
    }

  private void ouvrirCaisse() {
	  ouvre_caisse.setVisible(false);
      caisse_ouverte.setVisible(true);
      rect1.setVisible(true);
      rect2.setVisible(true);
      ouvrir_caisse.setVisible(false);
      fermer_caisse.setVisible(true);
	  rouge.setVisible(false);
	  vert.setVisible(true);
	  
  }
  
  private void fermerCaisse() {
	  ouvre_caisse.setVisible(true);
      caisse_ouverte.setVisible(false);
      rect1.setVisible(false);
      rect2.setVisible(false);
      fermer_caisse.setVisible(false);
      ouvrir_caisse.setVisible(true);
      rouge.setVisible(true);
	  vert.setVisible(false);
	  
     
	  
    
	  
  }

     

	
}
