//Fait par Maxime Daigle

public class Personnage {
	
	//coordonee en nombre de case sur la hauteur
	private int coordY;
	
	//coordonnee en nombre de case sur la largeur
	private int coordX;
	
	//nombre de vies restantes
	private int nbVies;
	
	//Nombre de vies du personnages au depart
	private int nbViesMax;
	
	//labyrinthe dans lequel le personnage se trouve
	private Labyrinthe labDuPersonnage;
	
	public int getCoordY(){
		return coordY;
	}
	
	public int getCoordX(){
		return coordX;
	}
	
	public int getNbVies(){
		return nbVies;
	}
	
	public void setNbVies(int vies){
		nbVies = vies;
		nbViesMax = vies;
		
	}
	
	//Constructeur
	public Personnage(Labyrinthe l, int i, int j){
		coordY = i;
		coordX = j;
		labDuPersonnage = l;
	}
	
	//Constructeur
	public Personnage(Labyrinthe l, int i, int j, int viePers){
		coordY = i;
		coordX = j;
		labDuPersonnage = l;
		nbVies = viePers;
	}
	
	
	
	//Deplace le personnage d'une case vers la droite
	
	public void moveDroit(){
		this.coordX = this.coordX + 1;
		
	}
	
	//Deplace le personnage vers la gauche
	public void moveGauche(){
		this.coordX = this.coordX - 1;
		
	}
	
	public void moveBas(){
		this.coordY = this.coordY + 1;
		
	}
	
	public void moveHaut(){
		this.coordY = this.coordY - 1;
		
	}

}
