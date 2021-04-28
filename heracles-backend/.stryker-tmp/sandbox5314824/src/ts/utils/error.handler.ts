// @ts-nocheck
function stryNS_9fa48() {
  var g = new Function("return this")();
  var ns = g.__stryker__ || (g.__stryker__ = {});

  if (ns.activeMutant === undefined && g.process && g.process.env && g.process.env.__STRYKER_ACTIVE_MUTANT__) {
    ns.activeMutant = Number(g.process.env.__STRYKER_ACTIVE_MUTANT__);
  }

  function retrieveNS() {
    return ns;
  }

  stryNS_9fa48 = retrieveNS;
  return retrieveNS();
}

stryNS_9fa48();

function stryCov_9fa48() {
  var ns = stryNS_9fa48();
  var cov = ns.mutantCoverage || (ns.mutantCoverage = {
    static: {},
    perTest: {}
  });

  function cover() {
    var c = cov.static;

    if (ns.currentTestId) {
      c = cov.perTest[ns.currentTestId] = cov.perTest[ns.currentTestId] || {};
    }

    var a = arguments;

    for (var i = 0; i < a.length; i++) {
      c[a[i]] = (c[a[i]] || 0) + 1;
    }
  }

  stryCov_9fa48 = cover;
  cover.apply(null, arguments);
}

function stryMutAct_9fa48(id) {
  var ns = stryNS_9fa48();

  function isActive(id) {
    return ns.activeMutant === id;
  }

  stryMutAct_9fa48 = isActive;
  return isActive(id);
}

import * as express from "express";
import { BAD_REQUEST_STATUS_CODE, UNSUPPORTED_MEDIA_TYPE } from "../constants/errorCodes";
import { BAD_REQUEST_MESSAGE, UNSUPPORTED_MEDIA_TYPE_MESSAGE, MISSING_PARAM_MESSAGE } from "../constants/errorMessages";
export class ErrorHandler {
  private req: express.Request;
  private res: express.Response;

  constructor(request: express.Request, response: express.Response) {
    if (stryMutAct_9fa48(41)) {
      {}
    } else {
      stryCov_9fa48(41);
      this.req = request;
      this.res = response;
    }
  }

  public checkContentType(): void {
    if (stryMutAct_9fa48(42)) {
      {}
    } else {
      stryCov_9fa48(42);

      if (stryMutAct_9fa48(45) ? this.req.is(stryMutAct_9fa48(46) ? "" : (stryCov_9fa48(46), "json")) : stryMutAct_9fa48(44) ? false : stryMutAct_9fa48(43) ? true : (stryCov_9fa48(43, 44, 45), !this.req.is(stryMutAct_9fa48(46) ? "" : (stryCov_9fa48(46), "json")))) {
        if (stryMutAct_9fa48(47)) {
          {}
        } else {
          stryCov_9fa48(47);
          const errorMessage = stryMutAct_9fa48(48) ? {} : (stryCov_9fa48(48), {
            error: UNSUPPORTED_MEDIA_TYPE_MESSAGE
          });
          throw this.res.status(UNSUPPORTED_MEDIA_TYPE).json(errorMessage);
        }
      }
    }
  }

  public checkParam(): void {
    if (stryMutAct_9fa48(49)) {
      {}
    } else {
      stryCov_9fa48(49);
      const errorMessage = stryMutAct_9fa48(50) ? {} : (stryCov_9fa48(50), {
        error: MISSING_PARAM_MESSAGE
      });

      if (stryMutAct_9fa48(53) ? this.req.body[stryMutAct_9fa48(54) ? "" : (stryCov_9fa48(54), "input")] !== undefined : stryMutAct_9fa48(52) ? false : stryMutAct_9fa48(51) ? true : (stryCov_9fa48(51, 52, 53), this.req.body[stryMutAct_9fa48(54) ? "" : (stryCov_9fa48(54), "input")] === undefined)) {
        if (stryMutAct_9fa48(55)) {
          {}
        } else {
          stryCov_9fa48(55);
          throw this.res.status(BAD_REQUEST_STATUS_CODE).json(errorMessage);
        }
      }
    }
  }

  public checkNumber(inputValue: string): void {
    if (stryMutAct_9fa48(56)) {
      {}
    } else {
      stryCov_9fa48(56);
      const errorMessage = stryMutAct_9fa48(57) ? {} : (stryCov_9fa48(57), {
        error: BAD_REQUEST_MESSAGE
      });

      if (stryMutAct_9fa48(60) ? isFinite(Number(inputValue)) : stryMutAct_9fa48(59) ? false : stryMutAct_9fa48(58) ? true : (stryCov_9fa48(58, 59, 60), !isFinite(Number(inputValue)))) {
        if (stryMutAct_9fa48(61)) {
          {}
        } else {
          stryCov_9fa48(61);
          throw this.res.status(BAD_REQUEST_STATUS_CODE).json(errorMessage);
        }
      }
    }
  }

}