#  SAE : Attrape Souris docker-sae203

Bienvenue dans **Attrape Souris**, un jeu de labyrinthe où vous reprenez les commandes ! Ce projet démontre comment Java et Docker collaborent pour offrir une expérience de jeu portable et sécurisée.

##  Concept

Ce projet utilise la puissance de **Docker** pour encapsuler un moteur de jeu complet. 

Contrairement aux versions précédentes où le mouvement était automatisé, cette version est **entièrement interactive**. L'intérêt technique réside dans la capacité de Docker à transmettre les entrées de votre clavier  et à renvoyer l'affichage graphique du conteneur vers votre écran en temps réel.

##  Objectif 

Incarnez le personnage **'P'** et traversez les méandres du labyrinthe pour capturer la souris située à la sortie **'S'**. Le jeu propose plusieurs niveaux de difficulté croissante.

##  L'équipe

* **Anjolaoluwa Sadiq**
* **Alexis Bouffay**
* **Samba Camara**

##  Structure du Projet

L'architecture est simplifiée pour se concentrer sur l'exécution :

1. **`src/Labyrinthe.java`** : Gère le moteur de jeu, les collisions et l'interface graphique Swing.
2. **`Dockerfile`** : Configure l'environnement de compilation (Java UTF-8) et prépare l'exécution interactive.
3. **`index.html`** : Page de présentation web du projet.

##  Installation et Lancement

### Prérequis
* **Docker** installé et opérationnel.
* Un serveur **X11** pour l'affichage (standard sur Linux, via XQuartz sur Mac ou WSLg sur Windows).

### Étapes à suivre

1. **Récupérer le projet** :
   ```bash
   git clone git@github.com:anjosdq/jeuCode.git

2.  **Autoriser l'affichage graphique** :
   ```bash
   xhost +local:docker
   ```
3.  **Lancer le jeu Attrape Souris** :
   ```bash
   docker-compose up --build 
