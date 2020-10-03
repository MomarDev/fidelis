package michelkaapp.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Trace;
import michelkaapp.objets.User;
import michelkaapp.utilis.PasswordInputDialog;
import michelkaapp.utilis.Utilis;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FermetureCaisse implements Initializable {
    @FXML
    private Label fond;
    @FXML
    private Label remboursement;
    @FXML
    private Label total;
    @FXML
    private Button btn_quitter;
    @FXML
    private Button btn_save;
    @FXML
    private Label date;
    @FXML
    private Label chiffre_affaire;
    @FXML
    private Label carte_banque;
    @FXML
    private Label cheque;
    @FXML
    private Label attente;
    @FXML
    private Label espece;
    @FXML
    private Button btn_voir;
    @FXML
    private Label now;
    @FXML
    private ComboBox operateur;
    @FXML
    private TextArea commentaire;
    private List<User> listOperateur;
    private List<String> nomOperateur = new ArrayList<String>();
    private Parent root;
    private Stage stage;
    public static User user;
    public static int chiffre=0;
    public static int carteB=0;
    public static int especeP=0;
    public static int chequeP=0;
    public static int attenteP=0;
    public static int rembo=0;
    public static String com;
    private Alert alertF;
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.0");

    Date today = new Date();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listOperateur = Utilis.driver.listUser();

        commentaire.textProperty().addListener((obs, old, nv) -> {
    		
    		com=commentaire.getText();
        });
        
        for (User op : listOperateur) {
            nomOperateur.add(op.getUsername());
        }

        operateur.getItems().addAll(nomOperateur);


        fond.setText(Utilis.separatorNumber(Integer.parseInt(CaisseControl.funds))+ " FCFA");

        date.setText(Utilis.separateurEspace(Utilis.driver.getLastOpenCaisse()));
        now.setText(Utilis.separateurEspace(format.format(today)));
        chiffre=Utilis.driver.chiffreAffaire(Utilis.driver.getLastOpenCaisse(),format.format(today));
       

        carteB=Utilis.driver.moneyMode(Utilis.driver.getLastOpenCaisse(),format.format(today),"Carte bancaire");
        carte_banque.setText(Utilis.separatorNumber(Utilis.driver.moneyMode(Utilis.driver.getLastOpenCaisse(),format.format(today),"Carte bancaire")) + " FCFA");
        
        chequeP =Utilis.driver.moneyMode(Utilis.driver.getLastOpenCaisse(),format.format(today),"Chéque");
        cheque.setText(Utilis.separatorNumber(Utilis.driver.moneyMode(Utilis.driver.getLastOpenCaisse(),format.format(today),"Chéque")) + " FCFA");
        
        especeP=Utilis.driver.moneyMode(Utilis.driver.getLastOpenCaisse(),format.format(today),"Espèce");
        espece.setText(Utilis.separatorNumber(Utilis.driver.moneyMode(Utilis.driver.getLastOpenCaisse(),format.format(today),"Espèce")) + " FCFA");

        if ( Utilis.driver.listRestants(Utilis.driver.getLastOpenCaisse(),format.format(today))!=null){
            attenteP=Utilis.driver.moneyModeRestant(Utilis.driver.getLastOpenCaisse(),format.format(today),"Attente");
            attente.setText(Utilis.separatorNumber(Utilis.driver.moneyModeRestant(Utilis.driver.getLastOpenCaisse(),format.format(today),"Attente")) + " FCFA");
        }
        else {
            attenteP=0;
            attente.setText(0+" FCFA");
        }
        
        rembo=Utilis.driver.moneyMode(Utilis.driver.getLastOpenCaisse(),format.format(today),"remboursement");
        remboursement.setText(Utilis.separatorNumber(Utilis.driver.moneyMode(Utilis.driver.getLastOpenCaisse(),format.format(today),"remboursement")) + " FCFA");
        chiffre_affaire.setText(Utilis.separatorNumber(chiffre-rembo)+ " FCFA");
        
        
        total.setText(Utilis.separatorNumber(chiffre+Integer.parseInt(CaisseControl.funds))+ " FCFA");
    }

    @FXML
    private void handlerButton(ActionEvent event){

        if(event.getSource()==btn_voir){
            Stage impStage = new Stage();
            impStage.setResizable(false);
            FXMLLoader impRoot = null;
            AnchorPane pane = null;
            try {

                impRoot = new FXMLLoader(getClass().getResource("Restant.fxml"));
                pane = impRoot.load();


            } catch (IOException ex) {
                Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

            impStage.setScene(new Scene(pane));
            impStage.show();

        }
        if (event.getSource()== btn_quitter){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {

                	
                    root = FXMLLoader.load(getClass().getResource("Payement.fxml"));
                    stage = (Stage) btn_quitter.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    Utilis.entrainPayer=true;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                alert.close();
            }
        }
        if(event.getSource()==btn_save){

            if (operateur.getValue()!=null){

                String userOp = (String) operateur.getValue();
                PasswordInputDialog passwordDialog = new PasswordInputDialog();
                passwordDialog.setHeaderText("Confirmer avec votre mot de Passe");
                passwordDialog.setContentText("Mot de Passe");
              
                System.out.println(user);
                Optional<String> result = passwordDialog.showAndWait();
                if(result.isPresent()) {
                    System.out.println(result);
                    user = Utilis.driver.getUserPasseword(result.get());
                    if (user!=null) {
                        alertF = new Alert(Alert.AlertType.INFORMATION,"La caisse est fermée avec succés!!!");
                        alertF.setHeaderText("Fermeture Caisse");
                        alertF.show();
                        try {
                            root = FXMLLoader.load(getClass().getResource("TicketFermeture.fxml"));
                            stage = (Stage) btn_save.getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                            stage.setOnCloseRequest(event1 -> {
                                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                                Utilis.entrainPayer=false;
                                Trace trace = new Trace();
                                trace.setAction("Caisse");
                                trace.setObjet("Fermeture");
                                trace.setUser(user);



                                trace.setValeur(Utilis.separatorNumber(chiffre-rembo)+ " FCFA");
                                Utilis.driver.insertTrace(trace);
                                Utilis.caisse=0;
                               
                            });

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Mot de passe incorrect");
                        alert.showAndWait();
                    }

                }


            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Information");
                alert.setContentText("Veuillez choisir votre nom avant de valider");
                alert.showAndWait();
            }
        }
    }

}
