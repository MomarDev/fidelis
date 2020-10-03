/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.utilis;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import michelkaapp.objets.Trace;

/**
 *
 * @author leyu
 */
public class SendMail {

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

    public SendMail() {
        properties = System.getProperties();
    }

    public SendMail(String username, String password, String[] dest, String sujet, String corps, String mailServer, String hostname) {
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
            if (Utilis.NOM_ENVOYEUR_MAIL.isEmpty()) {
                Utilis.NOM_ENVOYEUR_MAIL = Utilis.trouverParametre("MAIL_NOM_ENVOYEUR").getValeur();
            }

            msg.setFrom(new InternetAddress(username, Utilis.NOM_ENVOYEUR_MAIL));
            InternetAddress[] adresseClient = new InternetAddress[dest.length];
            for (int i = 0; i < dest.length; i++) {
                try {
                    if (dest[i] != null) {
                        adresseClient[i] = new InternetAddress(dest[i]);
                        // System.out.println("bien rentré");
                        System.out.println("User: " + dest[i]);
                        Trace trace = new Trace();
                        trace.setUser(Utilis.user);
                        trace.setAction("Envoie Mail");
                        trace.setObjet("Anniversaire:" + dest[i]);
                        trace.setValeur(Utilis.Success_Values);
                        boolean teste = Utilis.driver.insertTrace(trace);

                    }
                } catch (AddressException e) {
                    System.out.println("error to convert to email adresse" + adresseClient[0]);
                    Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));

                }

            }
            if (adresseClient == null && adresseClient.length == 0) {
                Utilis.LOGGER.log("Erreur lors de l'envoi.");
                Alert error = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'envoi.", ButtonType.OK);
                error.show();

            } else {
                try {
                    msg.addRecipients(Message.RecipientType.CC, adresseClient);
                } catch (MessagingException e) {
                    Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
                }
            }

            msg.setSubject(sujet);
            if (fichierJoint == null) {
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
                DataSource source = new FileDataSource(fichierJoint.getAbsolutePath());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fichierJoint.getAbsolutePath());
                multipart.addBodyPart(messageBodyPart);
                msg.setContent(multipart);
                Transport transport = session.getTransport("smtp");
                transport.connect(hostname, username, password);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
            }
        } catch (UnsupportedEncodingException | MessagingException e) {
            seeded = false;
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

}
