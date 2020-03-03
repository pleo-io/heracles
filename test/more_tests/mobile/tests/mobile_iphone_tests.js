const { expect } = require('chai');
const puppeteer = require('puppeteer');
var browser = null;
const devices = require('puppeteer/DeviceDescriptors');
const iPhonex = devices['iPhone X'];

const opts = {
  headless: false
};

describe('Simple Mobile Emulation Tests', function () {
    before (async function () {
        browser = await puppeteer.launch(opts);
      });
      
      after (function () {
        browser.close();
      });

    it('IphoneX Test', async function() {
        const page = (await browser.pages())[0]
        
        await page.emulate(iPhonex);
        await page.goto('http://localhost');

        await page.type('#input_number', '2223');
        await page.click('#button_submit');
        await page.waitForSelector('.alert');
        const textReturned = await page.$eval('.alert', el => el.textContent.trim())

        await page.screenshot({ path: 'images/result_iphoneX.png'});

        expect("Value: 2 223.00").to.be.equals(textReturned);
    });

});