package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import michelkaapp.objets.Achat;
import michelkaapp.objets.ProduitAchete;
import michelkaapp.objets.Tarif;
import michelkaapp.utilis.Utilis;

public class ChoixAchatPrestationControl implements Initializable{

	private static  int INTERVAL_PAGE = 40;
	private static  int MAX_PAGE =39 ;
	private static  int MIN_PAGE =0 ;
	private int PAGES = 0;
	private int PAGES2 = 0;
	@FXML
	private Label forfait_title;
	@FXML
	private Label carte_title;
	@FXML
	private TableColumn<Tarif, String> prestation_col;
	@FXML
	private TableColumn<Tarif, String> detail_col;
	@FXML
	private TableColumn<Tarif, String> prix_col;

	@FXML
	private TableColumn<Tarif, String> prestation_colC;
	@FXML
	private TableColumn<Tarif, String> detail_colC;
	@FXML
	private TableColumn<Tarif, String> prix_colC;
	@FXML
	private TableView<Tarif> table_view;
	@FXML
	private TableView<Tarif> table_viewC;
    private Parent root;
    private Stage stage;
    @FXML
    private String detail_achat;
	@FXML
	private Button btn_retour;
	@FXML
	private Button btn_quitter;
	@FXML
	private Button btn_suivant;

	public static List<Tarif> selectedForPrestation = new ArrayList<>();
	@FXML
	private TableColumn prestation_checker;
	@FXML
	private TableColumn prestation_checkerC;

	@FXML

	private ObservableList<Tarif> listTarifs;
	private ObservableList<Tarif> listTarifCarte;
	@FXML
	private Pagination pagination;
	@FXML
	private Pagination paginationC;

	private List<Tarif> tarifs = null;
	private List<Tarif> tarifCarte = null;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		/*if (selectedForPrestation != null) {
			selectedForPrestation.clear();
		}*/
		
		
		tarifs = PayementController.t;
		tarifCarte = PayementController.tc;
		
		
		initTable();
		if(tarifs!=null ) {
			PAGES = tarifs.size();
			if (PAGES % INTERVAL_PAGE == 0) {
	            pagination.setPageCount(PAGES / INTERVAL_PAGE);
	        } else {
	            pagination.setPageCount(PAGES / INTERVAL_PAGE + 1);
	        }
			
			pagination.currentPageIndexProperty().addListener((obs, old, nv) -> {
	            MIN_PAGE = nv.intValue() * INTERVAL_PAGE;
	            MAX_PAGE = Math.min(MIN_PAGE + INTERVAL_PAGE, PAGES);
	            System.out.println(nv.intValue());
	            System.out.println("MIN:" + MIN_PAGE);
	            System.out.println("MAX:" + MAX_PAGE);
	            //selectedForPrestation.clear();
	            initTable();
	        });
		}
		
		if(tarifCarte!=null ) {
			PAGES2 = tarifCarte.size();
	        
	        
	        if (PAGES2 % INTERVAL_PAGE == 0) {
	            paginationC.setPageCount(PAGES2 / INTERVAL_PAGE);
	        } else {
	            paginationC.setPageCount(PAGES2 / INTERVAL_PAGE + 1);
	        }
	        paginationC.currentPageIndexProperty().addListener((obs, old, nv) -> {
	            MIN_PAGE = nv.intValue() * INTERVAL_PAGE;
	            MAX_PAGE = Math.min(MIN_PAGE + INTERVAL_PAGE, PAGES2);
	            System.out.println(nv.intValue());
	            System.out.println("MIN:" + MIN_PAGE);
	            System.out.println("MAX:" + MAX_PAGE);
	            //selectedForPrestation.clear();
	            initTable();
	        });
			
		}else {
			paginationC.setVisible(false);
		}
		
        
        


	}
	
	

	private void initTable() {
		
		PAGES = tarifs.size();
		if (tarifs != null) {
			if(tarifs.get(0).getSexe().equalsIgnoreCase("femme") && tarifs.get(0).getNom().equalsIgnoreCase(" adulte") ) {
				forfait_title.setText("Forfaits Femmes Adultes");	
			}
			if(tarifs.get(0).getSexe().equalsIgnoreCase("femme") && tarifs.get(0).getNom().equalsIgnoreCase(" BEBE -5 ANS") ) {
				forfait_title.setText("Forfaits Bébé Féminin");	
			}
			if(tarifs.get(0).getSexe().equalsIgnoreCase("femme") && tarifs.get(0).getNom().equalsIgnoreCase(" Fillette -12 ans") ) {
				forfait_title.setText("Forfaits Fillette -12 ans");	
				carte_title.setText("À la carte Fillette -12 ans");
			}
			if(tarifs.get(0).getSexe().equalsIgnoreCase("femme") && tarifs.get(0).getNom().equalsIgnoreCase(" ADOS -20 ans") ) {
				forfait_title.setText("Forfaits Femmes ADO -20 ans");	
			}
			
			
			if(tarifs.get(0).getSexe().equalsIgnoreCase("homme") && tarifs.get(0).getNom().equalsIgnoreCase(" adulte") ) {
				forfait_title.setText("Forfaits Hommes Adultes");	
			}
			if(tarifs.get(0).getSexe().equalsIgnoreCase("homme") && tarifs.get(0).getNom().equalsIgnoreCase(" BEBE -5 ANS") ) {
				forfait_title.setText("Forfaits Bébé Masculin");
				carte_title.setText("À la carte Bébé Masculin");
			}
			if(tarifs.get(0).getSexe().equalsIgnoreCase("homme") && tarifs.get(0).getNom().equalsIgnoreCase(" Garçon -12 ans") ) {
				forfait_title.setText("Forfaits Garçon -12 ans");
				
			}
			if(tarifs.get(0).getSexe().equalsIgnoreCase("homme") && tarifs.get(0).getNom().equalsIgnoreCase(" ADOS -20 ans") ) {
				forfait_title.setText("Forfaits Hommes ADO -20 ans");	
			}
			
			
			
			if (PAGES % INTERVAL_PAGE == 0) {
				pagination.setPageCount(PAGES / INTERVAL_PAGE);
			} else {
				pagination.setPageCount(PAGES / INTERVAL_PAGE + 1);
			}
			
				
				if (tarifs.size() > INTERVAL_PAGE) {
					if (MAX_PAGE >= PAGES ) {
						listTarifs = FXCollections.observableArrayList(tarifs.subList(MIN_PAGE, tarifs.size()));
					} else {
						listTarifs = FXCollections.observableArrayList(tarifs.subList(MIN_PAGE, MAX_PAGE));
					}
				} else {
					listTarifs = FXCollections.observableArrayList(tarifs);
				}
		
			
			table_view.setItems(listTarifs);
		    
			
			
			prestation_col.setCellValueFactory(new PropertyValueFactory<>("prestation"));
			detail_col.setCellValueFactory(new PropertyValueFactory<>("detail"));
			prix_col.setCellValueFactory(new PropertyValueFactory<>("prixString"));

			prestation_checker.setCellValueFactory(new PropertyValueFactory(""));
			prestation_checker.setCellFactory(new Callback<TableColumn<Tarif, Boolean>, TableCell<Tarif, Boolean>>() {

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

										if (selectedForPrestation.size() < 10 ) {
											selectedForPrestation.add(c);
											System.out.println("test 3");

										} else {
											Alert noMail = new Alert(Alert.AlertType.ERROR, "Vous avez atteint le nombre maximum (10) de prestation. Veillez valider la commande SVP");
											noMail.show();
											noMail.setOnHidden((v) -> {
												checkBox.setSelected(false);
											});
										}
									} else {
										selectedForPrestation.remove(c);
										System.out.println("test 4");

									}
									
									System.out.println(Arrays.toString(selectedForPrestation.toArray()));
									Utilis.panier.setPrestations(selectedForPrestation);

								});
							}
						}
					};

					return cell;
				}

			});

		} else {
			System.out.println("No ok!!!");
		}
		
		
		
		
		
		
		
		
		if (tarifCarte != null) {

			PAGES2 = tarifCarte.size();
			if(tarifCarte.get(0).getSexe().equalsIgnoreCase("femme") && tarifCarte.get(0).getNom().equalsIgnoreCase(" adulte") ) {
				carte_title.setText(" À la carte Femmes Adultes");	
			}
			if(tarifCarte.get(0).getSexe().equalsIgnoreCase("femme") && tarifCarte.get(0).getNom().equalsIgnoreCase(" BEBE -5 ANS") ) {
				carte_title.setText("À la carte Bébé Féminin");	
			}
			if(tarifCarte.get(0).getSexe().equalsIgnoreCase("femme") && tarifCarte.get(0).getNom().equalsIgnoreCase(" Fillette -12 ans") ) {
				carte_title.setText("À la carte Fillette -12 ans");	
			}
			if(tarifCarte.get(0).getSexe().equalsIgnoreCase("femme") && tarifCarte.get(0).getNom().equalsIgnoreCase(" ADOS -20 ans") ) {
				carte_title.setText("À la carte Femmes ADO -20 ans");	
			}
			
			
			if(tarifCarte.get(0).getSexe().equalsIgnoreCase("homme") && tarifCarte.get(0).getNom().equalsIgnoreCase(" adulte") ) {
				carte_title.setText("À la carte Hommes Adultes");	
			}
			if(tarifCarte.get(0).getSexe().equalsIgnoreCase("homme") && tarifCarte.get(0).getNom().equalsIgnoreCase(" BEBE -5 ANS") ) {
				carte_title.setText("À la carte Bébé Masculin");	
			}
			if(tarifCarte.get(0).getSexe().equalsIgnoreCase("homme") && tarifCarte.get(0).getNom().equalsIgnoreCase(" Garçon -12 ans") ) {
				carte_title.setText("À la carte Garçon -12 ans");	
			}
			if(tarifCarte.get(0).getSexe().equalsIgnoreCase("homme") && tarifCarte.get(0).getNom().equalsIgnoreCase(" ADOS -20 ans") ) {
				carte_title.setText("À la carte Hommes ADO -20 ans");	
			}
			
			
			if (PAGES2 % INTERVAL_PAGE == 0) {
				paginationC.setPageCount(PAGES2 / INTERVAL_PAGE);
			} else {
				paginationC.setPageCount(PAGES2 / INTERVAL_PAGE + 1);
			}
			if (tarifCarte.size() > INTERVAL_PAGE) {
				if (MAX_PAGE >= PAGES2) {
					listTarifCarte = FXCollections.observableArrayList(tarifCarte.subList(MIN_PAGE, tarifCarte.size()));
				} else {
					listTarifCarte = FXCollections.observableArrayList(tarifCarte.subList(MIN_PAGE, MAX_PAGE));
				}
			} else {
				listTarifCarte = FXCollections.observableArrayList(tarifCarte);
			}
			table_viewC.setItems(listTarifCarte);
			
			prestation_colC.setCellValueFactory(new PropertyValueFactory<>("prestation"));
			detail_colC.setCellValueFactory(new PropertyValueFactory<>("detail"));
			prix_colC.setCellValueFactory(new PropertyValueFactory<>("prixString"));

			prestation_checkerC.setCellValueFactory(new PropertyValueFactory(""));
			prestation_checkerC.setCellFactory(new Callback<TableColumn<Tarif, Boolean>, TableCell<Tarif, Boolean>>() {

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

										if (selectedForPrestation.size() < 10 ) {
											selectedForPrestation.add(c);
											System.out.println("test 3");

										} else {
											Alert noMail = new Alert(Alert.AlertType.ERROR, "Vous avez atteint le nombre maximum (10) de prestation. Veillez valider la commande SVP");
											noMail.show();
											noMail.setOnHidden((v) -> {
												checkBox.setSelected(false);
											});
										}
									} else {
										selectedForPrestation.remove(c);
										System.out.println("test 4");

									}
									
									System.out.println(Arrays.toString(selectedForPrestation.toArray()));
									Utilis.panier.setPrestations(selectedForPrestation);

								});
							}
						}
					};

					return cell;
				}

			});

		} else {
			System.out.println("No ok!!!");
		}
		
		
		
		
	}
	
	@FXML
    private void handlerButtonAchat(ActionEvent event) {
        if(event.getSource()== btn_retour) {
        	
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
        
        if(event.getSource()== btn_suivant) {
        	if (selectedForPrestation.size()==0) {
        		Alert error = new Alert(Alert.AlertType.WARNING, "Veuillez d'abord selectionner au moins une Prestation .");
                error.show();
			}else {
        	try {
				root = FXMLLoader.load(getClass().getResource("Panier.fxml"));
				stage = (Stage) btn_suivant.getScene().getWindow();
	            Scene scene = new Scene(root);
	            stage.setScene(scene);
	            stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
        
        }
        if (event.getSource() == btn_quitter) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_quitter.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        }
    }


	
}