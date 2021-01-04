package sample;

import classes.cuisine.materiel.Assiette;
import classes.cuisine.materiel.Assiette.EtatAssiette;
import classes.cuisine.materiel.LaveVaisselle;
import classes.cuisine.materiel.Materiel;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import classes.*;
import classes.cuisine.*;
import classes.cuisine.materiel.*;
import classes.cuisine.Ingredient.Etat;
import classes.cuisine.Ingredient.Nom;

public class Controller implements Initializable {
	
	//permet de stopper le run quand on clique sur le bouton pour retourner au menuPrincipale
	private boolean finDuNiveau = false;

	private Niveau niveau;

	private Object container;

	private Materiel materielFriteuse, materielAssemblage, materielPlaqueDeCuisson, materielDecoupe,
			materielLaveVaisselle, materielPoubelle;

	private Comptoir comptoir;

	private Client client1, client2, client3;

	// les attributs suivants (en majuscules) sont utilisés en tant que paramètres
	// dans la fonction prendreIngredient()

	@FXML
	private ImageView PATATE, SALADE, TOMATE, OIGNON, PAIN, FROMAGE, STEAK_DE_SOJA, STEAK_DE_POULET, STEAK_DE_BOEUF;

	@FXML
	private ImageView emplacementAssemblagePain, emplacementAssemblageFromage, emplacementAssemblageOignon,
			emplacementAssemblageTomate, emplacementAssemblageSalade, emplacementAssemblagePatate,
			emplacementAssemblageSteak;

	@FXML
	private ImageView stock1, stock2, stock3;

	@FXML
	private ImageView imageViewClient1, imageViewClient2, imageViewClient3;
	@FXML
	private ProgressBar client1Progress, client2Progress, client3Progress;
	private Service<Void> client1EnCours, client2EnCours, client3EnCours;

	@FXML
	private ImageView emplacementAssiette, emplacementAssietteClient1, emplacementAssietteClient2,
			emplacementAssietteClient3;

	@FXML
	private BorderPane decoupe, plaqueCuisson, friteuse, assemblage, laveVaisselle;

	@FXML
	private ImageView containerDansDecoupe, containerDansCuisson, containerDansFriteuse;

	@FXML
	private ProgressBar cuissonProgress, frireProgress;
	private Service<Void> cuissonEnCoursSteak, frireEnCours;

	@FXML
	private ProgressIndicator laveProgress;
	private Service<Void> laveVaisselleEnCours;

	@FXML
	private ImageView gardeManger, poubelle, assiettePropre;

	@FXML
	private ImageView containerView;

	@FXML
	private Label compteurPain, compteurFromage, compteurOignon, compteurTomate, compteurSalade, compteurPatate,
			compteurSteakDeBoeuf, compteurSteakDePoulet, compteurSteakDeSoja, compteurAssiette,
			compteurPileAssietteSale;

	@FXML
	private Label tempsEnCours;

	@FXML
	private Label labelRecetteClient1, labelRecetteClient2, labelRecetteClient3;

	@FXML
	private Label scoreLabel, argentLabel;

	@FXML
	private VBox vBoxClient1, vBoxClient2, vBoxClient3;

	@FXML
	private ImageView btnClose;

	/**
	 * une classe qui est une extension de la classe TimerTask, celle ci permet de
	 * bon déroulement du jeu en permettant l'envoie des clients et la gestion du
	 * temps.
	 */
	public class TempsDuJeu extends TimerTask {

		private int temps = 240;

		private int attenteEntreClient = 0; // permet de ne pas envoyer tout les clients en même temps

		private int nbrClientEnvoye = 0;

		private ArrayList<Client> clientDuNiveau = niveau.getClients();

		private boolean enCours = true;

		private ArrayList<ProgressBar> clientProgress = new ArrayList<ProgressBar>();

		/**
		 * Constructeur
		 */
		public TempsDuJeu() {
			clientProgress.add(client1Progress);
			clientProgress.add(client2Progress);
			clientProgress.add(client3Progress);

		}

		/**
		 * Permet de rajouter du temps d'attente, et ainsi ne pas avoir tout les clients
		 * qui arrive en même temps
		 *
		 * @param attente correspond au temps d'attente des clients
		 */
		public void setAttenteClient(int attente) {
			this.attenteEntreClient = attente;
//			System.out.println(clientDuNiveau.size());
		}

		@Override
		public void run() {
			// pendant la durée de la partie, s'exécute toutes les secondes,
			while (enCours && finDuNiveau==false) {
				String time = String.valueOf(temps);
				temps--;
				attenteEntreClient--;

				Platform.runLater(() -> {
					tempsEnCours.setText(time);
					// regarde s'il y a encore des clients au comptoir
					// s'il n'y a plus de client alors la partie se termine
					if (temps == 0 || nbrClientEnvoye == clientDuNiveau.size()
							&& comptoir.getEmplacementClientDansComptoir()[0] == null
							&& comptoir.getEmplacementClientDansComptoir()[1] == null
							&& comptoir.getEmplacementClientDansComptoir()[2] == null) {
						enCours = false;
						System.out.println("Fin Du Niveau");
						try {
							affichageScore();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					// parcours les emplacements du comptoir pour y envoyer des clients
					for (int i = 0; i < comptoir.getEmplacementClientDansComptoir().length; i++) {
						// si la place est disponible et débarassée envoyer un client
						if (niveau.checkerSiListeDesClientsEstVide() == false) {
							if (comptoir.checkerSiUneAssietteEstPresenteDansEmplacementDuClient(i) == false) {
								if (comptoir.checkerSiUnClientEstAssisDansUnEmplacement(i) == false) {

									Client client = niveau.getClients().get(0);
									comptoir.ajouterClient(client, i);

									niveau.getClients().remove(0);

									if (i == 0) {
										try {

//											client.getCommande().afficherIngredientRecette();

											client1 = comptoir.getEmplacementClientDansComptoir()[0];
											client1EnCours = envoyerUnClient(client1, clientProgress.get(i));
											labelRecetteClient1.setText(client1.getCommande().getNom().toString());

											getGalerieImage(client, vBoxClient1);

										} catch (InterruptedException e) {
											e.printStackTrace();
										}

									}
									if (i == 1) {
										try {
											client2 = comptoir.getEmplacementClientDansComptoir()[1];
											client2EnCours = envoyerUnClient(client2, clientProgress.get(i));
											labelRecetteClient2.setText(client2.getCommande().getNom().toString());

											getGalerieImage(client, vBoxClient2);

										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									if (i == 2) {
										try {
											client3 = comptoir.getEmplacementClientDansComptoir()[2];
											client3EnCours = envoyerUnClient(client3, clientProgress.get(i));
											labelRecetteClient3.setText(client3.getCommande().getNom().toString());

											getGalerieImage(client, vBoxClient3);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
						// si un client est au comptoir, vérifier son timer d'attente
						// si le compteur est à 0 faire partir le client
						if (comptoir.checkerSiUnClientEstAssisDansUnEmplacement(i)) {
							if (comptoir.getEmplacementClientDansComptoir()[i].getTmpsAttente() == 0) {
								System.out.println(
										"client : " + comptoir.getEmplacementClientDansComptoir()[i] + " est parti");
								comptoir.retirerClient(i);
								if (i == 0) {
									client1 = null;
									vBoxClient1.getChildren().clear();
									labelRecetteClient1.setText(null);
									client1Progress.setVisible(false);
									client1EnCours.cancel();
									client1EnCours.reset();
								}
								if (i == 1) {
									client2 = null;
									vBoxClient2.getChildren().clear();
									labelRecetteClient2.setText(null);
									client2Progress.setVisible(false);
									client2EnCours.cancel();
									client2EnCours.reset();
								}
								if (i == 2) {
									client3 = null;
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

	/**
	 * Permet de servir un client qui est au comptoir
	 * 
	 * @param e
	 * @throws IllegalAccessException
	 */
	public void donnerAssietteClient(MouseEvent e) throws IllegalAccessException {
		// si le container n'est pas vide et qu'il s'agit d'une assiette d'état "Plat"
		if ((container != null) && (container instanceof Assiette)
				&& ((Assiette) container).getEtatAssiette().equals(EtatAssiette.PLAT)) {
			ImageView i = (ImageView) e.getSource();
			Assiette assietteTemporaire = (Assiette) container;

			// servir le client et déclencher la vérification, mettre à jour la vue
			switch (i.getId()) {
			case "imageViewClient1":
				if (client1 != null && comptoir.getEmplacementAssietteDansComptoir()[0] == null) {
					emplacementAssietteClient1.setImage(
							new Image(getClass().getResourceAsStream(((Assiette) container).getImgAssiette())));
					assietteTemporaire.setEtatAssiette(EtatAssiette.SALE);
					comptoir.getEmplacementAssietteDansComptoir()[0] = assietteTemporaire;

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
							((Assiette) comptoir.getEmplacementAssietteDansComptoir()[0]).getImgAssiette())));
					break;
				} else {
					break;
				}
			case "imageViewClient2":
				if (client2 != null && comptoir.getEmplacementAssietteDansComptoir()[1] == null) {
					emplacementAssietteClient2.setImage(
							new Image(getClass().getResourceAsStream(((Assiette) container).getImgAssiette())));
					vBoxClient2.getChildren().clear();
					client2EnCours.cancel();
					client2EnCours.reset();
					client2Progress.setVisible(false);
					comptoir.getEmplacementAssietteDansComptoir()[1] = (Assiette) container;
					if (client2.verifierLePlat((Assiette) container)) {
						niveau.setScoreArgent(100, 100);
						System.out.println("score augmenté");
						scoreLabel.setText(String.valueOf(niveau.getTabScoreArgent()[0]));
					}
					comptoir.retirerClient(1);
					labelRecetteClient2.setText("");
					comptoir.getEmplacementAssietteDansComptoir()[1].setEtatAssiette(EtatAssiette.SALE);
					viderContainer();
					emplacementAssietteClient2.setImage(new Image(getClass().getResourceAsStream(
							((Assiette) comptoir.getEmplacementAssietteDansComptoir()[1]).getImgAssiette())));
					break;
				} else {
					break;
				}
			case "imageViewClient3":
				if (client3 != null && comptoir.getEmplacementAssietteDansComptoir()[2] == null) {
					emplacementAssietteClient3.setImage(
							new Image(getClass().getResourceAsStream(((Assiette) container).getImgAssiette())));
					vBoxClient3.getChildren().clear();
					client3EnCours.cancel();
					client3EnCours.reset();
					client3Progress.setVisible(false);
					comptoir.getEmplacementAssietteDansComptoir()[2] = (Assiette) container;
					if (client3.verifierLePlat((Assiette) container)) {
						niveau.setScoreArgent(100, 100);
						System.out.println("score augmenté");
						scoreLabel.setText(String.valueOf(niveau.getTabScoreArgent()[0]));
					}
					comptoir.retirerClient(2);
					labelRecetteClient3.setText("");
					comptoir.getEmplacementAssietteDansComptoir()[2].setEtatAssiette(EtatAssiette.SALE);
					viderContainer();
					emplacementAssietteClient3.setImage(new Image(getClass().getResourceAsStream(
							((Assiette) comptoir.getEmplacementAssietteDansComptoir()[2]).getImgAssiette())));
					break;
				} else {
					break;
				}
			}
			// si l'emplacement n'est pas vide et que le container est vide alors on peut
			// débarrasser le client
		} else if (container == null) {
			ImageView i = (ImageView) e.getSource();
			switch (i.getId()) {
			case "imageViewClient1":
				mettreDansContainer(((Assiette) comptoir.getEmplacementAssietteDansComptoir()[0]));
				comptoir.getEmplacementAssietteDansComptoir()[0] = null;
				emplacementAssietteClient1.setImage(null);
				break;
			case "imageViewClient2":
				mettreDansContainer(((Assiette) comptoir.getEmplacementAssietteDansComptoir()[1]));
				comptoir.getEmplacementAssietteDansComptoir()[1] = null;
				emplacementAssietteClient2.setImage(null);
				break;
			case "imageViewClient3":
				mettreDansContainer(((Assiette) comptoir.getEmplacementAssietteDansComptoir()[2]));
				comptoir.getEmplacementAssietteDansComptoir()[2] = null;
				emplacementAssietteClient3.setImage(null);
				break;
			}
		}
	}

	/**
	 * Permet de prendre une assiette propre ou d'en remettre une dans la pile
	 */
	public void prendreAssiettePropre() {
		if (container == null) {
			Object c = niveau.getCuisine().retirerAssietteDeLaCuisine();
			mettreDansContainer(c);
		} else if (container instanceof Assiette
				&& ((Assiette) container).getEtatAssiette().equals(EtatAssiette.PROPRE)) {
			Assiette assiette = (Assiette) container;
			niveau.getCuisine().getAssiettes().add(assiette);
			// MODIFIER ! travailler avec le stock et non pas direct cuisine
			compteurAssiette.setText(String.valueOf(niveau.getCuisine().getAssiettes().size()));
			mettreDansContainer((Assiette) container);
			viderContainer();
		}

	}

	/**
	 * Permet de prendre une assiette propre ou d'en remettre une dans la pile
	 * 
	 * @param e permet de prendre, en fonction de l'image sélectionnée,
	 *          l'ingrédient dans le garde manger.
	 */

	public void prendreIngredient(MouseEvent e) {
		if (container == null) {
			Object image = e.getSource();
			String idImage = ((Node) image).getId();
			Object c = niveau.getCuisine().getGardeManger().prendreIngredient(Nom.valueOf(idImage));
			mettreDansContainer(c);
		} else if (container instanceof Ingredient) {
			Ingredient i = ((Ingredient) container);
			if (i.getTransformer() == false && i.getEtat().equals(Etat.CRU)) {
				niveau.getCuisine().getGardeManger().mettreIngredient(i.getNom());
				viderContainer();
				//pour le niveau 1, les ingredients sont infinis
				if (niveau.getNumNiveau() != 1) {
					switch (i.getNom()) {
					case PATATE:
						compteurPatate.setText(String.valueOf(compteur(Nom.PATATE)));
						break;
					case FROMAGE:
						compteurFromage.setText(String.valueOf(compteur(Nom.FROMAGE)));
						break;
					case PAIN:
						compteurPain.setText(String.valueOf(compteur(Nom.PAIN)));
						break;
					case OIGNON:
						compteurOignon.setText(String.valueOf(compteur(Nom.OIGNON)));
						break;
					case SALADE:
						compteurSalade.setText(String.valueOf(compteur(Nom.SALADE)));
						break;
					case STEAK_DE_BOEUF:
						compteurSteakDeBoeuf.setText(String.valueOf(compteur(Nom.STEAK_DE_BOEUF)));
						break;
					case STEAK_DE_POULET:
						compteurSteakDePoulet.setText(String.valueOf(compteur(Nom.STEAK_DE_POULET)));
						break;
					case STEAK_DE_SOJA:
						compteurSteakDeSoja.setText(String.valueOf(compteur(Nom.STEAK_DE_SOJA)));
						break;
					case TOMATE:
						compteurTomate.setText(String.valueOf(compteur(Nom.TOMATE)));
						break;
					}
				}
			}
		}
		// else { System.out.println("vous ne pouvez pas mettre ceci ici"); }

	}

	/**
	 * Permet de découper un ingrédient lorsque cette fonction est affectée à un
	 * élément dans la vue
	 * 
	 * @throws InterruptedException
	 */

	public void decouper() throws IllegalAccessException {
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
					if (((Decoupe) materielDecoupe).checkSiObjetsContenusEstVide()) {
						Ingredient ingredient = (Ingredient) container;
						containerDansDecoupe
								.setImage(new Image(getClass().getResourceAsStream(ingredient.getImgIngredient())));
						materielDecoupe.ajouterObjet(ingredient);
						viderContainer();
						// si le container est découpable
						if (ingredient.isDecoupable()) {
							// si cette ingredient découpable n'est pas déjà transformé alors le
							// découper
							if (ingredient.getTransformer() == false) {
								((Decoupe) materielDecoupe).decouper();
//								System.out.println(ingredient.getImgIngredient());
								containerDansDecoupe.setImage(
										new Image(getClass().getResourceAsStream(ingredient.getImgIngredient())));
//								System.out.println(ingredient.getNom() + " a �t� d�coup� ");
							}
							// sinon cette ingredient découpable a déjà été découpé
							// else { System.out.println(ingredient.getNom() + " a d�ja �t�
							// d�coup�"); }
						}
						// sinon cette ingredient n'est pas découpale
						// else { System.out.println(ingredient.getNom() + " n'est pas d�coupable"); }
//						System.out.println("transform� : " + ingredient.getNom() + " : " + ingredient.getTransformer());
					}
					// else { System.out.println("Il y a d�j� quelque chose dans ce materiel");
					// }
				}
				// else { System.out.println("l'ingredient n'est pas d�coupable");}
			} catch (Exception e2) {
				System.out.println("seul les ingredients peuvent etre d�coup�s");
			}
		}
	}

	/**
	 * Permet de rajouter des viandes dans la plaque de cuisson lorsque le joueur
	 * clique dessus dans la vue.
	 * 
	 * @throws InterruptedException
	 */
	public void cuire() {
		if (container == null) {
			checkSiIngredientPresentDansMateriel(materielPlaqueDeCuisson);
			if (cuissonEnCoursSteak != null) {
				cuissonEnCoursSteak.cancel();
				cuissonEnCoursSteak.reset();
				cuissonProgress.setProgress(0.0);
			}
		} else {
			Ingredient ingredient = ((Ingredient) container);
			if (((Ingredient) container).isSteak() == true) {
				if (((Ingredient) container).getEtat() == Etat.CRU) {
					try {
						materielPlaqueDeCuisson.ajouterObjet(ingredient);
						viderContainer();
						cuissonProgression(ingredient, cuissonProgress, 10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} else {
					// System.out.println(((Ingredient) container).getNom() + " a déjà été
					// cuit");
				}
			} else {
				// System.out.println(((Ingredient) container).getNom() + " ne peut pas etre
				// cuit");
			}
		}
	}

	/**
	 * Permet de rajouter des assiettes dans le lave vaisselle lorsque le joueur
	 * clique dessus dans la vue.
	 */
	public void frire() throws InterruptedException {
		if (container == null) {// si le container est vide alors on supose que le joueur veut récupérer
								// l'objet dans le mat�riel.
			System.out.println("ingredient contenu " + materielFriteuse.objetsContenus.size());
			if (checkSiIngredientPresentDansMateriel(materielFriteuse)) {
				containerDansFriteuse.setImage(new Image(getClass().getResourceAsStream("../image/friteuse.png")));
				if (frireEnCours != null) {// on arrete la cuisson si le joueur veut récupérer le contenu
					frireEnCours.cancel();
					frireEnCours.reset();
					frireProgress.setProgress(0.0);
				}

			}
		} else {
			// sinon le joueur veut mettre un objet dans le materiel
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
							System.out.println(a.getNom() + " a d�ja �t� cuit");
						}
					} else {
						System.out.println(a.getNom() + " doit etre d�coup�");
					}
				} else {
					System.out.println("seul les patates coup� peuvent être fries");
				}
			} else {
				System.out.println("ce que vous avez dans la main n'est m�me pas un ingrédient");
			}

		}
	}

	/**
	 * Permet de rajouter des assiettes dans le lave vaisselle lorsque le joueur
	 * clique dessus dans la vue.
	 */
	public void laveVaisselle() {
		if (container == null) {// si le container est vide alors on supose que le joueur veut r�cup�rer
								// l'objet dans le mat�riel.
			checkSiIngredientPresentDansMateriel(materielLaveVaisselle);
			if (laveVaisselleEnCours != null) {
				laveVaisselleEnCours.cancel();
				laveVaisselleEnCours.reset();
				laveProgress.setProgress(0.0);
			}
		} else if (materielLaveVaisselle.checkSiObjetsContenusEstVide()) {
			// sinon le joueur veut mettre un objet dans le materiel
			Assiette assiette = ((Assiette) container);
			if (((Assiette) container).getEtatAssiette() == EtatAssiette.SALE) {
				try {
					laverProgression(assiette, laveProgress, 5.0);
					materielLaveVaisselle.ajouterObjet(assiette);
					((LaveVaisselle) materielLaveVaisselle).nettoyer();
					viderContainer();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			} else {
				System.out.println("Ceci est d�j� propre");
			}
		}

	}

	/**
	 * Permet de gérer l'assemblage, si le joueur clic sur l'assemblage est que
	 * celle ci contient une assiette, alors le joueur pourra rajouter des
	 * éléments dans l'assiette et verra ces ingrédients dans l'assemblage.
	 */
	public void assembler() {
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
				System.out.println("l'assiette a d�ja �t� utilis�, veuillez la laver");
			} else {
				if (a.getEtatAssiette().equals(EtatAssiette.PLAT)) {
					emplacementAssiette.setImage(new Image(getClass().getResourceAsStream("../image/assiette.png")));
				} else {
					a.setEtatAssiette(EtatAssiette.PROPRE);
					emplacementAssiette.setImage(new Image(getClass().getResourceAsStream(a.getImgAssiette())));
				}
				materielAssemblage.ajouterObjet(a);

				// rechercher les ingredients pr�sents dans l'assiette
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
					case STEAK_DE_POULET:
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
		} else if (container instanceof Ingredient && materielAssemblage.objetsContenus.size() > 0) {
			// r�cupere l'assiette contenu dans l'assemblage
			Assiette assiette = (Assiette) materielAssemblage.objetsContenus.get(0);
			if (assiette.verifierSiIngredientPresentDansAssiette((Ingredient) container) == true) {
				if (((Ingredient) container).isSteak()
						|| ((Ingredient) container).getNom().equals(Ingredient.Nom.FROMAGE)) {

					assiette.ajouterObjet((Ingredient) container);
					assiette.afficherLaListeDesObjetsContenus();
					viderContainer();
				} else {
					System.out.println("ingredient d�ja pr�sent");
				}
			} else {
				// ajoute un ingredient � l'assiette
				if (assiette.getEtatAssiette() == EtatAssiette.PROPRE) {
					assiette.setEtatAssiette(EtatAssiette.PLAT);
				}
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
				case STEAK_DE_POULET:
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

	/**
	 * Permet de vérifier si un élément est présent dans un matériel.
	 * 
	 * @param m correspond au matériel visé, il faut vérifier si celui ci est
	 *          vide ou non
	 * 
	 * @return boolean si le matériel est vide false, sinon true
	 * 
	 */
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
			System.out.println("ingredient ajout� � container");
			return true;
		}
	}

	/**
	 * Permet de mettre un �l�ment dans le container.
	 * 
	 * @param n nom d'un ingredient (d'aprés l'énumération Nom).
	 * 
	 * @return int returne un chiffre correspond � la quantit� restant dans le
	 *         niveau.
	 * 
	 */
	public int compteur(Nom n) {
		return niveau.getCuisine().getGardeManger().getCompteurs().get(n);
	}

	/**
	 * Permet de mettre un �l�ment dans le container.
	 * 
	 * @param o objet r�cup�r� lors d'un clic du joueur.
	 * 
	 */

	public void mettreDansContainer(Object o) {
		if (o instanceof Ingredient) {
			container = ((Ingredient) o);
			containerView
					.setImage(new Image(getClass().getResourceAsStream(((Ingredient) container).getImgIngredient())));
			if (niveau.getNumNiveau() != 1) {
				Nom nomIngredient = ((Ingredient) container).getNom();
				switch (nomIngredient) {
				case PATATE:
					compteurPatate.setText(String.valueOf(compteur(Nom.PATATE)));
					break;
				case FROMAGE:
					compteurFromage.setText(String.valueOf(compteur(Nom.FROMAGE)));
					break;
				case PAIN:
					compteurPain.setText(String.valueOf(compteur(Nom.PAIN)));
					break;
				case OIGNON:
					compteurOignon.setText(String.valueOf(compteur(Nom.OIGNON)));
					break;
				case SALADE:
					compteurSalade.setText(String.valueOf(compteur(Nom.SALADE)));
					break;
				case STEAK_DE_BOEUF:
					compteurSteakDeBoeuf.setText(String.valueOf(compteur(Nom.STEAK_DE_BOEUF)));
					break;
				case STEAK_DE_POULET:
					compteurSteakDePoulet.setText(String.valueOf(compteur(Nom.STEAK_DE_POULET)));
					break;
				case STEAK_DE_SOJA:
					compteurSteakDeSoja.setText(String.valueOf(compteur(Nom.STEAK_DE_SOJA)));
					break;
				case TOMATE:
					compteurTomate.setText(String.valueOf(compteur(Nom.TOMATE)));
					break;
				}
			}
		}
		if (o instanceof Assiette) {
			container = ((Assiette) o);
			compteurAssiette.setText(String.valueOf(niveau.getCuisine().getAssiettes().size()));
			containerView.setImage(new Image(getClass().getResourceAsStream(((Assiette) container).getImgAssiette())));
		}
	}

	/**
	 * Permet de vider le container, supprimer le contenu du container et l'image.
	 * 
	 */
	public void viderContainer() {
		container = null;
		containerView.setImage(null);
	}

	/**
	 * Permet de de simuler le temps d'attente de la plaque de cuisson dans le
	 * niveau, avec la mise � jour du plaque de cuisson (temps d'attente de la
	 * ProgressBar) et du changement d'�tat de l'ingredient.
	 * 
	 * @param ingredient qui va etre cuit
	 * @param progress   correspondant � celui de la plaque de cuisson
	 * @param temps      un temps qui permet de d�cider de la dur�e de la
	 *                   cuisson
	 * 
	 */
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
						System.out.println("Votre steak est brul�");
						return null;
					}
				};
			}
		};
		cuissonEnCoursSteak = CuissonMateriel;
		CuissonMateriel.start();
	}

	/**
	 * Permet de de simuler le temps d'attente de la friteuse dans le niveau, avec
	 * la mise � jour de la friteuse (temps d'attente de la ProgressBar) et du
	 * changement d'�tat de l'ingredient.
	 * 
	 * @param ingredient qui va etre frit
	 * @param progress   correspondant � celui de la friteuse
	 * @param temps      un temps qui permet de d�cider de la dur�e de la
	 *                   cuisson
	 * 
	 */

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
		frireEnCours = CuissonMateriel;
		CuissonMateriel.start();
	}

	/**
	 * Permet de de simuler le temps d'attente du lave vaisselle dans le niveau
	 * (temps d'attente de la progressIndicator) avec la mise � jour du lave
	 * vaisselle et de l'�tat de l'assiette
	 * 
	 * @param assiette une assiette qui va etre lav�
	 * @param progress correspondant � celui du lave vaiselle
	 * @param temps    un temps qui permet de d�cider de la dur�e du lave
	 *                 vaisselle
	 * 
	 */

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
		laveVaisselleEnCours = laveEnCours;
		laveVaisselleEnCours.start();
	}

	/**
	 * Permet de de simuler le temps d'attente du client dans le niveau, avec la
	 * mise � jour du client (temps d'attente de la progressBar).
	 * 
	 * @param client         qui va commencer � attendre.
	 * @param progressClient
	 * @return un service avec � l'interieur la gestion du client
	 * @throws InterruptedException
	 */

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

	/**
	 * Permet de mettre en stock les assiette sale.
	 */
	public void stockageAssietteSale() {

		// stocker assiette sale dans la pile d'assiette sale
		if (container instanceof Assiette && ((Assiette) container).getEtatAssiette().equals(EtatAssiette.SALE)) {
			Assiette assiette = (Assiette) container;
			niveau.getCuisine().getStock().stockerAssiette(assiette);
			compteurPileAssietteSale.setText(String.valueOf(niveau.getCuisine().getStock().getAssiettesSale().size()));
			viderContainer();

		}
		// recup�rer assiette sale de la pile
		else if (container == null && niveau.getCuisine().getStock().getAssiettesSale().size() != 0) {
			mettreDansContainer(niveau.getCuisine().getStock().getAssiettesSale().get(0));
			niveau.getCuisine().getStock().getAssiettesSale().remove(0);
			compteurPileAssietteSale.setText(String.valueOf(niveau.getCuisine().getStock().getAssiettesSale().size()));
		}
	}

	/**
	 * Cette fonction est lancé lorsque le niveau est terminé, elle permet
	 * d'afficher le score du niveau, elle fait apparaitre la vueAffichageScore
	 */
	public void affichageScore() throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("vueAffichageScore.fxml"));
			Stage stage = (Stage) btnClose.getScene().getWindow();
			stage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cette fonction est lancer lorsque l'on appuie sur la croix du niveau, elle
	 * permet de quitter le niveau et de revenir sur l'�cran principale
	 */
	public void quitterNiveau() throws Exception {
		try {
			finDuNiveau=true;
			Stage stage = (Stage) btnClose.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("vueMenuPrincipal.fxml"));
			stage.setScene(new Scene(root));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet d'afficher la commande du client, elle affiche les images en fonctions
	 * de ce que le client veut comme commande
	 * 
	 * @param client pour par la suite r�cup�rer la recette correspondant �
	 *               celui-ci.
	 * @param vbox   correspond à l'emplacement du Client pour faire afficher les
	 *               images au dessus de lui.
	 */
	public void getGalerieImage(Client client, VBox vbox) {
		ArrayList<Image> images;
		ArrayList<ImageView> imagesView;
		Client clientTemporaire = client;
//		System.out.println("nombre ingredient" + c.getCommande().ingredients.size());
		Iterator it = clientTemporaire.getCommande().ingredients.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			int a = (int) pair.getValue();
			for (int i = 0; i < a; i++) {
				Ingredient ing = (Ingredient) pair.getKey();
//				System.out.println(ing.getImgIngredient() + " = " + pair.getValue());
				Image img = new Image(getClass().getResourceAsStream(ing.getImgIngredient()));
				ImageView imgVw = new ImageView(img);
				imgVw.setFitWidth(45);
				imgVw.setPreserveRatio(true);
				vbox.getChildren().add(imgVw);
				vbox.setAlignment(Pos.CENTER);
				vbox.setSpacing(5);
			}
		}
	}

	/**
	 * @Override Permet d'initialiser le controller
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		niveau = Main.niveau;

		// controlleur
		for (Materiel i : niveau.getMateriel().keySet()) {
			if (i instanceof Decoupe) {
				materielDecoupe = i;
//				System.out.println("ajout�");
			}
			if (i instanceof Assemblage) {
				materielAssemblage = i;
//				System.out.println("ajout�");
			}
			if (i instanceof Friteuse) {
				materielFriteuse = i;
//				System.out.println("ajout�");
			}
			if (i instanceof LaveVaisselle) {
				materielLaveVaisselle = i;
//				System.out.println("ajout�");
			}
			if (i instanceof PlaqueCuisson) {
				materielPlaqueDeCuisson = i;
//				System.out.println("ajout�");
			}
			if (i instanceof Poubelle) {
				materielPoubelle = i;
//				System.out.println("ajout�");
			}
		}

		//pour le niveau 1, les ingredients sont infinis
		if (niveau.getNumNiveau() != 1) {
			compteurPatate.setText(String.valueOf(compteur(Nom.PATATE)));
			compteurFromage.setText(String.valueOf(compteur(Nom.FROMAGE)));
			compteurPain.setText(String.valueOf(compteur(Nom.PAIN)));
			compteurOignon.setText(String.valueOf(compteur(Nom.OIGNON)));
			compteurSalade.setText(String.valueOf(compteur(Nom.SALADE)));
			compteurSteakDeBoeuf.setText(String.valueOf(compteur(Nom.STEAK_DE_BOEUF)));
			compteurSteakDePoulet.setText(String.valueOf(compteur(Nom.STEAK_DE_POULET)));
			compteurSteakDeSoja.setText(String.valueOf(compteur(Nom.STEAK_DE_SOJA)));
			compteurTomate.setText(String.valueOf(compteur(Nom.TOMATE)));
		}

		compteurAssiette.setText(String.valueOf(niveau.getCuisine().getAssiettes().size()));

		Timer timer = new Timer(true);
		timer.schedule(new TempsDuJeu(), 0, 1000);

		comptoir = niveau.getComptoir();

		scoreLabel.setText(String.valueOf(niveau.getTabScoreArgent()[0]));
		argentLabel.setText(String.valueOf(niveau.getTabScoreArgent()[1]));

//		System.out.println("nombre de client dans comptoir client " +comptoir.getEmplacementClientDansComptoire().length);

	}

}