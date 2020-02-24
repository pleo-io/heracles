Backend App for Pleo
====================

High level
----------
* Spring Java app
* Gradle used to build the project

Run App
-------
```
 docker build -t docker-backend-pleo .
 docker run  -p 8080:8080 docker-backend-pleo:latest
```

Run Tests
---------
```
 docker build -t docker-backend-pleo-test -f Dockerfile.test .
 docker run docker-backend-pleo-test:latest
```