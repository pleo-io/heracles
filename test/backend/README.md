Pleo Challenge Backend Test
===========================

API tests using groovy and gradle

Run
---
```
 docker-compose -f docker-compose-test-backend.yml up --build --abort-on-container-exit --exit-code-from docker-test-backend-pleo
```
Results will be in `./heracles/test/backend/build/reports/tests/test/index.html`