package io.pleo.heracles.infrastructure.util.logging.aspect

import io.pleo.heracles.infrastructure.api.common.Headers
import javax.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component

@Aspect
@Component
class RestControllerLoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    protected fun restController() {
    }

    /**
     * Public methods point cut
     */
    @Pointcut("execution(public * *(..))")
    protected fun publicOperation() {
    }

    /**
     * Add log context to the MDC map when the execution path reach a
     * resource that is annotated with @RestController.
     *
     *
     * before -> any public resource annotated with @Controller
     */
    @Before("restController() && publicOperation()")
    fun logBefore(joinPoint: JoinPoint) {
        MDC.put("CONTROLLER", joinPoint.signature.declaringTypeName)
        this.setRequestDetailsInLogContext(joinPoint)
        LOGGER.info("Request received")
    }

    /**
     * Remove log context from the MDC map when the execution path exits a
     * resource that is annotated with @RestController.
     *
     *
     * after -> Any public resource annotated with @Controller and public
     * method returns
     */
    @AfterReturning(pointcut = "restController() && publicOperation())", returning = "result")
    fun logAfter(joinPoint: JoinPoint, result: Any) {
        MDC.put("RESPONSE", result.toString())
        LOGGER.info("Response returned")
        MDC.clear()
    }

    /**
     * Log the exception for any resource annotated with @RestController.
     */
    @AfterThrowing(pointcut = "restController()", throwing = "exception")
    fun logAfterThrowing(joinPoint: JoinPoint, exception: Throwable) {
        MDC.put("EXCEPTION_MESSAGE", exception.message)
        MDC.put("STACK_TRACE", exception.stackTrace.toString())
        LOGGER.error("An exception has occurred")
        MDC.clear()
    }

    private fun setRequestDetailsInLogContext(joinPoint: JoinPoint) {
        for (`object` in joinPoint.args) {
            if (`object` is HttpServletRequest) {
                MDC.put("METHOD", `object`.method)
                MDC.put("URI", `object`.requestURI)
                MDC.put("REQUEST_GROUP_ID", `object`.getHeader(Headers.GROUP_ID.value))
                MDC.put("REQUEST_MESSAGE_ID", `object`.getHeader(Headers.MESSAGE_ID.value))
                MDC.put("REQUEST_TIMESTAMP", `object`.getHeader(Headers.TIMESTAMP.value))
                break
            }
        }
        MDC.put("REQUEST", joinPoint.args[0].toString())
    }

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(RestControllerLoggingAspect::class.java)
    }
}
