package com.amount;

import com.amount.model.FormatMoney;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Convert {

    public static void main(String[] args) {
        SpringApplication.run(Convert.class, args);
        //double amount = -160.00;

        //FormatMoney fm = new FormatMoney(amount);
    }
}