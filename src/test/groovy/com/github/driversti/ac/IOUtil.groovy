package com.github.driversti.ac

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.nio.file.Files
import org.springframework.core.io.ClassPathResource

class IOUtil {

    private static final ObjectMapper objectMapper

    static String jsonFromPath(String path) {
        def filePath = new ClassPathResource(path).getFile().toPath()
        Files.readString(filePath)
    }

    static <T> T readFromJson(String json, Class<T> clazz) {
        objectMapper.readValue(json, clazz)
    }

    static <T> T readFromJson(String json, TypeReference<T> typeReference) {
        objectMapper.readValue(json, typeReference)
    }

    static <T> T readFromPath(String path, Class<T> clazz) {
        def json = jsonFromPath(path)
        readFromJson(json, clazz)
    }

    static <T> T readFromPath(String path, TypeReference<T> typeReference) {
        def json = jsonFromPath(path)
        readFromJson(json, typeReference)
    }

    static {
        objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
    }
}
