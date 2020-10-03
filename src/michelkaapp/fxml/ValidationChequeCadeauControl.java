package michelkaapp.fxml;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import michelkaapp.objets.Product;
import michelkaapp.objets.Tarif;
import michelkaapp.utilis.Utilis;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ValidationChequeCadeauControl implements Initializable{
	@FXML
	private Button btn_retour;
	@FXML
	private Button btn_prestation;
	@FXML
	private Button btn_produit;
	@FXML
	private Button btn_payer;
	@FXML
	private Button vider;
	@FXML
	private CheckBox check_box;
	@FXML
	private TableColumn<Product, String> prixTotal_col;
	@FXML
	private TableColumn cc_checker;
	private Parent root;
    private Stage stage;
    public static List<Tarif> cheque = new ArrayList<>();
	@FXML
	private TableView<Tarif> table_view;
	@FXML
	private TableView<Product> table_view1;
	@FXML
	private TableColumn<Tarif, String> prestation_col;
	@FXML
	private TableColumn<Tarif, String> prixPres_col;
	@FXML
    private TableColumn<Product,String> produit_col;
	@FXML
	private TableColumn<Product,String> prixPro_col;
	@FXML
	private TableColumn<Product,Integer> quantite_col;
	
	private int prix_prod = 0;
	@FXML
	private Label prix_total;
	
	
	
	
	
	private ObservableList<Product> listProduit;
	private int prix_prestation =0;
	public static int prixPresstationVisible=0;
	private List<Tarif> choixAchat;
	private ObservableList<Tarif> listTarif;
	private List<Product> choixProduit;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(!cheque.isEmpty()) {
			cheque.clear();
		}
		
		
		
		choixProduit = Utilis.panier.getProduits();
		choixAchat = Utilis.panier.getPrestations();
		
     
		if(choixProduit==null && choixAchat!=null) {
			
			for (Tarif tarif : choixAchat) {
				prix_prestation = tarif.getPrix()+ prix_prestation;
				
			}
			
			listTarif = FXCollections.observableArrayList(choixAchat);
			table_view.setItems(listTarif);
			prestation_col.setCellValueFactory(new PropertyValueFactory<>("detail"));
			prixPres_col.setCellValueFactory(new PropertyValueFactory<>("prixString"));
			cc_checker.setCellValueFactory(new PropertyValueFactory(""));
			cc_checker.setCellFactory(new Callback<TableColumn<Tarif, Boolean>, TableCell<Tarif, Boolean>>() {

				@Override
				public TableCell<Tarif, Boolean> call(TableColumn<Tarif, Boolean> param) {
					final TableCell<Tarif, Boolean> cell = new TableCell<Tarif, Boolean>() {
						final CheckBox checkBox = new CheckBox();

						@Override
						protected void updateItem(Boolean item, boolean empty) {

							super.updateItem(item, empty);
							if (empty) {
								setGraphic(null);
								setText(null);
							} else {
								HBox hBox = new HBox(checkBox);
								hBox.setAlignment(Pos.CENTER);
								setGraphic(hBox);
//                                TODO:: faire une multiselection
								Tarif itm = (Tarif) getTableRow().getItem();
								//checkBox.selectedProperty().addListener((obs, old, nv) -> {
								//Client c = (Client) getTableRow().getItem();
								checkBox.setOnAction(event -> {
									Tarif c = (Tarif) getTableView().getItems().get(getIndex());

									System.out.println("test 1");
									if (checkBox.isSelected() == true) {
										System.out.println("test 2");

										if (cheque.size() < 10 ) {
											cheque.add(c);
											System.out.println("test 3");

										} else {
											Alert noMail = new Alert(Alert.AlertType.ERROR, "Vous avez atteint le nombre maximum (10) de prestation. Veillez valider la commande SVP");
											noMail.show();
											noMail.setOnHidden((v) -> {
												checkBox.setSelected(false);
											});
										}
									} else {
										cheque.remove(c);
										System.out.println("test 4");

									}
									
									System.out.println(Arrays.toString(cheque.toArray()));
									

								});
							}
						}
					};

					return cell;
				}

			});
			
			prix_total.setText( Utilis.separatorNumber(prix_prestation) +" FCFA");
        	
        }
		else if(choixAchat==null && choixProduit!=null) {
			for (Product prod : choixProduit) {
				prix_prod = prod.getPrixTotal()+ prix_prod;
			}
			
			listProduit = FXCollections.observableArrayList(choixProduit);
			table_view1.setItems(listProduit);
	        produit_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
	        prixPro_col.setCellValueFactory(new PropertyValueFactory<>("prixUString"));
	        quantite_col.setCellValueFactory(new PropertyValueFactory<>("quantite"));
	        prixTotal_col.setCellValueFactory(new PropertyValueFactory<>("prixTotalString") );
	        
	      prix_total.setText( Utilis.separatorNumber(prix_prod) +" FCFA");
		}else if(choixProduit!=null && choixAchat!=null) {
			
			for (Tarif tarif : choixAchat) {
				prix_prestation = tarif.getPrix()+ prix_prestation;
				
			}
			
			for (Product prod : choixProduit) {
				prix_prod = prix_prod + prod.getPrixTotal();
			}
			
			listTarif = FXCollections.observableArrayList(choixAchat);
			table_view.setItems(listTarif);
			prestation_col.setCellValueFactory(new PropertyValueFactory<>("detail"));
			prixPres_col.setCellValueFactory(new PropertyValueFactory<>("prixString"));
			cc_checker.setCellValueFactory(new PropertyValueFactory(""));
			cc_checker.setCellFactory(new Callback<TableColumn<Tarif, Boolean>, TableCell<Tarif, Boolean>>() {

				@Override
				public TableCell<Tarif, Boolean> call(TableColumn<Tarif, Boolean> param) {
					final TableCell<Tarif, Boolean> cell = new TableCell<Tarif, Boolean>() {
						final CheckBox checkBox = new CheckBox();

						@Override
						protected void updateItem(Boolean item, boolean empty) {

							super.updateItem(item, empty);
							if (empty) {
								setGraphic(null);
								setText(null);
							} else {
								HBox hBox = new HBox(checkBox);
								hBox.setAlignment(Pos.CENTER);
								setGraphic(hBox);
//                                TODO:: faire une multiselection
								Tarif itm = (Tarif) getTableRow().getItem();
								//checkBox.selectedProperty().addListener((obs, old, nv) -> {
								//Client c = (Client) getTableRow().getItem();
								checkBox.setOnAction(event -> {
									Tarif c = (Tarif) getTableView().getItems().get(getIndex());

									System.out.println("test 1");
									if (checkBox.isSelected() == true) {
										System.out.println("test 2");

										if (cheque.size() < 10 ) {
											cheque.add(c);
											System.out.println("test 3");

										} else {
											Alert noMail = new Alert(Alert.AlertType.ERROR, "Vous avez atteint le nombre maximum (10) de prestation. Veillez valider la commande SVP");
											noMail.show();
											noMail.setOnHidden((v) -> {
												checkBox.setSelected(false);
											});
										}
									} else {
										cheque.remove(c);
										System.out.println("test 4");

									}
									
									System.out.println(Arrays.toString(cheque.toArray()));
									

								});
							}
						}
					};

					return cell;
				}

			});
			
			listProduit = FXCollections.observableArrayList(choixProduit);
			table_view1.setItems(listProduit);
	        produit_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
	        prixPro_col.setCellValueFactory(new PropertyValueFactory<>("prixUString"));
	        quantite_col.setCellValueFactory(new PropertyValueFactory<>("quantite"));
	        prixTotal_col.setCellValueFactory(new PropertyValueFactory<>("prixTotalString") );
	        
	        
	        prix_total.setText( Utilis.separatorNumber(prix_prod+prix_prestation) +" FCFA");

		}else {
			Alert alertVide = new Alert(Alert.AlertType.ERROR, Utilis.ErrorPanier, ButtonType.OK);
            alertVide.show();
            stage.close();
			
		}
		
		prixPresstationVisible=prix_prestation;
		
		
		
		//prix_total.setText( Utilis.separatorNumber(prixTotal) +" FCFA");
	
	}
	
	@FXML
	private void handlerButton(ActionEvent event) {
		if (event.getSource()== btn_payer) {			
						
						/*if (check_box.isSelected()) {
						try {
							root = FXMLLoader.load(getClass().getResource("ChequeCadeau.fxml"));
							stage = (Stage) btn_valider.getScene().getWindow();
				            Scene scene = new Scene(root);
				            stage.setScene(scene);
				            stage.show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}*/
							try {
								root = FXMLLoader.load(getClass().getResource("ModePayement.fxml"));
								stage = (Stage) btn_payer.getScene().getWindow();
								Scene scene = new Scene(root);
								stage.setScene(scene);
								stage.show();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
					 
				
		
		if (event.getSource()== btn_retour) {
			if(choixAchat==null) {
				try {
					root = FXMLLoader.load(getClass().getResource("Payement.fxml"));
					stage = (Stage) btn_retour.getScene().getWindow();
		            Scene scene = new Scene(root);
		            stage.setScene(scene);
		            stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else {
				
				try {
					
					root = FXMLLoader.load(getClass().getResource("ChoixAchatPrestation.fxml"));
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
		if (event.getSource()== btn_prestation) {

			try {
				root = FXMLLoader.load(getClass().getResource("Payement.fxml"));
				stage = (Stage) btn_prestation.getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (event.getSource()== vider) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Vouler vous vraiment vider le panier");
			
			Optional<ButtonType> info = alert.showAndWait();
			if(info.get()==ButtonType.OK) {
				ModePayementControl.tva=0;
				ChoixAchatPrestationControl.selectedForPrestation.clear();
	            PayementController.selectedForProduit.clear();
	            Utilis.panier.setPrestations(null);
	            Utilis.panier.setProduits(null);
	            
	            try {
					root = FXMLLoader.load(getClass().getResource("Payement.fxml"));
					stage = (Stage) vider.getScene().getWindow();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
		}
		if (event.getSource()== btn_produit) {

			try {
				//PayementController.pan_prestation.setVisible(false);
				root = FXMLLoader.load(getClass().getResource("Payement.fxml"));
				stage = (Stage) btn_produit.getScene().getWindow();
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
