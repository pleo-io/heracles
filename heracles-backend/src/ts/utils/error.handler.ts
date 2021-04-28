import * as express from "express";
import {
  BAD_REQUEST_STATUS_CODE,
  UNSUPPORTED_MEDIA_TYPE,
} from "../constants/errorCodes";
import {
  BAD_REQUEST_MESSAGE,
  UNSUPPORTED_MEDIA_TYPE_MESSAGE,
  MISSING_PARAM_MESSAGE,
} from "../constants/errorMessages";
export class ErrorHandler {
  private req: express.Request;
  private res: express.Response;
  constructor(request: express.Request, response: express.Response) {
    this.req = request;
    this.res = response;
  }
  public checkContentType(): void {
    if (!this.req.is("json")) {
      const errorMessage = { error: UNSUPPORTED_MEDIA_TYPE_MESSAGE };
      throw this.res.status(UNSUPPORTED_MEDIA_TYPE).json(errorMessage);
    }
  }
  public checkParam(): void {
    const errorMessage = { error: MISSING_PARAM_MESSAGE };
    if (this.req.body["input"] === undefined) {
      throw this.res.status(BAD_REQUEST_STATUS_CODE).json(errorMessage);
    }
  }
  public checkNumber(inputValue: string): void {
    const errorMessage = { error: BAD_REQUEST_MESSAGE };
    if (!isFinite(Number(inputValue))) {
      throw this.res.status(BAD_REQUEST_STATUS_CODE).json(errorMessage);
    }
  }
}
