package michelkaapp.fxml;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import michelkaapp.objets.Carte;
import michelkaapp.objets.Client;
import michelkaapp.objets.CodeBar;
import michelkaapp.objets.Visite;
import michelkaapp.utilis.Utilis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NiveauVisiteControl implements Initializable {
    @FXML
    private Label points_fidelite;
    @FXML
    private Label total_fidel;
    @FXML
    private Label points_ajout;
    @FXML
    private Label niveau_precedent;
    @FXML
    private Label niveau_suivant;
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

    public static int points;
    private int fidele=0;
    @FXML
    private Button btn_annuler;
    @FXML
    private Label fidelite_title;
    private Client client;
    private Carte carte;
    public static String champ;
    @FXML
    private VBox vbox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btn_annuler.setOnMouseClicked(event -> {
            if (Utilis.A_IMPRIMER) {
                Alert im = new Alert(Alert.AlertType.WARNING);
                im.setContentText("Revenez plus tard pour pouvoir beneficier de votre bonus!");
                im.show();
            } else {
                ((Stage) btn_annuler.getScene().getWindow()).close();
            }
        });
    }


    public NiveauVisiteControl newInstence(Client client, String champ) {
        this.client = client;
        this.champ = champ;
        nom_prenom_client.setText(client.toString());
        reference_carte.setText(Utilis.separatorNumber(client.getCarte().getReference()) + "");
        michele_ka_telephone.setText(Utilis.trouverParametre("TELEPHONE").getValeur());
        adresse_michele_ka.setText(Utilis.trouverParametre("ADRESSE").getValeur());
        fermeture_michele_ka.setText(Utilis.trouverParametre("HORAIRE").getValeur());
        if (this.client != null) {
            carte = Utilis.driver.trouverCarte(client);
            List<Visite> visites = Utilis.driver.listVisites(carte.getReference());
            if (visites!=null){
                niveau_precedent.setText(visites.size()+ " éme Visite");
                niveau_suivant.setText(visites.size()+ 1 + " éme Visite");
            }

            points_ajout.setText(Utilis.separatorNumber(ValidationChequeCadeauControl.prixPresstationVisible*5/100));
            points=ValidationChequeCadeauControl.prixPresstationVisible;
            this.client.setCarte(Utilis.driver.trouverCarte(client));
            if (champ.contains("nombre_fidelite_prestation")) {
                points_fidelite.setText("" + Utilis.separatorNumber(client.getCarte().getNptPrestation()) + " F");
                fidelite_title.setText("Fidélité Prestation");
                fidele= (ValidationChequeCadeauControl.prixPresstationVisible*5/100) + client.getCarte().getNptPrestation();
                total_fidel.setText(Utilis.separatorNumber(fidele)+" F");
            } else {
                points_fidelite.setText("" + Utilis.separatorNumber(client.getCarte().getNptsFideliteAchat()) + " F");
                fidelite_title.setText("Fidélité Achat");
            }


        }
        return this;
    }
}
