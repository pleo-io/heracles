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

import app from "./app";
const server = app.listen(app.get(stryMutAct_9fa48(36) ? "" : (stryCov_9fa48(36), "port")), () => {
  if (stryMutAct_9fa48(37)) {
    {}
  } else {
    stryCov_9fa48(37);
    console.log(stryMutAct_9fa48(38) ? "" : (stryCov_9fa48(38), "App is running on http://localhost:%d in %s mode"), app.get(stryMutAct_9fa48(39) ? "" : (stryCov_9fa48(39), "port")), app.get(stryMutAct_9fa48(40) ? "" : (stryCov_9fa48(40), "env")));
  }
});
export default server;