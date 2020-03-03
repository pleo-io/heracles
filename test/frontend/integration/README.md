Pleo Challenge FrontEnd Integration Test
========================================

Technology Used
---------------
- nodejs
- mocha
- npm
- axios


Run
---
```
 docker-compose -f docker-compose-test-frontend.yml up --build --abort-on-container-exit --exit-code-from docker-test-frontend-integration
```
Results will be in `./heracles/test/frontend/integartion/mochawesome-report/mochawesome.html`