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
	 * Constructeur
	 * @param nom correspond au nom de l'ingrédient
	 * @param tmpsCuisson correspond au temps de cuisson de l'ingrédient
	 */
	public IngredientCuit(Nom nom, int tmpsCuisson) {
		super(nom);
		this.tmpsCuisson = tmpsCuisson ;
	}

}
