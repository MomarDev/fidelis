package michelkaapp.fxml;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import michelkaapp.MichelKaApp;
import michelkaapp.objets.*;
import michelkaapp.utilis.Utilis;

public class UpdateTarifControl implements Initializable{
	@FXML
	private Button btn_enregistrer;
	@FXML
	private Button btn_quitter;
	@FXML
	private ComboBox combo_sexe;
	@FXML
	private ComboBox combo_age;
	@FXML
	private TextField fdl_prix;
	@FXML
	private TextField prestation;
	@FXML
	private TextField fld_prestation;
	private String sex;
	private String age;
	private String prest;
	private String details;
	private int prix = 0;
	ObservableList<String> listAge = FXCollections.observableArrayList(
			" ADULTE", " ADOS -20 ans",  " Garçon -12 ans", " BEBE -5 ANS",  " FILLETTE -12 ANS"
	);
	ObservableList<String> sexe = FXCollections.observableArrayList("Homme","Femme");


	private Tarif tarif;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (Utilis.TarifToUpdate != null) {
			try {
				tarif = (Tarif) Utilis.TarifToUpdate.clone();
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(UpdateTarifControl.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		initializerComboboxes();
		
	}

	private boolean formIsOK() {
		return (fdl_prix != null && fld_prestation != null && prestation != null && combo_age.getValue()!=null && combo_sexe.getValue()!=null);
	}


	private void initializerComboboxes() {
		if (MichelKaApp.connexionIsOK) {


				combo_age.getItems().addAll(listAge);
				combo_sexe.getItems().addAll(sexe);

			if (Utilis.TarifToUpdate != null) {
				try {
					tarif = (Tarif) Utilis.TarifToUpdate.clone();
					prepareUpdate();
				} catch (CloneNotSupportedException ex) {
					Logger.getLogger(UpdateServiceFormController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}


	}

	@FXML
	private void comboBoxActions(ActionEvent event) {
		if (event.getSource() == combo_age) {
			age = (String) combo_age.getSelectionModel().getSelectedItem();
			if (MichelKaApp.connexionIsOK) {



					combo_age.setItems(listAge);

				//cbox_sous_categories_prestation.setItems(null);
			}
		} else if (event.getSource() == combo_sexe) {
			sex = (String) combo_sexe.getSelectionModel().getSelectedItem();





						combo_sexe.setItems(sexe);



		}
	}
	@FXML
	private void handlerButtonAction(ActionEvent event) {
		if (event.getSource() == btn_quitter) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setContentText(Utilis.Quitter);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Stage stage = (Stage) btn_quitter.getScene().getWindow();
				Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
				stage.close();
			} else {
				alert.close();
			}
		} else if (event.getSource() == btn_enregistrer) {
			if (!fdl_prix.getText().isEmpty() && !fld_prestation.getText().isEmpty()) {
				prest =prestation.getText();
				details = fld_prestation.getText();
				if (Utilis.isNumber(fdl_prix.getText())) {
					prix = Integer.parseInt(fdl_prix.getText());

				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNombre);
					alert.showAndWait();
				}


			}
			sex = (String) combo_sexe.getSelectionModel().getSelectedItem();
			age = (String) combo_age.getSelectionModel().getSelectedItem();

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setContentText(Utilis.QuestionValideForm);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				if (formIsOK()) {
					Tarif ts = new Tarif(tarif.getId(), sex, age, prest,details,prix,Integer.toString(prix));


					if (Utilis.driver.updateTarif(ts)) {
						Alert info = new Alert(Alert.AlertType.INFORMATION, "Le tarif a été mise à  jour avec succée.");
						info.show();

						prix = 0;
						Stage stage = (Stage) btn_enregistrer.getScene().getWindow();
						Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
						Utilis.SERVICE_IS_UPDATE = true;
						stage.close();
					} else {
						Alert erroSql = new Alert(Alert.AlertType.ERROR, "Une erreur c'est produite lors de l'enregistrement de la prestation", ButtonType.OK);
						erroSql.show();
					}
				} else {
					Alert erreForm = new Alert(Alert.AlertType.ERROR, "Veuillez revérifier les informations saisis s'il vous plait.", ButtonType.OK);
					erreForm.show();
				}
			} else {
				alert.close();
			}
		}
	}
	private void prepareUpdate() {
		prest = tarif.getPrestation();
		details = tarif.getDetail();
		prestation.setText(tarif.getPrestation());
		combo_sexe.setValue(tarif.getSexe());
		combo_age.setValue(tarif.getNom());
		fdl_prix.setText(Integer.toString(tarif.getPrix()));
		fld_prestation.setText(tarif.getDetail());
	}

}
