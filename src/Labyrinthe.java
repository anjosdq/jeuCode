import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Labyrinthe extends JFrame {

    int x;
    int y;
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

    public Labyrinthe() {

        this.setTitle("Labyrinthe");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.zoneCode = new JTextArea();
        this.add(this.zoneCode, BorderLayout.SOUTH);

        JButton bouton = new JButton("Executer");
        this.add(bouton, BorderLayout.NORTH);

        this.panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dessiner(g);
            }
        };

        this.add(this.panel, BorderLayout.CENTER);

        this.chargerNiveau();

        bouton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                executer();
            }
        });

        this.setupKeyBindings();

        this.setVisible(true);
    }

    void setupKeyBindings() {

        InputMap im = this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.panel.getActionMap();

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

        this.deplacer(direction);
        this.panel.repaint();

        if (this.grille[this.x][this.y] == 'S') {

            JOptionPane.showMessageDialog(this, "Niveau réussi !");
            this.niveau = this.niveau + 1;

            if (this.niveau == this.niveaux.length) {
                JOptionPane.showMessageDialog(this, "Bravo tu as fini !");
                return;
            }

            this.chargerNiveau();
            this.panel.repaint();
        }
    }

    void chargerNiveau() {
        this.grille = this.copier(this.niveaux[this.niveau]);
        this.trouverDepart();
    }

    void dessiner(Graphics g) {

        int taille = 40;

        for (int i = 0; i < this.grille.length; i++) {
            for (int j = 0; j < this.grille[i].length; j++) {

                if (this.grille[i][j] == '#') {
                    g.setColor(Color.PINK);
                }
                else if (this.grille[i][j] == 'S') {
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
        g.fillOval(this.y * taille + 10, this.x * taille + 10, 20, 20);
    }

    void executer() {

        String texte = this.zoneCode.getText().toUpperCase();
        String[] lignes = texte.split("\\n");

        Thread t = new Thread(new Runnable() {
            public void run() {

                for (int i = 0; i < lignes.length; i++) {

                    String ligne = lignes[i].trim();

                    deplacer(ligne);
                    panel.repaint();

                    if (grille[x][y] == 'S') {

                        JOptionPane.showMessageDialog(null, "Niveau réussi !");
                        niveau++;

                        if (niveau == niveaux.length) {
                            JOptionPane.showMessageDialog(null, "Bravo tu as fini !");
                            return;
                        }

                        chargerNiveau();
                        panel.repaint();
                        return;
                    }

                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();
    }

    void deplacer(String dir) {

        int nx = this.x;
        int ny = this.y;

        if (dir.equals("HAUT")) {
            nx = nx - 1;
        }
        if (dir.equals("BAS")) {
            nx = nx + 1;
        }
        if (dir.equals("GAUCHE")) {
            ny = ny - 1;
        }
        if (dir.equals("DROITE")) {
            ny = ny + 1;
        }

        if (nx >= 0 && nx < this.grille.length && ny >= 0 && ny < this.grille[0].length) {

            if (this.grille[nx][ny] != '#') {
                this.x = nx;
                this.y = ny;
            }
        }
    }

    void trouverDepart() {

        for (int i = 0; i < this.grille.length; i++) {
            for (int j = 0; j < this.grille[i].length; j++) {

                if (this.grille[i][j] == 'P') {
                    this.x = i;
                    this.y = j;
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
        new Labyrinthe();
    }
}
