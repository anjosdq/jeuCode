# On utilise l'image ultra-légère de Nginx
FROM nginx:alpine

# On copie tous les fichiers de notre projet dans le dossier que Nginx utilise pour servir le contenu
COPY . /usr/share/nginx/html/

# On expose le port 80 
EXPOSE 80

# Nginx se lance automatiquement au démarrage du container
