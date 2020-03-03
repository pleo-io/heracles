Pleo Challenge CI
=================

Technology used:
- Jenkins
- scripted pipeline
  
Here a very basic CI strategy

- With every push into a feature branch, all the unit tests must be run
![Push](./jenkins_push.png)
- With a new pull request, we must run Unit + Integration Tests
![PR](./jenkins_pr.png)
- After Merge in master, we must run Unit, Integration and End2End tests. In addition, tag `latest` must be applied. (No code provided)
![Merge](./jenkins_merge.png)
