package io.codety.scanner.reporter.console;

import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.reporter.ResultReporter;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.CodetyConstant;
import org.springframework.stereotype.Service;

@Service
public class ConsoleResultReporter implements ResultReporter {

    @Override
    public void deliverResult(AnalyzerRequest analyzerRequest, CodeAnalysisResultSetDto codeAnalysisResultSetDto) {

        if(!analyzerRequest.isEnableConsoleIssueReporter()) {
            CodetyConsoleLogger.info("Skip posting issue result into console terminal");
        }

        String consoleOutputString = codeAnalysisResultSetDto.toConsoleOutputString(analyzerRequest);
        if(consoleOutputString!=null && consoleOutputString.length() > 0) {
            CodetyConsoleLogger.info(CodetyConstant.INFO_SHOW_ANALYSIS_RESULT);
            CodetyConsoleLogger.info(consoleOutputString);
        }else{
            CodetyConsoleLogger.info("Based on the current code standard settings, no code issue was found for the changed files within current pull request.");
        }

    }
}
