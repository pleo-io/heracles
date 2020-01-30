var assert = require('assert');
const number = require('../../app/js/number')

describe('Number Unit Tests', function() {
    it('Test Simple Value', function() {
        const realNumber = number.validate('1');
        assert.equal(realNumber, true);
    });
    
    it('Test Not a Number - Strings', function() {
        const realNumber = number.validate('ss');
        assert.equal(realNumber, false);
    });

    it('Test Not a Number - Number + String', function() {
        const realNumber = number.validate('1ss');
        assert.equal(realNumber, false);
    });

    it('Test Number with spaces', function() {
        const realNumber = number.validate('1 000');
        assert.equal(realNumber, false);
    });

    it('Test Negative Number', function() {
        const realNumber = number.validate('-1');
        assert.equal(realNumber, true);
    });

    it('Test Float Number', function() {
        const realNumber = number.validate('-1.99');
        assert.equal(realNumber, true);
    });

    it('Test Not a Number - Special Characters', function() {
        const realNumber = number.validate('&&$$%$!@');
        assert.equal(realNumber, false);
    });
});