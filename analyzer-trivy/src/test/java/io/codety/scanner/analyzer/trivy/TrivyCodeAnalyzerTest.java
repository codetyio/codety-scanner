package io.codety.scanner.analyzer.trivy;

import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TrivyCodeAnalyzerTest {

    @Autowired
    TrivyCodeAnalyzer trivyCodeAnalyzer;

    @Test
    void testAnalyze() throws IOException {

        String path = Paths.get("../","code-issue-examples", "java-trivy").toFile().getAbsoluteFile().getPath();

        String language = "java";
        AnalyzerConfigurationDetailDto runnerConfiguration = new AnalyzerConfigurationDetailDto(language, CodeAnalyzerType.trivy);
        Map<String, String> env = new HashMap<>();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{path});
        List<CodeAnalysisResultDto> codeAnalysisResultDtos = trivyCodeAnalyzer.analyzeCode(runnerConfiguration, analyzerRequest);
        Assertions.assertTrue(codeAnalysisResultDtos.size() > 0);


        CodeAnalysisResultDto resultDto = codeAnalysisResultDtos.get(0);
        Assertions.assertEquals(resultDto.getLanguage(), language);
        Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
        Assertions.assertTrue(issuesByFile.size()>0);
        for(String file : issuesByFile.keySet()) {
            List<CodeAnalysisIssueDto> issues = issuesByFile.get(file);
            for (CodeAnalysisIssueDto issueDto : issues) {
                Assertions.assertTrue(issueDto.getStartLineNumber() > 0);
                Assertions.assertTrue(issueDto.getIssueCode().length() > 0);
                Assertions.assertTrue(issueDto.getPriority() > 0);
                Assertions.assertTrue(issueDto.getIssueCategory().equals("hard-coded secrets"));
                Assertions.assertTrue(issueDto.getDescription().length() > 10);
                Assertions.assertTrue(issueDto.getFilePath().length() > 10);
            }
        }

    }


}
