package classes.cuisine;

import classes.Recette;
import classes.cuisine.materiel.Assiette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe représentant un client
 * 
 * @version 1.0
 * @author Maia DA SILVA
 */
public class Client {

	// Variables de classes

	/**
	 * représente le temps que le client est prêt à attendre pour être servit
	 */
	private int tmpsAttente;

	/**
	 * représente la commande du client
	 */
	private Recette commande;

	/**
	 * Constructeur
	 * 
	 * @param tmpsAttente
	 * @param commande
	 */
	public Client(int tmpsAttente, Recette commande) {
		this.tmpsAttente = tmpsAttente;
		this.commande = commande;
	}

	// Getteur

	/**
	 * @return le temps d'attente du client
	 */
	public int getTmpsAttente() {
		return tmpsAttente;
	}

	/**
	 * @return la commande du client
	 */
	public Recette getCommande() {
		return commande;
	}

	// Méthodes

	/**
	 * Permet de vérifier que l'assiette qu'on lui a servit correspond à sa
	 * commande
	 * 
	 * @param a qui représente l'assiette servie
	 * @return true si la commande est bonne
	 * @throws IllegalAccessException
	 */
	public boolean verifierLePlat(Assiette a) {
		ArrayList<Ingredient> ingredientAssiette = new ArrayList<Ingredient>();
		ArrayList<Ingredient> ingredientRecette = new ArrayList<Ingredient>();
		boolean estConforme = false;
		int nbDeConformite = 0;
		for (int i = 0; i < a.objetsContenus.size(); i++) {
			Ingredient ing = (Ingredient) a.objetsContenus.get(i);
			ingredientAssiette.add(ing);
		}
		;
		Iterator it = commande.ingredients.entrySet().iterator();
//		System.out.println("nombre d'ingredtion dans client " + commande.ingredients.size());
		
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			int b= (int) pair.getValue();
			for(int i=0; i<b;i++) {
			ingredientRecette.add((Ingredient) pair.getKey());
			}
//			System.out.println("ingredient ajouté " + pair.getKey());
			it.remove(); // avoids a ConcurrentModificationException
		}
		
		while(ingredientAssiette.size()>0) {
			Ingredient IngredientAssietteAChecker = ingredientAssiette.get(0);
			for (int y = 0; y < ingredientRecette.size(); y++) {
				Ingredient IngredientRecetteAComparer = ingredientRecette.get(y);
				
				if (IngredientAssietteAChecker.getNom().equals(IngredientRecetteAComparer.getNom())
						&& IngredientAssietteAChecker.getTransformer() == IngredientRecetteAComparer.getTransformer()
						&& IngredientAssietteAChecker.getEtat().equals(IngredientRecetteAComparer.getEtat())) {
//					System.out.println(IngredientAssietteAChecker.getNom() + " est conforme");
					nbDeConformite++;
					y=ingredientRecette.size();
				}
			}
			ingredientAssiette.remove(0);
		}
			
		

//		System.out.println("nb conformité = " + nbDeConformite);
//		System.out.println("taille ingredientRecette dans client " + ingredientRecette.size());
		if (nbDeConformite == ingredientRecette.size()) {
			estConforme = true;
		}
		
//		System.out.println(" ");
//		for (int i = 0; i < ingredientRecette.size(); i++) {
//			System.out.println("ingredientRecette : " + ingredientRecette.get(i).getNom() + " etat : "
//					+ ingredientRecette.get(i).getEtat() + " transformé : "
//					+ ingredientRecette.get(i).getTransformer());
//		}
		
		System.out.println("est conforme ? " + estConforme);
		return estConforme;
		

	}

	/**
	 * Permet de vérifier qu'un ingrédient est présent en bonne quantité dans
	 * l'assiette
	 * 
	 * @param listeIngredients
	 * @param ingredient
	 * @param quantite
	 * @return true si l'ingrédient est présent en bonne quantité dans l'assiette
	 */
	public boolean verifierQuantite(ArrayList listeIngredients, Ingredient ingredient, int quantite) {
		return (Collections.frequency(listeIngredients, ingredient) != quantite);
	}

	/**
	 * Permet de vérifier la cuisson d'un ingrédient (cuisable)
	 * 
	 * @param ingredient
	 * @return true si l'ingrédient est bien cuit
	 */
	public boolean verifierCuisson(Ingredient ingredient) {
		return ((ingredient.isSteak() || ingredient.getNom() == Ingredient.Nom.PATATE)
				&& ingredient.getEtat() != Ingredient.Etat.CUIT);
	}

	/**
	 * Permet de vérifier si un ingrédient est découpé ou non
	 * 
	 * @param ingredient
	 * @return true si l'ingrédient est découpé
	 */
	public boolean verifierDecoupage(Ingredient ingredient) {
		return (ingredient.isDecoupable() && ingredient.getTransformer() == false);
	}

	/**
	 * Débute le timer d'attente du client
	 */
	public void debutTimerClient() {
		Timer timer = new Timer(true);
		timer.schedule(new Start(), 0, this.tmpsAttente);
	}

	/**
	 *
	 */
	public class Start extends TimerTask {

		/**
		 *
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (tmpsAttente > 0) {

				try {
//					System.out.println("temps attente = " + tmpsAttente);
					Thread.sleep(1000);
					tmpsAttente--;

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}
