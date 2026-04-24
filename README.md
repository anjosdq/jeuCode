# SAE : Labyrinthe Code-Challenge Java

Bienvenue dans ce projet de labyrinthe où la victoire ne dépend pas de vos réflexes, mais de votre capacité à **coder** !

## Concept

Ce projet est un environnement de simulation. Contrairement à un jeu classique, vous ne contrôlez pas le personnage avec le clavier. Vous devez **implémenter un algorithme de résolution** dans une classe Java. 

Votre code est ensuite implémenter, compilé et exécuté à l'intérieur d'un conteneur **Docker** qui fait tourner le moteur de jeu. C'est une excellente démonstration de la portabilité de Java combinée à la puissance d'isolation de Docker.

## L'équipe

* **Équipier 1** : Anjolaoluwa Sadiq
* **Équipier 2** : Alexis Bouffay
* **Équipier 3** : Samba Camara


## Structure du Projet

L'architecture est séparée en trois parties distinctes :

1.  **`src/Labyrinthe.java`** : Le cœur du système. Il gère la carte, vérifie les collisions et gère la boucle de rendu en console.
2.  **`src/Joueur.java`** : C'est votre espace de travail. Vous devez modifier la méthode `deplacer()` pour guider le personnage 'P' vers la sortie 'S'.
3.  **`Dockerfile`** : La recette de cuisine qui définit l'environnement Linux/Java nécessaire pour compiler et lancer le défi.

## Installation et Lancement

### Prérequis
* Docker Desktop installé et lancé.

### Étapes à suivre

1. **Modifiez le fichier `src/Joueur.java`** sur votre machine hôte avec votre logique de déplacement.
2. **Construisez l'image** (cette étape compile votre code Java à l'intérieur du conteneur) :
   ```bash
   docker build -t jeuCode .
3. **Pour lancer le jeu :**
   ```bash
   docker run -it --rm jeucode
