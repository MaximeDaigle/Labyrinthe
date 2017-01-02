# Labyrinthe

crée un labyrinthe de façon aléatoire dans un shell.

* args[0]: hauteur du labyrinthe en nombre de cases 
* args[1]: largeur du labyrinthe en nombre de cases
* args[2]: densité (entre 0 et 1) des murets
* args[3]: nombre de secondes d'affichage du labyrinthe complet avant qu'il devienne invisible
* args[4]: nombre de vies du personnage 

Utilisation: java Laby (hauteur) (largeur) (densite) (duree visible) (nb vies)

Ex: java Laby 10 20 0.20 10 5

deplacement:
* d: droite
* g: gauche
* b: bas
* h: haut

Option
* q: quitter le jeux
* p: nouvelle partie
* o: active une intelligence artificielle simple
* v: rend les murs invisibles
