package classes.cuisine;

import classes.cuisine.materiel.Assiette;

public class Stock {
	private Assiette[] stockassiette;
	
	public Stock() {
		
	}
	private Assiette prendreAssiette() {
		return stockassiette[stockassiette.length+1];
		// � modifier lorsqu'on commencera javafx
	}
	
}
