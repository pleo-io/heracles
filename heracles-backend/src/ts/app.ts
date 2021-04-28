import * as express from "express";
import * as cors from "cors";
import * as morgan from "morgan";
import { formatNumber } from "./helpers/formatNumber";
import { ErrorHandler } from "./utils/error.handler";

const app = express();
app.use(express.json());
app.use(cors());
app.use(morgan("tiny"));
app.set("port", process.env.PORT || 5000);
app.post("/api/v1/format", (req: express.Request, resp: express.Response) => {
  const inputValue = req.body.input;
  const err = new ErrorHandler(req, resp);
  err.checkContentType();
  err.checkParam();
  err.checkNumber(inputValue);
  return resp.status(200).json({ result: formatNumber(Number(inputValue)) });
});

export default app;
