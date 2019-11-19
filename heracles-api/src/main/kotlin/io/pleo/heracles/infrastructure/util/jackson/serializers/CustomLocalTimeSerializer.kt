package io.pleo.heracles.infrastructure.util.jackson.serializers

import java.io.IOException
import java.time.LocalTime

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class CustomLocalTimeSerializer : JsonSerializer<LocalTime>() {
    @Throws(IOException::class)
    override fun serialize(value: LocalTime, generator: JsonGenerator, serializers: SerializerProvider) {
        generator.writeString(value.toString())
    }
}
