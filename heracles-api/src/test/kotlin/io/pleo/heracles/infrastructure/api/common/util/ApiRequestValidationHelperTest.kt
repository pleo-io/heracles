package io.pleo.heracles.infrastructure.api.common.util

import io.mockk.every
import io.mockk.mockk
import io.pleo.heracles.infrastructure.api.common.Headers
import io.pleo.heracles.infrastructure.api.common.dto.v1.Header
import io.pleo.heracles.infrastructure.api.common.exceptions.MalformedRequestException
import java.time.OffsetDateTime
import javax.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ApiRequestValidationHelperTest {

    private val groupId = "groupId"
    private val messageId = "messageId"
    private val timestamp = OffsetDateTime.now()

    private val servletRequest = mockk<HttpServletRequest> {
        every { getHeader(Headers.GROUP_ID.value) } returns groupId
        every { getHeader(Headers.MESSAGE_ID.value) } returns messageId
        every { getHeader(Headers.TIMESTAMP.value) } returns timestamp.toString()
    }

    private val requestHeader = Header(groupId = groupId, messageId = messageId, timestamp = timestamp)

    @Nested
    inner class ValidateTimestamp {
        @Test
        fun `test valid timestamp string passes validation`() {
            assertDoesNotThrow {
                ApiRequestValidationHelper.validateTimestampField(OffsetDateTime.now().toString())
            }
        }

        @Test
        fun `test invalid timestamp string fails validation`() {
            assertThrows<MalformedRequestException> {
                ApiRequestValidationHelper.validateTimestampField("invalid timestamp")
            }
        }
    }

    @Nested
    inner class ValidateMandatoryRequestHeaders {
        @Test
        fun `test validation passes if messageId and timestamp are present and valid`() {
            assertDoesNotThrow {
                ApiRequestValidationHelper.validateMandatoryRequestHeaders(servletRequest)
            }
        }

        @Test
        fun `test validation passes if groupId is missing`() {
            every { servletRequest.getHeader(Headers.GROUP_ID.value) } returns null
            assertDoesNotThrow {
                ApiRequestValidationHelper.validateMandatoryRequestHeaders(servletRequest)
            }
        }

        @Test
        fun `test missing messageId fails validation`() {
            every { servletRequest.getHeader(Headers.MESSAGE_ID.value) } returns null
            assertThrows<MalformedRequestException> {
                ApiRequestValidationHelper.validateMandatoryRequestHeaders(servletRequest)
            }
        }

        @Test
        fun `test missing timestamp fails validation`() {
            every { servletRequest.getHeader(Headers.TIMESTAMP.value) } returns null
            assertThrows<MalformedRequestException> {
                ApiRequestValidationHelper.validateMandatoryRequestHeaders(servletRequest)
            }
        }

        @Test
        fun `test invalid timestamp fails validation`() {
            every { servletRequest.getHeader(Headers.TIMESTAMP.value) } returns "invalid timestamp"
            assertThrows<MalformedRequestException> {
                ApiRequestValidationHelper.validateMandatoryRequestHeaders(servletRequest)
            }
        }
    }

    @Nested
    inner class ValidateBodyRequestHeader {
        @Test
        fun `test validation passes if header is present and valid`() {
            assertDoesNotThrow {
                ApiRequestValidationHelper.validateBodyRequestHeader(requestHeader, servletRequest)
            }
        }

        @Test
        fun `test validation fails if messageId in body header differs with messageId in http headers`() {
            every { servletRequest.getHeader(Headers.MESSAGE_ID.value) } returns "conflicting messageId"
            assertThrows<MalformedRequestException> {
                ApiRequestValidationHelper.validateBodyRequestHeader(requestHeader, servletRequest)
            }
        }

        @Test
        fun `test validation fails if groupId in body header differs with groupId in http headers while present`() {
            every { servletRequest.getHeader(Headers.GROUP_ID.value) } returns "conflicting groupId"
            assertThrows<MalformedRequestException> {
                ApiRequestValidationHelper.validateBodyRequestHeader(requestHeader, servletRequest)
            }
        }
    }
}
