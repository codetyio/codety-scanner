package io.codety.scanner.analyzer.pylint;

import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.dto.unmaterialized.CodetyUnmaterializedPluginGroup;
import io.codety.scanner.analyzer.dto.unmaterialized.CodetyUnmaterializedRule;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.RuntimeExecUtil;
import io.codety.scanner.util.UnmaterializedSettingUtil;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PylintCodeAnalyzer implements CodeAnalyzerInterface {
    private static final String infoStartProcessing = "Start processing: ";
    private static final String cmdPylint = "pylint";

    String errorMsg = " execution error:";
    String resultInfo = "result: ";
    String message = "Cannot get any unmaterialized rules.";
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        CodetyConsoleLogger.debug("Scanning "+runnerConfiguration.getLanguage()+" code via "+runnerConfiguration.getCodeAnalyzerType().name()+"...");
        List<CodeAnalysisResultDto> result = new ArrayList<>();

        CodetyConsoleLogger.debug(infoStartProcessing + runnerConfiguration.getLanguage() + " " + runnerConfiguration.getPluginCode());
        try {
            CodetyUnmaterializedPluginGroup codetyUnmaterializedPluginGroup = UnmaterializedSettingUtil.convertToUnmaterializedSettingDto(runnerConfiguration);
            CodetyUnmaterializedRule[] rules = codetyUnmaterializedPluginGroup.getRules();
            if (rules == null || rules.length == 0) {

                throw new RuntimeException(message);
            }
            CodeAnalysisResultDto resultDto = analyzeCodeUsingPylint(runnerConfiguration, request, rules);
            result.add(resultDto);

        } catch (Exception e) {
            CodetyConsoleLogger.debug(cmdPylint + " Failed ", e);
            CodetyConsoleLogger.info(cmdPylint + " Failed ");
        }
        return result;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        AnalyzerConfigurationDetailDto analyzerConfigurationDetailDto = new AnalyzerConfigurationDetailDto(LanguageType.python, CodeAnalyzerType.pylint);
        List<CodeAnalysisResultDto> result = new ArrayList<>();
        try {
            CodeAnalysisResultDto codeAnalysisResultDto = analyzeCodeUsingPylint(analyzerConfigurationDetailDto, request, null);
            result.add(codeAnalysisResultDto);

        } catch (Exception e) {
            CodetyConsoleLogger.debug(cmdPylint + " Failed ", e);
            CodetyConsoleLogger.info(cmdPylint + " Failed ");
        }

        return result;
    }

    public CodeAnalysisResultDto analyzeCodeUsingPylint(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request, CodetyUnmaterializedRule[] rules) throws Exception {
        String localGitRepoPath = request.getLocalGitRepoPath();
        String[] command = createCommandAry(rules, localGitRepoPath);

        RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, null, 60, false, null);
        String errorOutput = runtimeExecResult.getErrorOutput();
        String successOutput = runtimeExecResult.getSuccessOutput();
        if (errorOutput != null && !errorOutput.isEmpty()) {
            CodetyConsoleLogger.debug(cmdPylint + errorMsg + errorOutput);
        }

//        CodetyConsoleLogger.debug(cmdPylint + resultInfo + successOutput);

        CodeAnalysisResultDto resultDto = PylintResultsConverter.convertFormat(successOutput, runnerConfiguration);
        return resultDto;
    }

    private String[] createCommandAry(CodetyUnmaterializedRule[] rules, String localGitRepoPath) {
        List<String> commandList = new ArrayList();
        commandList.add("pylint");

        if(rules != null) { //needs to keep this order, don't merge it with --enable params
            commandList.add("--disable");
            commandList.add("all");
        }

        commandList.add("--recursive");
        commandList.add("true");

        if(rules != null) {
            for (CodetyUnmaterializedRule unmaterializedRule : rules) {
                commandList.add("--enable");
                commandList.add(unmaterializedRule.getRuleId());
            }
        }

//        commandList.add("--msg-template");
//        commandList.add(pylintOutputFormat);
        commandList.add("--output-format");
        commandList.add("json");

        commandList.add(localGitRepoPath); //the code path.

        String[] command = commandList.toArray(new String[0]);
        return command;
    }
}
