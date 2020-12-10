package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.net.URL;

import java.util.HashMap;
import java.util.ResourceBundle;

import classes.cuisine.Ingredient;
import classes.cuisine.Ingredient.Etat;
import classes.cuisine.Ingredient.Nom;
import classes.*;
import classes.cuisine.*;
import classes.cuisine.materiel.*;

public class Controller implements Initializable {

//	

	private Object container;

	private Materiel materielFriteuse;
	private Materiel materielAssemblage;
	private Materiel materielPlaqueDeCuisson;
	private Materiel materielDecoupe;
	private Materiel materielLaveVaisselle;
	private Materiel materielPoubelle;

	// mickael
	private Ingredient patate = new Ingredient(Nom.PATATE, "image/amandine.png");
	private IngredientCuit beef = new IngredientCuit(Nom.STEAK_DE_BOEUF, "image/dsc_0315.jpg");
	private Decoupe plancheADecoupe = new Decoupe();
	private Friteuse appareilAFritte = new Friteuse();

	@FXML
	ImageView PATATE;
	@FXML
	Label compteurPatateLabel;
	
	@FXML
	ImageView STEAK_DE_BOEUF;
	@FXML
	ImageView ingredient3;
	@FXML
	ImageView ingredient4;
	@FXML
	ImageView ingredient5;
	@FXML
	ImageView ingredient6;
	@FXML
	ImageView ingredient7;
	@FXML
	ImageView ingredient8;
	@FXML
	ImageView ingredient9;

	@FXML
	ImageView stock1;
	@FXML
	ImageView stock2;
	@FXML
	ImageView stock3;

	@FXML
	ImageView client1;
	@FXML
	ImageView client2;
	@FXML
	ImageView client3;

	@FXML
	ImageView decoupe;

	@FXML
	ImageView containerDansDecoupe;

	@FXML
	ImageView plaque_cuisson;

	@FXML
	ImageView friteuse;

	@FXML
	ImageView containerDansFritteuse;

	@FXML
	ImageView assemblage;

	@FXML
	ImageView evier;

	@FXML
	ImageView garde_manger;

	// containerView à supprimer quand tout marche
	@FXML
	ImageView containerView;

	@FXML
	Label containerLabel;


	@FXML
	TextArea assemblageTextArea;
	
		// met l'image de l'ingredient dans un container (à supprimer à la fin, Mickael)
		//containerView.setImage(new Image(((Ingredient) container).getUrlImage()));
	

	@FXML
	public void prendreIngredient(MouseEvent e) {
		Object image = e.getSource();
		String idImage = ((Node) image).getId();
//		for (Ingredient i : Main.niveau1.getIngredient().keySet()) {
//			if (idImage.equals(i.getNom().toString())) {
//				container = new Object();
//				container = i;
//				containerLabel.setText(i.getNom().toString());
//			}
//		}
		System.out.println(idImage);
		container = Main.niveau1.getGardeManger().saisirUnIngredient(idImage);
		containerLabel.setText(((Ingredient) container).getNom().toString());
		}


//	}
	
	public void setCompteur() {
		GardeManger g =  Main.niveau1.getGardeManger();
		
		System.out.println(g);
		int a = g.getPatates().size()-1;
		compteurPatateLabel.setText(Integer.toString(a));
		
	}


	public void ajouterIngredientDansMateriel(Ingredient ingredient) {

	}

	public void decouper(MouseEvent e) throws IllegalAccessException {

		// si le container est nul alors il faut selectionner un ingredient
		if (container == null) {
			// si aucun objet n'est contenu dans le materiel de d�coupe
//			if (materielDecoupe.objetsContenus.isEmpty()) {
//				System.out.println("veuillez selectionner un ingredient");
//			}
//			// sinon, container = ingredient contenu dans le materiel de decoupe
//			else {
//				container = materielDecoupe.objetsContenus.get(0);
//				containerLabel.setText(((Ingredient) materielDecoupe.objetsContenus.get(0)).getNom().toString());
//			}
			checkSiIngredientPresentDansMateriel(materielDecoupe);
		}

		// sinon
		else {
			Ingredient ingredient = (Ingredient) container;
			// si le container est d�coupable
			if (ingredient.isDecoupable() == true) {
				// si cette ingredient d�coupable n'est pas d�ja transform� alors le d�couper
				if (ingredient.getTransformer() == false) {
					materielDecoupe.ajouterObjet(ingredient);
					((Decoupe) materielDecoupe).decouper();
					container = null;
					containerLabel.setText("vide");
					System.out.println(ingredient.getNom() + " a �t� d�coup� ");
				}
				// sinon cette ingredient d�coupable a d�ja �t� d�coup�
				else {
					System.out.println(ingredient.getNom() + " a d�ja �t� d�coup�");
				}
			}
			// sinon cette ingredient n'est pas d�coubale
			else {
				System.out.println(ingredient.getNom() + " n'est pas d�coupable");
			}
			System.out.println("transform� : " + ingredient.getNom() + " : " + ingredient.getTransformer());
		}
	}

	public void cuir(MouseEvent e) {
		if (container == null) {

			checkSiIngredientPresentDansMateriel(materielPlaqueDeCuisson);
//System.out.println("veuillez selectionner un ingredient");
		} else {
			Ingredient ingredient = ((Ingredient) container);
			if (((Ingredient) container).isSteak() == true) {
				if (((Ingredient) container).getEtat() == Etat.CRU) {
					ingredient.setEtat(Etat.CUIT);
					materielPlaqueDeCuisson.ajouterObjet(ingredient);
					container = null;
					containerLabel.setText("vide");
					System.out.println(ingredient.getNom() + " a �t� cuit");
				
				} else {
					System.out.println(((Ingredient) container).getNom() + " a d�ja �t� cuit");
				}
			} else {
				System.out.println(((Ingredient) container).getNom() + " ne peut pas etre cuit");
			}
		}
	}

	public void frire(MouseEvent e) {
		if (container == null) {
//			System.out.println("veuillez selectionner un ingredient");
			System.out.println("ingredient contenu " + materielFriteuse.objetsContenus.size());
			checkSiIngredientPresentDansMateriel(materielFriteuse);

		} else {

			Ingredient a = ((Ingredient) container);
			if (a.getNom().toString().equals("PATATE")) {
				if (a.getTransformer() == true) {
					if (a.getEtat() == Etat.CRU) {
						a.setEtat(Etat.CUIT);
						materielFriteuse.ajouterObjet(a);
						container = null;
						containerLabel.setText("vide");
						System.out.println(a.getNom() + " a �t� cuit");

					} else {
						System.out.println(a.getNom() + " a d�ja �t� cuit");
					}

				} else {
					System.out.println(a.getNom() + " doit etre d�coup�");
				}
			} else {
				System.out.println("seul les patates peuvent �tre fries");
			}
		}
	}

	public void assembler(MouseEvent event) {
		if (container == null) {
			System.out.println("selectionner un ingredient");
		} else {
			materielAssemblage.ajouterObjet(container);
			System.out.println("ajout�");
			assemblageTextArea.setText(assemblageTextArea.getText() + " ; " + ((Ingredient) container).getNom().toString());
			container=null;
			containerLabel.setText("vide");
		}
	}
	
	public void checkSiIngredientPresentDansMateriel(Materiel m) {
		if (m.objetsContenus.isEmpty()) {
			System.out.println("veuillez selectionner un ingredient");
		}
		// sinon, container = ingredient contenu dans le materiel de decoupe
		else {
			System.out.println("contenu du materiel avant " + m.objetsContenus.size());
			this.container = m.objetsContenus.get(0);
			m.retirerObjet(this.container);
			System.out.println("contenu du materiel apres " + m.objetsContenus.size());
			System.out.println("ingredient ajout� � container");
			this.containerLabel.setText(((Ingredient) container).getNom().toString());
		}

	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		for (Materiel i : Main.niveau1.getMateriel().keySet()) {
			if (i instanceof Decoupe) {
				materielDecoupe = i;
//				System.out.println("ajout�");
			}
			if (i instanceof Assemblage) {
				materielAssemblage = i;
//				System.out.println("ajout�");
			}
			if (i instanceof Friteuse) {
				materielFriteuse = i;
//				System.out.println("ajout�");
			}
			if (i instanceof LaveVaisselle) {
				materielLaveVaisselle = i;
//				System.out.println("ajout�");
			}
			if (i instanceof PlaqueCuisson) {
				materielPlaqueDeCuisson = i;
//				System.out.println("ajout�");
			}
			if (i instanceof Poubelle) {
				materielPoubelle = i;
//				System.out.println("ajout�");
			}
		}
		
		setCompteur();
		
		
	}
}
