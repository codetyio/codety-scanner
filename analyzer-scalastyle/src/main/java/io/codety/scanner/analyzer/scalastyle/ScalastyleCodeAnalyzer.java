package io.codety.scanner.analyzer.scalastyle;

import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.RuntimeExecUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ScalastyleCodeAnalyzer implements CodeAnalyzerInterface {


    @Value("${codety.base.path}")
    String scalaStyleJarPath;

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {

        //cmd example:
        //  java -jar ~/dev/scalastyle.jar --config analyzer-scalastyle/src/test/resources/default-config.xml --xmlOutput ~/dev/tmp.xml  code-issue-examples/scala
        CodetyConsoleLogger.info("Scanning "+runnerConfiguration.getLanguage()+" code via "+runnerConfiguration.getCodeAnalyzerType().name()+"...");
        File file = runnerConfiguration.getFile();
        ArrayList<CodeAnalysisResultDto> list = new ArrayList();

        String[] command;
        String jarPath = scalaStyleJarPath + "/scalastyle.jar";
        runnerConfiguration.getFile();
        File tmpConfigDownloadFolder = new File("/tmp/scala_output_" + System.currentTimeMillis() + ".xml");
        String absoluteFile = tmpConfigDownloadFolder.getAbsolutePath();

        if(runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()){
            command = new String[]{"java", "-jar", scalaStyleJarPath, "--config",    request.getLocalGitRepoPath(), "--xmlOutput", absoluteFile, request.getLocalGitRepoPath()};
        }else{
            command = new String[]{"java", "-jar", scalaStyleJarPath, "--config",    request.getLocalGitRepoPath(), "--xmlOutput", absoluteFile, request.getLocalGitRepoPath()};
        }
        try {

            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, null, 60, false, null);

            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();

//            List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = CppcheckResultConverter.convertResult(errorOutput);
//            if(codeAnalysisIssueDtoList == null || codeAnalysisIssueDtoList.isEmpty()){
//                return list;
//            }
//
//            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());
//
//            resultDto.setDisplayTitle(cppTitle);
//            resultDto.addIssues(codeAnalysisIssueDtoList);
//
//
//            list.add(resultDto);

        } catch (Exception e) {

            CodetyConsoleLogger.debug("Failed to run scalastyle due to error " + e.getMessage(), e);
        }

        return list;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return null;
    }
}
