Frontend App for Pleo
====================

High level
----------
* Node/Express app
* npm as dependency manager

Run
---
```
 docker build -t docker-frontend-pleo .
 docker run  -p 3000:3000 docker-frontend-pleo:latest
```

Run Tests
---------
```
 docker build -t docker-frontend-pleo-test -f Dockerfile.test .
 docker run  docker-frontend-pleo-test:latest
```