package io.codety.scanner.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JsonFactoryUtil {
    public static final ObjectMapper objectMapper = new JsonMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false)
            .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            ;
}
