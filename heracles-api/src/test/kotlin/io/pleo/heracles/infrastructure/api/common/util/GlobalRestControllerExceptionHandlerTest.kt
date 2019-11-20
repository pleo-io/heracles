package io.pleo.heracles.infrastructure.api.common.util

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.mockk.every
import io.mockk.mockk
import io.pleo.heracles.infrastructure.api.common.Headers
import io.pleo.heracles.infrastructure.api.common.ResponseStatus
import io.pleo.heracles.infrastructure.api.common.errors.ErrorCodes
import java.time.OffsetDateTime
import java.time.format.DateTimeParseException
import kotlin.reflect.KParameter
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.context.request.WebRequest

class GlobalRestControllerExceptionHandlerTest {
    private val groupId = "groupId"
    private val messageId = "messageId"
    private val timestamp = OffsetDateTime.now()

    private val request = mockk<WebRequest> {
        every { getHeader(Headers.GROUP_ID.value) } returns groupId
        every { getHeader(Headers.MESSAGE_ID.value) } returns messageId
        every { getHeader(Headers.TIMESTAMP.value) } returns timestamp.toString()
    }

    private val globalRestControllerExceptionHandler = GlobalRestControllerExceptionHandler()
    private val responseHelper = ApiResponseHelper

    @Nested
    inner class HandleHttpRequestMethodNotSupported {
        @Test
        fun `test correct API error response returned`() {

            val response = globalRestControllerExceptionHandler.handleHttpRequestMethodNotSupported(mockk(), request)
            val responseBody = response.body!!
            val responseBodyHeader = responseBody.header
            val expectedStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_METHOD_ERR.value)
            val expectedErrorMessage = responseHelper.lookupErrorMessage(ErrorCodes.INVALID_METHOD_ERR.value)

            assert(response.statusCode == HttpStatus.BAD_REQUEST)
            assert(responseBodyHeader.responseStatus!!.status == expectedStatus)
            assert(responseBodyHeader.responseStatus!!.errorCode == expectedErrorCode)
            assert(responseBodyHeader.responseStatus!!.errorMessage == expectedErrorMessage)
        }
    }

    @Nested
    inner class HandleHttpMessageNotReadable {
        @Test
        fun `test correct API error response returned when message body is missing`() {
            val httpMessageNotReadableException = HttpMessageNotReadableException(
                    "Message not readable", Exception("Message body missing"), mockk()
            )
            val response = globalRestControllerExceptionHandler.handleHttpMessageNotReadable(
                    httpMessageNotReadableException, request
            )
            val responseBody = response.body!!
            val responseBodyHeader = responseBody.header
            val expectedStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value)
            val expectedErrorMessage = httpMessageNotReadableException.message

            assert(response.statusCode == HttpStatus.BAD_REQUEST)
            assert(responseBodyHeader.responseStatus!!.status == expectedStatus)
            assert(responseBodyHeader.responseStatus!!.errorCode == expectedErrorCode)
            assert(responseBodyHeader.responseStatus!!.errorMessage == expectedErrorMessage)
        }

        @Test
        fun `test correct API error response returned when a required parameter is missing`() {
            val missingParameter = "missingParameter"
            val missingKParameter = mockk<KParameter> {
                every { name } returns missingParameter
            }
            val httpMessageNotReadableException = HttpMessageNotReadableException(
                    "Message not readable",
                    MissingKotlinParameterException(parameter = missingKParameter, msg = "Missing parameter"),
                    mockk()
            )
            val response = globalRestControllerExceptionHandler.handleHttpMessageNotReadable(
                    httpMessageNotReadableException, request
            )
            val responseBody = response.body!!
            val responseBodyHeader = responseBody.header
            val expectedStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value)
            val expectedErrorMessage = responseHelper.lookupErrorMessage(
                    ErrorCodes.MISSING_PARAMETER_ERR_MSG.value, missingParameter
            )

            assert(response.statusCode == HttpStatus.BAD_REQUEST)
            assert(responseBodyHeader.responseStatus!!.status == expectedStatus)
            assert(responseBodyHeader.responseStatus!!.errorCode == expectedErrorCode)
            assert(responseBodyHeader.responseStatus!!.errorMessage == expectedErrorMessage)
        }

        @Test
        fun `test correct API error response returned when a date time cannot be parsed`() {

            val parsedData = mockk<CharSequence>()
            val dateTimeParseException = DateTimeParseException("timestamp not a valid date", parsedData, 0)
            val httpMessageNotReadableException = HttpMessageNotReadableException(
                    "Message not readable",
                    dateTimeParseException,
                    mockk()
            )
            val response = globalRestControllerExceptionHandler.handleHttpMessageNotReadable(
                    httpMessageNotReadableException, request
            )
            val responseBody = response.body!!
            val responseBodyHeader = responseBody.header
            val expectedStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value)
            val expectedErrorMessage = responseHelper.lookupErrorMessage(
                    ErrorCodes.INVALID_TIMESTAMP_ERR_MSG.value, parsedData.toString()
            )

            assert(response.statusCode == HttpStatus.BAD_REQUEST)
            assert(responseBodyHeader.responseStatus!!.status == expectedStatus)
            assert(responseBodyHeader.responseStatus!!.errorCode == expectedErrorCode)
            assert(responseBodyHeader.responseStatus!!.errorMessage == expectedErrorMessage)
        }
    }
}
