package michelkaapp.fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import michelkaapp.objets.FermeCaisse;
import michelkaapp.objets.Trace;
import michelkaapp.utilis.Utilis;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketFermeture implements Initializable {
    @FXML
    private Label date;
    @FXML
    private Label fond;
    @FXML
    private Label rembourse;
    @FXML
    private Button btn_annuler;
    @FXML
    private Label chiffre;
    @FXML
    private Label espece;
    @FXML
    private Label carte_banque;
    @FXML
    private Label attente;
    @FXML
    private Label cheque;
    @FXML
    private Label prenomOp;
    @FXML
    private Text commentaire;
    @FXML
    private Button btn_imprimer;
    @FXML
    private AnchorPane vbox;
    @FXML
    private Label total;

    private FermeCaisse ferme = new FermeCaisse();


    SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY à HH:mm:ss");

    Date today = new Date();
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        fond.setText(Utilis.separatorNumber(Integer.parseInt(CaisseControl.funds))+" FCFA");
        rembourse.setText(Utilis.separatorNumber(FermetureCaisse.rembo) +" FCFA");
        chiffre.setText(Utilis.separatorNumber(FermetureCaisse.chiffre-FermetureCaisse.rembo)+" FCFA");
        carte_banque.setText(Utilis.separatorNumber(FermetureCaisse.carteB)+ " FCFA");
        espece.setText(Utilis.separatorNumber(FermetureCaisse.especeP)+ " FCFA");
        cheque.setText(Utilis.separatorNumber(FermetureCaisse.chequeP)+ " FCFA");
        attente.setText(Utilis.separatorNumber(FermetureCaisse.attenteP)+ " FCFA");
        commentaire.setText(FermetureCaisse.com);
        prenomOp.setText(FermetureCaisse.user.getPrenom() + " "+ FermetureCaisse.user.getNom());
        total.setText(Utilis.separatorNumber(FermetureCaisse.chiffre+Integer.parseInt(CaisseControl.funds))+" FCFA");
        ferme.setChiffre(FermetureCaisse.chiffre);
        ferme.setCarte(FermetureCaisse.carteB);
        ferme.setEspece(FermetureCaisse.especeP);
        ferme.setCheque(FermetureCaisse.chequeP);
        ferme.setAttente(FermetureCaisse.attenteP);
        ferme.setOperateur(FermetureCaisse.user.getPrenom()+ " "+ FermetureCaisse.user.getNom());
        ferme.setCommentaire(FermetureCaisse.com);

        date.setText(format.format(today));

        btn_imprimer.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etes vous sure de vouloir imprimer ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                btn_imprimer.getScene().getWindow().hide();
                boolean bol = false;
//            Paper.
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                Printer printer = Printer.getDefaultPrinter();
                PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
                double scaleX = pageLayout.getPrintableWidth() - 150;
                double scaleY = pageLayout.getPrintableHeight() - 190;
//            vbox.getTransforms().add(new Scale(scaleX, scaleY));
                vbox.setMaxSize(scaleX, scaleY);
                vbox.setScaleX(0.80);
                vbox.setScaleY(0.70);
                if (printerJob.showPrintDialog(((Stage) btn_imprimer.getScene().getWindow()).getOwner()) && printerJob.printPage(pageLayout, vbox)) {
                    bol = printerJob.endJob();
                }


                Trace trace = new Trace();
                trace.setAction("Caisse");
                trace.setObjet("Fermeture");
                trace.setUser(FermetureCaisse.user);
                
                
                
                trace.setValeur(chiffre.getText());
                Utilis.driver.insertTrace(trace);
                Utilis.caisse=0;
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


                Utilis.driver.insertFermerCaisse(ferme);
                Utilis.entrainPayer=true;


            }
        });

        btn_annuler.setOnMouseClicked(event ->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etes vous sure de vouloir quiter ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_annuler.getScene().getWindow();
                stage.close();
                Trace trace = new Trace();
                trace.setAction("Caisse");
                trace.setObjet("Fermeture");
                trace.setUser(FermetureCaisse.user);



                trace.setValeur(chiffre.getText());
                Utilis.driver.insertTrace(trace);
                Utilis.caisse=0;
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


                Utilis.driver.insertFermerCaisse(ferme);
                Utilis.entrainPayer=true;



            }

        });

    }
}
