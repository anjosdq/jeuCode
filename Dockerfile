# On change juste le chiffre ici
FROM openjdk:24-jdk-slim

WORKDIR /app

COPY src/ .

RUN javac MoteurJeu.java Joueur.java

CMD ["java", "MoteurJeu"]
