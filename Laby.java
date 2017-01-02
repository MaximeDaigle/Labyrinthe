//Fait par Maxime Daigle et Guyvens Janvier

import java.util.Scanner;



public class Laby{
	
//args[0]: hauteur du labyrinthe en nombre de cases
//args[1]: largeur du labyrinthe en nombre de cases
//args[2]: densité (entre 0 et 1) des murets
//args[3]: nombre de secondes d'affichage du labyrinthe complet avant qu'il devienne invisible
//args[4]: nombre de vies du personnage 
public static void main(String[] args) {
	
	if(args.length != 5){
		msgParamIncorrect();
	}
	
	                        //      hauteur                    largeur                    densite                             secondes                     nbVies 
	jeuxLabyrintheInvisible(Integer.parseInt(args[0]), Integer.parseInt(args[1]),  Double.parseDouble(args[2]), (long) Integer.parseInt(args[3]), Integer.parseInt(args[4]));
}

public static void jeuxLabyrintheInvisible(int i, int j, double densite, long secondes, int nbVies) {
	Scanner scan = new Scanner(System.in);

	//cree les deux instances de labyrinthes et le personnage
	Labyrinthe labyrinthe = new Labyrinthe(i,j);
	Labyrinthe labyInvisible= new Labyrinthe(i,j);
	int caseDeDepart = (int) (i*Math.random());  //choisit au hasard une case de depart dans la premiere colonne
	Personnage pers = new Personnage(labyrinthe,caseDeDepart,0);
	
	//initialise les labyrinthes et le personnage
	initialisationPartie(labyrinthe, labyInvisible, pers, caseDeDepart, nbVies, densite); 
	
	//montre labyrinthe pendant quelques secondes avant de reafficher labyInvisible
	montrerLabVisible(labyrinthe, labyInvisible, secondes);
	
	//pour savoir si le joueur a quitte le jeu ou s'il a perdu toutes ses vies sans sortir du labyrinthe
	boolean succesJoueur = false;
	
	//affiche au joueur le labyrinthe invisible ou visible.
	boolean visible = false; 
	
	//si l'intelligence artificielle est en marche
	boolean ia = false;

	// la partie continue tant que le joueur a des vies restantes
	while( pers.getNbVies() > 0){

		//le joueur est humain et non ia
		if(!ia){

			msgEnDessousDuLab(pers, nbVies);
			
			String direction = scan.nextLine();	
			
			//deplace le joueur si la commande dans le String direction est un deplacement
			deplacementDuJoueur(labyrinthe, labyInvisible, pers, direction);

			//si direction contient pas un deplacement mais un changement d'option (q, v, p ou o)
			
			//quitte le jeu
			if(direction.equalsIgnoreCase("q")){
				System.out.println("vous avez quitte le jeu");
				System.exit(0);
			}
			//nouvelle partie du jeu
			else if(direction.equalsIgnoreCase("p")){ 
				nouvellePartie(i,j,densite,secondes,nbVies);
			}	
			//affiche le labyrinthe avec les murs visibles ou enleve les murs visibles
			else if(direction.equalsIgnoreCase("v")){ visible = !visible;}
			
			//active l'ia
			else if(direction.equalsIgnoreCase("o")){
				visible = true;
				ia = true;
			}
			// cas ou le joueur rentre une lettre ne faisant pas partie du jeu
			else{System.out.println("choisissez une autre entree");} 

			//affiche le labyrinthe actualise avec les changements
			affichageActualiseeDuLab(visible, labyrinthe, labyInvisible);
			
		//l'ia joue
		}else{ 
			int coordYouverture = labyrinthe.getMurOuverture();	   
			ia(labyrinthe, pers, coordYouverture);   	    
		} 
		//affiche un message si le joueur ou l'ia finit le labyrinthe 		  
		if((pers.getCoordY()==labyrinthe.getMurOuverture()) && (pers.getCoordX()==j-1)){
			msgVictoire(ia, pers, nbVies);
			succesJoueur = true;    
			break;
		}              
	} // Fin du while
	
	//verifie si le joueur est sorti du labyrinthe et sinon affiche un message de defaite
	if(!succesJoueur){
		System.out.println("Vous avez perdu, vous avez épuisé vos "+ nbVies +" vies!");		 
	} 
	//propose au joueur de recommencer une nouvelle partie quand il finit 
	demandePourNouvellePartie(i, j, densite, secondes, nbVies, scan);
}

//message si le nombre de paramètres est incorrect
public static void msgParamIncorrect(){
	System.out.println("Nombre de paramètres incorrects. \n"
			+ "Utilisation: java Laby <hauteur> <largeur> <densite> <duree visible> <nb vies> \n"
			+ "Ex: java Laby 10 20 0.20 10 5");
	System.exit(0);
}

//initialise les 2 labyrinthes et le personnage
public static void initialisationPartie(Labyrinthe labyrinthe, Labyrinthe labyInvisible,Personnage pers, int caseDeDepart, int nbVies, double densite){
	labyrinthe.dessineMurdEnceinte();
	labyInvisible.dessineMurdEnceinte();
	labyrinthe.construitLabyrintheAleatoire(densite);
	labyInvisible.dessineOuverture(labyrinthe.getMurOuverture());
	labyrinthe.dessineDepart(caseDeDepart);
	labyInvisible.dessineDepart(caseDeDepart);
	pers.setNbVies(nbVies);
	labyrinthe.dessinePersonnage(pers);
	labyInvisible.dessinePersonnage(pers);	 
}

public static void montrerLabVisible(Labyrinthe labyrintheVisible, Labyrinthe labyInvisible, long secondes){
	labyrintheVisible.affiche();
	sleep(secondes*1000);
	Labyrinthe.effaceEcran();
	labyInvisible.affiche();
}

//deplace le joueur si la commande dans le String direction est un deplacement
public static void deplacementDuJoueur(Labyrinthe labyrinthe, Labyrinthe labyInvisible,Personnage pers, String direction){

	// les deplacements si aucun mur sinon, le joueur perd une vie
	
	//droite
	if(direction.equalsIgnoreCase("d")){ deplacementDroite(labyrinthe, labyInvisible, pers);}
	
	//gauche
	else if(direction.equalsIgnoreCase("g") || direction.equalsIgnoreCase("s")){ deplacementGauche(labyrinthe, labyInvisible, pers);}

	//haut
	else if(direction.equalsIgnoreCase("h") || direction.equalsIgnoreCase("e")){ deplacementHaut(labyrinthe, labyInvisible, pers);}

	//bas
	else if(direction.equalsIgnoreCase("b") || direction.equalsIgnoreCase("x")){ deplacementBas(labyrinthe, labyInvisible,pers);}
}

//message affiche en dessous du labyrinthe
public static void msgEnDessousDuLab(Personnage pers, int nbVies){
	System.out.println("Il vous reste " + pers.getNbVies() + " vies sur " + nbVies);
	System.out.println();
	System.out.println("Quelle direction souhaitez-vous prendre?");
	System.out.println("(droite: d; gauche: g ou s; haut: h ou e; bas: b ou x)");

}

//deplacement a droite du personnage si autorise
public static void deplacementDroite(Labyrinthe labyrinthe, Labyrinthe labyInvisible,Personnage pers){
	if(labyrinthe.aMuretADroite(pers.getCoordY(), pers.getCoordX())){
		pers.setNbVies(pers.getNbVies() - 1);
		labyInvisible.dessineMuretVertical(pers.getCoordY(), pers.getCoordX() + 1);
	}else{
		labyInvisible.effacePersonnage(pers);	
		labyrinthe.effacePersonnage(pers);
		pers.moveDroit();
		labyInvisible.dessinePersonnage(pers);
		labyrinthe.dessinePersonnage(pers);
	}
}

//deplacement a gauche du personnage
public static void deplacementGauche(Labyrinthe labyrinthe, Labyrinthe labyInvisible,Personnage pers){
	if(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX())){
		pers.setNbVies(pers.getNbVies()-1);
		labyInvisible.dessineMuretVertical(pers.getCoordY(), pers.getCoordX());
	}else if(labyrinthe.aEntreeAGauche(pers.getCoordY(), pers.getCoordX())){
		//do nothing
	}else{
		labyInvisible.effacePersonnage(pers);
		labyrinthe.effacePersonnage(pers);
		pers.moveGauche();
		labyInvisible.dessinePersonnage(pers);
		labyrinthe.dessinePersonnage(pers);
	}
}

//deplacement en haut du personnage
public static void deplacementHaut(Labyrinthe labyrinthe, Labyrinthe labyInvisible,Personnage pers){
	if(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())){
		pers.setNbVies(pers.getNbVies() - 1);
		labyInvisible.dessineMuretHorizontal(pers.getCoordY(), pers.getCoordX());
	}else{
		labyInvisible.effacePersonnage(pers);
		labyrinthe.effacePersonnage(pers);
		pers.moveHaut();
		labyInvisible.dessinePersonnage(pers);
		labyrinthe.dessinePersonnage(pers);
	}
}

//deplacement en bas du personnage
public static void deplacementBas(Labyrinthe labyrinthe, Labyrinthe labyInvisible,Personnage pers){
	if(labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX())){
		pers.setNbVies(pers.getNbVies() - 1);
		labyInvisible.dessineMuretHorizontal(pers.getCoordY() + 1, pers.getCoordX());
	}else{
		labyInvisible.effacePersonnage(pers);
		labyrinthe.effacePersonnage(pers);
		pers.moveBas();
		labyInvisible.dessinePersonnage(pers);
		labyrinthe.dessinePersonnage(pers);
	}
}


//reaffiche le labyrinthe apres les changements
public static void affichageActualiseeDuLab(boolean visible, Labyrinthe labyrinthe, Labyrinthe labyInvisible){
	Labyrinthe.effaceEcran();

	//affiche le labyrinthe visible ou invisible
	if(visible == true){labyrinthe.affiche();}
	else{labyInvisible.affiche();}
}

//message de victoire 
public static void msgVictoire(boolean ia, Personnage pers, int nbVies){
	if(ia==false){ 
		if(pers.getNbVies() == nbVies){
			System.out.println("Bravo, vous êtes parvenu jusqu'à la sortie en commettant aucune erreur!");
		}else{
			System.out.println("Bravo, vous êtes parvenu jusqu'à la sortie en commettant seulement "+(nbVies - pers.getNbVies())+ " erreurs.");
		}
	}else{
		if(pers.getNbVies() == nbVies){
			System.out.println("L'IA est sorti du labyrinthe en commettant aucune erreurs!");
		}else{
			System.out.println("L'IA est sorti du labyrinthe en commettant seulement "+(nbVies - pers.getNbVies())+ " erreurs.");	
		}
	}
}

public static void demandePourNouvellePartie(int i, int j, double densite, long secondes, int nbVies, Scanner scan){
	System.out.println("\nVoulez-vous jouer une nouvelle partie?");
	
	String nouveauJeu = scan.nextLine();			

	if(nouveauJeu.equalsIgnoreCase("p") || nouveauJeu.equalsIgnoreCase("oui")){
		nouvellePartie(i, j, densite, secondes, nbVies);	
	}else if(nouveauJeu.equalsIgnoreCase("q") || nouveauJeu.equalsIgnoreCase("non")){
		System.exit(0);
	}
	else{
		System.out.println("\n\n Taper: 'oui' ou 'non'\n");
		demandePourNouvellePartie(i, j, densite, secondes, nbVies, scan);
	}
}

//Commence une nouvelle partie
public static void nouvellePartie(int i, int j, double densite, long secondes, int nbVies){
	Labyrinthe.effaceEcran();	 
	jeuxLabyrintheInvisible(i,j,densite,secondes,nbVies);
	System.exit(0); 
}

//deplacement du personnage quand l'ia joue
public static void deplacementIA(Labyrinthe labyrinthe, Personnage pers, char direction){
	Labyrinthe.effaceEcran();  
	labyrinthe.effacePersonnage(pers);
	movePersonnageParIA(direction, pers);
	labyrinthe.dessinePersonnage(pers);
	labyrinthe.affiche();
	sleep(1000);
}

//selection de la direction du mouvement du personnage par IA
public static void movePersonnageParIA(char direction, Personnage pers){
	if(direction == 'd'){pers.moveDroit();}
	else if (direction == 'g'){pers.moveGauche();}
	else if (direction == 'h'){pers.moveHaut();}
	else if (direction == 'b'){pers.moveBas();}
}



//la strategie de l'IA est de se mettre sur la ligne ou se trouve l'ouverture et se deplacer vers la droite si possible sinon
// il contourne l'obstacle en allant vers le haut ou le bas jusqu'a trouver une ouverture vers la droite. S'il se bloque par un mur
//apres tous les mouvements vers la droite, le haut ou le bas, il va vers la gauche jusqu'a avoir une ouverture vers le bas ou le haut
//et il retourne sur la ligne ou il y a l'ouverture et se deplace vers la droite.	

//algorithme de l'ia
public static void ia(Labyrinthe labyrinthe, Personnage pers, int coordYouverture){

	if(pers.getCoordY() == coordYouverture){
		
		if(!labyrinthe.aMuretADroite(pers.getCoordY(), pers.getCoordX())){//Aucun muret a droite
			deplacementIA(labyrinthe, pers, 'd');
		}else{ 
			if(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX()) == false){//deplacement vers le haut pour trouver une ouverture vers la droite.
				A: while(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX()) == false){ 
					deplacementIA(labyrinthe, pers, 'h');

					if(!labyrinthe.aMuretADroite(pers.getCoordY(), pers.getCoordX())){
						deplacementIA(labyrinthe, pers, 'd');  
						break A;
					}

					if((labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())==true)&&(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==true)){
						B:while(labyrinthe.aMuretEnHaut(pers.getCoordY() + 1, pers.getCoordX()) == false){
							deplacementIA(labyrinthe, pers, 'b');
							if(!labyrinthe.aMuretADroite(pers.getCoordY(), pers.getCoordX())){
								deplacementIA(labyrinthe, pers, 'd');
								break B;
							}
						}
					}
				}
			}else if(!labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX())){//deplacement vers le bas pour trouver une sortie vers la droite
				C: while(!labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX())){
					deplacementIA(labyrinthe, pers, 'b');
					if(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==false){
						deplacementIA(labyrinthe, pers, 'd');
						break C;
					}
					if((labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX()))&&(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==true)){
						D:while(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())==false){
							deplacementIA(labyrinthe, pers, 'h');
							if(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==false){
								deplacementIA(labyrinthe, pers, 'd');
								break D;
							}
						}
					}
				}
			}else if(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX())==false){
				//Il va vers la gauche jusqu'a trouver une ouverture vers le haut ou le bas et quand il la trouve il va a droite	 
				while(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX())==false){
					deplacementIA(labyrinthe, pers, 'g');

					if(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())==false){
						deplacementIA(labyrinthe, pers, 'h');   
						if((labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==true)&&(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())==false)){   
							deplacementIA(labyrinthe, pers, 'h'); 
						}
					}else if(!labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX())){
						deplacementIA(labyrinthe, pers, 'b');	
						if((labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==true)&&(!labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX()))){   
							deplacementIA(labyrinthe, pers, 'b');
						}
					}

					if(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==false){
						deplacementIA(labyrinthe, pers, 'd');
					}
				}
			}
		}

	}else{//quand le personnage n'est pas sur la ligne ou se trouve l'ouverture

		while(pers.getCoordY()!= coordYouverture){
			/*tant qu'il n'est pas sur la ligne d'ouverture il monte ou descend en fonction de sa position par
			 * rapport a la ligne et si il trouve un obstacle il le contourne*/

			if((pers.getCoordY()<coordYouverture)&&(!labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX()))){
				deplacementIA(labyrinthe, pers, 'b');

			}
			else if((pers.getCoordY()>coordYouverture)&&(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())==false)){
				deplacementIA(labyrinthe, pers, 'h');

			}else if(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==false){
				deplacementIA(labyrinthe, pers, 'd');
			}else if(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())==false){
				E:while(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())==false){
					deplacementIA(labyrinthe, pers, 'h');
					if(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==false){
						deplacementIA(labyrinthe, pers, 'd');
						break E;
					}}	        		     
			}else if(!labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX())){
				F:while(!labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX())){
					deplacementIA(labyrinthe, pers, 'b');
					if(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX()+1)==false){
						deplacementIA(labyrinthe, pers, 'd');
						break F;
					}
				}
			}else if((pers.getCoordY()<coordYouverture)&&(labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX()))&&(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX())==false)){
				while((labyrinthe.aMuretEnBas(pers.getCoordY(), pers.getCoordX()))&&(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX())==false)){
					deplacementIA(labyrinthe, pers, 'g');
					}	

				deplacementIA(labyrinthe, pers, 'b');


			}else if((pers.getCoordY()>coordYouverture)&&(labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())==true)&&(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX())==false)){
				while((labyrinthe.aMuretEnHaut(pers.getCoordY(), pers.getCoordX())==true)&&(labyrinthe.aMuretAGauche(pers.getCoordY(), pers.getCoordX())==false)){
					deplacementIA(labyrinthe, pers, 'g');	
				}

				deplacementIA(labyrinthe, pers, 'h');
			}
		}

	} 
}




//affiche le labyrinthe complet pour le nombre de millisecondes désiré
public static void sleep(long millisecondes) {
	try {
		Thread.sleep(millisecondes);
	} catch (InterruptedException e) {
		System.out.println("Sleep interrompu");
		}
	}

}