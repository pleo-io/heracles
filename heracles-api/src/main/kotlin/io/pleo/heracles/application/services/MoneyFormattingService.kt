package io.pleo.heracles.application.services

import io.pleo.heracles.domain.model.MonetaryAmount
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

private const val DEFAULT_DECIMAL_PLACES = 2
private const val DEFAULT_THOUSANDS_SEPARATOR = ','
private const val DEFAULT_DECIMAL_SEPARATOR = '.'

class MoneyFormattingService {

    fun format(
            monetaryAmount: MonetaryAmount,
            decimalPlaces: Int? = 2,
            thousandsSeparator: Char? = ',',
            decimalSeparator: Char? = '.',
            locale: Locale? = null
    ): String {
        val numberFormat = this.customizeDecimalFormat(
                numberFormat = this.createDecimalFormat(locale),
                decimalPlaces = decimalPlaces ?: DEFAULT_DECIMAL_PLACES,
                thousandsSeparator = thousandsSeparator ?: DEFAULT_THOUSANDS_SEPARATOR,
                decimalSeparator = decimalSeparator ?: DEFAULT_DECIMAL_SEPARATOR
        )
        return numberFormat.format(monetaryAmount.valueInMajorUnits)
    }

    private fun createDecimalFormat(locale: Locale?): DecimalFormat {
        return if (locale != null) {
            NumberFormat.getInstance(locale) as DecimalFormat
        } else {
            NumberFormat.getInstance() as DecimalFormat
        }
    }

    private fun customizeDecimalFormat(
            numberFormat: DecimalFormat,
            decimalPlaces: Int,
            thousandsSeparator: Char,
            decimalSeparator: Char
    ): DecimalFormat {
        numberFormat.maximumFractionDigits = decimalPlaces
        numberFormat.minimumFractionDigits = decimalPlaces

        val formatSymbols = DecimalFormatSymbols(Locale.getDefault())
        formatSymbols.groupingSeparator = thousandsSeparator
        formatSymbols.decimalSeparator  = decimalSeparator
        numberFormat.decimalFormatSymbols = formatSymbols
        return numberFormat
    }
}
