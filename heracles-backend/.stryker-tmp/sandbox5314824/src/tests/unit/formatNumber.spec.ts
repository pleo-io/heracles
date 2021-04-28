// @ts-nocheck
import { formatNumber } from "../../ts/helpers/formatNumber";

describe("formatNumber ", () => {
  test("should add zeroes as decimal places  ", () => {
    expect(formatNumber(123)).toEqual("123.00");
  });
  test("should add zeroes as decimal places if there are no ", () => {
    expect(formatNumber(1234)).toEqual("1 234.00");
  });
  test("should round off the decimal places to 2 places", () => {
    expect(formatNumber(123.15986777)).toEqual("123.16");
  });
  test("should format the number in thousands formatter", () => {
    expect(formatNumber(123456789.15986777)).toEqual("123 456 789.16");
  });
  test("should round off to two numbers when there is one number after decimal ", () => {
    expect(formatNumber(123.4)).toEqual("123.40");
  });

  test("should handle ", () => {
    expect(formatNumber(123456)).toEqual("123 456.00");
  });
  test("should handle when passing 0", () => {
    expect(formatNumber(0)).toEqual("0.00");
  });
  test("should handle negative number", () => {
    expect(formatNumber(-123456)).toEqual("-123 456.00");
  });
  test("should return Invalid Number when passing NaN", () => {
    expect(formatNumber(NaN)).toEqual("Invalid Number");
  });
  test("should return Invalid Number when passing Infinity", () => {
    expect(formatNumber(Infinity)).toEqual("Invalid Number");
  });
  test("should return Invalid Number when passing -Infinity", () => {
    expect(formatNumber(-Infinity)).toEqual("Invalid Number");
  });
  test("should handle MAX SAFE INTEGER", () => {
    expect(formatNumber(Number.MAX_SAFE_INTEGER)).toEqual(
      "9 007 199 254 740 991.00"
    );
  });
  test("should handle MIN SAFE INTEGER", () => {
    expect(formatNumber(Number.MIN_SAFE_INTEGER)).toEqual(
      "-9 007 199 254 740 991.00"
    );
  });
  test("should handle MAX VALUE", () => {
    expect(formatNumber(Number.MAX_VALUE)).toEqual(
      "179 769 313 486 231 570 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000.00"
    );
  });
  test("should handle when passing numbers in exponential format", () => {
    const expectedResult =
      "20 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000.00";
    expect(formatNumber(2e64)).toEqual(expectedResult);
  });
});
