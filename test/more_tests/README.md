Pleo Challenge - Extra Tests
========================================

Mobile
------
Simple puppeteer tests that emulates iphoneX
Technology used:
- nodejs
- puppeteer
- mocha
- yarn

Prerq:
- node10
- yarn 1.21
  
Run with
```
yarn install
yarn mocha
```
Results will be in `./more_tests/mobile/mochawesome-report/mochawesome.html`

![MobileResults](./mobile/mobile_results.png)


Load
----
Test plan to generate small load against the server
Technology used:
- jmeter

Run with (with java8 and jmeter installed)
- Open jameter
- Open test plan
- Run it

![LoadRes1](./load/load_results_1.png)

![LoadRes2](./load/load_results_2.png)

From command line run
```
jmeter -n -t <TESTPLAN>
```

![LoadCommnadLine](./load/load_command_line.png)


Accessibiliby
------------
Accesibility issues in home page.
Technology used:
- nodejs
- puppeteer
- mocha
- yarn
- pa11y

Prerq:
- node10
- yarn 1.21

Run with 
```
yarn install
yarn mocha
```
After solved some issues
![AccIssues](./accessibility/acc_issues.png)


Results should be like the one in `./more_tests/accessibility/mochawesome-report/mochawesome.html`

![AccResults](./accessibility/acc_results.png)

