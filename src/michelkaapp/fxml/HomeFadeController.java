/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import michelkaapp.utilis.Restor;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class HomeFadeController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane anchor_pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!michelkaapp.MichelKaApp.isTransited) {
            FadeTransition();
        }
    }

    private void FadeTransition() {
        michelkaapp.MichelKaApp.isTransited = true;
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("Home.fxml"));
        } catch (IOException ex) {
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
            Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        anchorPane.getChildren().addAll(pane);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2.5), pane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.setOnFinished(e -> {
            try {
                Utilis.PARAMETRES_APPLICATION = Utilis.driver.listParametres();
                AnchorPane fichePane = FXMLLoader.load(getClass().getResource("FicheTechnique.fxml"));
                anchorPane.getChildren().addAll(fichePane);
            } catch (IOException ex) {
                Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fadeIn.play();
        if (!michelkaapp.MichelKaApp.IS_RTORED) {
//            sleepThreed();
        }
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

    private void sleepThreed() {
        Task<Void> task = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("THREAD SLEEP AFTER STORE DB.");
                Thread.sleep(5000);
                return null;
            }
        };
        new Thread(task).run();
        task.setOnSucceeded(succed -> {
            findDayToStor();
        });

    }
}
