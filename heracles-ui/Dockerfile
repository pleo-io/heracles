# Stage 1 - the build process
FROM node:alpine as build-deps
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
COPY . ./
RUN rm -rf node_modules
RUN npm install

RUN npm run-script build

# Stage 2 - the production environment
FROM nginx:1.17.1-alpine

## Copy our default nginx config
COPY ci/nginx/default.conf /etc/nginx/conf.d/

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

COPY --from=build-deps /usr/src/app/dist /usr/share/nginx/html

EXPOSE 3000

WORKDIR /usr/share/nginx/html

COPY ./env.sh .
COPY .env .

# Add bash
RUN apk add --no-cache bash

RUN apk add --no-cache curl

# Make our shell script executable
RUN chmod +x env.sh

# Start Nginx server

CMD ["/bin/bash", "-c", "/usr/share/nginx/html/env.sh && nginx -g \"daemon off;\""]
