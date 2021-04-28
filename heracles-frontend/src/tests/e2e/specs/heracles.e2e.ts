import { HeraclesPage } from "../pageobjects/heracles.page";
describe("Heracles frontend", () => {
  const heracles: HeraclesPage = new HeraclesPage();
  describe("Heracles Frontend currency formatter.", () => {
    beforeEach(async () => {
      await browser.url(browser.config.baseUrl);
    });

    it("should have the right title.", async () => {
      const expectedPageTitle = "Heracles Frontend";
      expect(await heracles.getPageTitle()).toEqual(expectedPageTitle);
    });
    it("should have the right header.", async () => {
      const expectedHeader = "Welcome to Heracles Currency Formatter";
      expect(await heracles.pageHeaderText()).toEqual(expectedHeader);
    });
    it("should return the currency formatted rounded to 2 places.", async () => {
      await heracles.enterNumber(3);
      await heracles.formatNumber();
      expect(await heracles.resultText()).toEqual("The result is 3.00");
    });
    it("should accept only positive numbers.", async () => {
      await heracles.enterNumber(-3);
      await heracles.formatNumber();
      expect(await heracles.resultText()).toEqual(
        "Please enter a positive number."
      );
    });
    it("should return the currency formatted in thousands formatted to 2 places", async () => {
      await heracles.enterNumber(1234.5578);
      await heracles.formatNumber();
      expect(await heracles.resultText()).toEqual("The result is 1 234.56");
    });
    it("should be able to use exp format.", async () => {
      await heracles.enterNumber(2e4);
      await heracles.formatNumber();
      expect(await heracles.resultText()).toEqual("The result is 20 000.00");
    });
    it("should make POST call to the heracles backend.", async () => {
      await (browser as any).setupInterceptor();
      await heracles.enterNumber(3);
      await heracles.formatNumber();
      await (browser as any).expectRequest(
        "POST",
        "http://localhost:5000/api/v1/format",
        200
      );
      await (browser as any).assertRequests();
    });
    it("should return the right message when the request fails.", async () => {
      await heracles.enterNumber(566);
      const strictMock = await browser.mock("**/format", {
        method: "post",
      });
      await strictMock.abort("Failed");
      await heracles.formatNumber();
      expect(await heracles.resultText()).toEqual(
        "The server seems to be down, please try again later"
      );
      await strictMock.clear();
    });
    it("should return the right message when the request aborted.", async () => {
      await heracles.enterNumber(566);
      const strictMock = await browser.mock("**/format", {
        method: "post",
      });
      await strictMock.abort("Aborted");
      await heracles.formatNumber();
      expect(await heracles.resultText()).toEqual(
        "The server seems to be down, please try again later"
      );
      await strictMock.clear();
    });
  });
});
