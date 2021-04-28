import { Converter } from "./fetchData";

const inputBox = document.getElementById("num");
if (inputBox) {
  inputBox.addEventListener(
    "blur",
    function () {
      const cnvrtr = new Converter();
      cnvrtr.registerListener((inputBox as any).value);
    },
    false
  );
}
