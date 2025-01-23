# Etapa base para o desenvolvimento
FROM node:20 AS dev

# Definir o diretório de trabalho no container
WORKDIR /app

# Copiar os arquivos de dependências
COPY package*.json ./

# Instalar as dependências
RUN npm install

# Copiar todo o projeto para o container
COPY . .

# Expor a porta padrão do Angular (4200)
EXPOSE 4200

# Comando para rodar o Angular com --host 0.0.0.0
CMD ["npm", "run", "start", "--", "--host", "0.0.0.0"]
