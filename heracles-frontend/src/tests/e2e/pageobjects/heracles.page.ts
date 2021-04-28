export class HeraclesPage {
  public async inputBox(): Promise<WebdriverIO.Element> {
    return await $("#num");
  }
  public async submitButton(): Promise<WebdriverIO.Element> {
    return await $("#submit");
  }
  public async result(): Promise<WebdriverIO.Element> {
    return await $("#result");
  }
  public async getPageTitle(): Promise<string> {
    return await browser.getTitle();
  }
  public async getPageHeaderElement(): Promise<WebdriverIO.Element> {
    return await $("#header");
  }
  public async pageHeaderText(): Promise<string> {
    const pageHeader = await this.getPageHeaderElement();
    return await pageHeader.getText();
  }
  public async enterNumber(num: number): Promise<void> {
    const inputElement = await this.inputBox();
    await inputElement.waitForDisplayed();
    await inputElement.setValue(num);
  }
  public async formatNumber(): Promise<void> {
    const submitButton = await this.submitButton();
    await submitButton.waitForDisplayed();
    await submitButton.click();
  }
  public async resultText(): Promise<string> {
    const resultElement = await this.result();
    await resultElement.waitForDisplayed();
    return await resultElement.getText();
  }
}
