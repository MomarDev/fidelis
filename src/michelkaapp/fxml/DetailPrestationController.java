/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import michelkaapp.objets.Client;
import michelkaapp.objets.Marque;
import michelkaapp.objets.PrestationTechnique;
import michelkaapp.objets.Produit;
import michelkaapp.objets.TypePrestation;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class DetailPrestationController implements Initializable {

    @FXML
    private VBox scn;
    @FXML
    private Label lb_reference_carte;
    @FXML
    private Label lb_nom_prenom;
    @FXML
    private Label lb_typePrestation;
    @FXML
    private Label nomPrestation;
    @FXML
    private Text txt_montage;
    @FXML
    private Text txt_technique;
    @FXML
    private Text txt_observation;
    @FXML
    private Text txt_marques;
    @FXML
    private Button btn_imprimer;
    @FXML
    private Button btn_fermer;
    @FXML
    private Label lb_pause;
    private Alert alert;
    private Client client;
    private PrestationTechnique prestation;
    private TypePrestation typePrestation;
    private Marque marques;
    private List<Produit> produits = null;
    @FXML
    private Label lab_marque;
    @FXML
    private Label lab_montage;
    @FXML
    private Label lab_technique;
    @FXML
    private VBox v_marque;
    @FXML
    private VBox v_montage;
    @FXML
    private VBox v_technique;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Utilis.clientActuel);
        if (Utilis.clientActuel != null) {
            try {
                client = (Client) Utilis.clientActuel.clone();
                if (Utilis.prestationTechniqueActuel != null) {
                    lb_nom_prenom.setText(client.getNom() + " " + client.getPrenom());
                    prestation = (PrestationTechnique) Utilis.prestationTechniqueActuel.clone();
                    produits = Utilis.driver.listProduitsTechnique(prestation.getId());
                    if (Utilis.DetailTypePrestation != null) {
                        typePrestation = (TypePrestation) Utilis.DetailTypePrestation.clone();
                        prestation.getCategeorie().setTypePrestation(typePrestation);
                    }
                    lb_typePrestation.setText(prestation.getCategeorie().getTypePrestation().getNom());
                    nomPrestation.setText(prestation.getCategeorie().getNom());
                    lb_reference_carte.setText(Utilis.separatorNumber(client.getCarte().getReference()));
                    if (produits != null && !produits.isEmpty()) {
                        txt_marques.setText(Arrays.toString(produits.toArray()));
                    }
                    txt_observation.setText(prestation.getObservation());
                    txt_montage.setText(prestation.getMontage());
                    txt_technique.setText(prestation.getTechnique());
                    lb_pause.setText(prestation.getPause() + "");
                    System.out.println(client);
                    System.out.println(produits != null);
                    if (prestation.getMontage() == null || prestation.getMontage().isEmpty()) {
                        scn.getChildren().remove(v_montage);
                    }
                    if (prestation.getTechnique() == null || prestation.getTechnique().isEmpty()) {
                        scn.getChildren().remove(v_technique);
                    }
                    if (produits == null || produits.isEmpty()) {
                        scn.getChildren().remove(v_marque);
                    }
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DetailPrestationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        btn_fermer.setOnAction(event -> {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etes vous sure de voiloir quitter");
            Optional<ButtonType> response = alert.showAndWait();
            if (response.get() == ButtonType.OK) {
                --Utilis.Max_DEFAULT;
                ((Stage) btn_fermer.getScene().getWindow()).close();
            }
        });
        btn_imprimer.setOnAction(event -> {
            PrinterJob printerJob = PrinterJob.createPrinterJob();
            Printer printer = Printer.getDefaultPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
            double scaleX = pageLayout.getPrintableWidth() - 150;
            double scaleY = pageLayout.getPrintableHeight() - 190;
            scn.setMaxSize(scaleX, scaleY);
            scn.setScaleX(0.80);
            scn.setScaleY(0.70);
            if (printerJob.showPrintDialog(((Stage) btn_imprimer.getScene().getWindow()).getOwner()) && printerJob.printPage(pageLayout, scn)) {
                printerJob.endJob();
                --Utilis.Max_DEFAULT;
                ((Stage) btn_imprimer.getScene().getWindow()).close();
            }
        });
    }

}
