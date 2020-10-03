package michelkaapp.fxml;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Product;
import michelkaapp.objets.Tarif;
import michelkaapp.objets.Trace;
import michelkaapp.objets.Visite;
import michelkaapp.utilis.Utilis;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FactureControl implements Initializable{

	

	@FXML
	private Label date;
	@FXML
	private TableColumn<Tarif, String> c1;
	@FXML
	private TableColumn<Tarif, Integer> c2;
	@FXML
	private Label prix_total;
	@FXML
	private AnchorPane pane_general;
	@FXML
	private Button btn_imprimer;
	@FXML
	private Button btn_annuler;
	
    private Parent root;
    private Stage stage;
	private double x=66.0;
	private double xP=310.0;
	private double y=126.0;
	private Map<String, Integer> detailPrix;
	private int prixTotal =0;
	private List<Tarif> choixAchat;
	private List<Product> choixProduit;
	private List<String> tempo = new ArrayList<String>();
	private ObservableList<String> achater;
	private ObservableList<Tarif> listTarif;
	private Alert alertF;
	Visite visite;
	SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");

	Date today = new Date();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btn_imprimer.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etes vous sûre de valider le paiement et imprimer le ticket ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                btn_imprimer.getScene().getWindow().hide();
                boolean bol = false;
//                Paper.
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                Printer printer = Printer.getDefaultPrinter();
                PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
                double scaleX = pageLayout.getPrintableWidth() - 150;
                double scaleY = pageLayout.getPrintableHeight() - 190;
//                vbox.getTransforms().add(new Scale(scaleX, scaleY));
                pane_general.setMaxSize(scaleX, scaleY);
                pane_general.setScaleX(0.80);
                pane_general.setScaleY(0.70);
                if (printerJob.showPrintDialog(((Stage) btn_imprimer.getScene().getWindow()).getOwner()) && printerJob.printPage(pageLayout, pane_general)) {
                    bol = printerJob.endJob();
                }
                 
                
            }
            System.out.println(ModePayementControl.user);
			Trace trace = new Trace();
            trace.setAction("Encaissement Prestation");
            trace.setObjet("Encaissement");
            trace.setUser(ModePayementControl.user);
            
            
            
            trace.setValeur(prix_total.getText());
            Utilis.driver.insertTrace(trace);
            
            if(ModePayementControl.maner==1) {
            	 Utilis.driver.insertEncaisser(ModePayementControl.varCard, ModePayementControl.restant, "Carte Bancaire");
            	 Utilis.driver.insertEncaisser(ModePayementControl.net-ModePayementControl.varCard, ModePayementControl.restant, "Chéque");
            	
            }
			if(ModePayementControl.maner==2) {
				Utilis.driver.insertEncaisser(ModePayementControl.varCard, ModePayementControl.restant, "Carte Bancaire");
           	    Utilis.driver.insertEncaisser(ModePayementControl.net-ModePayementControl.varCard, ModePayementControl.restant, "Espéce");
			            }	
			if(ModePayementControl.maner==3) {
				Utilis.driver.insertEncaisser(ModePayementControl.varCard, ModePayementControl.restant, "Carte Bancaire");
           	 Utilis.driver.insertEncaisser(ModePayementControl.restant,ModePayementControl.net-ModePayementControl.varCard, "Attente");
			            }
			if(ModePayementControl.maner==4) {
				Utilis.driver.insertEncaisser(ModePayementControl.varCheque, ModePayementControl.restant, "Chéque");
           	    Utilis.driver.insertEncaisser(ModePayementControl.net-ModePayementControl.varCheque, ModePayementControl.restant, "Espéce");
            }
			if(ModePayementControl.maner==5) {
				Utilis.driver.insertEncaisser(ModePayementControl.varCheque, ModePayementControl.restant, "Chéque");
           	    Utilis.driver.insertEncaisser(ModePayementControl.restant,ModePayementControl.net-ModePayementControl.varCheque, "Attente");
            }
			if(ModePayementControl.maner==6) {
				Utilis.driver.insertEncaisser(ModePayementControl.varEspece, ModePayementControl.restant, "Espéce");
           	    Utilis.driver.insertEncaisser(ModePayementControl.restant,ModePayementControl.net-ModePayementControl.varEspece, "Attente");
            }
			if(ModePayementControl.maner==0) {
			Utilis.driver.insertEncaisser(ModePayementControl.net-ModePayementControl.restant, ModePayementControl.restant, ModePayementControl.mode);
            }
           
            ChoixAchatPrestationControl.selectedForPrestation.clear();
            PayementController.selectedForProduit.clear();
            if(ModePayementControl.client!=null) {
            	 if (ModePayementControl.cli){
     				Utilis.driver.updateCarte(ModePayementControl.client.getCarte().getReference(), ImpressionFideliteController.champ, 0);
     				ModePayementControl.client.getCarte().setNptsFideliteAchat(0);
     			}else{
     				
     				if(NiveauVisiteControl.points!=0) {
     					visite = new Visite();
         				visite.setDate(format.format(today));
         				visite.setNombrePoints(Utilis.pointAvaloir(NiveauVisiteControl.points, "nombre_fidelite_prestation"));
                     	visite.setReferenceCarte(ModePayementControl.client.getCarte().getReference());
                     	
                     	
                     	//Utilis.driver.insertPointVisite(visite);
                     	
                     	
                     	Utilis.driver.insertVisite(visite);
                     	Utilis.driver.point(ModePayementControl.client.getCarte().getReference(), Utilis.pointAvaloir(NiveauVisiteControl.points, "nombre_fidelite_prestation"), "nombre_fidelite_prestation");
     				}
     			}
            }
           

            Utilis.panier.setPrestations(null);
            Utilis.panier.setProduits(null);
            ModePayementControl.tva=0;
            
            Utilis.caisse=1; 
            Stage impStage = new Stage();
            impStage.setResizable(false);
            impStage.setOnCloseRequest(event1 -> {
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                Utilis.entrainPayer=false;
               
            });
            
            FXMLLoader impRoot = null;
            AnchorPane pane = null;
            try {
            	  
                impRoot = new FXMLLoader(getClass().getResource("Payement.fxml"));
                pane = impRoot.load();
                
               
            } catch (IOException ex) {
                Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            impStage.setScene(new Scene(pane));
            impStage.show();
            
            
           
            Utilis.entrainPayer=true;
            alertF = new Alert(Alert.AlertType.INFORMATION,"Paiement enregistré !!! Merci pour votre fidélité");
            alertF.setHeaderText("Succés");
            alertF.show();
        });
		
		btn_annuler.setOnMouseClicked(event ->{
			  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	            alert.setContentText("Etes vous sure de vouloir quiter ?");
	            Optional<ButtonType> result = alert.showAndWait();
	            if (result.get() == ButtonType.OK) {
	            	try {
	    				root = FXMLLoader.load(getClass().getResource("ModePayement.fxml"));
	    				stage = (Stage) btn_annuler.getScene().getWindow();
	    	            Scene scene = new Scene(root);
	    	            stage.setScene(scene);
	    	            stage.show();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}

	            }
			
		});
		
		date.setText(Utilis.dateFrenchFormat(Utilis.NOW_LOCAL_DATE()));
		choixAchat = ChoixAchatPrestationControl.selectedForPrestation;
		choixProduit = PayementController.selectedForProduit;
		
		for (int i = 0; i < choixAchat.size(); i++) {
			Label label = new Label();
			Label prixU = new Label();
			label.setPrefWidth(231.0);
			label.setPrefHeight(44.0);
			label.setLayoutX(x);
			label.setLayoutY(y);
			
			prixU.setLayoutX(xP);
			prixU.setLayoutY(y+15);
			label.setText(choixAchat.get(i).getDetail());
			
			if(!ValidationChequeCadeauControl.cheque.isEmpty()) {
				if(ValidationChequeCadeauControl.cheque.get(ValidationChequeCadeauControl.cheque.size()-1).getDetail().equals(choixAchat.get(i).getDetail())) {
					prixU.setText(" Chéque Cadeau ");
				}else {
					prixU.setText((Utilis.separatorNumber(choixAchat.get(i).getPrix())) +" FCFA");
				}	
			}else {
				prixU.setText((Utilis.separatorNumber(choixAchat.get(i).getPrix())) +" FCFA");
			}
			
			
			pane_general.getChildren().add(label);
			pane_general.getChildren().add(prixU);
			y=y+30;
		}

		for (int i = 0; i < choixProduit.size(); i++) {
			Label label = new Label();
			Label prixU = new Label();
			Label qt = new Label();
			
			
			
			label.setPrefWidth(231.0);
			label.setPrefHeight(44.0);
			label.setLayoutX(x);
			label.setLayoutY(y);

			qt.setLayoutX(xP-x);
			qt.setLayoutY(y+10+5);
			
			prixU.setLayoutX(xP);
			prixU.setLayoutY(y+15);
			label.setText(choixProduit.get(i).getNom());

			qt.setText("X"+choixProduit.get(i).getQuantite());
			prixU.setText((Utilis.separatorNumber(choixProduit.get(i).getPrixU())) +" FCFA");
			
			pane_general.getChildren().add(label);
			pane_general.getChildren().add(qt);
			pane_general.getChildren().add(prixU);
			y=y+30;
		}

		if(ModePayementControl.tva!=0) {
			Label pan = new Label();
			
			pan.setLayoutX(x);
			pan.setLayoutY(y+30);
			
			pan.setText("remise de   "+ModePayementControl.tva + "%");
			pane_general.getChildren().add(pan);
		}
		
		if(ModePayementControl.client!=null && ModePayementControl.cli) {
			Label pan = new Label();
			
			pan.setLayoutX(x);
			pan.setLayoutY(y+50);
			
			pan.setText("remise de carte de fidelité                                "+ Utilis.separatorNumber(ModePayementControl.client.getCarte().getNptPrestation())+" FCFA");
			pane_general.getChildren().add(pan);
		}

		prix_total.setText( Utilis.separatorNumber(ModePayementControl.net) +" FCFA");

	}

}
