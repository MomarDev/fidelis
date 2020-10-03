package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Trace;
import michelkaapp.utilis.Utilis;

public class FactureDiversControl implements Initializable{
	    @FXML
	    private Button btn_annuler;
	    @FXML
	    private Button btn_imprimer;
	    @FXML
		private AnchorPane pane_general;
	    private Parent root;
	    private Stage stage;
	    @FXML
	    private Label date;
	    @FXML
	    private Label prix_total;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btn_imprimer.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etes vous sure de vouloir imprimer ?");
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
            
            System.out.println(PayementController.user);
			Trace trace = new Trace();
            trace.setAction("Encaissement Produit");
            trace.setObjet("Encaissement");
            trace.setUser(PayementController.user);
            
            trace.setValeur(prix_total.getText());
            Utilis.driver.insertTrace(trace);
        });
		
		btn_annuler.setOnMouseClicked(event ->{
			  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	            alert.setContentText("Etes vous sure de vouloir quiter ?");
	            Optional<ButtonType> result = alert.showAndWait();
	            if (result.get() == ButtonType.OK) {
	            	try {
	    				root = FXMLLoader.load(getClass().getResource("Payement.fxml"));
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
		
		Label nomPro = new Label();
		Label prixPro = new Label();
		nomPro.setPrefWidth(231.0);
		nomPro.setPrefHeight(44.0);
		nomPro.setLayoutX(66.0);
		nomPro.setLayoutY(126.0);
		
		prixPro.setPrefWidth(231.0);
		prixPro.setPrefHeight(44.0);
		prixPro.setLayoutX(300.0);
		prixPro.setLayoutY(131.0);
		nomPro.setText(PayementController.produit.getNom());
		prixPro.setText(Utilis.separatorNumber(PayementController.produit.getPrix()) +" FCFA");
		pane_general.getChildren().add(nomPro);
		pane_general.getChildren().add(prixPro);
		prix_total.setText(Utilis.separatorNumber(PayementController.produit.getPrix()) +" FCFA");
}
}
