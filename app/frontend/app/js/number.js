class Number {
    validate(number) {
        const converted_number = +number;
        if (isNaN(converted_number)) {
          return false;
        }
        return typeof converted_number === 'number'
    }
  }
  
  module.exports = new Number();