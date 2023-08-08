package com.serverless.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

public class SerializeUtil<T> {
    private static final ObjectMapper om = new ObjectMapper();

    /**
     * DeSerializes the JSON string passed in the method argument to the respective class type
     * passed in the method argument
     *
     * @param json
     * @param toValueType
     * @param <T>
     * @return POJO deserialized from the json string or null if deserialization fails
     */
    public static <T> T deserialize(String json, Class toValueType) {
        T t = null;
        try {
            t = (T) om.readValue(json, toValueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Serializes the POJO and returns JSON string
     *
     * @param toValueType
     * @param <T>
     * @return Serialized JSON String of the POJO passed in the method argument
     */
    public static <T> String serialize(T toValueType) {
        String s = null;
        try {
            s = om.writeValueAsString(toValueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
