/**
 * Commentaire de documentation de la classe IngredientCuit
 * @version 1.0
 * @author Thomas MOSCONI
 */
package classes.cuisine;

/**
 * Commentaire de documentation de la classe IngrédientCuit
 * @version 1.0
 * @author Thomas MOSCONI
 *
 * @version 2.0
 * @author Maia DA SILVA
 */
public class IngredientCuit extends Ingredient {

	/**
	 * Représente le temps de cuisson optimal de l'aliment
	 */
	private int tmpsCuisson ;


	/**
	 * Constructeur avec temps de cuisson définit par défaut
	 * @param nom
	 */
	public IngredientCuit(Nom nom) {
		super(nom);
		this.tmpsCuisson = 10000 ;
	}

	/**
	 * Constructeur avec précision du temps de cuisson
	 * @param nom
	 * @param tmpsCuisson
	 */
	public IngredientCuit(Nom nom, int tmpsCuisson) {
		super(nom);
		this.tmpsCuisson = tmpsCuisson ;
	}

	// Getteur

	/**
	 * @return le temps de cuisson optimale d'un ingrédient cuisable
	 */
//	public int getTempsCuisson() {
//		return tmpsCuisson;
//	}
	
	
	//mickael

	public int getTempsCuisson() {
		return tmpsCuisson;
	}
}
