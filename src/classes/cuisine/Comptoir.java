package classes.cuisine;

import classes.cuisine.materiel.Assiette;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Comptoir qui accueille jusqu'à 3 clients et leur commande
 * @version 2.0
 * @author Maïa DA SILVA
 */
public class Comptoir {

    // Variables de classes
	;

	/**
	 *
	 */
	private Client[] emplacementClientDansComptoire;

	/**
	 *
	 */
	private Assiette[] emplacementAssietteDansComptoire;

	/**
     * Constructeur
     */
    public Comptoir () {
        this.emplacementClientDansComptoire = new Client[3] ;
        this.emplacementAssietteDansComptoire = new Assiette[3];
    }

    // Méthodes

	/**
	 * @return
	 */
    public Assiette[] getEmplacementAssietteDansComptoire() {
		return emplacementAssietteDansComptoire;
	}

	/**
     * Ajouter un client au comptoir, par définition il n'est pas servit lorsqu'il arrive
     * @param client
     * @return true si le client a bien été ajouté
     */
    public boolean ajouterClient(Client client, int emplacement) {
//        if (this.comptoir.size() >= 3) {
//            //exception
//            return false ;
//        }
////        this.comptoir.put(client,null) ;
//        this.comptoir.add(client);
    	this.emplacementClientDansComptoire[emplacement]=client;
        client.debutTimerClient();
        return true ;
    }

	/**
	 * @param emplacement
	 * @return
	 */
  	public boolean retirerClient (int emplacement) {
  		this.emplacementClientDansComptoire[emplacement]=null;
  		return true ;
  	}

	/**
	 * @return
	 */
	public Client[] getEmplacementClientDansComptoire() {
		return emplacementClientDansComptoire;
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
