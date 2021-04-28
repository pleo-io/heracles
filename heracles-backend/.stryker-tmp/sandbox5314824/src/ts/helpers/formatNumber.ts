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

export function formatNumber(num: number): string {
  if (stryMutAct_9fa48(24)) {
    {}
  } else {
    stryCov_9fa48(24);
    let result = stryMutAct_9fa48(25) ? "Stryker was here!" : (stryCov_9fa48(25), "");

    if (stryMutAct_9fa48(27) ? false : stryMutAct_9fa48(26) ? true : (stryCov_9fa48(26, 27), checkFinite(num))) {
      if (stryMutAct_9fa48(28)) {
        {}
      } else {
        stryCov_9fa48(28);
        result = num.toLocaleString(stryMutAct_9fa48(29) ? "" : (stryCov_9fa48(29), "en-US"), stryMutAct_9fa48(30) ? {} : (stryCov_9fa48(30), {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2
        })).replaceAll(stryMutAct_9fa48(31) ? "" : (stryCov_9fa48(31), ","), stryMutAct_9fa48(32) ? "" : (stryCov_9fa48(32), " "));
      }
    } else {
      if (stryMutAct_9fa48(33)) {
        {}
      } else {
        stryCov_9fa48(33);
        result = stryMutAct_9fa48(34) ? "" : (stryCov_9fa48(34), "Invalid Number");
      }
    }

    return result;
  }
}

function checkFinite(num: number): boolean {
  if (stryMutAct_9fa48(35)) {
    {}
  } else {
    stryCov_9fa48(35);
    return Number.isFinite(num);
  }
}