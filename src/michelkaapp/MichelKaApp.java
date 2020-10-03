/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import michelkaapp.objets.Trace;
import michelkaapp.utilis.PreferenceDB;
import michelkaapp.utilis.Restor;
import michelkaapp.utilis.Utilis;

/**
 *
 * @author leyu
 */
public class MichelKaApp extends Application {

    public static boolean isTransited = false;
    public static boolean connexionIsOK = false;
    //public static boolean connexionIsOK = true;
    public static boolean ANNIVERSAIRE_ENVOYEE = false;
    public static boolean ALERT_SEUIL_PRODUIT = false;
    public static boolean IS_RTORED = false;

    @Override
    public void start(Stage stage) throws Exception {
        Utilis.BASE_DE_DONNEE = preference();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Fidélis");
        Image image = new Image(MichelKaApp.class.getResourceAsStream("images/background_app.png"));
        stage.getIcons().add(image);
        stage.setOnHidden(event -> Platform.exit());
        Platform.setImplicitExit(true);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Vous êtes sur le point de fermer l'application. \n Etes vous sûr de vouloir quitter?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (connexionIsOK) {
                        Trace trace = new Trace();
                        trace.setUser(Utilis.user);
                        trace.setAction("Fermeture");
                        trace.setObjet("Application");
                        trace.setValeur(Utilis.Success_Values);
                        Utilis.driver.insertTrace(trace);
                        Utilis.driver.close();
                    }
                    Platform.exit();
                }
            }
        });
        stage.show();
        restorBoucle();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private PreferenceDB preference() {
        PreferenceDB db = new PreferenceDB();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File("preference.bst")));
            String line = "";
            line += reader.readLine();
            System.out.println(line + "on while");
            System.out.println(line + "after while");
            if (!line.isEmpty()) {
                String pref[] = line.split(":");
                db.setHostname(pref[0]);
                db.setPort(pref[1]);
                db.setDbname(pref[2]);
                db.setUsername(pref[3]);
                db.setPassword(pref[4]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(MichelKaApp.class.getName()).log(Level.SEVERE, null, ex);
                Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
            }
        }
        System.out.println(db.toString());
        return db;
    }

    private void restorBoucle() {
        Timeline timer = new Timeline(new KeyFrame(Duration.hours(1), action -> {
            System.out.println("is boucle de restauration....");
            System.out.println(LocalDateTime.now().getHour());
            while (!MichelKaApp.IS_RTORED && (12 <= LocalDateTime.now().getHour() && LocalDateTime.now().getHour() <= 13)) {
                findDayToStor();
            }
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void lancerSauvegarde(File file) {
        if (Utilis.PARAMETRES_APPLICATION != null && !Utilis.PARAMETRES_APPLICATION.isEmpty()) {
            System.out.println("Parametre ok... for store");
            File src = new File(Utilis.trouverParametre(Utilis.PARA_SOURCE_PATH).getValeur());
            System.out.println("lancer copy thread...");
            new Restor(src, src.toPath(), file.toPath());
        }
    }

    private void findDayToStor() {
        File res = new File(Utilis.RESTOR_DIR);
        if (res.isFile() && res.isDirectory()) {
            System.out.println("is ok here for restaure.");
        } else {
            if (res.mkdir()) {
                System.out.println("la creation d fichier a bien reussie.");
            }
        }
        String jour = Integer.toString(Utilis.jourDeSemaine());
        File jdir = new File(Utilis.RESTOR_DIR + '/' + jour);
        if (jdir.exists() && jdir.isDirectory()) {
            System.out.println("is ok ");
        } else {
            if (jdir.mkdir()) {
                System.out.println("creation reussi.");
            }
        }
        if (jdir.exists() && jdir.isDirectory()) {
            System.out.println("LancerSauvegarde...");
            lancerSauvegarde(jdir);
        }
        System.out.println(jdir.exists() + " && " + jdir.isDirectory());
    }

}
