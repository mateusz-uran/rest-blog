FROM node:18-alpine3.16 as build

COPY package.json package-lock.json .

RUN npm install

COPY . .

# RUN npm run build

CMD ["npm", "start"]