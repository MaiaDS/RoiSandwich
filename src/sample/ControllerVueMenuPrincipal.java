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
	private Label lbl1, lbl2, argentJoueur, messageAuJoueur;

	@FXML
	private Button btn1, btn2, btn3;

	/**
	 * Permet de lancer le niveau correspondant au boutton associer
	 */
	@FXML
	private void buttonNiveau1(ActionEvent event) throws Exception {

		Stage stage;
		Parent root;

		Main.niveau = new Niveau(1); // initialise la variable du main avec le niveau sur lequel nous avons cliquï¿½
		stage = (Stage) btn1.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("vueNiveau.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Permet de lancer le niveau correspondant au boutton associer
	 * l'argent doit etre supérieur ou égal à 100 pour y acceder
	 */
	@FXML
	private void buttonNiveau2(ActionEvent event) throws Exception {

		if (Main.joueur.afficherArgent() >= 100) {

			Stage stage;
			Parent root;

			Main.niveau = new Niveau(2);// initialise la variable du main avec le niveau sur lequel nous avons
										// cliquï¿½
			stage = (Stage) btn2.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("vueNiveau.fxml"));

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else {
			messageAuJoueur.setText("argent doit etre de 100 minimum");
		}

	}

	/**
	 * Permet de lancer le niveau correspondant au boutton associer
	 * l'argent doit etre supérieur ou égal à 300 pour y acceder
	 */
	@FXML
	private void buttonNiveau3(ActionEvent event) throws Exception {
		if (Main.joueur.afficherArgent() >= 300) {
			Stage stage;
			Parent root;

			Main.niveau = new Niveau(3); // initialise la variable du main avec le niveau sur lequel nous avons cliquï¿½
			stage = (Stage) btn3.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("vueNiveau.fxml"));

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else {
			messageAuJoueur.setText("argent doit etre de 300 minimum");
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (Main.niveau != null) {
			Main.joueur.ajouterArgent(Main.niveau.getTabScoreArgent()[1]);
			argentJoueur.setText(String.valueOf(Main.joueur.afficherArgent()));
		} else {
			argentJoueur.setText("0");
		}
	}
}
