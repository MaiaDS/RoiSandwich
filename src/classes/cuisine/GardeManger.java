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
	 * liste des compteurs des ingrédients : représente le nb d'ingrédient utilisé par le joueur
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
		// ajouter les ingrédients au garde-manger
		for (int i = 0 ; i < ingredients.length ; i++) {
			this.compteurs.put(ingredients[i],niveau.getNbIngredient()) ;
		}
	}

	// Getteur

	/**
	 * @return une liste des ingrédients associés à leur compteur
	 */
	public HashMap<Ingredient.Nom, Integer> getCompteurs() {
		return compteurs;
	}

	// Méthodes

	/**
	 * Permet de prendre un ingrédient du garde-manger
	 * @param ingredient qui correspond à l'ingrédient à prendre
	 * @return l'ingrédient pris
	 */
	public Ingredient prendreIngredient (Ingredient.Nom ingredient) {
		int compteur = compteurs.get(ingredient) ;
		// System.out.println(compteur);
		if (compteur > 0) {
			// décrémenter le compteur
			compteurs.put(ingredient, compteur-1);
			return new Ingredient(ingredient);
		}
		return null ;
	}

	/**
	 * Permet de remettre un ingrédient dans le garde-manger
	 * @param ingredient qui correspond à l'ingrédient à déposer
	 */
	public void mettreIngredient (Ingredient.Nom ingredient) {
		int compteur = compteurs.get(ingredient) ;
		// System.out.println(compteur);
		// incrémenter le compteur
		compteurs.put(ingredient, compteur+1);
	}
}