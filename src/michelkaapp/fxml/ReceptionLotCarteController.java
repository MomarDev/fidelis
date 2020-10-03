/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Trace;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class ReceptionLotCarteController implements Initializable {

    @FXML
    private AnchorPane pane_second;
    @FXML
    private AnchorPane tab_form;
    @FXML
    private Label titre;
    @FXML
    private Button btn_quitter;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private AnchorPane form;
    @FXML
    private DatePicker datepicker;
    @FXML
    private TextField debut_field;
    @FXML
    private TextField fin_field;

    private String debut = "";
    private String fin = "";
    private LocalDate date;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private ProgressBar progressBar;
    private int valDebut;
    private int valFind;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datepicker.setValue(Utilis.NOW_LOCAL_DATE());
        progressBar.setVisible(false);
        progressIndicator.setVisible(false);
        debut_field.textProperty().addListener((ob, old, nv) -> {
            if (nv != null && !nv.isEmpty() && !Utilis.isNumber(nv)) {
                debut_field.setStyle("-fx-background-color:#ddaa96");
            } else {
                debut_field.setStyle(null);
            }
        });
        fin_field.textProperty().addListener((ob, old, nv) -> {
            if (nv != null && !nv.isEmpty() && !Utilis.isNumber(nv)) {
                fin_field.setStyle("-fx-background-color:#ddaa96");
            } else {
                fin_field.setStyle(null);
            }
        });
    }

    @FXML
    private void handlerButtonAction(ActionEvent event) {
        if (event.getSource() == btn_quitter) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage = (Stage) btn_quitter.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        } else if (event.getSource() == btn_enregistrer) {
            debut = debut_field.getText();
            fin = fin_field.getText();
            date = datepicker.getValue();
            if (debut.isEmpty() || fin.isEmpty()) {
                Alert errorForm = new Alert(Alert.AlertType.ERROR, Utilis.ErrorForm, ButtonType.OK);
                errorForm.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText(Utilis.QuestionValideForm);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        valDebut = Integer.parseInt(debut);
                        valFind = Integer.parseInt(fin);
                        progressBar.setVisible(true);
                        progressIndicator.setVisible(true);
                        stage = (Stage) btn_enregistrer.getScene().getWindow();
                        Job job = new Job();
                        job.setOnSucceeded(
                                new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
                                Utilis.driver.insertTrace(new Trace(0, "Nouveau lot de carte", "Carte fidélité", "debut lot: " + valDebut + " fin lot :" + valFind, "", Utilis.user));
                                stage.close();
                            }
                        });
                        progressBar.progressProperty().bind(job.progressProperty());
                        progressIndicator.progressProperty().bind(job.progressProperty());
                        progressIndicator.progressProperty().addListener((obs, old, nv) -> {

                        });

                        Thread task = new Thread(job);
                        task.start();
                        fin_field.setText("");
                        debut_field.setText("");

                    } catch (Exception e) {
                        Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
                        e.printStackTrace();
                        Alert errorField = new Alert(Alert.AlertType.INFORMATION, "Seul des valeurs numérique sont acceptés!",
                                ButtonType.OK);
                        errorField.show();
                    }

                } else {
                    alert.close();
                }
            }
            Utilis.Carte_IS_UPDATE = true;
        }
    }
    class Job extends Task<Integer> {

        @Override
        protected Integer call() throws Exception {
            for (int i = valDebut; i <= valFind; i++) {
                if (!Utilis.driver.insertCarte(i, date.toString())) {
                    return i;
                }
                updateProgress(i, valFind);
                if (isCancelled()) {
                    return i;
                }
            }
            return valFind;
        }

        @Override
        protected void updateMessage(String message) {
            super.updateMessage(message);
        }

        @Override
        protected void updateProgress(double workDone, double max) {
            updateMessage("nombre de carte inserer est de :" + workDone);
            super.updateProgress(workDone, max);
        }

    }

    public static Alert createAlertWithOptOut(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        // Need to force the alert to layout in order to grab the graphic,
        // as we are replacing the dialog pane with a custom pane
        alert.getDialogPane().applyCss();
        Node graphic = alert.getDialogPane().getGraphic();
        // Create a new dialog pane that has a checkbox instead of the hide/show details button
        // Use the supplied callback for the action of the checkbox
        alert.setDialogPane(new DialogPane() {
            @Override
            protected Node createDetailsButton() {
                ProgressBar optOut = new ProgressBar();
                return optOut;
            }
        });
//        alert.getDialogPane().getButtonTypes().addAll(buttonTypes);
        alert.getDialogPane().setContentText(message);
        // Fool the dialog into thinking there is some expandable content
        // a Group won't take up any space if it has no children
        alert.getDialogPane().setExpandableContent(new Group());
        alert.getDialogPane().setExpanded(true);
        // Reset the dialog graphic using the default style
        alert.getDialogPane().setGraphic(graphic);
        alert.setTitle(title);
//      alert.setHeaderText(headerText);
        return alert;
    }

}
