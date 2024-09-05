package io.codety.scanner.analyzer.eslint;

import io.codety.common.dto.CodeAnalyzerPluginType;
import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.eslint.dto.EslintResults;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.RuntimeExecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EslintCodeAnalyzer implements CodeAnalyzerInterface {

    @Autowired
    EslintConfigFileFactory eslintConfigFileFactory;

    @Value("${codety.eslint.path}")
    String eslintPath;

    private static final String eslint = "eslint";
    private static final String eslintVersion = "eslint9";
    private static final String eslint8Version = "eslint8";
    private static final String eslintBinPath = "/node_modules/.bin/eslint";
    private static final String paramFormat = "--format";
    private static final String paramFormatValue = "json-with-metadata";
    private static final String paramConfig = "--config";
    private static final String paramNoInlineConfig = "--no-inline-config";
    private static final String paramQuiet = "--quiet";
    private static final String noErrorOnUnmatchedPattern = "--no-error-on-unmatched-pattern";
    private static final String rootExecutionPath = "/";
    private static final String infoStartProcessing = "Start processing: ";
    private static final String infoFailedEslint = "Failed to process analyzer, ";
    String typescriptPluginCode = "typescript-eslint";
    String eslintUseFlatConfig = "ESLINT_USE_FLAT_CONFIG";
    String aTrue = "true";
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        CodetyConsoleLogger.info("Scanning "+runnerConfiguration.getLanguage()+" code via "+runnerConfiguration.getPluginCode()+"...");
        List<CodeAnalysisResultDto> result = new ArrayList<>();

        CodetyConsoleLogger.debug(infoStartProcessing + runnerConfiguration.getLanguage() + " " + runnerConfiguration.getPluginCode());
        try {
            String eslintVer = "9";
            Map<String, String> additionalEnv = null;
            if(runnerConfiguration.getPluginCode().equals(typescriptPluginCode)){
                additionalEnv = Map.of(eslintUseFlatConfig, aTrue);
                eslintVer = "8";
            }

            File eslintConfig = eslintConfigFileFactory.createEslintConfig(runnerConfiguration, eslintVer);

            String[] command;
            String configFilePath = eslintConfig.getAbsolutePath();
            String sourceLocation = Path.of(request.getLocalGitRepoPath()).toString().toString();
            CodetyConsoleLogger.debug("source location: " + sourceLocation);

            String eslintNpmFolder = eslint + eslintVer;
            command = new String[]{eslintPath + "/" + eslintNpmFolder + eslintBinPath, paramConfig, configFilePath, paramQuiet, noErrorOnUnmatchedPattern, paramNoInlineConfig, paramFormat, paramFormatValue, sourceLocation};
            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, rootExecutionPath, 60, false, additionalEnv);
            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();

            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());
            result.add(resultDto);

            if(errorOutput!=null && !errorOutput.isEmpty()){
                CodetyConsoleLogger.debug(eslint + " execution error:" + errorOutput);
                return result;
            }
            CodetyConsoleLogger.debug(eslint + " analysis result: " + successOutput);

            EslintResults deserialize = EslintJsonWithMetadataDeserializer.deserialize(successOutput);

            CodetyConsoleLogger.debug(eslint + " results from deserialization:" + (deserialize== null || deserialize.getResults() == null ? 0 :deserialize.getResults().length));
            EslintResultsConverter.convertFormat(resultDto, deserialize, sourceLocation);

        }catch (Exception e){
            CodetyConsoleLogger.debug(infoFailedEslint, e);
            CodetyConsoleLogger.info(infoFailedEslint + e.getMessage());
        }
        return result;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {

        List<CodeAnalysisResultDto> result = new ArrayList<>();

        result.addAll(getCodeAnalysisResultDtos(request, CodeAnalyzerPluginType.EslintHtml));
        result.addAll(getCodeAnalysisResultDtos(request, CodeAnalyzerPluginType.EslintJsdoc));
//        result.addAll(getCodeAnalysisResultDtos(request, CodeAnalyzerPluginType.EslintTypeScript));

        return result;
    }

    private List<CodeAnalysisResultDto> getCodeAnalysisResultDtos(AnalyzerRequest request, String pluginCode) {
        AnalyzerConfigurationDetailDto runnerConfiguration = new AnalyzerConfigurationDetailDto(LanguageType.javascript, CodeAnalyzerType.eslint);
        runnerConfiguration.setPluginCode(pluginCode);
        List<CodeAnalysisResultDto> codeAnalysisResultDtos = analyzeCode(runnerConfiguration, request);
        return codeAnalysisResultDtos;
    }

}
