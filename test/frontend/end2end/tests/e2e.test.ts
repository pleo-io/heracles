import { mainPage } from '../page-objects/mainPage';
import { resultsPage } from '../page-objects/resultsPage';
import { expect } from 'chai';

describe('End to End Suite - Heracle Challenge', () => {
    it('Successful Message', async () => {
        await mainPage.open();
        await mainPage.addValue("1234");
        await mainPage.submit();

        const valueCalculated = await resultsPage.getMessageValueCalculated();

        expect('Value: 1 234.00').to.equal(valueCalculated);
        expect(await resultsPage.isSuccessfulMessage()).to.be.true;
        expect(await resultsPage.isErrorMessage()).to.be.false;
    });

    it('Error Message', async () => {
        await mainPage.open();
        await mainPage.addValue("aaaa");
        await mainPage.submit();

        const valueCalculated = await resultsPage.getMessageValueCalculated();

        expect('Uh! Some Error in the input: Please Enter a number').to.equal(valueCalculated);
        expect(await resultsPage.isErrorMessage()).to.be.true;
        expect(await resultsPage.isSuccessfulMessage()).to.be.false;
    });

    it('Negative Value', async () => {
        await mainPage.open();
        await mainPage.addValue("-237");
        await mainPage.submit();

        const valueCalculated = await resultsPage.getMessageValueCalculated();

        expect('Value: -237.00').to.equal(valueCalculated);
        expect(await resultsPage.isSuccessfulMessage()).to.be.true;
        expect(await resultsPage.isErrorMessage()).to.be.false;
    });

    it('Decimal Value', async () => {
        await mainPage.open();
        await mainPage.addValue("1298.88");
        await mainPage.submit();

        const valueCalculated = await resultsPage.getMessageValueCalculated();

        expect('Value: 1 298.88').to.equal(valueCalculated);
        expect(await resultsPage.isSuccessfulMessage()).to.be.true;
        expect(await resultsPage.isErrorMessage()).to.be.false;
    });

    it('Decimal Long Value', async () => {
        await mainPage.open();
        await mainPage.addValue("1298.12349585");
        await mainPage.submit();

        const valueCalculated = await resultsPage.getMessageValueCalculated();

        expect('Value: 1 298.12').to.equal(valueCalculated);
        expect(await resultsPage.isSuccessfulMessage()).to.be.true;
        expect(await resultsPage.isErrorMessage()).to.be.false;
    });

    it('Decimal Round Value', async () => {
        await mainPage.open();
        await mainPage.addValue("1298.998888");
        await mainPage.submit();

        const valueCalculated = await resultsPage.getMessageValueCalculated();

        expect('Value: 1 299.00').to.equal(valueCalculated);
        expect(await resultsPage.isSuccessfulMessage()).to.be.true;
        expect(await resultsPage.isErrorMessage()).to.be.false;
    });

    it('Back Link Working', async () => {
        await mainPage.open();
        await mainPage.addValue("1");
        await mainPage.submit();

        await resultsPage.clickBackButton();

        expect(await mainPage.submitButtonExists()).to.be.true;
    });
})