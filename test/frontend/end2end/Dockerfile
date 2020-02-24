FROM node:10

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install
COPY . .
ADD . /usr/src/app

CMD [ "npm", "test" ]