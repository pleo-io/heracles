package com.amount.model;

import java.text.DecimalFormat;
import com.amount.result.ResultFormattedMoney;

public class FormatMoney {
    ResultFormattedMoney resultFormattedMoney;

    private String formatString(String str) {
        String strReversed = new StringBuilder(str).reverse().toString();
        String withSpaces = strReversed.replaceAll("...", "$0 ");

        return new StringBuilder(withSpaces).reverse().toString().replace("- ","-").trim();
    }

    public  FormatMoney(double amount) {
            DecimalFormat df = new DecimalFormat("0.00");
            String stringAmount = df.format(amount);
            String[] numberParts = stringAmount.split("\\.");

            resultFormattedMoney = new ResultFormattedMoney(0, this.formatString(numberParts[0]) + "." + numberParts[1]);
    }

    public ResultFormattedMoney getResultFormattedMoney() {
        return this.resultFormattedMoney;
    }
}
