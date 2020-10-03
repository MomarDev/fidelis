/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import michelkaapp.objets.Carte;
import michelkaapp.objets.Client;
import michelkaapp.objets.CodeBar;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class ImpressionFideliteController implements Initializable {

    @FXML
    private Label points_fidelite;
    @FXML
    private Label date_fidelite;
    @FXML
    private Label nom_prenom_client;
    @FXML
    private Label reference_carte;
    @FXML
    private Label adresse_michele_ka;
    @FXML
    private Label fermeture_michele_ka;
    @FXML
    private Label michele_ka_telephone;
    @FXML
    private Button btn_imprimer;
    @FXML
    private Button btn_annuler;
    @FXML
    private Label fidelite_title;
    private Client client;
    private Carte carte;
    public static String champ;
    @FXML
    private VBox vbox;
    @FXML
    private Pane pane_code_bar;
    @FXML
    private ImageView code_bar_image;
    public static CodeBar codebar;
    @FXML
    private Label code_chiffre;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_annuler.setOnMouseClicked(event -> {
            if (Utilis.A_IMPRIMER) {
                Alert im = new Alert(Alert.AlertType.WARNING);
                im.setContentText("Imprimer d'abors le ticket pour pouvoir fermer la fenetre!");
                im.show();
            } else {
                ((Stage) btn_annuler.getScene().getWindow()).close();
            }
        });
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
                vbox.setMaxSize(scaleX, scaleY);
                vbox.setScaleX(0.80);
                vbox.setScaleY(0.70);
                if (printerJob.showPrintDialog(((Stage) btn_imprimer.getScene().getWindow()).getOwner()) && printerJob.printPage(pageLayout, vbox)) {
                    bol = printerJob.endJob();
                }
                if (bol && Utilis.driver.updateCarte(client.getCarte().getReference(), champ, 0) && Utilis.driver.remettreCompteurAvoirZero(codebar)) {
                    ((Stage) btn_imprimer.getScene().getWindow()).close();
                }
            }
        });
    }

    public ImpressionFideliteController newInstence(Client client, String champ, boolean toImprime) {
        this.client = client;
        this.champ = champ;
        nom_prenom_client.setText(client.toString());
        reference_carte.setText(Utilis.separatorNumber(client.getCarte().getReference()) + "");
        date_fidelite.setText(Utilis.dateFrenchFormat(Utilis.NOW_LOCAL_DATE()));
        michele_ka_telephone.setText(Utilis.trouverParametre("TELEPHONE").getValeur());
        adresse_michele_ka.setText(Utilis.trouverParametre("ADRESSE").getValeur());
        fermeture_michele_ka.setText(Utilis.trouverParametre("HORAIRE").getValeur());
        if (this.client != null) {
            btn_imprimer.setVisible(toImprime);
            if (!toImprime) {
                btn_annuler.setText("Fermer");
            }
            this.client.setCarte(Utilis.driver.trouverCarte(client));
            if (champ.contains("nombre_fidelite_prestation")) {
                points_fidelite.setText("" + Utilis.separatorNumber(client.getCarte().getNptPrestation()) + " F");
                fidelite_title.setText("Fidélité Prestation");
            } else {
                points_fidelite.setText("" + Utilis.separatorNumber(client.getCarte().getNptsFideliteAchat()) + " F");
                fidelite_title.setText("Fidélité Achat");
            }
            code_bar_image.setImage(SwingFXUtils.toFXImage(barCode(), null));
            code_chiffre.setText(Utilis.separatorNumber(Integer.parseInt(codebar.getCodeBar().substring(0, 8))) + " " + Utilis.separatorNumber(Integer.parseInt(codebar.getCodeBar().substring(8, 14))));
        }
        return this;
    }

    private BufferedImage barCode() {
        int width = 220;
        int height = 20; // change the height and width as per your requirement
        String text = "";
        boolean teste = true;
        while (teste) {
            text = nombreAleatoire();
            CodeBar bar = Utilis.driver.existCodeBar(text);
            if (bar == null) {
                teste = false;
                System.out.println(text);
            }
        }
        codebar = new CodeBar(text, client.getCarte().getReference(), true, champ, false);
        Utilis.driver.insertCodeBar(codebar);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height);
        } catch (WriterException ex) {
            Logger.getLogger(ImpressionFideliteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage img = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return img;
    }

    private String nombreAleatoire() {
        int min = 1000000;
        int max = 48749279;
        String nombreAleatoireDebut = "" + (int) (Math.random() * ((max - min) + 1));
        String nombreAleatoireFin = "" + (int) max + (int) (Math.random() * ((max - min) + 1));
        String nb = nombreAleatoireDebut + nombreAleatoireFin;
        int len = nb.length();
        if (nb.length() > 14) {
            nb = nb.substring(0, 14);
        } else {
            nb = "0" + nb;
        }
        return nb;
    }
}
