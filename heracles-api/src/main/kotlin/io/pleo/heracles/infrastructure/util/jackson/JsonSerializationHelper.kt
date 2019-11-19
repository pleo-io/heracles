package io.pleo.heracles.infrastructure.util.jackson

import java.time.LocalTime
import java.time.OffsetDateTime

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.pleo.heracles.infrastructure.util.jackson.exceptions.JsonSerializationException
import io.pleo.heracles.infrastructure.util.jackson.serializers.CustomLocalTimeDeserializer
import io.pleo.heracles.infrastructure.util.jackson.serializers.CustomLocalTimeSerializer
import io.pleo.heracles.infrastructure.util.jackson.serializers.CustomOffsetDateTimeDeserializer
import io.pleo.heracles.infrastructure.util.jackson.serializers.CustomOffsetDateTimeSerializer
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

/**
 * Utility class to serialize and deserialize objects to and from JSON.
 */
object JsonSerializationHelper {
    /**
     * Method to serialize a java Object into a JSON string.
     *
     * @param object  POJO
     * @param modules an array of custom [Module] extensions to default functionality
     * @return String
     * @throws JsonSerializationException an exception used to wrap any caught [Throwable]
     */
    @Throws(JsonSerializationException::class)
    fun serializeToJson(`object`: Any, vararg modules: Module): String {
        try {
            return getObjectMapper(*modules).writeValueAsString(`object`)
        } catch (throwable: Throwable) {
            throw JsonSerializationException(throwable)
        }

    }

    /**
     * Method to deserialize a JSON string into a java Object of specified type.
     *
     * @param jsonString    JSON string
     * @param objectClass   Object class
     * @param modules       an array of custom [Module] extensions to default functionality
     * @param <ObjectClass> object class
     * @return objectClass
     * @throws JsonSerializationException an exception used to wrap any caught [Throwable]
    </ObjectClass> */
    @Throws(JsonSerializationException::class)
    fun <ObjectClass> deserializeFromJson(
            jsonString: String, objectClass: Class<ObjectClass>, vararg modules: Module): ObjectClass {
        try {
            return getObjectMapper(*modules).readValue(jsonString, objectClass)
        } catch (throwable: Throwable) {
            throw JsonSerializationException(throwable)
        }

    }

    /**
     * Method to deserialize a JSON string into a java Object of specified type.
     *
     * @param jsonString            JSON string
     * @param modules               an array of custom [Module] extensions to default functionality
     * @param <ObjectTypeReference> object class
     * @return [ObjectTypeReference]
     * @throws JsonSerializationException an exception used to wrap any caught [Throwable]
    </ObjectTypeReference> */
    @Throws(JsonSerializationException::class)
    fun <ObjectTypeReference> deserializeFromJson(
            jsonString: String, typeReference: TypeReference<ObjectTypeReference>, vararg modules: Module): ObjectTypeReference {
        try {
            return getObjectMapper(*modules).readValue(jsonString, typeReference)
        } catch (throwable: Throwable) {
            throw JsonSerializationException(throwable)
        }

    }

    private fun getObjectMapper(vararg modules: Module): ObjectMapper {
        val javaTimeModule = JavaTimeModule()

        // offset date time
        javaTimeModule.addDeserializer(OffsetDateTime::class.java, CustomOffsetDateTimeDeserializer())
        javaTimeModule.addSerializer(OffsetDateTime::class.java, CustomOffsetDateTimeSerializer())

        // local time
        javaTimeModule.addDeserializer(LocalTime::class.java, CustomLocalTimeDeserializer())
        javaTimeModule.addSerializer(LocalTime::class.java, CustomLocalTimeSerializer())

        val objectMapper = Jackson2ObjectMapperBuilder().build<ObjectMapper>()
        objectMapper.registerModule(javaTimeModule)
        objectMapper.registerModules(*modules)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return objectMapper
    }
}
