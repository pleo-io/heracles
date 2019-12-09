package com.amount.helper;

import com.amount.exceptions.BoundaryException;
import org.junit.Test;

public class AmountValidationsTest {

    @Test(expected = BoundaryException.class)
    public void exceedBigNumberTest() throws BoundaryException {
        new AmountValidations().validateBoundaries(123456789012345678901234567890.00);
    }

    @Test(expected = BoundaryException.class)
    public void exceedBigNumberWithDecimalsTest() throws BoundaryException {
        new AmountValidations().validateBoundaries(123456789012345678901234567890.988);
    }

    @Test(expected = BoundaryException.class)
    public void exceedSmallNumberWithDecimalsTest() throws BoundaryException {
        new AmountValidations().validateBoundaries(-123456789012345678901234567890.988);
    }

    @Test
    public void exceedSmallNumberTest() throws BoundaryException {
        new AmountValidations().validateBoundaries(1.00);
    }

}