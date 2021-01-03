package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.net.URL;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import classes.cuisine.Ingredient.Etat;
import classes.cuisine.Ingredient.Nom;
import classes.*;
import classes.cuisine.*;
import classes.cuisine.materiel.*;
import classes.cuisine.materiel.Assiette.EtatAssiette;

public class Controller implements Initializable {

//	
	private Niveau niveau;

	private Object container;

	private Materiel materielFriteuse;

	private Materiel materielAssemblage;

	private Materiel materielPlaqueDeCuisson;

	private Materiel materielDecoupe;

	private Materiel materielLaveVaisselle;

	private Materiel materielPoubelle;

	// mick 28/12
	private Comptoir comptoir;

	@FXML
	private ImageView PATATE;
	@FXML
	private ImageView SALADE;
	@FXML
	private ImageView TOMATE;
	@FXML
	private ImageView OIGNON;
	@FXML
	private ImageView PAIN;
	@FXML
	private ImageView FROMAGE;
	@FXML
	private ImageView STEAK_DE_SOJA;
	@FXML
	private ImageView STEAK_DE_POULET;
	@FXML
	private ImageView STEAK_DE_BOEUF;

	@FXML
	private ImageView emplacementAssemblagePain;

	@FXML
	private ImageView emplacementAssemblageFromage;

	@FXML
	private ImageView emplacementAssemblageOignon;

	@FXML
	private ImageView emplacementAssemblageTomate;

	@FXML
	private ImageView emplacementAssemblageSalade;

	@FXML
	private ImageView emplacementAssemblagePatate;

	@FXML
	private ImageView emplacementAssemblageSteak;

	@FXML
	private ImageView stock1;
	@FXML
	private ImageView stock2;
	@FXML
	private ImageView stock3;

	@FXML
	private ImageView imageViewClient1;
	@FXML
	private ProgressBar client1Progress;
	private Service<Void> client1EnCours;

	@FXML
	private ImageView imageViewClient2;
	@FXML
	private ProgressBar client2Progress;
	private Service<Void> client2EnCours;

	@FXML
	private ImageView imageViewClient3;
	@FXML
	private ProgressBar client3Progress;
	private Service<Void> client3EnCours;

	@FXML
	private ImageView emplacementAssietteClient1;

	@FXML
	private ImageView emplacementAssietteClient2;

	@FXML
	private ImageView emplacementAssietteClient3;

	@FXML
	private BorderPane decoupe;

	@FXML
	private ImageView containerDansDecoupe;

	@FXML
	private BorderPane plaque_cuisson;

	@FXML
	private ImageView containerDansCuisson;

	@FXML
	private ProgressBar cuissonProgress;

	private Service<Void> CuissonEnCoursSteak;

	@FXML
	private BorderPane friteuse;

	@FXML
	private ProgressBar frireProgress;

	private Service<Void> FrireEnCours;

	@FXML
	private BorderPane assemblage;

	@FXML
	private ImageView containerDansFriteuse;

	@FXML
	private BorderPane lavevaisselle;

	@FXML
	private ProgressIndicator LaveProgress;

	private Service<Void> LaveVaisselleEnCours;

	@FXML
	private ImageView garde_manger;

	@FXML
	private ImageView poubelle;

	@FXML
	private ImageView containerView;

	@FXML
	private ImageView assiettePropre;

	@FXML
	private Label compteurPAIN;

	@FXML
	private Label compteurFROMAGE;

	@FXML
	private Label compteurOIGNON;

	@FXML
	private Label compteurTOMATE;

	@FXML
	private Label compteurSALADE;

	@FXML
	private Label compteurPATATE;

	@FXML
	private Label compteurSTEAK_DE_BOEUF;

	@FXML
	private Label compteurSTEAK_DE_POULET;

	@FXML
	private Label compteurSTEAK_DE_SOJA;

	@FXML
	private Label compteurAssitette;

	@FXML
	private ImageView emplacementAssiette;

	@FXML
	private Label tempsEnCours;

	@FXML
	private Label labelRecetteClient1;

	@FXML
	private Label labelRecetteClient2;
	@FXML
	private Label labelRecetteClient3;
	// <===== a supprimer quand progressBar sera implementée
	@FXML
	private Label labelTimerClient1;
	@FXML
	private Label labelTimerClient2;
	@FXML
	private Label labelTimerClient3;
	// =======>
	@FXML
	private Label scoreLabel, argentLabel;
	
	private Client client1;
	private Client client2;
	private Client client3;

	
	
	/**
	 * methode qui donne l' assiette aux clients.
	 * si le container n'est pas null et qu'il contient un objet de classe assiette, que cette assiette a pour état PLAT, alors 
	 * on vérifie qu'il y a bien un client qui attend et cette assiette lui est distribué en cliquant sur lui.
	 * Le client vérifie que les ingredients dans l'assiette correspondent à sa commande, le score augmente en fonction, et il s'en va.
	 * L'assiette passe en état SALE.
	 * Si le container est null et qu'il y a une assiette dans l'emplacement du client, en cliquant sur le client, on récupere l'assiette SALE
	 * @param e
	 * @throws IllegalAccessException
	 */
	public void donnerAssietteClient(MouseEvent e) throws IllegalAccessException {
		//si le container n'est pas vide et quand dans ce container il s'agit d'une assiette "Plat"
		if ((container != null) && (container instanceof Assiette) && ((Assiette)container).getEtatAssiette().equals(EtatAssiette.PLAT)) {
			ImageView i = (ImageView) e.getSource();
			Assiette assietteTemporaire = (Assiette) container;
			switch (i.getId()) {
			case "imageViewClient1":
				if (client1!=null && comptoir.getEmplacementAssietteDansComptoire()[0]==null) {			
					emplacementAssietteClient1.setImage(
							new Image(getClass().getResourceAsStream(((Assiette) container).getImgAssiette())));
					assietteTemporaire.setEtatAssiette(EtatAssiette.SALE);
					comptoir.getEmplacementAssietteDansComptoire()[0] = assietteTemporaire;

					if (client1.verifierLePlat(assietteTemporaire)) {
						niveau.setScoreArgent(100, 100);
						System.out.println("score augmenté");
						scoreLabel.setText(String.valueOf(niveau.getTabScoreArgent()[0]));
						argentLabel.setText(String.valueOf(niveau.getTabScoreArgent()[1]));
					}
					comptoir.retirerClient(0);
					labelRecetteClient1.setText("");
					vBoxClient1.getChildren().clear();
					client1EnCours.cancel();
					client1EnCours.reset();
					client1Progress.setVisible(false);
					viderContainer();
					emplacementAssietteClient1.setImage(new Image(getClass().getResourceAsStream(
							((Assiette) comptoir.getEmplacementAssietteDansComptoire()[0]).getImgAssiette())));
					break;
				}
				else {
					break;
				}
			case "imageViewClient2":
				if (client2!=null && comptoir.getEmplacementAssietteDansComptoire()[1]==null) {
					emplacementAssietteClient2.setImage(
							new Image(getClass().getResourceAsStream(((Assiette) container).getImgAssiette())));
					vBoxClient2.getChildren().clear();
					client2EnCours.cancel();
					client2EnCours.reset();
					client2Progress.setVisible(false);
					comptoir.getEmplacementAssietteDansComptoire()[1] = (Assiette) container;
					if (client2.verifierLePlat((Assiette) container)) {
						niveau.setScoreArgent(100, 100);
						System.out.println("score augmenté");
						scoreLabel.setText(String.valueOf(niveau.getTabScoreArgent()[0]));
					}
					comptoir.retirerClient(1);
					labelRecetteClient2.setText("");
					comptoir.getEmplacementAssietteDansComptoire()[1].setEtatAssiette(EtatAssiette.SALE);
					viderContainer();
					emplacementAssietteClient2.setImage(new Image(getClass().getResourceAsStream(
							((Assiette) comptoir.getEmplacementAssietteDansComptoire()[1]).getImgAssiette())));
					break;
				}
				else {
					break;
				}
			case "imageViewClient3":
				if (client3!=null && comptoir.getEmplacementAssietteDansComptoire()[2]==null) {
					emplacementAssietteClient3.setImage(
							new Image(getClass().getResourceAsStream(((Assiette) container).getImgAssiette())));
					vBoxClient3.getChildren().clear();
					client3EnCours.cancel();
					client3EnCours.reset();
					client3Progress.setVisible(false);
					comptoir.getEmplacementAssietteDansComptoire()[2] = (Assiette) container;
					if (client3.verifierLePlat((Assiette) container)) {
						niveau.setScoreArgent(100, 100);
						System.out.println("score augmenté");
						scoreLabel.setText(String.valueOf(niveau.getTabScoreArgent()[0]));
					}
					comptoir.retirerClient(2);
					labelRecetteClient3.setText("");
					comptoir.getEmplacementAssietteDansComptoire()[2].setEtatAssiette(EtatAssiette.SALE);
					viderContainer();
					emplacementAssietteClient3.setImage(new Image(getClass().getResourceAsStream(
							((Assiette) comptoir.getEmplacementAssietteDansComptoire()[2]).getImgAssiette())));
					break;
				}
				else {
					break;
				}
			}
			
		} else if (container == null) {
			ImageView i = (ImageView) e.getSource();
			switch (i.getId()) {
			case "imageViewClient1":
				mettreDansContainer(((Assiette) comptoir.getEmplacementAssietteDansComptoire()[0]));
				comptoir.getEmplacementAssietteDansComptoire()[0] = null;
				emplacementAssietteClient1.setImage(null);
				break;
			case "imageViewClient2":
				mettreDansContainer(((Assiette) comptoir.getEmplacementAssietteDansComptoire()[1]));
				comptoir.getEmplacementAssietteDansComptoire()[1] = null;
				emplacementAssietteClient2.setImage(null);
				break;
			case "imageViewClient3":
				mettreDansContainer(((Assiette) comptoir.getEmplacementAssietteDansComptoire()[2]));
				comptoir.getEmplacementAssietteDansComptoire()[2] = null;
				emplacementAssietteClient3.setImage(null);
				break;
			}
		}
	}

	
	
	
	
	
	
	/**
	 * Permet de prendre une assiette propre ou d'en remettre une
	 * Fonctionne au click.
	 * l'assiette se situe dans niveau => cuisine
	 * 
	 */
	public void prendreAssiettePropre() {
		if (container == null) {
//			Object image = e.getSource();
			Object c = niveau.getCuisine().retirerAssietteDeLaCuisine();
			mettreDansContainer(c);
		} else if (container instanceof Assiette
				&& ((Assiette) container).getEtatAssiette().equals(EtatAssiette.PROPRE)) {
			Assiette assiette = (Assiette) container;
			niveau.getCuisine().getAssiettes().add(assiette);
			// MODIFIER ! travailler avec le stock et non pas direct cuisine
			compteurAssitette.setText(String.valueOf(niveau.getCuisine().getAssiettes().size()));
			mettreDansContainer((Assiette) container);
			viderContainer();
		}

	}

	
	public void prendreIngredient(MouseEvent e) {
//		if (materielAssemblage.objetsContenus.size() == 0) {
//			System.out.println("attention, il n'y a pas d'assiette dans l'assemblage");
//		} else {
			if (container == null) {
				Object image = e.getSource();
				String idImage = ((Node) image).getId();
				Object c = niveau.getGardeManger().prendreIngredient(Nom.valueOf(idImage));
				mettreDansContainer(c);
			} else if (container instanceof Ingredient) {
				Ingredient i = ((Ingredient) container);
				if (i.getTransformer() == false && i.getEtat().equals(Etat.CRU)) {
					niveau.getGardeManger().mettreIngredient(i.getNom());
					viderContainer();
					switch (i.getNom()) {
					case PATATE:
						compteurPATATE.setText(String.valueOf(compteur(Nom.PATATE)));
						break;
					case FROMAGE:
						compteurFROMAGE.setText(String.valueOf(compteur(Nom.FROMAGE)));
						break;
					case PAIN:
						compteurPAIN.setText(String.valueOf(compteur(Nom.PAIN)));
						break;
					case OIGNON:
						compteurOIGNON.setText(String.valueOf(compteur(Nom.OIGNON)));
						break;
					case SALADE:
						compteurSALADE.setText(String.valueOf(compteur(Nom.SALADE)));
						break;
					case STEAK_DE_BOEUF:
						compteurSTEAK_DE_BOEUF.setText(String.valueOf(compteur(Nom.STEAK_DE_BOEUF)));
						break;
					case STEAK_DE_POULET:
						compteurSTEAK_DE_POULET.setText(String.valueOf(compteur(Nom.STEAK_DE_POULET)));
						break;
					case STEAK_DE_SOJA:
						compteurSTEAK_DE_SOJA.setText(String.valueOf(compteur(Nom.STEAK_DE_SOJA)));
						break;
					case TOMATE:
						compteurTOMATE.setText(String.valueOf(compteur(Nom.TOMATE)));
						break;
					}
				}
			} else {
				System.out.println("vous ne pouvez pas mettre ceci ici");
			}

		}

//	}

	public void decouper(MouseEvent e) throws IllegalAccessException {
		// si le container est nul alors il faut selectionner un ingredient
		if (container == null) {
			if (checkSiIngredientPresentDansMateriel(materielDecoupe)) {
				containerDansDecoupe.setImage(null);
			}
		}
		// si le container n'est pas vide
		else {
			try {

				if (((Ingredient) container).isDecoupable()) {
					if (((Decoupe) materielDecoupe).getEmplacementVide()) {
						Ingredient ingredient = (Ingredient) container;
						containerDansDecoupe
								.setImage(new Image(getClass().getResourceAsStream(ingredient.getImgIngredient())));
						materielDecoupe.ajouterObjet(ingredient);
						viderContainer();
						// si le container est découpable
						if (ingredient.isDecoupable()) {
							// si cette ingredient découpable n'est pas déja transformé alors le découper
							if (ingredient.getTransformer() == false) {
								((Decoupe) materielDecoupe).decouper();
//								System.out.println(ingredient.getImgIngredient());
								containerDansDecoupe.setImage(
										new Image(getClass().getResourceAsStream(ingredient.getImgIngredient())));
//								System.out.println(ingredient.getNom() + " a été découpé ");
							}
							// sinon cette ingredient découpable a déja été découpé
							else {
//								System.out.println(ingredient.getNom() + " a déja été découpé");
							}
						}
						// sinon cette ingredient n'est pas découpale
						else {
//							System.out.println(ingredient.getNom() + " n'est pas découpable");
						}
//						System.out.println("transformé : " + ingredient.getNom() + " : " + ingredient.getTransformer());
					} else {
						System.out.println("Il y a déjà quelque chose dans ce materiel");
					}
				} else {
					System.out.println("l'ingredient n'est pas découpable");
				}
			} catch (Exception e2) {
				System.out.println("seul les ingredients peuvent etre découpés petit malin ;)");
			}
		}
	}

	public void cuir(MouseEvent e) {
		if (container == null) {
			checkSiIngredientPresentDansMateriel(materielPlaqueDeCuisson);
			if (CuissonEnCoursSteak != null) {
				CuissonEnCoursSteak.cancel();
				CuissonEnCoursSteak.reset();
				cuissonProgress.setProgress(0.0);
			}
		} else {
			Ingredient ingredient = ((Ingredient) container);
			if (((Ingredient) container).isSteak() == true) {
				if (((Ingredient) container).getEtat() == Etat.CRU) {
					try {
						materielPlaqueDeCuisson.ajouterObjet(ingredient);
						// containerDansCuisson.setImage(new
						// Image(getClass().getResourceAsStream("../image/friteuse.png")));
						viderContainer();
						cuissonProgression(ingredient, cuissonProgress, 10);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					System.out.println(((Ingredient) container).getNom() + " a déja été cuit");
				}
			} else {
				System.out.println(((Ingredient) container).getNom() + " ne peut pas etre cuit");
			}
		}
	}

	public void frire(MouseEvent e) throws InterruptedException {
		if (container == null) {
			System.out.println("ingredient contenu " + materielFriteuse.objetsContenus.size());
			if (checkSiIngredientPresentDansMateriel(materielFriteuse)) {
				containerDansFriteuse.setImage(new Image(getClass().getResourceAsStream("../image/friteuse.png")));
				if (FrireEnCours != null) {
					FrireEnCours.cancel();
					FrireEnCours.reset();
					frireProgress.setProgress(0.0);
				}

			}
		} else {
			if (container instanceof Ingredient) {
				Ingredient a = ((Ingredient) container);
				if (a.getNom().toString().equals("PATATE") & a.getTransformer()) {
					if (a.getTransformer() == true) {
						if (a.getEtat() == Etat.CRU) {
							materielFriteuse.ajouterObjet(a);
							containerDansFriteuse.setImage(
									new Image(getClass().getResourceAsStream("../image/friteuse_cuisson.png")));
							viderContainer();
							frireProgression(a, frireProgress, 5);
						} else {
							System.out.println(a.getNom() + " a déja été cuit");
						}
					} else {
						System.out.println(a.getNom() + " doit etre découpé");
					}
				} else {
					System.out.println("seul les patates coupé peuvent être fries");
				}
			} else {
				System.out.println("ce que vous avez dans la main n'est même pas un ingrédient");
			}

		}
	}

	public void lavevaisselle(MouseEvent e) {
		if (container == null) {
			checkSiIngredientPresentDansMateriel(materielLaveVaisselle);
			if (LaveVaisselleEnCours != null) {
				LaveVaisselleEnCours.cancel();
				LaveVaisselleEnCours.reset();
				LaveProgress.setProgress(0.0);
			}
		} else if (materielLaveVaisselle.getEmplacementVide()){
			Assiette assiette = ((Assiette) container);
			if (((Assiette) container).getEtatAssiette() == EtatAssiette.SALE) {
				try {
					laverProgression(assiette, LaveProgress, 5.0);
					materielLaveVaisselle.ajouterObjet(assiette);
					((LaveVaisselle) materielLaveVaisselle).nettoyer();
					viderContainer();
					// containerDansCuisson.setImage(new
					// Image(getClass().getResourceAsStream("../image/friteuse.png")));
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			} else {
				System.out.println("Ceci est déjà propre");
			}
		}

	}

	public void assembler(MouseEvent event) {
		if (container == null) {
			if (materielAssemblage.objetsContenus.size() != 0) {
//				System.out.println("selectionner un ingredient");
				mettreDansContainer(((Assiette) materielAssemblage.objetsContenus.get(0)));
				materielAssemblage.objetsContenus.remove(0);
				emplacementAssiette.setImage(null);
				emplacementAssemblagePatate.setImage(null);
				emplacementAssemblageFromage.setImage(null);
				emplacementAssemblagePain.setImage(null);
				emplacementAssemblageOignon.setImage(null);
				emplacementAssemblageSalade.setImage(null);
				emplacementAssemblageSteak.setImage(null);
				emplacementAssemblageTomate.setImage(null);
			}
		} else if (container instanceof Assiette) {
			Assiette a = (Assiette) container;
			if (a.getEtatAssiette().equals(EtatAssiette.SALE)) {
				System.out.println("l'assiette a déja été utilisé, veuillez la laver");
			} else {
				if(a.getEtatAssiette().equals(EtatAssiette.PLAT)) {
					emplacementAssiette.setImage(new Image(getClass().getResourceAsStream("../image/assiette.png")));
				}
				else {
					a.setEtatAssiette(EtatAssiette.PROPRE);
					emplacementAssiette.setImage(new Image(getClass().getResourceAsStream(a.getImgAssiette())));
				}
				materielAssemblage.ajouterObjet(a);
				
				// rechercher les ingredients présents dans l'assiette
				for (int i = 0; i < a.objetsContenus.size(); i++) {
					Ingredient ing = (Ingredient) a.objetsContenus.get(i);
					switch (ing.getNom()) {
					case PATATE:
						emplacementAssemblagePatate
								.setImage(new Image(getClass().getResourceAsStream(ing.getImgIngredient())));

						break;
					case FROMAGE:
						emplacementAssemblageFromage
								.setImage(new Image(getClass().getResourceAsStream(ing.getImgIngredient())));

						break;
					case PAIN:
						emplacementAssemblagePain
								.setImage(new Image(getClass().getResourceAsStream(ing.getImgIngredient())));

						break;
					case OIGNON:
						emplacementAssemblageOignon
								.setImage(new Image(getClass().getResourceAsStream(ing.getImgIngredient())));

						break;
					case SALADE:
						emplacementAssemblageSalade
								.setImage(new Image(getClass().getResourceAsStream(ing.getImgIngredient())));

						break;
					case STEAK_DE_BOEUF:
						emplacementAssemblageSteak
								.setImage(new Image(getClass().getResourceAsStream(ing.getImgIngredient())));

						break;
					case STEAK_DE_POULET:
						emplacementAssemblageSteak
								.setImage(new Image(getClass().getResourceAsStream(ing.getImgIngredient())));

						break;
					case STEAK_DE_SOJA:
						emplacementAssemblageSteak
								.setImage(new Image(getClass().getResourceAsStream(ing.getImgIngredient())));

						break;
					case TOMATE:
						emplacementAssemblageTomate
								.setImage(new Image(getClass().getResourceAsStream(ing.getImgIngredient())));

						break;
					}

				}
				viderContainer();

			}

//			si le container est un ingredient
		} else if (container instanceof Ingredient &&  materielAssemblage.objetsContenus.size()>0 ) {
			// récupere l'assiette contenu dans l'assemblage
			Assiette assiette = (Assiette) materielAssemblage.objetsContenus.get(0);
			if (assiette.verifierSiIngredientPresentDansAssiette((Ingredient) container) == true) {
				if(((Ingredient) container).isSteak() || ((Ingredient)container).getNom().equals(Ingredient.Nom.FROMAGE)){
					assiette.ajouterObjet((Ingredient) container);
					assiette.afficher_La_Liste_Des_Objets_Contenus();
					viderContainer();
				}
				else {
					System.out.println("ingredient déja présent");
				}
			} else {
				// ajoute un ingredient à l'assiette
				switch (((Ingredient) container).getNom()) {
				case PATATE:
					emplacementAssemblagePatate.setImage(
							new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
					assiette.ajouterObjet((Ingredient) container);
					viderContainer();
					break;
				case FROMAGE:
					emplacementAssemblageFromage.setImage(
							new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
					assiette.ajouterObjet((Ingredient) container);
					viderContainer();
					break;
				case PAIN:
					emplacementAssemblagePain.setImage(
							new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
					assiette.ajouterObjet((Ingredient) container);
					viderContainer();
					break;
				case OIGNON:
					emplacementAssemblageOignon.setImage(
							new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
					assiette.ajouterObjet((Ingredient) container);
					viderContainer();
					break;
				case SALADE:
					emplacementAssemblageSalade.setImage(
							new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
					assiette.ajouterObjet((Ingredient) container);
					viderContainer();
					break;
				case STEAK_DE_BOEUF:
					emplacementAssemblageSteak.setImage(
							new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
					assiette.ajouterObjet((Ingredient) container);
					viderContainer();
					break;
				case STEAK_DE_POULET:
					emplacementAssemblageSteak.setImage(
							new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
					assiette.ajouterObjet((Ingredient) container);
					viderContainer();
					break;
				case STEAK_DE_SOJA:
					emplacementAssemblageSteak.setImage(
							new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
					assiette.ajouterObjet((Ingredient) container);
					viderContainer();
					break;
				case TOMATE:
					emplacementAssemblageTomate.setImage(
							new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
					assiette.ajouterObjet((Ingredient) container);
					viderContainer();
					break;
				}
			}

		} else {
			System.out.println("tu dois mettre une assiette d'abord");
		}
	}
	
	

	public boolean checkSiIngredientPresentDansMateriel(Materiel m) {
		if (m.objetsContenus.isEmpty()) {
//			System.out.println("veuillez selectionner un ingredient");
			return false;
		}
		// sinon, container = ingredient contenu dans le materiel de decoupe
		else {
			System.out.println("contenu du materiel avant " + m.objetsContenus.size());
//			this.container = m.objetsContenus.get(0);
			mettreDansContainer(m.objetsContenus.get(0));
			m.retirerObjet(this.container);
			System.out.println("contenu du materiel apres " + m.objetsContenus.size());
			System.out.println("ingredient ajouté à container");
			return true;
		}
	}

	public void jeter() {
		viderContainer();
	}

	public int compteur(Nom n) {
		return niveau.getGardeManger().getCompteurs().get(n);
	}

	public void mettreDansContainer(Object o) {
		if (o instanceof Ingredient) {
			container = ((Ingredient) o);
			containerView
					.setImage(new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
			Nom nomIngredient = ((Ingredient) container).getNom();
			switch (nomIngredient) {
			case PATATE:
				compteurPATATE.setText(String.valueOf(compteur(Nom.PATATE)));
				break;
			case FROMAGE:
				compteurFROMAGE.setText(String.valueOf(compteur(Nom.FROMAGE)));
				break;
			case PAIN:
				compteurPAIN.setText(String.valueOf(compteur(Nom.PAIN)));
				break;
			case OIGNON:
				compteurOIGNON.setText(String.valueOf(compteur(Nom.OIGNON)));
				break;
			case SALADE:
				compteurSALADE.setText(String.valueOf(compteur(Nom.SALADE)));
				break;
			case STEAK_DE_BOEUF:
				compteurSTEAK_DE_BOEUF.setText(String.valueOf(compteur(Nom.STEAK_DE_BOEUF)));
				break;
			case STEAK_DE_POULET:
				compteurSTEAK_DE_POULET.setText(String.valueOf(compteur(Nom.STEAK_DE_POULET)));
				break;
			case STEAK_DE_SOJA:
				compteurSTEAK_DE_SOJA.setText(String.valueOf(compteur(Nom.STEAK_DE_SOJA)));
				break;
			case TOMATE:
				compteurTOMATE.setText(String.valueOf(compteur(Nom.TOMATE)));
				break;
			}
		}
		if (o instanceof Assiette) {
			container = ((Assiette) o);
			compteurAssitette.setText(String.valueOf(niveau.getCuisine().getAssiettes().size()));
			containerView.setImage(new Image(getClass().getResourceAsStream(((Assiette) container).getImgAssiette())));
		}
	}

	public void viderContainer() {
		container = null;
		containerView.setImage(null);
	}

	public class tempsDuJeu extends TimerTask {

		private int temps = 240;
		// permet de ne pas envoyer tout les clients en même temps
		private int attenteEntreClient = 0;

		private int nbrClientEnvoye = 0;

		private ArrayList<Client> clientDuNiveau = niveau.getClients();

		private boolean enCours = true;

		private ArrayList<ProgressBar> clientProgress = new ArrayList<ProgressBar>();

		public tempsDuJeu() {
			clientProgress.add(client1Progress);
			clientProgress.add(client2Progress);
			clientProgress.add(client3Progress);

		}

		public void setAttenteClient(int attente) {
			this.attenteEntreClient = attente;
//			System.out.println(clientDuNiveau.size());
		}

		@Override
		public void run() {
			while (enCours) {

				String time = String.valueOf(temps);
				temps--;
				attenteEntreClient--;

				Platform.runLater(() -> {
					tempsEnCours.setText(time);
					
					if (temps == 0 || nbrClientEnvoye == clientDuNiveau.size() && comptoir.getEmplacementClientDansComptoire()[0]==null && comptoir.getEmplacementClientDansComptoire()[1]==null && comptoir.getEmplacementClientDansComptoire()[2]==null) {
						this.enCours = false;
						System.out.println("Fin Du Niveau");
						try {
							affichageScore();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					for (int i = 0; i < comptoir.getEmplacementClientDansComptoire().length; i++) {
						if (niveau.checker_Si_Liste_Des_Clients_Est_Vide()==false) {
							if (comptoir.checker_Si_Une_Assiette_Est_Presente_Dans_Emplacement_Du_Client(i)==false) {

								if (comptoir.checker_Si_Un_Client_Est_Assis_Dans_Un_Emplacement(i)==false) {

									Client client = niveau.getClients().get(0);
									comptoir.ajouterClient(client, i);


									niveau.getClients().remove(0);

									if (i == 0) {
										try {
											
//											client.getCommande().afficherIngredientRecette();
											
											client1 = comptoir.getEmplacementClientDansComptoire()[0];
											client1EnCours = envoyerUnClient(
													client1,
													clientProgress.get(i));
											labelRecetteClient1.setText(client1
													.getCommande().getNom().toString());

											getGalleryView(client, vBoxClient1);

										} catch (InterruptedException e) {
											e.printStackTrace();
										}

									}
									if (i == 1) {
										try {
											client2 = comptoir.getEmplacementClientDansComptoire()[1];
											client2EnCours = envoyerUnClient(
													client2,
													clientProgress.get(i));
											labelRecetteClient2.setText(client2
													.getCommande().getNom().toString());

											getGalleryView(client, vBoxClient2);

										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									if (i == 2) {
										try {
											client3 = comptoir.getEmplacementClientDansComptoire()[2];
											client3EnCours = envoyerUnClient(
													client3,
													clientProgress.get(i));
											labelRecetteClient3.setText(client3
													.getCommande().getNom().toString());

											getGalleryView(client, vBoxClient3);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
						if (comptoir.checker_Si_Un_Client_Est_Assis_Dans_Un_Emplacement(i)) {
							if (comptoir.getEmplacementClientDansComptoire()[i].getTmpsAttente() == 0) {
								System.out.println(
										"client : " + comptoir.getEmplacementClientDansComptoire()[i] + " est parti");
								comptoir.retirerClient(i);
								if (i == 0) {
									client1=null;
									vBoxClient1.getChildren().clear();
									labelRecetteClient1.setText(null);
									client1Progress.setVisible(false);
									client1EnCours.cancel();
									client1EnCours.reset();
								}
								if (i == 1) {
									client2=null;
									vBoxClient2.getChildren().clear();
									labelRecetteClient2.setText(null);
									client2Progress.setVisible(false);
									client2EnCours.cancel();
									client2EnCours.reset();
								}
								if (i == 2) {
									client3=null;
									vBoxClient3.getChildren().clear();
									labelRecetteClient3.setText(null);
									client3Progress.setVisible(false);
									client3EnCours.cancel();
									client3EnCours.reset();
								}
							}
						}
					}
				});
				try {

					Thread.sleep(1000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void cuissonProgression(Ingredient ingredient, ProgressBar progress, double temps)
			throws InterruptedException {

		Service<Void> CuissonMateriel = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						for (int i = 0; i < temps; i++) {
							if (isCancelled()) {
								break;
							}
							progress.setProgress(progress.getProgress() + (1.0 / temps));
							progress.setStyle("-fx-accent: orange;");
							Thread.sleep(1000);
						}
						System.out.println("Votre steak est cuit");
						ingredient.setEtat(Etat.CUIT);
						cuissonProgress.setProgress(0.0);
						for (int i = 0; i < 10 / 2; i++) {
							if (isCancelled()) {
								break;
							}
							progress.setProgress(progress.getProgress() + (1.0 / temps) * 2.0);
							progress.setStyle("-fx-accent: green;");
							Thread.sleep(1000);
						}
						ingredient.setEtat(Etat.BRULE);
						progress.setProgress(1);
						progress.setStyle("-fx-accent: red;");
						System.out.println("Votre steak est brulé");
						return null;
					}
				};
			}
		};
		CuissonEnCoursSteak = CuissonMateriel;
		CuissonMateriel.start();
	}

	public void frireProgression(Ingredient ingredient, ProgressBar progress, double temps)
			throws InterruptedException {

		Service<Void> CuissonMateriel = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						for (int i = 0; i < temps; i++) {
							if (isCancelled()) {
								break;
							}
							progress.setProgress(progress.getProgress() + (1.0 / temps));
							progress.setStyle("-fx-accent: orange;");
							Thread.sleep(1000);
						}

						System.out.println("Vos frites sont cuites");
						ingredient.setEtat(Etat.CUIT);

						progress.setStyle("-fx-accent: green;");

						return null;
					}
				};
			}
		};
		FrireEnCours = CuissonMateriel;
		CuissonMateriel.start();
	}

	public void laverProgression(Assiette assiette, ProgressIndicator progress, double temps)
			throws InterruptedException {

		Service<Void> laveEnCours = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						for (int i = 0; i < temps; i++) {
							Thread.sleep(1000);
							if (isCancelled()) {
								break;
							}
							progress.setProgress(progress.getProgress() + (1.0 / temps));
							progress.setStyle("-fx-accent: green;");
							
						}

						System.out.println("l'assiette sont propre");
						assiette.setEtatAssiette(EtatAssiette.PROPRE);

						return null;
					}
				};
			}
		};
		LaveVaisselleEnCours = laveEnCours;
		LaveVaisselleEnCours.start();
	}

	public Service<Void> envoyerUnClient(Client client, ProgressBar progressClient) throws InterruptedException {

		Service<Void> ArriverClient = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						progressClient.setVisible(true);

						double temps = client.getTmpsAttente();

						progressClient.setProgress(1.0);
						progressClient.setStyle("-fx-accent: green;");
						for (double i = temps; i > 1; i--) {
							if (isCancelled()) {
								break;
							}
							progressClient.setProgress(progressClient.getProgress() - (1.0 / temps));
							Thread.sleep(1000);
							if (i < temps * 0.4) {
								progressClient.setStyle("-fx-accent: orange;");
							}
							if (i < temps * 0.2) {
								progressClient.setStyle("-fx-accent: red;");
							}
						}
//						System.out.println("Le client est parti");
						return null;
					}
				};
			}
		};
		ArriverClient.start();
		return ArriverClient;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		niveau = Main.niveau1;

		// mickael : récupere tous l'adresse de tous les materiels du niveau pour le
		// controlleur
		for (Materiel i : niveau.getMateriel().keySet()) {
			if (i instanceof Decoupe) {
				materielDecoupe = i;
//				System.out.println("ajouté");
			}
			if (i instanceof Assemblage) {
				materielAssemblage = i;
//				System.out.println("ajouté");
			}
			if (i instanceof Friteuse) {
				materielFriteuse = i;
//				System.out.println("ajouté");
			}
			if (i instanceof LaveVaisselle) {
				materielLaveVaisselle = i;
//				System.out.println("ajouté");
			}
			if (i instanceof PlaqueCuisson) {
				materielPlaqueDeCuisson = i;
//				System.out.println("ajouté");
			}
			if (i instanceof Poubelle) {
				materielPoubelle = i;
//				System.out.println("ajouté");
			}
		}

		compteurPATATE.setText(String.valueOf(compteur(Nom.PATATE)));
		compteurFROMAGE.setText(String.valueOf(compteur(Nom.FROMAGE)));
		compteurPAIN.setText(String.valueOf(compteur(Nom.PAIN)));
		compteurOIGNON.setText(String.valueOf(compteur(Nom.OIGNON)));
		compteurSALADE.setText(String.valueOf(compteur(Nom.SALADE)));
		compteurSTEAK_DE_BOEUF.setText(String.valueOf(compteur(Nom.STEAK_DE_BOEUF)));
		compteurSTEAK_DE_POULET.setText(String.valueOf(compteur(Nom.STEAK_DE_POULET)));
		compteurSTEAK_DE_SOJA.setText(String.valueOf(compteur(Nom.STEAK_DE_SOJA)));
		compteurTOMATE.setText(String.valueOf(compteur(Nom.TOMATE)));

		compteurAssitette.setText(String.valueOf(niveau.getCuisine().getAssiettes().size()));

		Timer timer = new Timer(true);
		timer.schedule(new tempsDuJeu(), 0, 1000);

		// mick 28/12
		comptoir = niveau.getComptoir();

		scoreLabel.setText(String.valueOf(niveau.getTabScoreArgent()[0]));
		argentLabel.setText(String.valueOf(niveau.getTabScoreArgent()[1]));
		
		
//		System.out.println("nombre de client dans comptoir client " +comptoir.getEmplacementClientDansComptoire().length);


	}

	@FXML
	private Label compteurPileAssietteSale;

	public void stockageAssietteSale() {

		// stocker assiette sale dans la pile d'assiette sale
		// bug à fixer : si l'assiette est vide, puis donné à client sans ingredient,
		// elle passe sale !!!!!
		if (container instanceof Assiette && ((Assiette) container).getEtatAssiette().equals(EtatAssiette.SALE)) {
			Assiette assiette = (Assiette) container;
			niveau.getCuisine().getStock().stockerAssiette(assiette);
			compteurPileAssietteSale
					.setText(String.valueOf(niveau.getCuisine().getStock().getAssiettesSale().size()));
			viderContainer();

		}
		// recupérer assiette sale de la pile
		else if (container == null && niveau.getCuisine().getStock().getAssiettesSale().size() != 0) {
			mettreDansContainer(niveau.getCuisine().getStock().getAssiettesSale().get(0));
			niveau.getCuisine().getStock().getAssiettesSale().remove(0);
			compteurPileAssietteSale
					.setText(String.valueOf(niveau.getCuisine().getStock().getAssiettesSale().size()));

		}
	}

	@FXML
	private ImageView btnMenu;

	public void affichageScore() throws Exception {
		try {
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vueMenuNiveau.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("vueAffichageScore.fxml"));
//	        Parent root1 = FXMLLoader.load(getClass().getResource("vueNiveau1.fxml"));
			Stage stage = (Stage) btnMenu.getScene().getWindow();
			stage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void quitterNiveau() throws Exception {
		try {
			Stage stage = (Stage) btnMenu.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("vueMenuPrincipal.fxml"));
			stage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	private VBox vBoxClient1;
	@FXML
	private VBox vBoxClient2;
	@FXML
	private VBox vBoxClient3;

//	 @FXML private void OnClick(ActionEvent ae)
//	 {
//	   getGalleryView(ae);
//	 }
	public void getGalleryView(Client client, VBox vbox) {
//		 scrollPaneClient1 =new ScrollPane();
//		 hbBoxClient1=new HBox();
		ArrayList<Image> images;
		ArrayList<ImageView> imagesView;
		Client clientTemporaire = client;
//		System.out.println("nombre ingredient" + c.getCommande().ingredients.size());
		Iterator it = clientTemporaire.getCommande().ingredients.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			int a= (int) pair.getValue();
			for(int i=0; i<a;i++) {
				Ingredient ing = (Ingredient) pair.getKey();
//				System.out.println(ing.getImgIngredient() + " = " + pair.getValue());
				Image img = new Image(getClass().getResourceAsStream(ing.getImgIngredient()));
				ImageView imgVw = new ImageView(img);
				imgVw.setFitWidth(50);
				imgVw.setPreserveRatio(true);
				vbox.getChildren().add(imgVw);
				vbox.setAlignment(Pos.CENTER);
				vbox.setSpacing(10);
			}
			
//		        vBoxClient1.setBackground(new Background(new BackgroundFill(Color.GREY,null,null)));
//			it.remove(); // avoids a ConcurrentModificationException
		}
	}
	

}