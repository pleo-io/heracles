package io.pleo.heracles.infrastructure.api.formatamount.v1

import io.mockk.every
import io.mockk.mockk
import io.pleo.heracles.application.exceptions.UnknownLocaleException
import io.pleo.heracles.application.services.MoneyFormattingService
import io.pleo.heracles.infrastructure.api.common.Headers
import io.pleo.heracles.infrastructure.api.common.ResponseStatus
import io.pleo.heracles.infrastructure.api.common.dto.v1.Amount
import io.pleo.heracles.infrastructure.api.common.dto.v1.Header
import io.pleo.heracles.infrastructure.api.common.errors.ErrorCodes
import io.pleo.heracles.infrastructure.api.common.util.ApiResponseHelper
import java.time.OffsetDateTime
import javax.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class FormatAmountRestControllerTest {

    private val valueInMinorUnits = 100099L
    private val currency = "KES"
    private val precision = 2
    private val mockedFormattedAmount = "1,000.99"

    private val groupId = "groupId"
    private val messageId = "testMessageId"
    private val timestamp = OffsetDateTime.now()

    private var httpRequestWithGroupId = mockk<HttpServletRequest> {
        every { getHeader(Headers.GROUP_ID.value) } returns groupId
        every { getHeader(Headers.MESSAGE_ID.value) } returns messageId
        every { getHeader(Headers.TIMESTAMP.value) } returns timestamp.toString()
    }

    private var moneyFormattingService = mockk<MoneyFormattingService> {
        every { format(any(), any(), any(), any(), any()) } returns mockedFormattedAmount
    }

    private val formatAmountRestController = FormatAmountRestController(moneyFormattingService)

    private val formatAmountRequest = FormatAmountRequest(
            header = Header(groupId = groupId, messageId = messageId, timestamp = timestamp),
            amount = Amount(currency = currency, value = valueInMinorUnits, precision = precision)
    )

    @Nested
    inner class FormatAmount {
        @Test
        fun `test amount formatted and success response returned`() {
            val response = formatAmountRestController.formatAmount(formatAmountRequest, httpRequestWithGroupId)
            val responseBody = response.body!!

            assert(response.statusCode == HttpStatus.OK)
            assert(response.headers[Headers.GROUP_ID.value]!![0] == groupId)
            assert("$currency $mockedFormattedAmount" == responseBody.formattedAmount)
        }

        @Test
        fun `test MalformedRequestException handled and rejected response returned`() {
            every { httpRequestWithGroupId.getHeader(Headers.TIMESTAMP.value) } returns null

            val response = formatAmountRestController.formatAmount(formatAmountRequest, httpRequestWithGroupId)
            val responseBody = response.body!!

            val expectedResponseStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = ApiResponseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value)
            val expectedErrorMessage = ApiResponseHelper.lookupErrorMessage(
                    ErrorCodes.MISSING_TIMESTAMP_HEADER_ERR_MSG.value
            )

            assert(response.statusCode == HttpStatus.BAD_REQUEST)
            assert(response.headers[Headers.GROUP_ID.value]!![0] == groupId)
            assert(responseBody.header.responseStatus!!.status == expectedResponseStatus)
            assert(responseBody.header.responseStatus!!.errorCode == expectedErrorCode)
            assert(responseBody.header.responseStatus!!.errorMessage == expectedErrorMessage)
        }

        @Test
        fun `test UnknownLocaleException handled and rejected response returned`() {
            val exceptionMessage = "unknown locale"
            val unknownLocaleException = UnknownLocaleException(exceptionMessage)
            every { moneyFormattingService.format(any(), any(), any(), any(), any()) } throws unknownLocaleException

            val response = formatAmountRestController.formatAmount(formatAmountRequest, httpRequestWithGroupId)
            val responseBody = response.body!!

            val expectedResponseStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = ApiResponseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value)
            val expectedErrorMessage = unknownLocaleException.message

            assert(response.statusCode == HttpStatus.BAD_REQUEST)
            assert(response.headers[Headers.GROUP_ID.value]!![0] == groupId)
            assert(responseBody.header.responseStatus!!.status == expectedResponseStatus)
            assert(responseBody.header.responseStatus!!.errorCode == expectedErrorCode)
            assert(responseBody.header.responseStatus!!.errorMessage == expectedErrorMessage)
        }

        @Test
        fun `test unknown error handled and failure response returned`() {
            val exceptionMessage = "Boy! Are we in a pickle now!"
            val unknownException = Exception(exceptionMessage)
            every { moneyFormattingService.format(any(), any(), any(), any(), any()) } throws unknownException

            val response = formatAmountRestController.formatAmount(formatAmountRequest, httpRequestWithGroupId)
            val responseBody = response.body!!

            val expectedResponseStatus = ResponseStatus.FAILURE.value
            val expectedErrorCode = ApiResponseHelper.lookupErrorCode(ErrorCodes.UNKNOWN_FAILURE.value)
            val expectedErrorMessage = unknownException.message

            assert(response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR)
            assert(response.headers[Headers.GROUP_ID.value]!![0] == groupId)
            assert(responseBody.header.responseStatus!!.status == expectedResponseStatus)
            assert(responseBody.header.responseStatus!!.errorCode == expectedErrorCode)
            assert(responseBody.header.responseStatus!!.errorMessage == expectedErrorMessage)
        }
    }
}
