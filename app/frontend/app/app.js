var express = require('express');
var request = require('request');

const numberValidator = require('./js/number.js')

const path = require('path');
const bodyParser = require('body-parser');
const config = require(path.join(__dirname+'/config/config'));

var app = express();
app.set('views', path.join(__dirname+'/views'));
app.use(express.static(__dirname + '/images'));
app.use(express.static(__dirname + '/css'));
app.use(express.static(__dirname + '/js'));
app.set('view engine', 'pug');
app.use(bodyParser());

app.get('/', function(req, res){
    res.render('home');
});

app.post('/', function(req, res){
    const number = req.body.numberID;
    if (numberValidator.validate(number)) {
        console.log("Conencting to " + config.service.url);
        request({
            url: config.service.url,
            qs: {amount: number },
            method: 'GET'
        }, function(error, response, body){
        if(error) {
            res.render('error', {value_number: "Something happened with the server comunication!" }  );
        } else {
            //console.log(response.statusCode, body);
            if (JSON.parse(body).status === 0) {
                res.render('response', {value_number: JSON.parse(body).moneyFormatted} );
            } else {
                res.render('error', {value_number: JSON.parse(body).moneyFormatted} );
            }
        }
        });        
    } else {
        res.render('error', {value_number: 'Please Enter a number'} );
    }

});

app.listen(config.app.port);