package io.codety.scanner.service;

import io.codety.scanner.analyzer.dto.materialized.CodetyRulesetPullApiResponseProtocolDto;
import io.codety.scanner.util.JsonFactoryUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DeserializationTest {

    @Test
    public void testDeserialize() throws IOException {
        String s = Files.readString(Path.of(this.getClass().getResource("/pull-api/response.json").getPath()));
        CodetyRulesetPullApiResponseProtocolDto resultProtocolDto = JsonFactoryUtil.objectMapper.readValue(s, CodetyRulesetPullApiResponseProtocolDto.class);
        Assertions.assertNotNull(resultProtocolDto);
        Assertions.assertEquals(resultProtocolDto.getPayloads().length, 5);
        Assertions.assertEquals(resultProtocolDto.getPayloads()[0].getLanguage(), "java");
        Assertions.assertEquals(resultProtocolDto.getPayloads()[0].getCodeAnalyzerType(), 2);
        Assertions.assertTrue(resultProtocolDto.getPayloads()[0].getPayload().contains("category/java/bestpractices.xml/AccessorClassGeneration"));
    }
}
