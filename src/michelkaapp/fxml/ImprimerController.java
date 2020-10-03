/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import michelkaapp.objets.Carte;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class ImprimerController implements Initializable {

    @FXML
    private VBox second_pane;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Button btn_quitter;
    Carte carte = null;
    @FXML
    private Label point_achat_valeur;
    @FXML
    private Label point_visite_valeur;
    @FXML
    private Label point_parrainage_valeur;
    @FXML
    private Label reference_carte_client;
    @FXML
    private Label date_impression;
    @FXML
    private VBox vbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carte = Utilis.carteActuel;
        if (carte != null) {
            point_achat_valeur.setText(Utilis.separatorNumber(carte.getNptsFideliteAchat()) + " F");
            point_visite_valeur.setText(Utilis.separatorNumber(carte.getNptsFideliteAchat()) + " F");
            point_parrainage_valeur.setText(Utilis.separatorNumber(carte.getNtpsParrainage()) + " P");
            reference_carte_client.setText(Utilis.separatorNumber(Integer.parseInt(carte.toString())));
            System.out.println(Utilis.NOW_LOCAL_DATE());
            date_impression.setText("La date du: " + Utilis.dateFrenchFormat(Utilis.NOW_LOCAL_DATE()));
            System.out.println(Utilis.NOW_LOCAL_DATE());
        }
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etes vous sûr de vouloir imprimer ses informations?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_enregistrer.getScene().getWindow();
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                Printer printer = Printer.getDefaultPrinter();
                PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
                double scaleX = pageLayout.getPrintableWidth() - 150;
                double scaleY = pageLayout.getPrintableHeight() - 190;
                vbox.setMaxSize(scaleX, scaleY);
                vbox.setScaleX(0.80);
                vbox.setScaleY(0.70);
                if (printerJob.showPrintDialog(stage.getOwner()) && printerJob.printPage(pageLayout, vbox)) {
                    printerJob.endJob();
                }
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();
            } else {
                alert.close();
            }
        }
    }

}
