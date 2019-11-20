package io.pleo.heracles.infrastructure.util.jackson.exceptions

/**
 * Simple exception to be used when json serialization fails
 *
 * @author kelvin.wahome
 *
 */
class JsonSerializationException(cause: Throwable) : Exception(cause)
