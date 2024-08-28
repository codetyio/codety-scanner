package io.codety.scanner.analyzer.eslint;

import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.dto.unmaterialized.CodetyUnmaterializedPluginGroup;
import io.codety.scanner.analyzer.dto.unmaterialized.CodetyUnmaterializedRule;
import io.codety.scanner.util.JsonFactoryUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class EslintConfigFileFactory {
    private static final String FAILED_TO_DESERIALIZE_ERROR_PREFIX = "Failed to deserialize json for ";
    private static final String JSON_PARSE_ERROR_MIDDLE = " code: ";

    @Value("${codety.base.path}")
    String basePath;
    @Value("${codety.eslint.path}")
    String eslintPath;

    public static final String CURRENT_RULE = "currentRule";
    private static final String quote = "\"";
    private static final String placeholder = "//CODETY";

    public File createEslintConfig(AnalyzerConfigurationDetailDto runnerConfiguration, String eslintVersion) {

        try {
            String payload = runnerConfiguration.getPayload();
//            CodetyConsoleLogger.debug("Eslint payload: " + payload);

            String content = null;
            String pluginCode = runnerConfiguration.getPluginCode();
            if(payload != null){
                CodetyUnmaterializedPluginGroup codetyUnmaterializedPluginGroup = JsonFactoryUtil.objectMapper.readValue(payload, CodetyUnmaterializedPluginGroup.class);
                File templateFile = new File(basePath + "/" + pluginCode);
                if(!templateFile.exists()){
                    throw new RuntimeException("Cannot load template file " + templateFile);
                }

                content = Files.readString(templateFile.toPath());
                StringBuilder sb = new StringBuilder();

                for(CodetyUnmaterializedRule unmaterializedRule : codetyUnmaterializedPluginGroup.getRules()){
                    String ruleId = unmaterializedRule.getRuleId();
                    //the default ruleId has no prefix. e.g.
                    if(!runnerConfiguration.getPluginCode().equals("eslint")){
                        ruleId = CURRENT_RULE + "/" + ruleId;
                    }
                    sb.append(quote).append(ruleId).append(quote).append(":").append(quote).append("error").append(quote).append(",");
                }

                content = content.replaceAll(placeholder, sb.toString());
            }else{
                File templateFile = new File(basePath + "/" + pluginCode + "-default");
                content = Files.readString(templateFile.toPath());
            }

            File targetFile = new File(eslintPath + "/eslint"+eslintVersion+"/eslint.config.mjs");
            //CodetyConsoleLogger.debug("Prepare to write file " + targetFile + " with content: " + content);
            targetFile.getParentFile().mkdirs();
            //CodetyConsoleLogger.debug("Writing config file to " + targetFile.getAbsolutePath());
            Files.write(targetFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
            return targetFile;

        } catch (Exception e) {

            throw new RuntimeException(FAILED_TO_DESERIALIZE_ERROR_PREFIX + runnerConfiguration.getLanguage() + JSON_PARSE_ERROR_MIDDLE + runnerConfiguration.getCodeAnalyzerType() + " " + e.getMessage());
        }
    }
}
