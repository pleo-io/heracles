FROM node:10

WORKDIR /usr/src/app-fe-int

COPY package*.json ./

RUN npm install
COPY . .
ADD . /usr/src/app-fe-int

CMD [ "npm", "test" ]