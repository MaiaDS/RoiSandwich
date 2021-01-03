package classes.cuisine;

import classes.Niveau;
import classes.cuisine.Ingredient.Etat;
import classes.cuisine.Ingredient.Nom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Commentaire de documentation de la classe GardeManger
 * @version 1.0
 * @author Thomas MOSCONI
 *
 * @version 2.0
 * @author Maia DA SILVA
 */
public class GardeManger {

	// Variables de classe

	/**
	 * liste des compteurs des ingrédients : représente le nb d'ingrédient utilisé par le joueur
	 */
	private HashMap<Ingredient.Nom, Integer> compteurs = new HashMap<>();

	/**
	 * niveau de la partie en cours
	 */
	private Niveau niveau ;

	/**
	 * Constructeur
	 * @param quantité ingrédient, correspondant au niveau
	 */
	public GardeManger(Niveau niveau) {
		this.niveau = niveau;
		Ingredient.Nom [] ingredients = Ingredient.Nom.values() ;
		for (int i = 0 ; i < ingredients.length ; i++) {
			this.compteurs.put(ingredients[i],niveau.getNbIngredient()) ;
		}
	}

	public Ingredient prendreIngredient (Ingredient.Nom ingredient) {
		int compteur = compteurs.get(ingredient) ;
		System.out.println(compteur);
		if (compteur > 0) {
			// décrémenter le compteur
			compteurs.put(ingredient, compteur-1);
			return new Ingredient(ingredient);
		}
		return null ;
	}
	
	public Ingredient mettreIngredient (Ingredient.Nom ingredient) {
		int compteur = compteurs.get(ingredient) ;
		System.out.println(compteur);
		
			// décrémenter le compteur
			compteurs.put(ingredient, compteur+1);
			return new Ingredient(ingredient);
	
		
	}

	
	//Mickael
	public HashMap<Ingredient.Nom, Integer> getCompteurs() {
		return compteurs;
	}
}