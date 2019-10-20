package com.alexromanov.heracles.service;

import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Service
public class MoneyConverterService {
	public String formatMoney(double inputMoney){
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setGroupingSeparator(' ');

		DecimalFormat formatter = new DecimalFormat("###,###.00", symbols);
		return formatter.format(inputMoney);
	}
}
