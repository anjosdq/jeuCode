import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Labyrinthe extends JFrame {

	int x;
	int y;
	int niveau = 0;
	int score = 0;

	ArrayList<Point> chats = new ArrayList<>();

	char[][][] niveaux = {

		{
			{'#','#','#','#','#','#','#'},
			{'#','P','.','.','M','S','#'},
			{'#','.','#','.','.','.','#'},
			{'#','.','.','.','C','.','#'},
			{'#','#','#','#','#','#','#'}
		},

		{
			{'#','#','#','#','#','#','#','#'},
			{'#','P','.','.','.','#','M','#'},
			{'#','.','#','#','.','#','.','#'},
			{'#','.','.','#','.','.','.','#'},
			{'#','#','.','#','#','#','.','#'},
			{'#','.','.','.','.','#','.','#'},
			{'#','C','.','#','.','.','S','#'},
			{'#','#','#','#','#','#','#','#'}
		},

		{
			{'#','#','#','#','#','#','#','#','#'},
			{'#','P','.','#','.','.','.','M','#'},
			{'#','.','#','#','.','#','#','.','#'},
			{'#','.','.','.','.','#','.','.','#'},
			{'#','#','#','#','.','#','.','#','#'},
			{'#','.','.','.','.','.','.','#','#'},
			{'#','.','#','#','#','#','.','.','#'},
			{'#','C','.','.','.','.','.','S','#'},
			{'#','#','#','#','#','#','#','#','#'}
		},

		{
			{'#','#','#','#','#','#','#','#','#'},
			{'#','P','.','.','M','.','.','M','#'},
			{'#','.','#','#','#','#','.','#','#'},
			{'#','.','.','.','.','#','.','.','#'},
			{'#','#','#','#','.','#','#','.','#'},
			{'#','.','.','.','.','.','.','.','#'},
			{'#','.','#','#','#','#','#','.','#'},
			{'#','C','.','.','.','.','.','S','#'},
			{'#','#','#','#','#','#','#','#','#'}
		},

		{
			{'#','#','#','#','#','#','#','#','#','#'},
			{'#','P','.','.','.','#','.','.','M','#'},
			{'#','.','#','#','.','#','.','#','.','#'},
			{'#','.','.','#','.','.','.','#','.','#'},
			{'#','#','.','#','#','#','.','#','.','#'},
			{'#','.','.','.','.','#','.','.','.','#'},
			{'#','.','#','#','.','#','#','#','.','#'},
			{'#','C','.','#','.','.','.','#','C','#'},
			{'#','.','.','.','.','.','.','.','S','#'},
			{'#','#','#','#','#','#','#','#','#','#'}
		}
	};

	char[][] grille;

	JTextArea zoneCode;
	JPanel panel;

	public Labyrinthe() {

		this.setTitle("Labyrinthe Chat & Souris");
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

		if (Math.random() < 0.5) {
			this.deplacerChats();
		}

		this.panel.repaint();

		// collision avec un chat
		for (Point chat : this.chats) {
			if (this.x == chat.x && this.y == chat.y) {
				JOptionPane.showMessageDialog(this, "Le chat t'a attrapé !");
				this.niveau = 0;
				this.score = 0;
				this.chargerNiveau();
				return;
			}
		}

		// victoire
		if (this.grille[this.x][this.y] == 'S') {

			JOptionPane.showMessageDialog(this, "Niveau réussi !");
			this.niveau++;

			if (this.niveau == this.niveaux.length) {
				JOptionPane.showMessageDialog(this, "Bravo ! Score = " + this.score);
				return;
			}

			this.chargerNiveau();
		}
	}

	void chargerNiveau() {
		this.grille = this.copier(this.niveaux[this.niveau]);
		this.trouverPositions();
	}

	void dessiner(Graphics g) {

		int largeur = this.panel.getWidth();
		int hauteur = this.panel.getHeight();

		int nbColonnes = this.grille[0].length;
		int nbLignes = this.grille.length;

		int taille = Math.min(largeur / nbColonnes, hauteur / nbLignes);

		int offsetX = (largeur - taille * nbColonnes) / 2;
		int offsetY = (hauteur - taille * nbLignes) / 2;

		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.grille[i].length; j++) {

				if (this.grille[i][j] == '#') g.setColor(Color.PINK);
				else if (this.grille[i][j] == 'S') g.setColor(Color.GREEN);
				else if (this.grille[i][j] == 'M') g.setColor(Color.ORANGE);
				else g.setColor(Color.WHITE);

				g.fillRect(offsetX + j * taille, offsetY + i * taille, taille, taille);
				g.setColor(Color.BLACK);
				g.drawRect(offsetX + j * taille, offsetY + i * taille, taille, taille);
			}
		}

		// joueur
		g.setColor(Color.MAGENTA);
		g.fillOval(offsetX + this.y * taille + taille/4,
				   offsetY + this.x * taille + taille/4,
				   taille/2, taille/2);

		// chats
		g.setColor(Color.RED);
		for (Point chat : this.chats) {
			g.fillOval(offsetX + chat.y * taille + taille/4,
					   offsetY + chat.x * taille + taille/4,
					   taille/2, taille/2);
		}

		g.setColor(Color.BLACK);
		g.drawString("Score : " + this.score, 10, 20);
	}

	void executer() {

		String[] lignes = this.zoneCode.getText().toUpperCase().split("\\n");

		Thread t = new Thread(new Runnable() {
			public void run() {

				for (int i = 0; i < lignes.length; i++) {

					String ligne = lignes[i].trim();

					deplacer(ligne);
					deplacerChats();
					panel.repaint();

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

		if (dir.equals("HAUT")) nx--;
		if (dir.equals("BAS")) nx++;
		if (dir.equals("GAUCHE")) ny--;
		if (dir.equals("DROITE")) ny++;

		if (nx >= 0 && nx < this.grille.length && ny >= 0 && ny < this.grille[0].length) {

			if (this.grille[nx][ny] != '#') {
				this.x = nx;
				this.y = ny;

				if (this.grille[this.x][this.y] == 'M') {
					this.score++;
					this.grille[this.x][this.y] = '.';
				}
			}
		}
	}

	void deplacerChats() {

		for (Point chat : this.chats) {

			int choix = (int)(Math.random() * 4);

			int dx = 0;
			int dy = 0;

			if (choix == 0) dx = -1;
			if (choix == 1) dx = 1;
			if (choix == 2) dy = -1;
			if (choix == 3) dy = 1;

			int nx = chat.x + dx;
			int ny = chat.y + dy;

			if (nx >= 0 && nx < this.grille.length && ny >= 0 && ny < this.grille[0].length) {
				if (this.grille[nx][ny] != '#') {
					chat.x = nx;
					chat.y = ny;
				}
			}
		}
	}

	void trouverPositions() {

		this.chats.clear();

		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.grille[i].length; j++) {

				if (this.grille[i][j] == 'P') {
					this.x = i;
					this.y = j;
				}

				if (this.grille[i][j] == 'C') {
					this.chats.add(new Point(i, j));
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
