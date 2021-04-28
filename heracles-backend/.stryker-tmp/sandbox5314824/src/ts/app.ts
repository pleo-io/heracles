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
import * as cors from "cors";
import * as morgan from "morgan";
import { formatNumber } from "./helpers/formatNumber";
import { ErrorHandler } from "./utils/error.handler";
const app = express();
app.use(express.json());
app.use(cors());
app.use(morgan(stryMutAct_9fa48(13) ? "" : (stryCov_9fa48(13), "tiny")));
app.set(stryMutAct_9fa48(14) ? "" : (stryCov_9fa48(14), "port"), stryMutAct_9fa48(17) ? process.env.PORT && 5000 : stryMutAct_9fa48(16) ? false : stryMutAct_9fa48(15) ? true : (stryCov_9fa48(15, 16, 17), process.env.PORT || 5000));
app.post(stryMutAct_9fa48(18) ? "" : (stryCov_9fa48(18), "/api/v1/format"), (req: express.Request, resp: express.Response) => {
  if (stryMutAct_9fa48(19)) {
    {}
  } else {
    stryCov_9fa48(19);
    const inputValue = req.body.input;
    const err = new ErrorHandler(req, resp);
    err.checkContentType();
    err.checkParam();
    err.checkNumber(inputValue);
    return resp.status(200).json(stryMutAct_9fa48(20) ? {} : (stryCov_9fa48(20), {
      result: formatNumber(Number(inputValue))
    }));
  }
});
export default app;