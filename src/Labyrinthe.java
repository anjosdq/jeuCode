import java.util.Scanner;

/**
 * Classe principale gérant la logique du labyrinthe et l'exécution du code joueur.
 * Ce labyrinhte permet de choisir entre 3 niveaux de difficulté.
 */
public class Labyrinthe 
{
    // Variables de position du joueur
    private static int playerX;
    private static int playerY;
    private static char[][] grilleActuelle;

    // Grille simple : # = mur, . = chemin, S = sortie, P = personnage 
    private static char[][][] niveaux = {
        { // Niveau 1 : L'échauffement
            {'#', '#', '#', '#', '#', '#'},
            {'#', 'P', '.', '.', 'S', '#'},
            {'#', '#', '#', '#', '#', '#'}
        },
        { // Niveau 2 : L'intermédiaire
            {'#', '#', '#', '#', '#'},
            {'#', 'P', '.', '#', '#'},
            {'#', '#', '.', 'S', '#'},
            {'#', '#', '#', '#', '#'}
        },
        { // Niveau 3 : Le boss final
            {'#', '#', '#', '#', '#', '#', '#'},
            {'#', 'P', '.', '.', '.', '#', '#'},
            {'#', '#', '#', '#', '.', '.', '#'},
            {'#', 'S', '.', '.', '.', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#'}
        }
    };

    public static void main(String[] args) 
    {
        int choixNiveau = 0; 
        
        // Initialisation de la grille pour le niveau choisi
        grilleActuelle = copierGrille(niveaux[choixNiveau]);
        trouverPositionDepart();

        Joueur monJoueur = new Joueur();
        boolean gagne = false;
        int tours = 0;

        System.out.println("--- Début de la simulation : Niveau " + (choixNiveau + 1) + " ---");

        // Boucle de jeu 
        while (!gagne && tours < 30) 
        {
            afficherGrille();
            
            String direction = monJoueur.deplacer(playerX, playerY, grilleActuelle);
            appliquerMouvement(direction);

            // Vérification de la victoire (Case 'S')
            if (grilleActuelle[playerX][playerY] == 'S') 
            {
                gagne = true;
                afficherGrille();
                System.out.println("\n✨ Félicitations ! Sortie trouvée en " + tours + " tours.");
            }
            tours++;
            
            // Petite pause pour l'affichage (optionnel)
            try { Thread.sleep(300); } catch (InterruptedException e) {}
        }

        if (!gagne) 
        {
            System.out.println("\n Dommage ! Trop de tours ou personnage bloqué.");
        }
    }

    /**
     * Affiche la grille dans la console avec le joueur 'P'
     */
    private static void afficherGrille() 
    {
        System.out.println("\nTour : " + playerX + "," + playerY);
        for (int i = 0; i < grilleActuelle.length; i++) 
        {
            for (int j = 0; j < grilleActuelle[i].length; j++) 
            {
                if (i == playerX && j == playerY && grilleActuelle[i][j] != 'S') 
                {
                    System.out.print('P');
                } else {
                    System.out.print(grilleActuelle[i][j]);
                }
            }
            System.out.println();
        }
    }

    /**
     * Gère le déplacement et vérifie les collisions avec les murs '#'
     */
    private static void appliquerMouvement(String dir) 
    {
        int nextX = playerX;
        int nextY = playerY;

        switch (dir.toUpperCase()) {
            case "HAUT": nextX--; break;
            case "BAS": nextX++; break;
            case "GAUCHE": nextY--; break;
            case "DROITE": nextY++; break;
        }

        // Vérification des limites et des murs
        if (nextX >= 0 && nextX < grilleActuelle.length && 
            nextY >= 0 && nextY < grilleActuelle[0].length &&
            grilleActuelle[nextX][nextY] != '#') {
            playerX = nextX;
            playerY = nextY;
        } else {
            System.out.println("(!) Obstacle ou mur en direction : " + dir);
        }
    }

    /**
     * Cherche la position initiale du caractère 'P' dans la grille
     */
    private static void trouverPositionDepart() 
    {
        for (int i = 0; i < grilleActuelle.length; i++) 
        {
            for (int j = 0; j < grilleActuelle[i].length; j++) 
            {
                if (grilleActuelle[i][j] == 'P') 
                {
                    playerX = i;
                    playerY = j;
                    return;
                }
            }
        }
    }

    /**
     * Copie la grille pour ne pas modifier l'originale
     */
    private static char[][] copierGrille(char[][] source) 
    {
        char[][] destination = new char[source.length][source[0].length];
        for (int i = 0; i < source.length; i++) 
        {
            System.arraycopy(source[i], 0, destination[i], 0, source[i].length);
        }
        return destination;
    }
}
