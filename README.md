# 🐭 Attrape Souris docker-sae203

Un jeu de labyrinthe dynamique développé en **JavaScript** avec génération procédurale de niveaux et déploiement via **Docker**.

## 🌟 Points Forts Techniques

- **Génération Procédurale** : Utilisation d'un automate (Recursive Backtracker) pour créer des labyrinthes uniques et parfaits à l'infini.
- **IA de Traque** : Les ennemis (Tom Nook) utilisent une logique de calcul de distance pour poursuivre le joueur.
- **Difficulté Évolutive** : La taille du labyrinthe et le nombre d'ennemis augmentent automatiquement à chaque niveau réussi.
- **Architecture Docker** : Le projet est entièrement containerisé avec Nginx pour un déploiement instantané.

## 🎮 Comment Jouer ?

1. Déplacez votre villageois avec les **flèches directionnelles** de votre clavier.
2. Récupérez les **clochettes** 💰 pour augmenter votre score.
3. Atteignez le **drapeau** 🏁 pour passer au niveau suivant (plus grand et plus dur).
4. Attention ! Si **Tom Nook** 🐱 vous attrape, vous perdez vos clochettes et revenez au niveau 1.

##  Lancer avec Docker

Assurez-vous d'avoir Docker installé, puis lancez les commandes suivantes à la racine du projet :

# 1. Récupérer le projet
```bash
git clone https://github.com/anjosdq/jeuCode.git
cd jeuCode
```

# 2. Construire l'image
```bash
docker build -t attrape-souris .
```

# 3. Lancer le container
```bash
docker run -d -p 8080:80 attrape-souris
```
