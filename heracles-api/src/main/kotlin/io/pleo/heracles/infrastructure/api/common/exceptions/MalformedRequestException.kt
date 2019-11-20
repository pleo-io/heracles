package io.pleo.heracles.infrastructure.api.common.exceptions

/**
 * Simple exception to be used when a request is missing required information.
 *
 * @author kelvin.wahome
 *
 */
class MalformedRequestException(override val message: String) : Exception(message)
