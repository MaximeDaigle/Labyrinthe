//Fait par Maxime Daigle

public class Labyrinthe {
	
	//represente le dessin du labyrinthe
	private char[][] tabLab; 
	
	//Hauteur des murets
	private static final int LMURET = 8;
	
	//Largeur des murets
	private static int HMURET = 4;
	
	//hauteur du labyrinthe
	private int hauteur;
	
	//largeur du labyrinthe
	private int largeur;
	
	//Constructeur
	public Labyrinthe(int h, int w){
		tabLab = new char[1+h*HMURET][1+w*LMURET]; 
		hauteur = h;
		largeur = w;
	}

	//Constructeur
	//recopie le labyrinthe pris en paramètre dans celui qui est créé
	public Labyrinthe(Labyrinthe l){
		this.tabLab = new char[l.tabLab.length][l.tabLab[0].length]; 
		System.arraycopy(l.tabLab, 0, this.tabLab, 0, l.tabLab.length);
		this.hauteur = l.getHauteur();
		this.largeur = l.getLargeur();
	}
	
	// return la hauteur du labyrinthe
	public int getHauteur(){
		return hauteur;
	}
	
	// return la largeur du labyrinthe
	public int getLargeur(){
		return largeur;
	}
	
	// creation du tableau et le remplit avec de caracteres d'espace
	public char [][] creeTableau(int hauteur, int largeur){
	char tabLaby [] []= new char[hauteur*HMURET+1][largeur*HMURET+1];
		for(int i=0; i<hauteur*HMURET+1; i++){
			for(int j=0; j<hauteur*LMURET+1; j++){
				tabLab[i][j] = ' ';
			}
		}
		return tabLaby;
	}
	
	// remplit le tableau de caracteres d'espace
	public char[] [] effaceTableau(char [] [] tabLaby){
		for( int i=0; i<tabLaby.length; i++){
			for(int j=0; j<tabLaby[0].length; j++){
				tabLaby[i][j]=' ';
			}
		}
		return tabLaby;
	}
	
	
	//dessine un mur d'enceinte completement fermé
	public void dessineMurdEnceinte(){
		
		//fait les coins
		dessineCoins();
		
		//fait les murs du contour
		dessineMurDEnceinte();
	}
	
	//dessines les coins du tableau
	public void dessineCoins(){
		tabLab[0][0] = '+';
		tabLab[0][tabLab[0].length-1] = '+';
		tabLab[tabLab.length-1][0] = '+';
		tabLab[tabLab.length-1][tabLab[0].length-1] = '+';
	}
	
	//dessines les murs de l'enceintes
	public void dessineMurDEnceinte(){
		
		//fait le mur superieur et celui inferieur de l'enceinte
		for(int i = 1; i < tabLab[0].length - 1; i++){
			tabLab[0][i] = '-';
			tabLab[tabLab.length-1][i] = '-';
		}

		//fait le mur de gauche et celui de droite de l'enceinte
		for(int i = 1; i < tabLab.length - 1; i++){
			tabLab[i][0] = '|';
			tabLab[i][tabLab[0].length-1] = '|';
		}
	}
	
	//dessine l'ouverture de droite
	//j: position verticale en nombre de cases l'ouverture de droite
	public void dessineOuverture(int j){
		for(int i = j*HMURET+1; i <j*HMURET+HMURET; i++){
			tabLab[i][tabLab[0].length-1] = ' ';
		}
	}
	
	//dessine l'ouverture de gauche
	public void dessineDepart(int j){
		for(int i = 1; i < HMURET; i++){
			tabLab[i+j*HMURET][0] = ' ';
		}
	}
	
	//dessine muret vertical sur le bord gauche de la case (i,j)
	//i: position verticale en nombre de case de la case
	//j: position horizontale en nombre de case de la case 
	public void dessineMuretVertical(int i, int j){
		for(int k = i*HMURET+1; k <i*HMURET+HMURET; k++){
			tabLab[k][j*LMURET] = '|';
		}
	}
	
	//dessine muret horizontal sur le bord haut de la case (i,j)
	//i: position verticale en nombre de case de la case
	//j: position horizontale en nombre de case de la case 
	public void dessineMuretHorizontal(int i, int j){
		for(int k = j*LMURET+1; k <j*LMURET+LMURET; k++){
			tabLab[i*HMURET][k] = '-';
		}
	}
	
	//Retourne true s'il y a un mur/muret sur le bord gauche de la case (i,j)
	//i: position verticale en nombre de case de la case
	//j: position horizontale en nombre de case de la case 
	public boolean aMuretAGauche(int i, int j){
		if(tabLab[i*HMURET + 1][j*LMURET] == '|') return true;
		else return false;
	}
	
	//Retourne true s'il y a un mur/muret sur le bord droit de la case (i,j)
	//i: position verticale en nombre de case de la case
	//j: position horizontale en nombre de case de la case 
	public boolean aMuretADroite(int i, int j){
		return aMuretAGauche(i,j+1);
	}
	
	//Retourne true s'il y a un mur/muret sur le bord en haut de la case (i,j)
	//i: position verticale en nombre de case de la case
	//j: position horizontale en nombre de case de la case 
	public boolean aMuretEnHaut(int i, int j){
		if(tabLab[i*HMURET][j*LMURET + 1] == '-') return true;
		else return false;
	}
	
	//Retourne true s'il y a un mur/muret sur le bord en bas de la case (i,j)
	//i: position verticale en nombre de case de la case
	//j: position horizontale en nombre de case de la case 
	public boolean aMuretEnBas(int i, int j){
		return aMuretEnHaut(i+1,j);
	}
	
	//Retourne true si c'est l'entree sur le bord gauche de la case (i,j)
	//i: position verticale en nombre de case de la case
	//j: position horizontale en nombre de case de la case 
	public boolean aEntreeAGauche(int i, int j){
		if((j == 0) && !aMuretAGauche(i,j)){
			return true;
		}
		else return false;
	}
	
	//Retourne true si c'est la sortie sur le bord droit de la case (i,j)
	//i: position verticale en nombre de case de la case
	//j: position horizontale en nombre de case de la case 
	public boolean aSortieADroite(int i, int j){
		if(((j+1)*LMURET == tabLab[0].length-1) && !aMuretADroite(i,j)){
			return true;
		}
		else return false;
	}
	
	//dessine le personnage (representé par @) au centre de la case correspondant à sa position
	public void dessinePersonnage(Personnage p){
		tabLab[HMURET/2+p.getCoordY()*HMURET][LMURET/2 + p.getCoordX()*LMURET] = '@';
	}
	
	//efface le personnage
	public void effacePersonnage(Personnage p){
		tabLab[HMURET/2+p.getCoordY()*HMURET][LMURET/2+ p.getCoordX()*LMURET] = ' ';
	}
	
	//efface l'ecran
	public static void effaceEcran(){
		for(int i = 0; i < 200; i++){
			System.out.println();
		}
	}
	
	//affiche le tableau de caracteres à l'écran
	public void affiche(){
		
		String str = "";
		for(int i=0; i<tabLab.length; i++){
			for(int j=0; j<tabLab[0].length; j++){
				str+=tabLab[i][j];
			}
			str+="\n";
		}
		System.out.println(str);
	}
	
	//Construit des murets aleatoirement selon la densité et crée l'ouverture de la sortie
	//densite: densité d'apparition des murets entre 0 et 1. 1 implique tous les murets sont présents et 0 implique aucun muret
	public void construitLabyrintheAleatoire(double densite){
		for(int i = 0; 1 + i*HMURET < tabLab.length; i++){
			for(int j=0; 1 + j*LMURET < tabLab[0].length; j++){
				if(densite >= Math.random()) dessineMuretVertical(i,j);
				if(densite >= Math.random()) dessineMuretHorizontal(i,j);
			}
		}
		dessineOuverture( (int)((tabLab.length / HMURET)*Math.random()));   // (tabLab.length/HMURET) donne le nombre de case verticale
	}
	
	// indice de l'ouverture de labyrinthe
	public int getMurOuverture(){
		int j=0;
		for(int i=0; i<hauteur; i++){
			if(tabLab[i*HMURET+1][tabLab[0].length-1] == ' '){
				j=i;
			}
		}
		return j;
	}
}