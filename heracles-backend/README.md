# Heracles Backend

Heracles frontend exposes a REST API to format the number in a desired format.
A sample input

```js
/api/v1/format
{
  input:1
}
Result{
    "result":1.00
}
```

## Pre-requisites:

1. [Nodejs](https://nodejs.org/en/)
2. [NPM](https://nodejs.org/en/)
3. [Docker](https://www.docker.com/)

## Technologies used

This server is written in Typescript to have type safety.

1. Express (For server development)
2. Jest and Supertest as test runner and test framework
   for writing API tests.
3. K6 for load testing
4. Stryker for mutation testing.
5. Prettier for code formatting.
6. ESLint for code linting.

# Building and running the backend

1. Install the right node version using `nvm install`.
2. Set the right node version using `nvm use`.
3. Install the dependencies using `npm install`.
4. Build the backend using `npm run build`
5. To run the tests just run `npm test`(this runs both unit and integration tests). Once the tests are run
   you can find the coverage report under `coverage` folder.
6. Start the server using `npm run start:dev`, you should see an express server running on port 5000
   which can be validated by checking the console.

### Checking API requests

Fire up postman and start making requests to the backend server. There is also a postman collection with the name
`heracles-backend.postman_collection.json` which can be imported in postman.

### Running load tests

As part of this solution I have also implemented a basic load test using
[k6](https://k6.io/). The load test is quite simple straighforward
where in we hit the API with 50 virtual users for a period of 10 seconds and we make sure that the API
is still up and running . However in a real world those virtual users and the time would
be heavily driven by SLI's and SLO's which is defined together
with the team and the product.
All the performance tests are found under `tests/performance`

### Running load tests

Pre Requisites:

1. Make sure that the server is running.
2. Replace `localhost` with your IP of your machine.
3. First build the scripts by running `npm run build:perf`.
4. Run the tests using `docker run -i loadimpact/k6 run - <dist/heracles.js`

### Mutation Testing

I have used [Stryker](https://stryker-mutator.io/docs/stryker/getting-started) as a tool
for running mutation testing . To unleash the mutants run the command
`npm run test:mutation`.
The reports for mutation testing can be found under `reports` folder.

# FAQ's

All the code has been tested and developed on MACOS. It should
work fine on most \*nix based OS(However it has not been tested ).
