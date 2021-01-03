package classes.cuisine;

import classes.Recette;
import classes.cuisine.materiel.Assiette;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe repésentant un client
 * 
 * @version 1.0
 * @author Maïa DA SILVA
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

	// Méthodes

	/**
	 * @return la commande du client
	 */
	public Recette getCommande() {
		return commande;
	}

	/**
	 * Vérifie que l'assiette servie correspond bien à la commande du Client
	 * @param a correspond à l'assiette servie au client
	 * @return true si le plat est bon, false sinon
	 */
	public boolean verifierLePlat(Assiette a) {
		ArrayList<Ingredient> ingredientAssiette = new ArrayList<Ingredient>();
		ArrayList<Ingredient> ingredientRecette = new ArrayList<Ingredient>();
		boolean estConforme = false;
		int nbDeConformite = 0;

		// récupérer les ingrédients contenus dans l'assiette
		for (int i = 0; i < a.objetsContenus.size(); i++) {
			Ingredient ing = (Ingredient) a.objetsContenus.get(i);
			ingredientAssiette.add(ing);
		}

		Iterator it = commande.ingredients.entrySet().iterator();

		// récupérer les ingrédients nécessaires de la commande du client
		while (it.hasNext()) {
			Map.Entry ingCommande = (Map.Entry) it.next();
			int nbIng = (int) ingCommande.getValue();
			for(int i=0; i<nbIng;i++) {
				ingredientRecette.add((Ingredient) ingCommande.getKey());
			}
			it.remove();
		}

		// vérifier la conformité des ingrédients de l'assiette par rapport à la commande client
		while(ingredientAssiette.size()>0) {
			Ingredient IngredientAssietteAChecker = ingredientAssiette.get(0);

			for (int y = 0; y < ingredientRecette.size(); y++) {
				Ingredient IngredientRecetteAComparer = ingredientRecette.get(y);
				// vérifier que l'ingrédient correspond et qu'il a été correctement cusiné
				if (IngredientAssietteAChecker.getNom().equals(IngredientRecetteAComparer.getNom())
						&& IngredientAssietteAChecker.getTransformer() == IngredientRecetteAComparer.getTransformer()
						&& IngredientAssietteAChecker.getEtat().equals(IngredientRecetteAComparer.getEtat())) {
					nbDeConformite++;
					y=ingredientRecette.size();
				}
			}
			ingredientAssiette.remove(0);
		}
		// si tout les ingrédients sont conformes alors le service est bon
		if (nbDeConformite == ingredientRecette.size()) {
			estConforme = true;
		}
		
		System.out.println("est conforme ? " + estConforme);
		return estConforme;
	}

	/**
	 * Permet de démarrer le timer d'attente du client
	 */
	public void debutTimerClient() {
		Timer timer = new Timer(true);
		timer.schedule(new Start(), 0, this.tmpsAttente);
	}

	/**
	 * Permet de gérer le timer du client
	 */
	public class Start extends TimerTask {

		/**
		 * @Override méthode run() de la classe TimerTask
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (tmpsAttente > 0) {

				try {
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
