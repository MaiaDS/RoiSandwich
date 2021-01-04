package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerFinDePartie implements Initializable {

	@FXML
	private Button btnMenu;
	
	@FXML
	private ImageView btnClose;
	
	@FXML
	private Label scoreLabel, argentLabel;
	
	/**
	 * Cette fonction est lancer lorsque l'on appuie sur la croix du niveau, 
	 * elle permet de quitter le niveau et de revenir sur l'�cran principale
	 */
	public void quitterNiveau() throws Exception {
		try {
			Stage stage = (Stage) btnMenu.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("vueMenuPrincipal.fxml"));
			stage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		scoreLabel.setText(String.valueOf(Main.niveau.getTabScoreArgent()[0]));//affiche le score
		argentLabel.setText(String.valueOf(Main.niveau.getTabScoreArgent()[1]));//affiche l'argent
	}
	
}
