/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import michelkaapp.objets.Client;
import michelkaapp.objets.DestinataireMail;
import michelkaapp.objets.Mail;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class SendMailController implements Initializable {

    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_envoyer;
    @FXML
    private Button btnJointFile;
    @FXML
    private Label labelFileName;
    @FXML
    private AnchorPane second_pane;
    @FXML
    private TextField emailObjet;
    @FXML
    private TextArea emailText;
    @FXML
    private TextField emailDestinataires;
    @FXML
    private Label delecteFile;
    File file = null;
    private List<Client> clients = null;
    private String objet;
    private String texte;
    private EMail mail;
    private String[] destinataires = null;
    private int count = 0;
    Mail mailEnvoye = null;
    private DestinataireMail destinataireMail;
    private Stage stage;
    @FXML
    private Label status;
    @FXML
    private ProgressIndicator indeterminedProgressIndicator;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.clientsTomail != null && !Utilis.clientsTomail.isEmpty()) {
            clients = Utilis.clientsTomail;
            destinataireMail = new DestinataireMail();
            int len = clients.size();
            String tab = "";
            for (int i = 0; i < len; i++) {
                if (Utilis.isAEmailAdress(clients.get(i).getEmail()) && !clients.get(i).getEmail().isEmpty()) {
                    destinataireMail.setDestinataire(clients.get(i).toString());
                    destinataireMail.setMail(clients.get(i).getEmail());
                    if (count == 0) {
                        emailDestinataires.setText(clients.get(i).getEmail());
                        tab = clients.get(i).getEmail();
                    } else {
                        emailDestinataires.setText(emailDestinataires.getText() + ";" + clients.get(i).getEmail());
                        tab += ";" + clients.get(i).getEmail();
                    }
                    count++;
                }
            }
            if (count != 0) {
                if (Utilis.PARAMETRES_APPLICATION == null || Utilis.PARAMETRES_APPLICATION.isEmpty()) {
                    Utilis.PARAMETRES_APPLICATION = Utilis.driver.listParametres();
                }
                String bst = Utilis.trouverParametre("MAIL_BST").getValeur();
                String michele = Utilis.trouverParametre("EMAIL_MICHELE_KA").getValeur();
                if (bst != null && michele != null && !michele.isEmpty() && !bst.isEmpty()
                        && Utilis.isAEmailAdress(bst) && Utilis.isAEmailAdress(michele)) {
                    tab += ";" + bst + ";" + michele;
                } else {
                    tab += ";" + "micheleka@orange.sn";
                }
            }
            Utilis.clientsTomail = null;
            destinataires = tab.split(";");
        }
        if (Utilis.MAIL_ANNIVERSAIRE != null) {
            emailObjet.setText(Utilis.MAIL_ANNIVERSAIRE.getObjet());
            emailText.setText(Utilis.MAIL_ANNIVERSAIRE.getTexte());
            Utilis.MAIL_ANNIVERSAIRE = null;
        }
        delecteFile.setVisible(false);
        delecteFile.setOnMouseClicked((event) -> {
            if (file != null) {
                file = null;
                labelFileName.setText("");
                delecteFile.setVisible(false);
            }
        });
    }

    private void initForm() {
        objet = emailObjet.getText();
        texte = emailText.getText();
    }

    private boolean formIsOk() {
        return !texte.isEmpty() && !objet.isEmpty() && destinataires != null && destinataires.length != 0;
    }

    private void creerMail() {
        mail = new EMail();
        mail.setUsername(Utilis.trouverParametre("EMAIL").getValeur());
        mail.setDestinataires(destinataires);
        mail.setCorps(texte);
        mail.setFichierJoint(file);
        mail.setHostname(Utilis.trouverParametre("HOSTNAME_SMTP").getValeur());
        mail.setPassword(Utilis.trouverParametre("MOT_DE_PASSE_MAIL").getValeur());
        mail.setMailServer(Utilis.trouverParametre("TYPE_SERVER_MAIL").getValeur());
        mail.setSujet(objet);
    }

    @FXML
    private void handlerbutton(ActionEvent event) {
        if (event.getSource() == btnJointFile) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Fichier joint");
            file = fileChooser.showOpenDialog(btn_envoyer.getScene().getWindow());
            if (file != null) {
                labelFileName.setText(file.getName());
                delecteFile.setVisible(true);
            } else {
                Utilis.LOGGER.log("Le fichier n'a pas été chargé.");
                Alert errorFile = new Alert(Alert.AlertType.ERROR, "Le fichier n'a pas été chargé.", ButtonType.CANCEL);
                errorFile.close();
            }
        }
        if (event.getSource() == btn_annuler) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage = (Stage) btn_annuler.getScene().getWindow();
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();
            } else {
                alert.close();
            }
        }
        if (event.getSource() == btn_envoyer) {
            initForm();
            if (formIsOk()) {
                creerMail();
                mailTask();
                stage = (Stage) btn_envoyer.getScene().getWindow();
            } else {
                Utilis.LOGGER.log(Utilis.ErrorForm);
                Alert err = new Alert(Alert.AlertType.ERROR, Utilis.ErrorForm, ButtonType.OK);
                err.show();
                ((Stage) btn_envoyer.getScene().getWindow()).close();
            }
        }
    }

    public class EMail {

        private String username;
        private String password;
        private String[] dest;
        private String sujet;
        private String corps;
        private Properties properties = null;
        private String mailServer;
        private Boolean seeded = true;
        private String hostname;
        private File fichierJoint = null;

        public EMail() {
            properties = System.getProperties();
        }

        public EMail(String username, String password, String[] dest, String sujet, String corps, String mailServer, String hostname) {
            this.username = username;
            this.password = password;
            this.dest = dest;
            this.sujet = sujet;
            this.corps = corps;
            this.mailServer = mailServer;
            this.hostname = hostname;
        }

        public String[] getDestinataires() {
            return dest;
        }

        public void setDestinataires(String[] dest) {
            this.dest = dest;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSujet() {
            return sujet;
        }

        public void setSujet(String sujet) {
            this.sujet = sujet;
        }

        public String getCorps() {
            return corps;
        }

        public void setCorps(String corps) {
            this.corps = corps;
        }

        public String getMailServer() {
            return mailServer;
        }

        public void setMailServer(String mailServer) {
            this.mailServer = mailServer;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public File getFichierJoint() {
            return fichierJoint;
        }

        public void setFichierJoint(File fichierJoint) {
            this.fichierJoint = fichierJoint;
        }

        public Boolean isSeeded() {
            return this.seeded;
        }

        public void send() {
            properties = System.getProperties();
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", hostname);
            properties.put("mail.smtp.user", username);
            properties.put("mail.smtp.password", password);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(properties);
            try {
                MimeMessage msg = new MimeMessage(session);
                if (Utilis.PARAMETRES_APPLICATION != null && !Utilis.PARAMETRES_APPLICATION.isEmpty()) {
                    Utilis.NOM_ENVOYEUR_MAIL = Utilis.trouverParametre("MAIL_NOM_ENVOYEUR").getValeur();
                }
                msg.setFrom(new InternetAddress(username, Utilis.NOM_ENVOYEUR_MAIL));
                InternetAddress[] adresseClient = new InternetAddress[count];
                for (int i = 0; i < count; i++) {
                    try {
                        if (dest[i] != null) {
                            adresseClient[i] = new InternetAddress(dest[i]);
                            System.out.println(dest[i]);
                        }
                    } catch (AddressException e) {
                        System.out.println("error to convert to email adresse" + adresseClient[0]);
                        Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
                    }

                }
                if (adresseClient == null && adresseClient.length == 0) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'envoi.", ButtonType.OK);
                    error.show();
                } else {
                    try {
                        msg.addRecipients(Message.RecipientType.BCC, adresseClient);
                    } catch (MessagingException e) {
                        Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
                    }
                }

                msg.setSubject(sujet);
                if (file == null) {
                    fichierJoint = file;
                    msg.setContent(corps, "text/html");
                    try (Transport transport = session.getTransport("smtp")) {
                        transport.connect(hostname, username, password);
                        transport.sendMessage(msg, msg.getAllRecipients());
                    }
                } else {
                    // Create a multipar message
                    Multipart multipart = new MimeMultipart();
                    // Create the message part
                    BodyPart messageBodyPart = new MimeBodyPart();

                    // Now set the actual message
                    messageBodyPart.setText(corps);

                    multipart.addBodyPart(messageBodyPart);
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(file.getAbsolutePath());
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(file.getAbsolutePath());
                    multipart.addBodyPart(messageBodyPart);
                    msg.setContent(multipart);
                    Transport transport = session.getTransport("smtp");
                        transport.connect(hostname, username, password);
                        transport.sendMessage(msg, msg.getAllRecipients());
                    }
            } catch (Exception e) {
                seeded = false;
                Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
            }
        }

    }

    private void runTask() {

        final double wndwWidth = 300.0d;
        Label updateLabel = new Label("Envoi en cour...");
        updateLabel.setPrefWidth(wndwWidth);
        ProgressIndicator progress = new ProgressIndicator();
        progress.setPrefWidth(wndwWidth);

        VBox updatePane = new VBox();
        updatePane.setPadding(new Insets(10));
        updatePane.setSpacing(5.0d);
        updatePane.getChildren().addAll(updateLabel, progress);

        Stage taskUpdateStage = new Stage(StageStyle.UTILITY);
        taskUpdateStage.setAlwaysOnTop(true);
        taskUpdateStage.setIconified(false);
        taskUpdateStage.setScene(new Scene(updatePane));
        taskUpdateStage.show();

        Task longTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                mail.send();
                mailEnvoye = new Mail();
                mailEnvoye.setDestinataire(destinataireMail);
                mailEnvoye.setObjet(mail.getSujet());
                mailEnvoye.setEtat(mail.isSeeded());
                mailEnvoye.setExpediteur(mail.getUsername());
                mailEnvoye.setTexte(mail.getCorps());
                if (file != null) {
                    mailEnvoye.setFichierAttache(mail.getFichierJoint().getAbsolutePath());
                }
                Utilis.driver.insertEmailEnvoye(mailEnvoye);

                return null;
            }
        };

        longTask.setOnSucceeded(
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
            taskUpdateStage.hide();
            taskUpdateStage.close();
            if (mail.seeded) {
                stage.close();
            }

            }
        }
        );
//        progress.progressProperty().bind(longTask.progressProperty());
//        updateLabel.textProperty().bind(longTask.messageProperty());

        taskUpdateStage.show();

        new Thread(longTask)
                .start();
    }

    private void mailTask() {

        Task longTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                status.setText("Envoi en cour...");
                status.setVisible(true);
                indeterminedProgressIndicator.setVisible(true);
                btn_annuler.setDisable(true);
                btn_envoyer.setDisable(true);
                btnJointFile.setDisable(true);
                mail.send();
                mailEnvoye = new Mail();
                mailEnvoye.setDestinataire(destinataireMail);
                mailEnvoye.setObjet(mail.getSujet());
                mailEnvoye.setEtat(mail.isSeeded());
                mailEnvoye.setExpediteur(mail.getUsername());
                mailEnvoye.setTexte(mail.getCorps());
                if (file != null) {
                    mailEnvoye.setFichierAttache(mail.getFichierJoint().getAbsolutePath());
                }
                Utilis.driver.insertEmailEnvoye(mailEnvoye);

                return null;
            }
        };

        longTask.setOnSucceeded(
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                btn_annuler.setDisable(false);
                btn_envoyer.setDisable(false);
                btnJointFile.setDisable(false);
                if (mail.seeded) {
                    status.setText("Envoi réussi.");
                    Alert infoMail = new Alert(AlertType.INFORMATION, "Mail envoyé !", ButtonType.OK);
                    infoMail.show();
                    Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                    stage.close();
                } else {
                    Utilis.LOGGER.log("SEND MAIL CONTROLLER: Error to Seed Mail");
                    status.setText("Envoi échec!");
                    Alert errorMail = new Alert(AlertType.ERROR, "Mail non envoyé ! Veuillez réessayer.", ButtonType.CLOSE);
                    errorMail.show();
                }

            }
        }
        );
//        mail le boulot
        new Thread(longTask)
                .start();
    }

}
