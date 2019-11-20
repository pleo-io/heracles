package io.pleo.heracles.infrastructure.api.common.util

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.pleo.heracles.infrastructure.api.common.dto.v1.ApiErrorResponse
import io.pleo.heracles.infrastructure.api.common.errors.ErrorCodes
import java.time.format.DateTimeParseException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalRestControllerExceptionHandler {

    val responseHelper = ApiResponseHelper

    /**
     * Custom override of [ResponseEntityExceptionHandler] handleHttpRequestMethodNotSupported method to handle a
     * [HttpRequestMethodNotSupportedException] and return our custom error codes and messages.
     *
     * @param ex [HttpRequestMethodNotSupportedException]
     * @param request [WebRequest]
     * @return ResponseEntity<Object>
    </Object> */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        request: WebRequest
    ): ResponseEntity<ApiErrorResponse> {
        val header = responseHelper.createRejectedHeader(
                request, responseHelper.lookupErrorCode(ErrorCodes.INVALID_METHOD_ERR.value),
                responseHelper.lookupErrorMessage(ErrorCodes.INVALID_METHOD_ERR.value)
        )
        val response = ApiErrorResponse(header, null)
        return responseHelper.createResponseEntity(header, response)
    }

    /**
     * Custom override of [ResponseEntityExceptionHandler] handleHttpMessageNotReadable method to handle a
     * [HttpMessageNotReadableException] thrown when a body is not supplied or when the body cannot be
     * serialized into a request class in the request and return our custom error codes and messages.
     *
     * @param ex [HttpRequestMethodNotSupportedException]
     * @param request [WebRequest]
     * @return ResponseEntity<Object>
    </Object> */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        request: WebRequest
    ): ResponseEntity<ApiErrorResponse> {
        val response: ApiErrorResponse
        when (ex.rootCause) {
            is MissingKotlinParameterException -> {
                val header = responseHelper.createRejectedHeader(
                        webRequest = request,
                        errorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value),
                        errorMessage = responseHelper.lookupErrorMessage(
                                ErrorCodes.MISSING_PARAMETER_ERR_MSG.value,
                                (ex.rootCause as MissingKotlinParameterException).parameter.name.toString())
                )
                response = ApiErrorResponse(header = header, result = null)
            }
            is DateTimeParseException -> {
                val header = responseHelper.createRejectedHeader(
                        webRequest = request,
                        errorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value),
                        errorMessage = responseHelper.lookupErrorMessage(
                                ErrorCodes.INVALID_TIMESTAMP_ERR_MSG.value,
                                (ex.rootCause as DateTimeParseException).parsedString)
                )
                response = ApiErrorResponse(header = header, result = null)
            }
            else -> {
                val header = responseHelper.createRejectedHeader(
                        webRequest = request,
                        errorCode = responseHelper.lookupErrorCode(ErrorCodes.INVALID_REQUEST_ERR.value),
                        errorMessage = ex.message.toString()
                )
                response = ApiErrorResponse(header = header, result = null)
            }
        }
        return responseHelper.createResponseEntity(response.header, response)
    }
}
