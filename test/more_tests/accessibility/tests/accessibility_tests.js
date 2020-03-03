const { expect } = require('chai');
const puppeteer = require('puppeteer');
var browser = null;
const pa11y = require('pa11y');

const opts = {
  headless: false
};

describe('Simple Accessibility Tests', function () {
    before (async function () {
        browser = await puppeteer.launch(opts);
      });
      
      after (function () {
        browser.close();
      });

    it('Home Page', async function() {
        const results = await pa11y('http://localhost:3000');
        const issues = results.issues;

        expect(0).to.be.equals(issues.length);

    });

});