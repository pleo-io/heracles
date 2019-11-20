package io.pleo.heracles.integration

import io.pleo.heracles.HeraclesApplication
import io.pleo.heracles.infrastructure.api.common.Headers
import io.pleo.heracles.infrastructure.api.common.ResponseStatus
import io.pleo.heracles.infrastructure.api.common.dto.v1.Amount
import io.pleo.heracles.infrastructure.api.common.dto.v1.Header
import io.pleo.heracles.infrastructure.api.common.errors.ErrorCodes
import io.pleo.heracles.infrastructure.api.common.util.ApiResponseHelper
import io.pleo.heracles.infrastructure.api.formatamount.v1.FormatAmountRequest
import io.pleo.heracles.infrastructure.api.formatamount.v1.FormatAmountResponse
import java.net.URI
import java.time.OffsetDateTime
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("test")
@ContextConfiguration(classes = [HeraclesApplication::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @Value("\${http.auth-token-header-name}")
    private val apiKeyHeader: String? = null

    @Value("\${http.auth-token}")
    private val apiKey: String? = null

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val groupId = "groupId"
    private val messageId = "testMessageId"
    private val timestamp = OffsetDateTime.now()

    private val valueInMinorUnits = 100099L
    private val currency = "KES"
    private val precision = 2
    private val expectedFormattedAmount = "1,000.99"

    private val testRequest = FormatAmountRequest(
            header = Header(groupId = groupId, messageId = messageId, timestamp = timestamp),
            amount = Amount(currency = currency, value = valueInMinorUnits, precision = precision)
    )

    private val formatAmountEndpoint = "/api/v1/formatAmount"

    @Nested
    inner class SuccessCase {
        @Test
        fun `test amount formatted and success response returned`() {
            val headers = generateHttpHeaders()

            val requestEntity: RequestEntity<FormatAmountRequest> = RequestEntity(
                    testRequest, headers, HttpMethod.POST, URI(formatAmountEndpoint))
            val responseEntity: ResponseEntity<FormatAmountResponse> = restTemplate.exchange(
                    requestEntity, FormatAmountResponse::class.java)

            val response: FormatAmountResponse = responseEntity.body!!

            val expectedResponseStatus = ResponseStatus.SUCCESS.value

            assert(HttpStatus.OK == responseEntity.statusCode)
            assert(responseEntity.headers[Headers.GROUP_ID.value]!![0] == response.header.groupId)
            assert(responseEntity.headers[Headers.MESSAGE_ID.value]!![0] == response.header.messageId)
            assert(responseEntity.headers[Headers.TIMESTAMP.value]!![0] == response.header.timestamp.toString())

            assert(expectedResponseStatus == response.header.responseStatus!!.status)
            assert("$currency $expectedFormattedAmount" == response.formattedAmount)
            assert(null == response.header.responseStatus!!.errorCode)
            assert(null == response.header.responseStatus!!.errorMessage)
        }
    }

    @Nested
    inner class FailureCase {
        @Test
        fun `test rejected response returned due to missing or invalid api key`() {
            val headers = generateHttpHeaders()
            headers.set(apiKeyHeader!!, null)

            val requestEntity: RequestEntity<FormatAmountRequest> = RequestEntity(
                    testRequest, headers, HttpMethod.POST, URI(formatAmountEndpoint))
            val responseEntity: ResponseEntity<FormatAmountResponse> = restTemplate.exchange(
                    requestEntity, FormatAmountResponse::class.java)

            val response: FormatAmountResponse = responseEntity.body!!

            val expectedResponseStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = ApiResponseHelper.lookupErrorCode(ErrorCodes.INVALID_API_KEY_ERR.value)
            val expectedErrorMessage = ApiResponseHelper.lookupErrorMessage(ErrorCodes.INVALID_API_KEY_ERR.value)

            assert(HttpStatus.UNAUTHORIZED == responseEntity.statusCode)
            assert(expectedResponseStatus == response.header.responseStatus!!.status)
            assert(expectedErrorCode == response.header.responseStatus!!.errorCode)
            assert(expectedErrorMessage == response.header.responseStatus!!.errorMessage)
        }

        @Test
        fun `test rejected response returned due to bad http method requested`() {
            val headers = generateHttpHeaders()

            val requestEntity: RequestEntity<FormatAmountRequest> = RequestEntity(
                    testRequest, headers, HttpMethod.DELETE, URI(formatAmountEndpoint))
            val responseEntity: ResponseEntity<FormatAmountResponse> = restTemplate.exchange(
                    requestEntity, FormatAmountResponse::class.java)

            val response: FormatAmountResponse = responseEntity.body!!

            val expectedResponseStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = ApiResponseHelper.lookupErrorCode(ErrorCodes.INVALID_METHOD_ERR.value)
            val expectedErrorMessage = ApiResponseHelper.lookupErrorMessage(ErrorCodes.INVALID_METHOD_ERR.value)

            assert(HttpStatus.BAD_REQUEST == responseEntity.statusCode)
            assert(expectedResponseStatus == response.header.responseStatus!!.status)
            assert(expectedErrorCode == response.header.responseStatus!!.errorCode)
            assert(expectedErrorMessage == response.header.responseStatus!!.errorMessage)
        }

        @Test
        fun `test rejected response returned due to missing parameter`() {
            val headers = generateHttpHeaders()
            headers.remove(Headers.MESSAGE_ID.value)

            val requestEntity: RequestEntity<FormatAmountRequest> = RequestEntity(
                    testRequest, headers, HttpMethod.POST, URI(formatAmountEndpoint))
            val responseEntity: ResponseEntity<FormatAmountResponse> = restTemplate.exchange(
                    requestEntity, FormatAmountResponse::class.java)

            val response: FormatAmountResponse = responseEntity.body!!

            val expectedResponseStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = ApiResponseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value)
            val expectedErrorMessage = ApiResponseHelper.lookupErrorMessage(
                    ErrorCodes.MISSING_MESSAGE_ID_HEADER_ERR_MSG.value
            )

            assert(HttpStatus.BAD_REQUEST == responseEntity.statusCode)
            assert(expectedResponseStatus == response.header.responseStatus!!.status)
            assert(expectedErrorCode == response.header.responseStatus!!.errorCode)
            assert(expectedErrorMessage == response.header.responseStatus!!.errorMessage)
        }

        @Test
        fun `test rejected response returned due to invalid timestamp`() {
            val headers = generateHttpHeaders()
            val invalidTimestamp = "invalid timestamp"
            headers.set(Headers.TIMESTAMP.value, invalidTimestamp)

            val requestEntity: RequestEntity<FormatAmountRequest> = RequestEntity(
                    testRequest, headers, HttpMethod.POST, URI(formatAmountEndpoint))
            val responseEntity: ResponseEntity<FormatAmountResponse> = restTemplate.exchange(
                    requestEntity, FormatAmountResponse::class.java)

            val response: FormatAmountResponse = responseEntity.body!!

            val expectedResponseStatus = ResponseStatus.REJECTED.value
            val expectedErrorCode = ApiResponseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value)
            val expectedErrorMessage = ApiResponseHelper.lookupErrorMessage(
                    ErrorCodes.INVALID_TIMESTAMP_ERR_MSG.value, invalidTimestamp
            )

            assert(HttpStatus.BAD_REQUEST == responseEntity.statusCode)
            assert(expectedResponseStatus == response.header.responseStatus!!.status)
            assert(expectedErrorCode == response.header.responseStatus!!.errorCode)
            assert(expectedErrorMessage == response.header.responseStatus!!.errorMessage)
        }
    }

    private fun generateHttpHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.add(Headers.GROUP_ID.value, groupId)
        headers.add(Headers.MESSAGE_ID.value, messageId)
        headers.add(Headers.TIMESTAMP.value, OffsetDateTime.now().toString())
        headers.add(apiKeyHeader!!, apiKey)
        return headers
    }
}
