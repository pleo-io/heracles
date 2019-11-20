package io.pleo.heracles.application.services

import io.pleo.heracles.application.util.Localizer
import io.pleo.heracles.domain.model.MonetaryAmount
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import org.springframework.stereotype.Service

private const val DEFAULT_DECIMAL_PLACES = 2
private const val DEFAULT_THOUSANDS_SEPARATOR = ','
private const val DEFAULT_DECIMAL_SEPARATOR = '.'

/**
 * Money formatting service
 */
@Service
class MoneyFormattingService {

    /**
     * Method that takes a [MonetaryAmount] and various user configurations
     * and returns a string formatted value of the amount.
     */
    fun format(
        monetaryAmount: MonetaryAmount,
        decimalPlaces: Int? = 2,
        thousandsSeparator: Char? = null,
        decimalSeparator: Char? = null,
        localeString: String? = null
    ): String {
        val locale = localeString?.let { Localizer.resolveLocale(it) }
        return this.createDecimalFormat(locale, decimalPlaces, thousandsSeparator, decimalSeparator)
                .format(monetaryAmount.valueInMajorUnits)
    }

    /**
     * Creates a [DecimalFormat] customized with the passed in configuration
     */
    private fun createDecimalFormat(
        locale: Locale?,
        decimalPlaces: Int?,
        thousandsSeparator: Char?,
        decimalSeparator: Char?
    ): DecimalFormat {
        val numberFormat: NumberFormat
        val minimumFractionDigits: Int
        val maximumFractionDigits: Int
        val groupingSeparatorChar: Char
        val decimalSeparatorChar: Char

        if (locale != null) {
            numberFormat = NumberFormat.getInstance(locale) as DecimalFormat
            minimumFractionDigits = decimalPlaces ?: numberFormat.minimumFractionDigits
            maximumFractionDigits = decimalPlaces ?: numberFormat.maximumFractionDigits
            groupingSeparatorChar = thousandsSeparator ?: numberFormat.decimalFormatSymbols.groupingSeparator
            decimalSeparatorChar = decimalSeparator ?: numberFormat.decimalFormatSymbols.decimalSeparator
        } else {
            numberFormat = NumberFormat.getInstance() as DecimalFormat
            minimumFractionDigits = decimalPlaces ?: DEFAULT_DECIMAL_PLACES
            maximumFractionDigits = decimalPlaces ?: DEFAULT_DECIMAL_PLACES
            groupingSeparatorChar = thousandsSeparator ?: DEFAULT_THOUSANDS_SEPARATOR
            decimalSeparatorChar = decimalSeparator ?: DEFAULT_DECIMAL_SEPARATOR
        }

        numberFormat.maximumFractionDigits = minimumFractionDigits
        numberFormat.minimumFractionDigits = maximumFractionDigits

        val formatSymbols = DecimalFormatSymbols(Locale.getDefault())
        formatSymbols.groupingSeparator = groupingSeparatorChar
        formatSymbols.decimalSeparator = decimalSeparatorChar
        numberFormat.decimalFormatSymbols = formatSymbols

        return numberFormat
    }
}
