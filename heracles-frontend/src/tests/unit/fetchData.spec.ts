import { Converter } from "../../ts/fetchData";
import * as fetchMock from "fetch-mock";
import { BAD_REQUEST_MESSAGE, INVALID_INPUT } from "../../constants";
fetchMock.config.overwriteRoutes = true;
fetchMock.config.sendAsJson = true;

describe("Currency Formatter ", () => {
  const currencyFormatter = new Converter();
  const API_URL = `http://localhost:5000/api/v1/format`;
  it("should return appropriate message when the API returns 400", async function () {
    fetchMock.mock(
      API_URL,
      {
        status: 400,
        headers: { "Content-Type": "application/json" },
      },
      { method: "POST" }
    );

    expect(await currencyFormatter.fetchData("2")).toEqual(BAD_REQUEST_MESSAGE);
  });
  it("should return appropriate message when the API returns 415", async function () {
    document.body.innerHTML = '<div id ="result"> </div>';
    fetchMock.mock(
      API_URL,
      {
        status: 415,
        headers: { "Content-Type": "application/json" },
      },
      { method: "POST" }
    );
    expect(await currencyFormatter.fetchData("2")).toEqual(BAD_REQUEST_MESSAGE);
  });
  it("should return appropriate message when the API returns 500", async function () {
    document.body.innerHTML = '<div id ="result"> </div>';
    fetchMock.mock(
      API_URL,
      {
        status: 500,
        headers: { "Content-Type": "application/json" },
      },
      { method: "POST" }
    );
    expect(await currencyFormatter.fetchData("2")).toEqual(BAD_REQUEST_MESSAGE);
  });
  it("should return appropriate message when the API returns 200", async function () {
    document.body.innerHTML = '<div id ="result"> </div>';
    fetchMock.mock(
      API_URL,
      {
        status: 200,
        result: "2.00",
        statusText: "OK",
        headers: { "Content-Type": "application/json" },
      },
      { method: "POST" }
    );
    expect(await currencyFormatter.fetchData("2")).toEqual(
      `The result is 2.00`
    );
  });
  it("should return appropriate message when the API returns 200", async function () {
    document.body.innerHTML = '<div id ="result"> </div>';
    fetchMock.mock(
      API_URL,
      {
        status: 200,
        result: "2.00",
        statusText: "OK",
        headers: { "Content-Type": "application/json" },
      },
      { method: "POST" }
    );

    expect(await currencyFormatter.fetchData("2")).toEqual(
      `The result is 2.00`
    );
  });
  it("should return appropriate message when the use enters a negative number", async function () {
    expect(await currencyFormatter.fetchData("-2")).toEqual(INVALID_INPUT);
  });
});
