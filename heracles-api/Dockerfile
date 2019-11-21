# image 1 - compilation
FROM openjdk:11-jdk as builder

MAINTAINER "Kelvin Wahome<kevowahome@gmail.com>"

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

COPY . .

# Download and setup gradle

RUN ./gradlew --version

RUN ./gradlew clean assemble

# image 2 - execution
FROM openjdk:11-jdk

RUN apt install -y netcat

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

COPY --from=builder $APP_HOME/build/libs/heracles-api-0.0.1-SNAPSHOT.jar $APP_HOME/bin/start.sh ./
EXPOSE 8080
CMD ["bash", "start.sh"]
