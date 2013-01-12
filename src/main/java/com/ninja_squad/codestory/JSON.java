package com.ninja_squad.codestory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON tools
 */
public abstract class JSON {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJson(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to write JSON", e);
        }
    }
}
