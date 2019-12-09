package com.amount.helper;

import com.amount.exceptions.BoundaryException;

public class AmountValidations {
    private static final double UPPER_BOUNDARY = 1234567890.00;
    private static final double LOWER_BOUNDARY = -1234567890.00;

    public static void validateBoundaries(double amount) throws BoundaryException {
        if ((amount > UPPER_BOUNDARY) || (amount < LOWER_BOUNDARY)) {
            throw new BoundaryException("Out of boundary");
        }
    }

    public static double validateDouble(String amount) throws Exception {
        return Double.parseDouble(amount);

    }
}
