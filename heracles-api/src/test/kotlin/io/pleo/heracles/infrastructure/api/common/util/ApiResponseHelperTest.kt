package io.pleo.heracles.infrastructure.api.common.util

import io.mockk.every
import io.mockk.mockk
import io.pleo.heracles.infrastructure.api.common.Headers
import io.pleo.heracles.infrastructure.api.common.ResponseStatus
import io.pleo.heracles.infrastructure.api.common.dto.v1.Header
import io.pleo.heracles.infrastructure.api.common.dto.v1.Status
import java.time.OffsetDateTime
import javax.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ApiResponseHelperTest {
    private val groupId = "groupId"
    private val messageId = "messageId"
    private val timestamp = OffsetDateTime.now()

    private val errorCode = "Test.ErrorCode"
    private val errorMessage = "Test error message"

    private val servletRequest = mockk<HttpServletRequest> {
        every { getHeader(Headers.GROUP_ID.value) } returns groupId
        every { getHeader(Headers.MESSAGE_ID.value) } returns messageId
        every { getHeader(Headers.TIMESTAMP.value) } returns timestamp.toString()
    }

    private val requestHeader = Header(groupId = groupId, messageId = messageId, timestamp = timestamp)

    @Nested
    inner class CreateSuccessHeader {
        @Test
        fun `test correct success header created`() {
            val header = ApiResponseHelper.createSuccessHeader(httpRequest = servletRequest, header = requestHeader)

            assert(header.groupId == groupId)
            assert(header.responseStatus!!.status == ResponseStatus.SUCCESS.value)
            assert(header.responseStatus!!.errorCode == null)
            assert(header.responseStatus!!.errorMessage == null)
        }
    }

    @Nested
    inner class CreateFailureHeader {
        @Test
        fun `test correct failure header created when request header is supplied`() {
            val header = ApiResponseHelper.createFailureHeader(
                    httpRequest = servletRequest,
                    header = requestHeader,
                    errorCode = errorCode,
                    errorMessage = errorMessage
            )

            assert(header.groupId == groupId)
            assert(header.responseStatus!!.status == ResponseStatus.FAILURE.value)
            assert(header.responseStatus!!.errorCode == errorCode)
            assert(header.responseStatus!!.errorMessage == errorMessage)
        }

        @Test
        fun `test correct failure header created when request header is not supplied`() {
            val header = ApiResponseHelper.createFailureHeader(
                    httpRequest = servletRequest,
                    header = null,
                    errorCode = errorCode,
                    errorMessage = errorMessage
            )

            assert(header.groupId == groupId)
            assert(header.responseStatus!!.status == ResponseStatus.FAILURE.value)
            assert(header.responseStatus!!.errorCode == errorCode)
            assert(header.responseStatus!!.errorMessage == errorMessage)
        }
    }

    @Nested
    inner class CreateRejectedHeader {
        @Test
        fun `test correct rejected header created when request header is supplied`() {
            val header = ApiResponseHelper.createRejectedHeader(
                    httpRequest = servletRequest,
                    header = requestHeader,
                    errorCode = errorCode,
                    errorMessage = errorMessage
            )

            assert(header.groupId == groupId)
            assert(header.responseStatus!!.status == ResponseStatus.REJECTED.value)
            assert(header.responseStatus!!.errorCode == errorCode)
            assert(header.responseStatus!!.errorMessage == errorMessage)
        }

        @Test
        fun `test correct rejected header created when request header is not supplied`() {
            val header = ApiResponseHelper.createRejectedHeader(
                    httpRequest = servletRequest,
                    header = null,
                    errorCode = errorCode,
                    errorMessage = errorMessage
            )

            assert(header.groupId == groupId)
            assert(header.responseStatus!!.status == ResponseStatus.REJECTED.value)
            assert(header.responseStatus!!.errorCode == errorCode)
            assert(header.responseStatus!!.errorMessage == errorMessage)
        }
    }

    @Nested
    inner class CreateResponseEntity {
        @Test
        fun `test success response status entity created correctly`() {
            val responseHeader = Header(
                    groupId = groupId,
                    messageId = messageId,
                    timestamp = timestamp,
                    responseStatus = Status(status = ResponseStatus.SUCCESS.value)
            )
            val body = "this is the body"
            val responseEntity = ApiResponseHelper.createResponseEntity(responseHeader, body)

            assert(responseEntity.statusCode == HttpStatus.OK)

            assert(responseEntity.headers[Headers.GROUP_ID.value]!![0]  == groupId)
            assert(responseEntity.headers[Headers.MESSAGE_ID.value]!![0]  == messageId)
            assert(responseEntity.headers[Headers.TIMESTAMP.value]!![0]  == timestamp.toString())

            assert(responseEntity.hasBody())
            assert(responseEntity.body == body)
        }

        @Test
        fun `test rejected response status entity created correctly`() {
            val responseHeader = Header(
                    groupId = groupId,
                    messageId = messageId,
                    timestamp = timestamp,
                    responseStatus = Status(
                            status = ResponseStatus.REJECTED.value,
                            errorCode = errorCode,
                            errorMessage = errorMessage
                    )
            )
            val body = "this is the body"
            val responseEntity = ApiResponseHelper.createResponseEntity(responseHeader, body)

            assert(responseEntity.statusCode == HttpStatus.BAD_REQUEST)

            assert(responseEntity.headers[Headers.GROUP_ID.value]!![0]  == groupId)
            assert(responseEntity.headers[Headers.MESSAGE_ID.value]!![0]  == messageId)
            assert(responseEntity.headers[Headers.TIMESTAMP.value]!![0]  == timestamp.toString())

            assert(responseEntity.hasBody())
            assert(responseEntity.body == body)
        }

        @Test
        fun `test failure response status entity created correctly`() {
            val responseHeader = Header(
                    groupId = groupId,
                    messageId = messageId,
                    timestamp = timestamp,
                    responseStatus = Status(
                            status = ResponseStatus.FAILURE.value,
                            errorCode = errorCode,
                            errorMessage = errorMessage
                    )
            )
            val body = "this is the body"
            val responseEntity = ApiResponseHelper.createResponseEntity(responseHeader, body)

            assert(responseEntity.statusCode == HttpStatus.INTERNAL_SERVER_ERROR)

            assert(responseEntity.headers[Headers.GROUP_ID.value]!![0]  == groupId)
            assert(responseEntity.headers[Headers.MESSAGE_ID.value]!![0]  == messageId)
            assert(responseEntity.headers[Headers.TIMESTAMP.value]!![0]  == timestamp.toString())

            assert(responseEntity.hasBody())
            assert(responseEntity.body == body)
        }

        @Test
        fun `test unknown response status entity created correctly`() {
            val responseHeader = Header(
                    groupId = groupId,
                    messageId = messageId,
                    timestamp = timestamp,
                    responseStatus = Status(
                            status = ResponseStatus.UNKNOWN.value,
                            errorCode = errorCode,
                            errorMessage = errorMessage
                    )
            )
            val body = "this is the body"
            val responseEntity = ApiResponseHelper.createResponseEntity(responseHeader, body)

            assert(responseEntity.statusCode == HttpStatus.INTERNAL_SERVER_ERROR)

            assert(responseEntity.headers[Headers.GROUP_ID.value]!![0]  == groupId)
            assert(responseEntity.headers[Headers.MESSAGE_ID.value]!![0]  == messageId)
            assert(responseEntity.headers[Headers.TIMESTAMP.value]!![0]  == timestamp.toString())

            assert(responseEntity.hasBody())
            assert(responseEntity.body == body)
        }

        @Test
        fun `test unrecognized response status entity defaults to internal server error`() {
            val responseHeader = Header(
                    groupId = groupId,
                    messageId = messageId,
                    timestamp = timestamp,
                    responseStatus = Status(
                            status = "this is an unrecognized status",
                            errorCode = errorCode,
                            errorMessage = errorMessage
                    )
            )
            val body = "this is the body"
            val responseEntity = ApiResponseHelper.createResponseEntity(responseHeader, body)

            assert(responseEntity.statusCode == HttpStatus.INTERNAL_SERVER_ERROR)

            assert(responseEntity.headers[Headers.GROUP_ID.value]!![0]  == groupId)
            assert(responseEntity.headers[Headers.MESSAGE_ID.value]!![0]  == messageId)
            assert(responseEntity.headers[Headers.TIMESTAMP.value]!![0]  == timestamp.toString())

            assert(responseEntity.hasBody())
            assert(responseEntity.body == body)
        }
    }

    @Nested
    inner class CreateBasicHeaderFromHttpRequest {
        private val status = Status(status = ResponseStatus.SUCCESS.value)

        @Test
        fun `test correct header created when request header is supplied`() {
            val header = ApiResponseHelper.createBasicHeaderFromHttpRequest(
                    httpRequest = servletRequest,
                    requestHeader = requestHeader,
                    status = status
            )

            assert(header.groupId == groupId)
            assert(header.responseStatus!!.status == ResponseStatus.SUCCESS.value)
            assert(header.responseStatus!!.errorCode == null)
            assert(header.responseStatus!!.errorMessage == null)
        }

        @Test
        fun `test correct header created when request header is not supplied but messageId in the headers is present`() {
            every { servletRequest.getHeader(Headers.MESSAGE_ID.value) } returns "headerMessageId"
            every { servletRequest.getHeader(Headers.GROUP_ID.value) } returns null

            val header = ApiResponseHelper.createBasicHeaderFromHttpRequest(
                    httpRequest = servletRequest,
                    requestHeader = null,
                    status = status
            )

            assert(header.groupId == "headerMessageId")
            assert(header.responseStatus!!.status == ResponseStatus.SUCCESS.value)
            assert(header.responseStatus!!.errorCode == null)
            assert(header.responseStatus!!.errorMessage == null)
        }

        @Test
        fun `test correct header created when request header is not supplied but groupId in the headers is present`() {
            every { servletRequest.getHeader(Headers.GROUP_ID.value) } returns "headerGroupId"
            every { servletRequest.getHeader(Headers.MESSAGE_ID.value) } returns null

            val header = ApiResponseHelper.createBasicHeaderFromHttpRequest(
                    httpRequest = servletRequest,
                    requestHeader = null,
                    status = status
            )

            assert(header.groupId == "headerGroupId")
            assert(header.responseStatus!!.status == ResponseStatus.SUCCESS.value)
            assert(header.responseStatus!!.errorCode == null)
            assert(header.responseStatus!!.errorMessage == null)
        }
    }

    @Nested
    inner class CreateBasicHeaderFromHttpRequestHeader {
        @Test
        fun `test correct header created from http request header`() {
            val header = ApiResponseHelper.createBasicHeaderFromHttpRequestHeader(httpRequest = servletRequest)

            assert(header.groupId == groupId)
            assert(header.messageId == messageId)
        }

    }

    @Nested
    inner class CreateBasicHeader {
        private val status = Status(status = ResponseStatus.SUCCESS.value)

        @Test
        fun `test body groupId takes first priority`() {
            val header = ApiResponseHelper.createBasicHeader(
                    requestBodyGroupId = "bodyGroupId",
                    requestBodyMessageId = "bodyMessageId",
                    requestHeaderGroupId = "headerGroupId",
                    requestHeaderMessageId = "headerMessageId",
                    status = status
            )

            assert(header.groupId == "bodyGroupId")
            assert(header.responseStatus!! == status)
        }

        @Test
        fun `test header groupId takes second priority`() {
            val header = ApiResponseHelper.createBasicHeader(
                    requestBodyGroupId = null,
                    requestBodyMessageId = "bodyMessageId",
                    requestHeaderGroupId = "headerGroupId",
                    requestHeaderMessageId = "headerMessageId",
                    status = status
            )

            assert(header.groupId == "headerGroupId")
            assert(header.responseStatus!! == status)
        }

        @Test
        fun `test header messageId takes third priority`() {
            val header = ApiResponseHelper.createBasicHeader(
                    requestBodyGroupId = null,
                    requestBodyMessageId = null,
                    requestHeaderGroupId = "headerGroupId",
                    requestHeaderMessageId = "headerMessageId",
                    status = status
            )

            assert(header.groupId == "headerGroupId")
            assert(header.responseStatus!! == status)
        }

        @Test
        fun `test body messageId takes last priority`() {
            val header = ApiResponseHelper.createBasicHeader(
                    requestBodyGroupId = null,
                    requestBodyMessageId = null,
                    requestHeaderGroupId = null,
                    requestHeaderMessageId = "headerMessageId",
                    status = status
            )

            assert(header.groupId == "headerMessageId")
            assert(header.responseStatus!! == status)
        }
    }
}
