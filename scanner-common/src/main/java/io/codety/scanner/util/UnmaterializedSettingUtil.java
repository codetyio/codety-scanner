package io.codety.scanner.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.dto.unmaterialized.CodetyUnmaterializedPluginGroup;

public class UnmaterializedSettingUtil {
    public static CodetyUnmaterializedPluginGroup convertToUnmaterializedSettingDto(AnalyzerConfigurationDetailDto runnerConfiguration) throws Exception {

        String payload = runnerConfiguration.getPayload();
        try {
            CodetyUnmaterializedPluginGroup codetyUnmaterializedPluginGroup = JsonFactoryUtil.objectMapper.readValue(payload, CodetyUnmaterializedPluginGroup.class);
            return codetyUnmaterializedPluginGroup;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize Unmaterialized setting. " + e.getMessage());
        }

    }
}
