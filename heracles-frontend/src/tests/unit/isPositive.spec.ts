import { Converter } from "../../ts/fetchData";

describe("Currency Formatter ", () => {
  const currencyFormatter = new Converter();
  it("should return true if the number is positive.", () => {
    expect(currencyFormatter.isNumberPositive(2)).toBeTruthy();
  });
  it("should return false if the number is negative.", () => {
    expect(currencyFormatter.isNumberPositive(-2)).toBeFalsy();
  });
  it("should return false if the number is negative.", () => {
    expect(currencyFormatter.isNumberPositive(-2e1)).toBeFalsy();
  });
  it("should return false if the number is positive.", () => {
    expect(currencyFormatter.isNumberPositive(2e1)).toBeTruthy();
  });
});
