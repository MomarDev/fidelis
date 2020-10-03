/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Button btn_admin;
    private Button btn_salon;

//  Non variables
    private Stage stage;
    private Parent root;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane anchor_pane;
    @FXML
    private Label welcome_user;

    public void adminEvent(ActionEvent event) throws IOException {
        if (event.getSource() == btn_admin) {
            root = FXMLLoader.load(getClass().getResource("Utilisateurs.fxml"));
            stage = (Stage) btn_salon.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (event.getSource() == btn_salon) {
            root = FXMLLoader.load(getClass().getResource("FicheTechnique.fxml"));
            stage = (Stage) btn_salon.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.user != null) {
            welcome_user.setText(Utilis.user.getNom());
        }
    }

}
