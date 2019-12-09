package com.amount.model;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class FormatMoneyTest {

    @Test
    public void longDecimalTest() {
        FormatMoney fm = new FormatMoney(2310000.159897);
        assertTrue("true", "2 310 000.16".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void thousandSeparatorTest() {
        FormatMoney fm = new FormatMoney(1600);
        assertTrue("true", "1 600.00".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void roundThridDigitUpTest() {
        FormatMoney fm = new FormatMoney(1234.998);
        assertTrue("true", "1 235.00".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void roundThridDigitDownTest() {
        FormatMoney fm = new FormatMoney(1234.991);
        assertTrue("true", "1 234.99".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void negativeThousandSeparatorTest() {
        FormatMoney fm = new FormatMoney(-1600);
        assertTrue("true", "-1 600.00".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void small3DigitsNegativeTest() {
        FormatMoney fm = new FormatMoney(-160);
        assertTrue("true", "-160.00".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void small2DigitsNegativeTest() {
        FormatMoney fm = new FormatMoney(-16);
        assertTrue("true", "-16.00".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void negativeWithDecimalsTest() {
        FormatMoney fm = new FormatMoney(-45722.977);
        assertTrue("true", "-45 722.98".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void zeroTest() {
        FormatMoney fm = new FormatMoney(0);
        assertTrue("true", "0.00".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void smallNumberTest() {
        FormatMoney fm = new FormatMoney(0.00001);
        assertTrue("true", "0.00".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void bigNumberWithDecimalsTest() {
        FormatMoney fm = new FormatMoney(123456789.98);
        assertTrue("true", "123 456 789.98".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }

    @Test
    public void bigNumberTest() {
        FormatMoney fm = new FormatMoney(1234567890.00);
        assertTrue("true", "1 234 567 890.00".equals(fm.getResultFormattedMoney().getMoneyFormatted()));
    }


}