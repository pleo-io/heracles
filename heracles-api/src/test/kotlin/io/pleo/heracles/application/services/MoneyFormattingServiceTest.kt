package io.pleo.heracles.application.services

import io.pleo.heracles.domain.model.MonetaryAmount
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MoneyFormattingServiceTest {

    private val monetaryAmount = MonetaryAmount(currency = "KES", valueInMajorUnits = BigDecimal.valueOf(1000))

    private val moneyFormattingService = MoneyFormattingService()

    @Nested
    inner class Format {
        @Test
        fun `test amount formatted with default preferences if none is supplied`() {
            val expected = "1,000.00"
            assert(expected == moneyFormattingService.format(monetaryAmount = monetaryAmount))
        }

        @Test
        fun `test amount formatted with locale preferences if supplied`() {
            val expected = "1٬000٫00"  // note the thousands separator is not a '.' for ar locale
            assert(expected == moneyFormattingService.format(monetaryAmount = monetaryAmount, localeString = "ar"))
        }

        @Test
        fun `test amount formatted with supplied separator and decimal places preferences`() {
            val expected = "1 000'0000"
            assert(
                    expected ==
                    moneyFormattingService.format(
                            monetaryAmount = monetaryAmount,
                            decimalPlaces = 4,
                            thousandsSeparator = ' ',
                            decimalSeparator = '\''
                    )
            )
        }
    }
}
