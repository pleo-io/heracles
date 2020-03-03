Pleo Challenge End To End Test
==============================

Technology used:
- typescript
- webdriverio
- mocha
- chai
- allure


Run
---
```
 docker-compose -f docker-compose-e2e.yml up --build --abort-on-container-exit --exit-code-from e2etest
```
Results will be generated in `./heracles/test/frontend/end2end/allure-report`
To see the rich Allure content you will need run `allure open` command (or have a web server)