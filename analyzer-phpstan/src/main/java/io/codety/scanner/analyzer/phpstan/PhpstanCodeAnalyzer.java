package io.codety.scanner.analyzer.phpstan;

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

import java.util.ArrayList;
import java.util.List;

@Service
public class PhpstanCodeAnalyzer implements CodeAnalyzerInterface {

    private static final long memoryLimit = 1024000000;

    @Value("${codety.phpstan.path}")
    String phpstanBinPath;

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {

        CodetyConsoleLogger.info("Scanning "+runnerConfiguration.getLanguage()+" code via "+runnerConfiguration.getCodeAnalyzerType().name()+"...");
        ArrayList<CodeAnalysisResultDto> list = new ArrayList();

        String[] command;
        String localGitRepoPath = request.getLocalGitRepoPath();
        //./vendor/bin/phpstan --memory-limit=1024000000  --no-interaction --no-progress  --error-format=json  analyse

        String phpstanPath = phpstanBinPath + "phpstan";
        if(runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()){
            command = new String[]{phpstanPath, "--memory-limit=" + memoryLimit, "--no-interaction", "--no-progress", "--error-format=json", "analyse", localGitRepoPath};
        }else{
            //Use multiple rules: --check CKV_GCP_33,CKV_GCP_34,CKV_GCP_35 ...
            command = new String[]{phpstanPath, "--memory-limit=" + memoryLimit, "--no-interaction", "--no-progress", "--error-format=json", "analyse", localGitRepoPath};
        }
        try {
            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, "/", 60, false, null);

            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();

            if(errorOutput!=null && errorOutput.length() > 0){
                CodetyConsoleLogger.info("Phpstan error output: " + errorOutput);
            }

            List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = PhpstanConverter.convertResult(successOutput, localGitRepoPath);

            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());
            resultDto.addIssues(codeAnalysisIssueDtoList);
            list.add(resultDto);

        } catch (Exception e) {
            CodetyConsoleLogger.info("Skip checkov analyzer due to exceptions");
            CodetyConsoleLogger.debug("Skip checkov analyzer due to exceptions " + e.getMessage(), e);
        }

        return list;

    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.php, CodeAnalyzerType.phpstan), request);
    }
}
