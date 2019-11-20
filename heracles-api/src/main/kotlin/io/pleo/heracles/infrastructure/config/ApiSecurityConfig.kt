package io.pleo.heracles.infrastructure.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.pleo.heracles.infrastructure.api.common.dto.v1.ApiErrorResponse
import io.pleo.heracles.infrastructure.api.common.errors.ErrorCodes
import io.pleo.heracles.infrastructure.api.common.util.ApiResponseHelper
import java.io.IOException
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class ApiSecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${http.endpoint-pattern}")
    private val antPattern: String? = null

    @Value("\${http.auth-token-header-name}")
    private val principalRequestHeader: String? = null

    @Value("#{'\${http.auth-token}'.split(',')}")
    private val principalRequestValues: List<String>? = null

    @Autowired
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint? = null

    @Bean
    internal fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.cors()
        httpSecurity.antMatcher(antPattern).csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilter(apiAuthenticationFilter())
                .authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
    }

    fun apiAuthenticationFilter(): ApiKeyAuthFilter? {
        val filter = ApiKeyAuthFilter(principalRequestHeader)
        filter.setAuthenticationManager { authentication ->
            val principal = authentication.principal as String
            val validPrincipal = principalRequestValues!!.stream().anyMatch {
                principalRequestValue -> principalRequestValue.trim { it <= ' ' } == principal }
            if (!validPrincipal) {
                //not made a constant as this value gets converted by to a message that adheres to API standards
                // in the RestAuthenticationEntryPoint below.
                throw BadCredentialsException("Invalid API Key received.")
            }
            authentication.isAuthenticated = true
            authentication
        }
        return filter
    }

    @Component
    class RestAuthenticationEntryPoint : AuthenticationEntryPoint {

        @Autowired
        private val objectMapper: ObjectMapper? = null

        val responseHelper = ApiResponseHelper

        @Throws(IOException::class, ServletException::class)
        override fun commence(
                httpServletRequest: HttpServletRequest,
                httpServletResponse: HttpServletResponse,
                exception: AuthenticationException
        ) {
            httpServletResponse.contentType = "application/json"
            httpServletResponse.status = HttpStatus.UNAUTHORIZED.value()
            val header = responseHelper.createRejectedHeader(
                    httpServletRequest,
                    null,
                    responseHelper.lookupErrorCode(ErrorCodes.INVALID_API_KEY_ERR.value),
                    responseHelper.lookupErrorMessage(ErrorCodes.INVALID_API_KEY_ERR.value))
            val response = ApiErrorResponse(header = header, result = null)

            val out = httpServletResponse.outputStream
            objectMapper!!.writeValue(out, response)
            out.flush()
        }
    }

    inner class ApiKeyAuthFilter(private val principalRequestHeader: String?) :
            AbstractPreAuthenticatedProcessingFilter() {

        override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any {
            return request.getHeader(principalRequestHeader)
        }

        override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any {
            return "N/A"
        }
    }
}
