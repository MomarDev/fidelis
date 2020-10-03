/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.utilis;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import michelkaapp.fxml.LoginController;
import michelkaapp.objets.Trace;

/**
 *
 * @author leyu
 */
public class Logout {

    public boolean logout(Stage stage) {
        Trace trace = new Trace();
        trace.setUser(Utilis.user);
        trace.setAction("Logout");
        trace.setObjet("Application");
        trace.setValeur(Utilis.Success_Values);
        boolean teste = Utilis.driver.insertTrace(trace);
        michelkaapp.MichelKaApp.isTransited = false;
        Parent root = null;
        try {
            root = FXMLLoader.load(LoginController.class.getResource("Login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Logout.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        return teste;
    }
}
