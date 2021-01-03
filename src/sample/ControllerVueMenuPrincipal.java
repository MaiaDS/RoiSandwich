package sample;

import java.net.URL;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

import classes.Niveau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerVueMenuPrincipal implements Initializable {


	@FXML
	private Label lbl1, lbl2;

	@FXML
	private Button btn1, btn2, btn3;

	@FXML
	private void buttonNiveau1(ActionEvent event) throws Exception {
			Stage stage;
			Parent root;

			Main.niveau1 = new Niveau(1);
			stage = (Stage) btn1.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("vueNiveau1.fxml"));

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
	}
	
	@FXML
	private void buttonNiveau2(ActionEvent event) throws Exception {
		Stage stage;
		Parent root;

		Main.niveau1 = new Niveau(2);
		stage = (Stage) btn2.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("vueNiveau1.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void buttonNiveau3(ActionEvent event) throws Exception {
		Stage stage;
		Parent root;

		Main.niveau1 = new Niveau(3);
		stage = (Stage) btn3.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("vueNiveau1.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	
	}
}
