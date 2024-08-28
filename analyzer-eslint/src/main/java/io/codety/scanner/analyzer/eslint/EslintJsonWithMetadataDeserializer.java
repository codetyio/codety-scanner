package io.codety.scanner.analyzer.eslint;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.eslint.dto.EslintResults;
import io.codety.scanner.util.JsonFactoryUtil;

public class EslintJsonWithMetadataDeserializer {


    public static EslintResults deserialize(String s) throws JsonProcessingException {

        EslintResults eslintResults = JsonFactoryUtil.objectMapper.readValue(s, EslintResults.class);

        return eslintResults;
    }
}
