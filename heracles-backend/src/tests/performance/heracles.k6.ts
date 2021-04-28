import { sleep, check } from "k6";
import { Options } from "k6/options";
import http from "k6/http";

export const options: Options = {
  vus: 50,
  duration: "10s",
};

export default (): void => {
  const API_URL = "http://localhost:5000/api/v1/format";
  const data: string = JSON.stringify({ input: 1 });
  const params: Record<string, unknown> = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  const res = http.post(API_URL, data, params);
  check(res, {
    "status is 200": () => res.status === 200,
  });
  sleep(1);
};
