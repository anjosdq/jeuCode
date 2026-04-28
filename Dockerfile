# 1. Base ultra-légère
FROM nginx:alpine

# 2. Copie des fichiers (HTML, CSS, JS, Images)
COPY . /usr/share/nginx/html/

# 3. Documentation du port
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
