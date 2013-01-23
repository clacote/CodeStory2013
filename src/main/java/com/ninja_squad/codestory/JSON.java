package com.ninja_squad.codestory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja_squad.codestory.planning.Vol;

import java.io.IOException;
import java.util.List;

/**
 * JSON tools
 */
public abstract class JSON {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TypeReference<List<Vol>> TYPE = new TypeReference<List<Vol>>() {
    };

    public static String toJson(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to write JSON", e);
        }
    }

    public static List<Vol> toVols(String json) {
        try {
            return MAPPER.readValue(json, TYPE);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to read JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read JSON", e);
        }
    }
}
