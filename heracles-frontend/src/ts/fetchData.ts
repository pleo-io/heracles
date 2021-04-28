import {
  BAD_REQUEST_MESSAGE,
  INVALID_INPUT,
  SERVER_DOWN_MESSAGE,
  SUCCESS_MESSAGE,
} from "../constants";

export class Converter {
  registerListener(inputValue: string): void {
    document
      .getElementById("submit")
      .addEventListener(
        "click",
        this.printMessage.bind(this, inputValue),
        false
      );
  }
  isNumberPositive(num: number): boolean {
    return Math.sign(num) > 0;
  }

  async fetchData(inputValue: string): Promise<string> {
    const API_URL = `http://localhost:5000/api/v1/format`;
    const inputNumber = Number(inputValue);
    if (this.isNumberPositive(inputNumber)) {
      const data = { input: Number(inputValue) };
      return fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      })
        .then(function (response) {
          if (response.status !== 200) {
            return BAD_REQUEST_MESSAGE;
          }
          return response.json().then(function (data) {
            return `${SUCCESS_MESSAGE} ${data["result"]}`;
          });
        })
        .catch(function () {
          return SERVER_DOWN_MESSAGE;
        });
    }
    return INVALID_INPUT;
  }
  printMessage(res: string): void {
    const resDiv = document.getElementById("result");
    this.fetchData(res).then((data) => {
      resDiv.innerHTML = data;
    });
  }
}
