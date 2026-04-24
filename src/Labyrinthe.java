import java.util.Scanner;

/**
 * Classe principale gérant la logique du labyrinthe et l'exécution du code joueur.
 */
public class Labyrinthe 
{
    // Grille simple : # = mur, . = chemin, S = sortie, P = personnage
    private static char[][] labyrinthe = {
        {'#', '#', '#', '#', '#', '#', '#'},
        {'#', 'P', '.', '.', '.', '.', '#'},
        {'#', '#', '#', '#', '.', '#', '#'},
        {'#', '.', '.', '.', '.', 'S', '#'},
        {'#', '#', '#', '#', '#', '#', '#'}
    };

    private static int playerX = 1;
    private static int playerY = 1;

    public static void main(String[] args) 
    {
        Joueur monJoueur = new Joueur();
        boolean gagne = false;
        int tours = 0;

        System.out.println("--- Début de la simulation du Labyrinthe ---");

        while (!gagne && tours < 20) 
        {
            afficherGrille();
            
            // Appel de la méthode codée par l'utilisateur
            String direction = monJoueur.deplacer(playerX, playerY, labyrinthe);
            appliquerMouvement(direction);

            if (labyrinthe[playerX][playerY] == 'S') 
            {
                gagne = true;
                System.out.println("Félicitations ! Sortie trouvée en " + tours + " tours.");
            }
            tours++;
        }

        if (!gagne) 
        {
            System.out.println("Dommage ! Trop de tours ou personnage bloqué.");
        }
    }

    private static void afficherGrille() 
    {
        for (int i = 0; i < labyrinthe.length; i++) 
        {
            for (int j = 0; j < labyrinthe[i].length; j++) 
            {
                if (i == playerX && j == playerY && labyrinthe[i][j] != 'S') 
                {
                    System.out.print('P');
                } 
                else 
                {
                    System.out.print(labyrinthe[i][j]);
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

        if (labyrinthe[nextX][nextY] != '#') 
        {
            playerX = nextX;
            playerY = nextY;
        } 
        else 
        {
            System.out.println("Obstacle rencontré !");
        }
    }
}
