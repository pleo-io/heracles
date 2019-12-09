package com.amount.controller;

import com.amount.exceptions.BoundaryException;
import com.amount.model.FormatMoney;
import com.amount.helper.AmountValidations;
import com.amount.result.ResultFormattedMoney;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormatMoneyController {
    private final int ERROR_STATUS_GENERIC = 400;
    private final String ERROR_MESSAGE_MESSAGE = "Error trying to format the number";
    private final int ERROR_STATUS_BOUNDARIES = 101;
    private final String ERROR_MESSAGE_BOUNDARIES = "Amount out of Boundaries";

    @RequestMapping("/format")
    public ResultFormattedMoney formatMoney(@RequestParam(value="amount", defaultValue="0.00") String amount) {
        try {
            double value = AmountValidations.validateDouble(amount);
            AmountValidations.validateBoundaries(value);
            return new FormatMoney(value).getResultFormattedMoney();
        } catch (BoundaryException boundEx) {
            return new ResultFormattedMoney(ERROR_STATUS_BOUNDARIES, ERROR_MESSAGE_BOUNDARIES);
        } catch (Exception ex) {
            return new ResultFormattedMoney(ERROR_STATUS_GENERIC, ERROR_MESSAGE_MESSAGE);
        }
    }

}
