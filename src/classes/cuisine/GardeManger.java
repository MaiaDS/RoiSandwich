package classes.cuisine;

import classes.Niveau;
import java.util.HashMap;

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
	 * liste des compteurs des ingr�dients : repr�sente le nb d'ingr�dient utilis� par le joueur
	 */
	private HashMap<Ingredient.Nom, Integer> compteurs = new HashMap<>();

	/**
	 * niveau de la partie en cours
	 */
	private Niveau niveau ;

	/**
	 * Constructeur
	 * @param niveau qui correspond au niveau en cours
	 */
	public GardeManger(Niveau niveau) {
		this.niveau = niveau;
		Ingredient.Nom [] ingredients = Ingredient.Nom.values() ;
		for (int i = 0 ; i < ingredients.length ; i++) {
			this.compteurs.put(ingredients[i],niveau.getNbIngredient()) ;
		}
	}

	// Getteur

	/**
	 * @return
	 * @author Mickael
	 */
	public HashMap<Ingredient.Nom, Integer> getCompteurs() {
		return compteurs;
	}

	// Méthodes

	/**
	 * Permet de ....
	 * @param ingredient qui correspond à ...
	 * @return ...
	 */
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

	/**
	 * Permet de ...
	 * @param ingredient qui correspons à ...
	 * @return ...
	 */
	public Ingredient mettreIngredient (Ingredient.Nom ingredient) {
		int compteur = compteurs.get(ingredient) ;
		System.out.println(compteur);
		// décrémenter le compteur
		compteurs.put(ingredient, compteur+1);
		return new Ingredient(ingredient);
	}
}