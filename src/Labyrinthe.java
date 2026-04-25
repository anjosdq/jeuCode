import java.util.Scanner;

/**
 * Classe principale gérant la logique du labyrinthe et l'exécution du code joueur.
 * Ce labyrinthe enchaîne automatiquement 3 niveaux de difficulté.
 */
public class Labyrinthe
{  
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
        Joueur monJoueur = new Joueur();
        
        System.out.println("======= BIENVENUE DANS JEUCODE =======");

        // Boucle qui parcourt les niveaux un par un
        for (int i = 0; i < niveaux.length; i++) 
        {
            System.out.println("\n CHARGEMENT DU NIVEAU " + (i + 1) + "...");
            
            grilleActuelle = copierGrille(niveaux[i]);
            trouverPositionDepart();
            
            boolean niveauReussi = jouerNiveau(monJoueur);
            
            if (!niveauReussi) 
            {
                System.out.println("\n GAME OVER - Vous avez échoué au niveau " + (i + 1));
                return; // Arrête tout le programme si un niveau échoue
            }
            
            System.out.println("\n NIVEAU " + (i + 1) + " TERMINÉ !");
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
        }

        System.out.println("\n FÉLICITATIONS ! Vous avez terminé tous les niveaux !");
    }

    /**
     * Gère la boucle de jeu pour un niveau spécifique
     */
    private static boolean jouerNiveau(Joueur joueur) 
    {
        boolean gagne = false;
        int tours = 0;
        int maxTours = 30;

        while (!gagne && tours < maxTours) 
        {
            afficherGrille();
            
            String direction = joueur.deplacer(playerX, playerY, grilleActuelle);
            appliquerMouvement(direction);

            if (grilleActuelle[playerX][playerY] == 'S') 
            {
                gagne = true;
                afficherGrille();
            }
            tours++;
            try { Thread.sleep(400); } catch (InterruptedException e) {}
        }
        return gagne;
    }

    private static void afficherGrille() 
    {
        System.out.println("\nPosition : [" + playerX + "," + playerY + "]");
        for (int i = 0; i < grilleActuelle.length; i++) 
        {
            for (int j = 0; j < grilleActuelle[i].length; j++) 
            {
                if (i == playerX && j == playerY && grilleActuelle[i][j] != 'S') 
                {
                    System.out.print('P');
                } 
                else 
                {
                    System.out.print(grilleActuelle[i][j]);
                }
            }
            System.out.println();
        }
    }

    private static void appliquerMouvement(String dir) 
    {
        int nextX = playerX;
        int nextY = playerY;

        switch (dir.toUpperCase()) 
        {
            case "HAUT": nextX--; break;
            case "BAS": nextX++; break;
            case "GAUCHE": nextY--; break;
            case "DROITE": nextY++; break;
        }

        if (nextX >= 0 && nextX < grilleActuelle.length && 
            nextY >= 0 && nextY < grilleActuelle[0].length &&
            grilleActuelle[nextX][nextY] != '#') 
        {
            playerX = nextX;
            playerY = nextY;
        }
    }

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
