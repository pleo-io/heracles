const axios = require('axios')
const chai = require('chai');
const assert = chai.assert;

URL_FRONTEND_SERVICE = 'http://docker-frontend-pleo:3000/';

describe('Integration Test Suite', function() {
   after(async function() {
    await axios.post(URL_FRONTEND_SERVICE, {
      numberID: '999'
    })
  });
  

  it('Test Simple Value', async function() {
    const resp = await axios.post(URL_FRONTEND_SERVICE, {
        numberID: 1
    });
    console.log(resp.data);
    assert.isTrue(resp.data.includes('class="alert alert-success">Value: 1.00<'),'Value expected is not 1.00 (or alert is not success)');
  })

  it('Test Letter', async function() {
    const resp = await axios.post(URL_FRONTEND_SERVICE, {
      numberID: 'a'
    });
    console.log(resp.data);
    assert.isTrue(resp.data.includes('class="alert alert-danger">Uh! Some Error in the input: Please Enter a number'),'Value expected is not ERROR (or alert is not error)');
  })

  it('Test Empty Value', async function() {
    const resp = await axios.post(URL_FRONTEND_SERVICE, {
      numberID: ''
    });
    console.log(resp.data);
    assert.isTrue(resp.data.includes('class="alert alert-success">Value: 0.00'),'Value expected is not 1.00 (or alert is not success)');
  })

  it('Test Negative Value', async function() {
    const resp = await axios.post(URL_FRONTEND_SERVICE, {
      numberID: '-1000.00'
    });
    console.log(resp.data);
    assert.isTrue(resp.data.includes('class="alert alert-success">Value: -1 000.00')," Value expected is not -1 000.00 (or alert is not success)");
  })

  it('Test 100 Value', async function() {
    const resp = await axios.post(URL_FRONTEND_SERVICE, {
      numberID: 100.88
    });
    console.log(resp.data);
    assert.isTrue(resp.data.includes('class="alert alert-success">Value: 100.88')," Value expected is not 100.88 (or alert is not success)");
  })

  it('Test Long Value', async function() {
    const resp = await axios.post(URL_FRONTEND_SERVICE, {
        numberID: '111111111111111111111'
    });
    console.log(resp.data);
    assert.isTrue(resp.data.includes('class="alert alert-danger">Uh! Some Error in the input: Amount out of Boundaries')," Value expected is not Out Of Boundary (or alert is not Error)");
  })

  it('Test Delay in communication', async function() {
    const resp = await axios.post(URL_FRONTEND_SERVICE, {
        numberID: '1234'
    });
    assert.isTrue(resp.data.includes('class="alert alert-danger">Uh! Some Error in the input: TimeOut from server')," Value expected is not Timeout (or alert is not Error)");
  })

});
