package io.codety.scanner.analyzer.cppcheck;

import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.RuntimeExecUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class CppcheckCodeAnalyzer implements CodeAnalyzerInterface {
    private static final String cppcheck = "cppcheck";
    private static final String suppressionListParam = "--suppressions-list=";
    private static final String templateParam = "--template=";
    static final String cppcheckResultLocationPrefix = "|--@@";
    static final String cppcheckResultLocationSeparator = "@@@@@@";
    static final String cppcheckIssueResultPrefix = "##@@_@@##";
    private static final String templateParamValue = cppcheckIssueResultPrefix + "{id}"+cppcheckResultLocationSeparator+"{cwe}"+cppcheckResultLocationSeparator+"{file}"+cppcheckResultLocationSeparator+"{line}"+cppcheckResultLocationSeparator+"{severity}"+cppcheckResultLocationSeparator+"{message}";
    private static final String templateLocationParam = "--template-location=";
    private static final String templateLocationParamValue = cppcheckResultLocationPrefix + "{file}"+cppcheckResultLocationSeparator+"{line}"+cppcheckResultLocationSeparator+"{column}"+cppcheckResultLocationSeparator+"{info}";
    private static final String cppTitle = "C/C++";
    private static final String INFO_failedToRunCppcheck = "Failed to run cpp analyzer due to error: ";
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        CodetyConsoleLogger.info("Scanning "+runnerConfiguration.getLanguage()+" code via "+runnerConfiguration.getCodeAnalyzerType().name()+"...");
        File file = runnerConfiguration.getFile();
//cppcheck  . --suppressions-list=suppression.txt  --xml 2>error1.txt
        ArrayList<CodeAnalysisResultDto> list = new ArrayList();

        String[] command;

        if(runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()){
            command = new String[]{cppcheck, request.getLocalGitRepoPath(), templateParam + templateParamValue, templateLocationParam + templateLocationParamValue,};
        }else{
            command = new String[]{cppcheck, ".", suppressionListParam + file.getAbsoluteFile().getAbsolutePath(), templateParam + templateParamValue, templateLocationParam + templateLocationParamValue};
        }
        try {

            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, null, 60, false, null);

            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();

            List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = CppcheckResultConverter.convertResult(errorOutput);
            if(codeAnalysisIssueDtoList == null || codeAnalysisIssueDtoList.isEmpty()){
                return list;
            }

            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());

            resultDto.setDisplayTitle(cppTitle);
            resultDto.addIssues(codeAnalysisIssueDtoList);


            list.add(resultDto);

        } catch (Exception e) {

            CodetyConsoleLogger.info(INFO_failedToRunCppcheck + e.getMessage());
        }

        return list;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.cpp, CodeAnalyzerType.cppcheck), request);
    }
}
