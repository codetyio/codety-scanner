package io.codety.scanner.analyzer.golangcilint;

import io.codety.common.dto.CodeAnalyzerType;
import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.RuntimeExecUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class GolangcilintCodeAnalyzer implements CodeAnalyzerInterface {
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        List<CodeAnalysisResultDto> list = new ArrayList<>();
        try {
            List<File> goModules = GolangcilintModuleUtil.findGoModules(request.getLocalGitRepoPath());

            for(File file : goModules) {
                List<String> cmdList = new ArrayList<>();
                cmdList.add("golangci-lint");
                cmdList.add("run");
                cmdList.add("--out-format");
                cmdList.add("json");

                if (runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()) {
                    cmdList.add("--no-config");
                    cmdList.add("--enable-all");
                } else {
                    String absolutePath = runnerConfiguration.getFile().getAbsolutePath();
                    cmdList.add("--disable-all");
                    cmdList.add("--config");
                    cmdList.add(absolutePath);
                }

                cmdList.add( "./...");

                String[] command = cmdList.toArray(new String[0]);


                RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, file.getAbsolutePath(), 60, false, null);

                String errorOutput = runtimeExecResult.getErrorOutput();
                String successOutput = runtimeExecResult.getSuccessOutput();

                if (errorOutput != null && errorOutput.length() > 0) {
                    CodetyConsoleLogger.debug("Error output from golangci-lint " + errorOutput);
                }
                if (successOutput == null || successOutput.isEmpty()) {
                    continue;
                }

                List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = GolangcilintResultConverter.convertResult(successOutput);
                if (codeAnalysisIssueDtoList == null || codeAnalysisIssueDtoList.isEmpty()) {
                    continue;
                }

                CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());

                resultDto.setDisplayTitle("Golang");
                resultDto.addIssues(codeAnalysisIssueDtoList);

                list.add(resultDto);
            }

        } catch (Exception e) {

            CodetyConsoleLogger.debug("Skip Golang due to error " + e.getMessage(), e);
        }


        return list;
    }


    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.go, CodeAnalyzerType.golangcilint), request);
    }
}
