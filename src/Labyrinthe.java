import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LabyrintheGUI extends JFrame {

    int x, y;
    int niveau = 0;

    char[][][] niveaux = {

        {{'#','#','#','#','#','#'}, {'#','P','.','.','S','#'}, {'#','#','#','#','#','#'}},

        {{'#','#','#','#','#','#','#'}, {'#','P','.','.','.','S','#'}, {'#','#','#','#','#','#','#'}},

        {{'#','#','#','#','#'}, {'#','P','.','#','#'}, {'#','#','.','S','#'}, {'#','#','#','#','#'}},

        {{'#','#','#','#','#','#'}, {'#','P','.','.','#','#'}, {'#','#','#','.','.','#'}, {'#','#','#','#','S','#'}},

        {{'#','#','#','#','#','#','#'}, {'#','P','.','#','.','.','#'}, {'#','.','.','#','.','#','#'}, {'#','#','.','.','.','S','#'}},

        {{'#','#','#','#','#','#','#','#'}, {'#','P','.','.','#','.','.','#'}, {'#','#','#','.','#','.','#','#'}, {'#','.','.','.','.','.','S','#'}},

        {{'#','#','#','#','#','#','#','#'}, {'#','P','.','.','.','.','.','#'}, {'#','.','.','#','#','#','.','#'}, {'#','.','#','.','.','.','S','#'}},

        {{'#','#','#','#','#','#','#','#','#'}, {'#','P','.','.','.','#','.','.','#'}, {'#','#','#','#','.','#','.','#','#'}, {'#','.','.','.','.','.','.','S','#'}},

        {{'#','#','#','#','#','#','#','#','#','#'}, {'#','P','.','.','#','.','.','.','.','#'}, {'#','#','#','.','#','#','#','.','#','#'}, {'#','.','.','.','.','.','.','.','S','#'}, {'#','#','#','#','#','#','#','#','#','#'}}
    };

    char[][] grille;

    JTextArea zoneCode;
    JPanel panel;

    public LabyrintheGUI() {

        setTitle("Labyrinthe");
        setSize(600,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        zoneCode = new JTextArea();
        add(zoneCode, BorderLayout.SOUTH);

        JButton bouton = new JButton("Executer");
        add(bouton, BorderLayout.NORTH);

        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dessiner(g);
            }
        };

        add(panel, BorderLayout.CENTER);

        chargerNiveau();

        bouton.addActionListener(e -> executer());

        // 🔥 Key Bindings (meilleure méthode)
        setupKeyBindings();

        setVisible(true);
    }

    void setupKeyBindings() {

        InputMap im = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = panel.getActionMap();

        im.put(KeyStroke.getKeyStroke("UP"), "haut");
        im.put(KeyStroke.getKeyStroke("DOWN"), "bas");
        im.put(KeyStroke.getKeyStroke("LEFT"), "gauche");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "droite");

        am.put("haut", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jouer("HAUT");
            }
        });

        am.put("bas", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jouer("BAS");
            }
        });

        am.put("gauche", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jouer("GAUCHE");
            }
        });

        am.put("droite", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jouer("DROITE");
            }
        });
    }

    void jouer(String direction) {
        deplacer(direction);
        panel.repaint();

        if (grille[x][y] == 'S') {
            JOptionPane.showMessageDialog(this, "Niveau réussi !");
            niveau++;

            if (niveau == niveaux.length) {
                JOptionPane.showMessageDialog(this, "Bravo tu as fini !");
                return;
            }

            chargerNiveau();
            panel.repaint();
        }
    }

    void chargerNiveau() {
        grille = copier(niveaux[niveau]);
        trouverDepart();
    }

    void dessiner(Graphics g) {

        int taille = 40;

        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {

                if (grille[i][j] == '#') {
                    g.setColor(Color.PINK);
                }
                else if (grille[i][j] == 'S') {
                    g.setColor(Color.GREEN);
                }
                else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(j * taille, i * taille, taille, taille);

                g.setColor(Color.BLACK);
                g.drawRect(j * taille, i * taille, taille, taille);
            }
        }

        g.setColor(Color.MAGENTA);
        g.fillOval(y * taille + 10, x * taille + 10, 20, 20);
    }

    void executer() {

        String[] lignes = zoneCode.getText().toUpperCase().split("\\n");

        new Thread(() -> {

            for (String ligne : lignes) {

                deplacer(ligne.trim());
                panel.repaint();

                if (grille[x][y] == 'S') {
                    JOptionPane.showMessageDialog(this, "Niveau réussi !");
                    niveau++;

                    if (niveau == niveaux.length) {
                        JOptionPane.showMessageDialog(this, "Bravo tu as fini !");
                        return;
                    }

                    chargerNiveau();
                    panel.repaint();
                    return;
                }

                try { Thread.sleep(300); } catch(Exception e){}
            }

        }).start();
    }

    void deplacer(String dir) {

        int nx = x;
        int ny = y;

        if (dir.equals("HAUT")) nx--;
        if (dir.equals("BAS")) nx++;
        if (dir.equals("GAUCHE")) ny--;
        if (dir.equals("DROITE")) ny++;

        if (nx >= 0 && nx < grille.length && ny >= 0 && ny < grille[0].length) {
            if (grille[nx][ny] != '#') {
                x = nx;
                y = ny;
            }
        }
    }

    void trouverDepart() {

        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {

                if (grille[i][j] == 'P') {
                    x = i;
                    y = j;
                }
            }
        }
    }

    char[][] copier(char[][] src) {

        char[][] dest = new char[src.length][src[0].length];

        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[i].length; j++) {
                dest[i][j] = src[i][j];
            }
        }

        return dest;
    }

    public static void main(String[] args) {
        new LabyrintheGUI();
    }
}
