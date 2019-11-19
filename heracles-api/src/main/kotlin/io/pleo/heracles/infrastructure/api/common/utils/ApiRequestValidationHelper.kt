package io.pleo.heracles.infrastructure.api.common.utils

import io.pleo.heracles.infrastructure.api.common.Headers
import io.pleo.heracles.infrastructure.api.common.dto.v1.Header
import io.pleo.heracles.infrastructure.api.common.errors.ErrorCodes
import io.pleo.heracles.infrastructure.api.common.exceptions.MalformedRequestException
import java.lang.Exception
import java.time.OffsetDateTime
import javax.servlet.http.HttpServletRequest

object ApiRequestValidationHelper {

    private val responseHelper = ApiResponseHelper

    @Throws(MalformedRequestException::class)
    fun validateTimestampField(timestamp: String) {
        try {
            OffsetDateTime.parse(timestamp)
        } catch (err: Exception) {
            throw MalformedRequestException(
                    responseHelper.lookupErrorMessage(ErrorCodes.INVALID_TIMESTAMP_ERR_MSG.value, timestamp))
        }
    }

    @Throws(MalformedRequestException::class)
    fun validateMandatoryRequestHeaders(servletRequest: HttpServletRequest) {
        servletRequest.getHeader(Headers.MESSAGE_ID.value)
                ?: throw MalformedRequestException(
                        responseHelper.lookupErrorMessage(ErrorCodes.MISSING_MESSAGE_ID_HEADER_ERR_MSG.value))

        val timestamp = servletRequest.getHeader(Headers.TIMESTAMP.value)
                ?: throw MalformedRequestException(
                        responseHelper.lookupErrorMessage(ErrorCodes.MISSING_TIMESTAMP_HEADER_ERR_MSG.value))
        this.validateTimestampField(timestamp)

    }


    /**
     * A simple method to validate expected fields in a request header.
     *
     * @param header {@link Header} expected on all API requests
     * @throws MalformedRequestException an exception indicating that the request is malformed
     */
    @Throws(MalformedRequestException::class)
    fun validateBodyRequestHeader(header: Header, servletRequest: HttpServletRequest) {
        this.validateMandatoryRequestHeaders(servletRequest)
        val headerMessageId = servletRequest.getHeader(Headers.MESSAGE_ID.value)
        val headerGroupId = servletRequest.getHeader(Headers.GROUP_ID.value)

        // both messageId's are not null, yet they differ.
        val bodyMessageId = header.messageId
        if (bodyMessageId != headerMessageId) {
            throw MalformedRequestException(
                    responseHelper.lookupErrorMessage(ErrorCodes.CONFLICTING_MESSAGE_ID_ERR_MSG.value))
        }
        // both groupId's are not null, yet they differ.
        val bodyGroupId = header.groupId
        if (headerGroupId != null && bodyGroupId != headerGroupId) {
            throw MalformedRequestException(
                    responseHelper.lookupErrorMessage(ErrorCodes.CONFLICTING_GROUP_ID_ERR_MSG.value))
        }
    }
}
