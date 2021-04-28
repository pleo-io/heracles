// @ts-nocheck
// eslint-disable-next-line
const request = require("supertest");
import app from "../../ts/app";
describe("Format API", () => {
  const API_PATH = "/api/v1/format";
  it("should format the number when input is passed as number.", async () => {
    const res = await request(app).post(API_PATH).send({
      input: 1,
    });
    expect(res.statusCode).toEqual(200);
    expect(res.body).toStrictEqual({ result: "1.00" });
    expect(res.headers["content-type"]).toContain("application/json");
  });
  it("should format the number when input is passed as string.", async () => {
    const res = await request(app).post(API_PATH).send({
      input: "123",
    });
    expect(res.statusCode).toEqual(200);
    expect(res.body).toStrictEqual({ result: "123.00" });
    expect(res.headers["content-type"]).toContain("application/json");
  });
  it("should return BAD request when the mandatory param is missing.", async () => {
    const res = await request(app).post(API_PATH).send({
      invalid: "123",
    });
    expect(res.statusCode).toEqual(400);
    expect(res.headers["content-type"]).toContain("application/json");
  });
  it("should return invalid media type request if content type is wrong", async () => {
    const res = await request(app)
      .post(API_PATH)
      .set("Content-Type", "application/octet-stream")
      .send();
    expect(res.statusCode).toEqual(415);
    expect(res.body).toStrictEqual({
      error: "Invalid Content-Type, valid content-type is application/json",
    });
  });
  it("should throw BAD Request when passing an alphanumeric.", async () => {
    const res = await request(app).post(API_PATH).send({
      input: "abc123",
    });
    expect(res.statusCode).toEqual(400);
    expect(res.body).toStrictEqual({
      error: "Invalid Type, type should be a number",
    });
    expect(res.headers["content-type"]).toContain("application/json");
  });
  it("should throw BAD Request when passing Infinity ", async () => {
    const res = await request(app).post(API_PATH).send({
      input: "Infinity",
    });
    expect(res.statusCode).toEqual(400);
    expect(res.body).toStrictEqual({
      error: "Invalid Type, type should be a number",
    });
    expect(res.headers["content-type"]).toContain("application/json");
  });
  it("should format the number when passing numbers in exp format ", async () => {
    const res = await request(app).post(API_PATH).send({
      input: "2e4",
    });
    expect(res.statusCode).toEqual(200);
    expect(res.body).toStrictEqual({
      result: "20 000.00",
    });
    expect(res.headers["content-type"]).toContain("application/json");
  });
});
