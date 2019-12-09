package com.amount.result;

public class ResultFormattedMoney {
    private int status;
    private String moneyFormatted;

    public ResultFormattedMoney(int status, String moneyFormatted) {
        this.status = status;
        this.moneyFormatted = moneyFormatted;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMoneyFormatted() {
        return this.moneyFormatted;
    }

}
