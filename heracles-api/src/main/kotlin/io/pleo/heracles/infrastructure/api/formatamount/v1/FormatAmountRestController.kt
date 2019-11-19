package io.pleo.heracles.infrastructure.api.formatamount.v1

import io.pleo.heracles.infrastructure.api.common.errors.ErrorCodes
import io.pleo.heracles.infrastructure.api.common.exceptions.MalformedRequestException
import io.pleo.heracles.infrastructure.api.common.utils.ApiRequestValidationHelper
import io.pleo.heracles.infrastructure.api.common.utils.ApiResponseHelper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
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
            this.validateFormatAmountRequest(formatAmountRequest, httpRequest)
            FormatAmountResponse(
                    header = responseHelper.createSuccessHeader(httpRequest, formatAmountRequest.header))


        } catch (err: MalformedRequestException) {
            FormatAmountResponse(
                    header = responseHelper.createRejectedHeader(
                            httpRequest = httpRequest,
                            header = formatAmountRequest.header,
                            errorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value),
                            errorMessage = err.message))

        } catch (err: Exception) {
            FormatAmountResponse(
                    header = responseHelper.createFailureHeader(
                            httpRequest = httpRequest,
                            header = formatAmountRequest.header,
                            errorCode = responseHelper.lookupErrorCode(ErrorCodes.UNKNOWN_FAILURE.value),
                            errorMessage = err.toString()))
        }
        return responseHelper.createResponseEntity(response.header, response)
    }

    @Throws(MalformedRequestException::class)
    private fun validateFormatAmountRequest(request: FormatAmountRequest, servletRequest: HttpServletRequest) {

        this.validationHelper.validateBodyRequestHeader(header = request.header, servletRequest = servletRequest)

        if (request.amount == null) {
            throw MalformedRequestException(
                    responseHelper.lookupErrorMessage(
                            code = ErrorCodes.MISSING_PARAMETER_ERR_MSG.value,
                            params = *arrayOf("amount")
                    )
            )
        }
    }
}
