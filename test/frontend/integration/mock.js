var express = require('express');

var app = express();
function pausecomp(ms) {
    ms += new Date().getTime();
    while (new Date() < ms){}
};

app.get('/format', function(req, res){
    amount = req.query.amount;
    let response = {};
    console.log(`Value received in mock: '${amount}'`);

    if (amount === '1') {
        response = { status :0, moneyFormatted : "1.00"};
    }
    if (amount === 'a') {
        response = {"status":400,"moneyFormatted":"Error trying to format the number"};
    }
    if (amount === '') {
        response = {"status":0,"moneyFormatted":"0.00"};
    }
    if (amount === '-1000.00') {
        response = {"status":0,"moneyFormatted":"-1 000.00"};
    }
    if (amount === '100.88') {
        response = {"status":0,"moneyFormatted":"100.88"};
    }
    if (amount === '111111111111111111111') {
        response = {"status":101,"moneyFormatted":"Amount out of Boundaries"};
    }
    if (amount === '1234') {
        pausecomp(5000) ;
        response = {"status":0,"moneyFormatted":"1 234"};
    }

    if (amount === '999') {
        pausecomp(1000) ;
        process.exit();
    }

    res.send(response);
});

app.listen(8080, () => console.log(`Mock  started on port 8080`))