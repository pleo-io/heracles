package io.pleo.heracles.domain.model

import java.math.BigDecimal

data class MonetaryAmount(
        val currency: String,
        val valueInMajorUnits: BigDecimal
)
