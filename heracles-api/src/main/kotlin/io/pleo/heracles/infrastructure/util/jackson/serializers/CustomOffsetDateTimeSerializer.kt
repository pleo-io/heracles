package io.pleo.heracles.infrastructure.util.jackson.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Class to serialize an [OffsetDateTime] into a string.
 */
class CustomOffsetDateTimeSerializer : JsonSerializer<OffsetDateTime>() {

    @Throws(IOException::class)
    override fun serialize(offsetDateTime: OffsetDateTime, jsonGenerator: JsonGenerator, serializers: SerializerProvider) {
        jsonGenerator.writeString(offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME))
    }
}
