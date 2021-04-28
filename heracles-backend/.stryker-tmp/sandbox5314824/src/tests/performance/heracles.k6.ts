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

import { sleep, check } from "k6";
import { Options } from "k6/options";
import http from "k6/http";
export const options: Options = stryMutAct_9fa48(0) ? {} : (stryCov_9fa48(0), {
  vus: 50,
  duration: stryMutAct_9fa48(1) ? "" : (stryCov_9fa48(1), "10s")
});
export default ((): void => {
  if (stryMutAct_9fa48(2)) {
    {}
  } else {
    stryCov_9fa48(2);
    const API_URL = stryMutAct_9fa48(3) ? "" : (stryCov_9fa48(3), "http://localhost:5000/api/v1/format");
    const data: string = JSON.stringify(stryMutAct_9fa48(4) ? {} : (stryCov_9fa48(4), {
      input: 1
    }));
    const params: Record<string, unknown> = stryMutAct_9fa48(5) ? {} : (stryCov_9fa48(5), {
      headers: stryMutAct_9fa48(6) ? {} : (stryCov_9fa48(6), {
        "Content-Type": stryMutAct_9fa48(7) ? "" : (stryCov_9fa48(7), "application/json")
      })
    });
    const res = http.post(API_URL, data, params);
    check(res, stryMutAct_9fa48(8) ? {} : (stryCov_9fa48(8), {
      "status is 200": stryMutAct_9fa48(9) ? () => undefined : (stryCov_9fa48(9), () => stryMutAct_9fa48(12) ? res.status !== 200 : stryMutAct_9fa48(11) ? false : stryMutAct_9fa48(10) ? true : (stryCov_9fa48(10, 11, 12), res.status === 200))
    }));
    sleep(1);
  }
});