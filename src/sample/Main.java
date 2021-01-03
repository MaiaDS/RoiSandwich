package sample;


import classes.Joueur;
import classes.Niveau;
import classes.cuisine.Ingredient;
import classes.cuisine.Ingredient.Nom;

import classes.cuisine.materiel.Decoupe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	

	public static Niveau niveau1;
	
	public static Joueur joueur;


    @Override
    public void start(Stage stage) throws Exception{

//    	Parent root = FXMLLoader.load(getClass().getResource("vueNiveau.fxml"));
    	
    	joueur = new Joueur();
    	Parent root = FXMLLoader.load(getClass().getResource("vueMenuPrincipal.fxml"));

    	stage.setTitle("RoiSandwich");
    	stage.setScene(new Scene(root));
    	stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        }

}



