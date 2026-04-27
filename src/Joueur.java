/**
 * C'est ici que vous pouvez modifier votre chemin, pour vous déplacer dans le labyrinthe.
 */
public class Joueur {

    /**
     * Méthode à implémenter pour déplacer le personnage.
     * @param x Position X actuelle
     * @param y Position Y actuelle
     * @param grille Vision du labyrinthe
     * @return "HAUT", "BAS", "GAUCHE" ou "DROITE"
     */
    public String deplacer(int x, int y, char[][] grille) {
        // --- À VOUS DE JOUER ---
        if (y < 5) {
            return "DROITE";
        } else {
            return "BAS";
        }
    }
}
