package io.pleo.heracles.infrastructure.api.formatamount.v1

import io.pleo.heracles.application.services.MoneyFormattingService
import io.pleo.heracles.domain.model.MonetaryAmount
import io.pleo.heracles.infrastructure.api.common.errors.ErrorCodes
import io.pleo.heracles.infrastructure.api.common.exceptions.MalformedRequestException
import io.pleo.heracles.infrastructure.api.common.utils.ApiRequestValidationHelper
import io.pleo.heracles.infrastructure.api.common.utils.ApiResponseHelper
import io.pleo.heracles.util.Localizer
import io.pleo.heracles.util.exceptions.UnknownLocaleException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import javax.servlet.http.HttpServletRequest

@RestController
class FormatAmountRestController {

    val responseHelper = ApiResponseHelper
    val validationHelper = ApiRequestValidationHelper

    @PostMapping("/api/v1/formatAmount")
    fun formatAmount(
            @RequestBody formatAmountRequest: FormatAmountRequest,
            httpRequest: HttpServletRequest
    ): ResponseEntity<FormatAmountResponse> {

        val response: FormatAmountResponse
        response = try {
            this.validationHelper.validateBodyRequestHeader(
                    header = formatAmountRequest.header,
                    servletRequest = httpRequest
            )
            val locale = formatAmountRequest.locale?.let { Localizer.resolveLocale(it) }
            val amount = formatAmountRequest.amount
            val monetaryAmount = MonetaryAmount(
                    currency = amount.currency,
                    valueInMajorUnits = BigDecimal(
                            amount.value.toString()).divide(BigDecimal(10).pow(amount.precision)
                    )
            )
            val formattedAmount = MoneyFormattingService().format(
                    monetaryAmount,
                    decimalPlaces = formatAmountRequest.decimalPlaces,
                    thousandsSeparator = formatAmountRequest.thousandsSeparator?.single(),
                    decimalSeparator = formatAmountRequest.decimalSeparator?.single(),
                    locale = locale
            )
            FormatAmountResponse(
                    header = responseHelper.createSuccessHeader(httpRequest, formatAmountRequest.header),
                    formattedAmount = "${monetaryAmount.currency} $formattedAmount"
            )

        } catch (err: MalformedRequestException) {
            FormatAmountResponse(
                    header = responseHelper.createRejectedHeader(
                            httpRequest = httpRequest,
                            header = formatAmountRequest.header,
                            errorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value),
                            errorMessage = err.message))

        } catch (err: UnknownLocaleException) {
            FormatAmountResponse(
                    header = responseHelper.createRejectedHeader(
                            httpRequest = httpRequest,
                            header = formatAmountRequest.header,
                            errorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value),
                            errorMessage = err.message!!))

        } catch (err: Throwable) {
            FormatAmountResponse(
                    header = responseHelper.createFailureHeader(
                            httpRequest = httpRequest,
                            header = formatAmountRequest.header,
                            errorCode = responseHelper.lookupErrorCode(ErrorCodes.UNKNOWN_FAILURE.value),
                            errorMessage = err.toString()))
        }
        return responseHelper.createResponseEntity(response.header, response)
    }
}
