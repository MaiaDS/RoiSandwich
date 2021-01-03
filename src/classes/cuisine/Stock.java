package classes.cuisine;

import classes.cuisine.materiel.Assiette;
import java.util.ArrayList;

/**
 * Commentaire de documentation de la classe GardeManger
 * @version 1.0
 * @author Thomas MOSCONI
 *
 * @version 2.0
 * @author Maia DA SILVA
 */
public class Stock {

    /**
     * stock des assiettes propres
     */
    private ArrayList<Assiette> assiettesPropres ;
    /**
     * stock des assiettes sales
     */
    private ArrayList<Assiette> assiettesSale ;
    /**
     * stock des assiettes contenant un plat
     */
    private ArrayList<Assiette> plats ;

    /**
     * Constructeur
     */
    public Stock () {
        this.assiettesPropres = new ArrayList<Assiette>() ;
        this.assiettesSale = new ArrayList<Assiette>() ;
        this.plats = new ArrayList<Assiette>() ;
    }

    // Getteur

    /**
     * @return
     */
    public ArrayList<Assiette> getAssiettesSale() {
        return assiettesSale;
    }

    // Méthodes

    /**
     * Permet d'ajouter une assiette à un stock
     * @param assiette
     * @return true si l'assiette a bien été ajoutée à un stock
     */
    public boolean stockerAssiette (Assiette assiette) {
        if (assiette.getPlat() != null) {
            return this.plats.add(assiette) ;
        } else if (assiette.objetsContenus.size() == 0) {
            return this.assiettesPropres.add(assiette) ;
        } else {
            return this.assiettesSale.add(assiette) ;
        }
    }
	
}
