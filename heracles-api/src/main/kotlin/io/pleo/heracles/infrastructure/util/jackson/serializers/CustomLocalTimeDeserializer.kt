package io.pleo.heracles.infrastructure.util.jackson.serializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.time.LocalTime

class CustomLocalTimeDeserializer : JsonDeserializer<LocalTime>() {
    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalTime {
        return LocalTime.parse(parser.text)
    }
}
