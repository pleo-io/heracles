package io.pleo.heracles.infrastructure.util.jackson.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.LocalTime

class CustomLocalTimeSerializer : JsonSerializer<LocalTime>() {
    @Throws(IOException::class)
    override fun serialize(value: LocalTime, generator: JsonGenerator, serializers: SerializerProvider) {
        generator.writeString(value.toString())
    }
}
