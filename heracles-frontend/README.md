# Heracles Frontend

Heracles frontend is a simple web application which
allows users to format the number in the desired format.
## Pre-requisites
1. [Nodejs](https://nodejs.org/)
2. [NVM](https://github.com/nvm-sh/nvm)
## Technologies used

1. Typescript
2. Webpack
3. Jest(Unit tests)
4. WebdriverIO(e2e tests)
5. Stryker(mutation tests)

# Building and running the application

1. Install the right node version using ``nvm install``.
2. Set the right node version using ``nvm use``.  
3. First install the dependencies using `npm install`.
4. To run the unit tests , please run `npm test:unit`, which then creates the coverage
   reports under `coverage` folder.
5. To run the `e2e` tests .

   3.1 First navigate to `heracles-backend` folder , install the dependencies
   using `npm install`

   3.2 Build the project `npm run build`.

   3.3 Start the `heracles-backend` server using `npm run start:dev`

4. Open a new terminal window and navigate to `heracles-frontend` folder

   4.1 Install the dependencies(if not installed)

   4.2 Build the project using `npm run build`

   4.3 Start the webpack server using `npm run serve`.

5. Now run the tests using `npm run test:e2e`.
6. To generate the allure reports run `npm run e2e:report`, and you should be able to see the
   reports under `allure-report` folder.

### Mutation Testing

I have used [Stryker](https://stryker-mutator.io/docs/stryker/getting-started) as a tool
for running mutation testing . To unleash the mutants run the command
`npm run test:mutation`.
The reports for mutation testing can be found under `reports` folder.

# FAQ's

All the code has been tested and developed on MACOS. It should
work fine on most \*nix based OS(However it has not been tested ).
