FROM openjdk:25-jdk-slim

#  Installation des bibliothèques pour l'affichage graphique
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# On copie tout le contenu du dossier src
COPY src/ .

# On compile 
RUN javac *.java

# Lancement du jeu
CMD ["java", "Labyrinthe"]
