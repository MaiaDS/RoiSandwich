package sample;

import java.net.URL;

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
	private Label lbl1, lbl2, argentJoueur;

	@FXML
	private Button btn1, btn2, btn3;

	
	/**
	 * Permet de lancer le niveau correspondant au boutton associer
	 */
	@FXML
	private void buttonNiveau1(ActionEvent event) throws Exception {
		
			Stage stage;
			Parent root;

			Main.niveau = new Niveau(1); //initialise la variable du main avec le niveau sur lequel nous avons cliqu�
			stage = (Stage) btn1.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("vueNiveau.fxml"));

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
	}
	/**
	 * Permet de lancer le niveau correspondant au boutton associer
	 */
	@FXML
	private void buttonNiveau2(ActionEvent event) throws Exception {
		Stage stage;
		Parent root;

		Main.niveau = new Niveau(2);//initialise la variable du main avec le niveau sur lequel nous avons cliqu�
		stage = (Stage) btn2.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("vueNiveau.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * Permet de lancer le niveau correspondant au boutton associer
	 */
	@FXML
	private void buttonNiveau3(ActionEvent event) throws Exception {
		Stage stage;
		Parent root;

		Main.niveau = new Niveau(3); //initialise la variable du main avec le niveau sur lequel nous avons cliqu�
		stage = (Stage) btn3.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("vueNiveau.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if(Main.niveau != null) {
			Main.joueur.ajouterArgent(Main.niveau.getTabScoreArgent()[1]);
			argentJoueur.setText(Main.joueur.afficherArgent());
		}
		else {
			argentJoueur.setText("0");
		}
	}
}
