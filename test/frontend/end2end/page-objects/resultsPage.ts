const ALERT_TEXT = '.alert';
const ALERT_SUCCESS_TEXT = '.alert-success';
const ALERT_ERROR_TEXT = '.alert-danger';
const BACK_TRY = '#back_try';

class ResultsPage {
    async getMessageValueCalculated() {
        const divReturned = await $(ALERT_TEXT);
        return await divReturned.getText();
    }

    async isErrorMessage() {
        const errorMessage = await $(ALERT_ERROR_TEXT);
        return await errorMessage.isExisting();
    }

    async isSuccessfulMessage() {
        const successfulMessage = await $(ALERT_SUCCESS_TEXT);
        return await successfulMessage.isExisting();
    }

    async clickBackButton() {
        const back = await $(BACK_TRY);
        await back.click();
    }
}

export const resultsPage = new ResultsPage();