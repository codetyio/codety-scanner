package io.codety.scanner.analyzer.pylint;

import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.dto.unmaterialized.CodetyUnmaterializedRule;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles(value = "dev")
public class PylintCodeAnalyzerTest {

    @Autowired
    PylintCodeAnalyzer pylintCodeAnalyzer;

    @Test
    void testAnalyzePylint() throws Exception {

        AnalyzerConfigurationDetailDto runnerConfiguration = new AnalyzerConfigurationDetailDto("python", CodeAnalyzerType.pylint);
        CodetyUnmaterializedRule[] codetyUnmaterializedRules = {
                new CodetyUnmaterializedRule("E0602"),
                new CodetyUnmaterializedRule("C0114"),
        };
        Map<String, String> env = new HashMap<>();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{"./"});
        String path = Path.of(this.getClass().getResource("/pylint/code-smell-examples/placeholder").getFile()).getParent().toAbsolutePath().toString();
        analyzerRequest.setLocalGitRepoPath(path);

        CodeAnalysisResultDto resultDto = pylintCodeAnalyzer.analyzeCodeUsingPylint(runnerConfiguration, analyzerRequest, codetyUnmaterializedRules);
        Assertions.assertNotNull(resultDto);
        Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
        Assertions.assertTrue(issuesByFile.size()>0);
        for(String file : issuesByFile.keySet()) {
            List<CodeAnalysisIssueDto> issues = issuesByFile.get(file);
            for (CodeAnalysisIssueDto issue : issues) {

                Assertions.assertTrue(issue.getIssueCode().length() > 0);
                Assertions.assertTrue(issue.getDescription().length() > 0);
                Assertions.assertTrue(issue.getIssueCategory().length() > 0);
                Assertions.assertTrue(issue.getStartLineNumber() >= 0);
                Assertions.assertTrue(issue.getFilePath().length() > 5);

            }
        }


    }
}
