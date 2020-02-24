
const SUBMIT_BUTTON = '#button_submit';
const INPUT_TEXT = '#input_number';

class MainPage {

    async open() {
        await browser.url('/');
    }

    async addValue(value: string) {
        const textbox = await $(INPUT_TEXT);
        await textbox.setValue(value);
    }

    async submit() {
        const button = await $(SUBMIT_BUTTON);
        await button.click();
    }

    async submitButtonExists() {
        const submitButton = await $(SUBMIT_BUTTON);
        return await submitButton.isExisting();
    }

}
export const mainPage = new MainPage();
