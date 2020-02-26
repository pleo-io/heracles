FROM node:10

WORKDIR /usr/src/app-fe-e2e

COPY package*.json ./

RUN npm install
COPY . .
ADD . /usr/src/app-fe-e2e

CMD [ "npm", "test" ]