package io.pleo.heracles.application.services

import io.pleo.heracles.domain.model.MonetaryAmount
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

class MoneyFormattingService {

    fun format(
            monetaryAmount: MonetaryAmount,
            decimalPlaces: Int? = 2,
            thousandsSeparator: Char? = ',',
            decimalSeparator: Char? = '.',
            locale: Locale? = null
    ): String {
        val numberFormat: NumberFormat = if (locale != null) {
            createLocaleBasedDecimalFormat(decimalPlaces ?: 2, locale)
        } else {
            createCustomDecimalFormat(decimalPlaces!!, thousandsSeparator!!, decimalSeparator!!)
        }
        return numberFormat.format(monetaryAmount.valueInMajorUnits)
    }

    private fun createLocaleBasedDecimalFormat(decimalPlaces: Int, locale: Locale): DecimalFormat {
        val numberFormat: DecimalFormat = NumberFormat.getInstance(locale) as DecimalFormat
        numberFormat.maximumFractionDigits = decimalPlaces
        numberFormat.minimumFractionDigits = decimalPlaces
        return numberFormat
    }

    private fun createCustomDecimalFormat(
            decimalPlaces: Int,
            thousandsSeparator: Char,
            decimalSeparator: Char
    ): DecimalFormat {
        val numberFormat: DecimalFormat = NumberFormat.getInstance() as DecimalFormat
        numberFormat.maximumFractionDigits = decimalPlaces
        numberFormat.minimumFractionDigits = decimalPlaces

        val formatSymbols = DecimalFormatSymbols(Locale.getDefault());
        formatSymbols.decimalSeparator  = decimalSeparator
        formatSymbols.groupingSeparator = thousandsSeparator
        numberFormat.decimalFormatSymbols = formatSymbols
        return numberFormat
    }
}
