package io.codety.scanner.analyzer.scalastyle;

import io.codety.common.dto.CodeAnalyzerType;
import io.codety.common.dto.LanguageType;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScalastyleCodeAnalyzer implements CodeAnalyzerInterface {


    @Value("${codety.base.path}")
    String scalaStyleJarPath;

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {

        //cmd example:
        //  java -jar ~/dev/scalastyle.jar --config analyzer-scalastyle/src/test/resources/default-config.xml --xmlOutput ~/dev/tmp.xml  code-issue-examples/scala
        CodetyConsoleLogger.info("Scanning "+runnerConfiguration.getLanguage()+" code via "+runnerConfiguration.getCodeAnalyzerType().name()+"...");
        ArrayList<CodeAnalysisResultDto> list = new ArrayList();
        try {

            String[] command;
            String separator = scalaStyleJarPath.endsWith("/") ? "" : "/";
            String jarPath = scalaStyleJarPath + separator + "scalastyle.jar";

            File tmpConfigDownloadFolder = new File("/tmp/scala_output_" + System.currentTimeMillis() + ".xml");
            String absoluteFile = tmpConfigDownloadFolder.getAbsolutePath();
            String defaultConfigPath = scalaStyleJarPath + separator + "scalastyle-default-config.xml";
            if(runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()){

                command = new String[]{"java", "-jar", jarPath, "--config",    defaultConfigPath, "--xmlOutput", absoluteFile, request.getLocalGitRepoPath()};
            }else{
                command = new String[]{"java", "-jar", jarPath, "--config",    defaultConfigPath, "--xmlOutput", absoluteFile, request.getLocalGitRepoPath()};
            }


            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, null, 60, false, null);

            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();
            String payload = Files.readString(Path.of(tmpConfigDownloadFolder.getAbsolutePath()));
            List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = ScalastyleConverter.convertResult(payload);

            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());
            resultDto.setDisplayTitle(runnerConfiguration.getLanguage());
            list.add(resultDto);

            if(codeAnalysisIssueDtoList == null || codeAnalysisIssueDtoList.isEmpty()){
                return list;
            }
            resultDto.addIssues(codeAnalysisIssueDtoList);

        } catch (Exception e) {
            CodetyConsoleLogger.debug("Failed to run scalastyle due to error " + e.getMessage(), e);
            CodetyConsoleLogger.info("Skip Scalastyle analyzer due to error " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.scala, CodeAnalyzerType.scalastyle), request);
    }
}
