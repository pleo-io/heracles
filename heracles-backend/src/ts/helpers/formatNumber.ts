export function formatNumber(num: number): string {
  let result = "";
  if (checkFinite(num)) {
    result = num
      .toLocaleString("en-US", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
      })
      .replaceAll(",", " ");
  } else {
    result = "Invalid Number";
  }
  return result;
}
function checkFinite(num: number): boolean {
  return Number.isFinite(num);
}
