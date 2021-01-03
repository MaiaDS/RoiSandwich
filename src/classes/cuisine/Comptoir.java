package classes.cuisine;

import classes.cuisine.materiel.Assiette;

/**
 * Comptoir qui accueille jusqu'à 3 clients et leur commande
 * @version 2.0
 * @author Maïa DA SILVA
 *
 * @version 3.0
 * @author ???
 */
public class Comptoir {

    // Variables de classes

	/**
	 *
	 */
	private Client[]emplacementClientDansComptoire;

	/**
	 *
	 */
	private Assiette[]emplacementAssietteDansComptoire;


	/**
     * Constructeur
     */
    public Comptoir () {
        this.emplacementClientDansComptoire = new Client[3] ;
        this.emplacementAssietteDansComptoire = new Assiette[3];
    }

    //Getteur

	/**
	 * @return
	 */
	public Client[] getEmplacementClientDansComptoire() {
		return emplacementClientDansComptoire;
	}

	/**
	 * @return
	 */
    public Assiette[] getEmplacementAssietteDansComptoire() {
		return emplacementAssietteDansComptoire;
	}

	// Méthodes

	/**
     * Ajouter un client au comptoir, par définition il n'est pas servit lorsqu'il arrive
     * @param client
     * @return true si le client a bien été ajouté
     */
    public boolean ajouterClient(Client client, int emplacement) {
    	this.emplacementClientDansComptoire[emplacement]=client;
        client.debutTimerClient();
        return true ;
    }

	/**
	 * Permet de retirer un client du comptoir
	 * @param emplacement
	 * @return true si le client a bien été retiré
	 */
  	public boolean retirerClient (int emplacement) {
  		this.emplacementClientDansComptoire[emplacement]=null;
  		return true ;
	}

	/**
	 * @param i
	 * @return
	 */
	public boolean checkerSiUneAssietteEstPresenteDansEmplacementDuClient(int i) {
		if(emplacementAssietteDansComptoire[i]==null) {
			return false;
		}
		else {
			return true;
		}
		
	}

	/**
	 * @param i
	 * @return
	 */
	public boolean checkerSiUnClientEstAssisDansUnEmplacement(int i) {
		if(emplacementClientDansComptoire[i]==null) {
			return false;
		}
		else {
			return true;
		}
	}
}
