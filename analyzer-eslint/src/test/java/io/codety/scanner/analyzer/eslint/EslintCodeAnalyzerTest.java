package io.codety.scanner.analyzer.eslint;

import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles(value = "dev")
public class EslintCodeAnalyzerTest {

    @Autowired
    EslintCodeAnalyzer eslintCodeAnalyzer;

    @Test
    void testTest() throws IOException {
        Map<String, String> env = new HashMap<>();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{"./"});
        String path = Path.of(this.getClass().getResource("/eslint/code-smell-examples/placeholder").getFile()).getParent().toAbsolutePath().toString();
        analyzerRequest.setLocalGitRepoPath(path);
        AnalyzerConfigurationDetailDto runnerConfiguration = new AnalyzerConfigurationDetailDto("html", CodeAnalyzerType.eslint);

        runnerConfiguration.setPayload("{\"codeAnalyzerPluginCode\":\"eslint\",\"rules\":[{\"ruleId\":\"no-duplicate-id\"},{\"ruleId\":\"require-closing-tags\"}]}");
        runnerConfiguration.setPluginCode("html-eslint");
        List<CodeAnalysisResultDto> codeAnalysisResultDtos = eslintCodeAnalyzer.analyzeCode(runnerConfiguration, analyzerRequest);

        Assertions.assertNotNull(codeAnalysisResultDtos);
        Assertions.assertTrue(codeAnalysisResultDtos.size() > 0);
        Map<String, List<CodeAnalysisIssueDto>> issuesByFile = codeAnalysisResultDtos.get(0).getIssuesByFile();
        Assertions.assertTrue(issuesByFile.size()>0);
        for(String file : issuesByFile.keySet()) {
            List<CodeAnalysisIssueDto> issues = issuesByFile.get(file);
            for (CodeAnalysisIssueDto dto : issues) {
                Assertions.assertTrue(dto.getIssueCode().length() > 0);
                Assertions.assertTrue(dto.getStartLineNumber() >= 0);
                Assertions.assertTrue(dto.getFilePath().length() > 0);
                Assertions.assertTrue(dto.getIssueCategory().length() > 0);
            }
        }

    }


    @Test
    void testTypescriptTest() throws IOException {
        Map<String, String> env = new HashMap();

        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{"./"});
        String path = Path.of(this.getClass().getResource("/eslint/code-smell-examples/placeholder").getFile()).getParent().toAbsolutePath().toString();
        analyzerRequest.setLocalGitRepoPath(path);
        AnalyzerConfigurationDetailDto runnerConfiguration = new AnalyzerConfigurationDetailDto("javascript", CodeAnalyzerType.eslint);

        runnerConfiguration.setPayload("{\"codeAnalyzerPluginCode\":\"eslint\",\"rules\":[{\"ruleId\":\"no-array-delete\"},{\"ruleId\":\"no-unused-vars\"}]}");
        runnerConfiguration.setPluginCode("typescript-eslint");
        List<CodeAnalysisResultDto> codeAnalysisResultDtos = eslintCodeAnalyzer.analyzeCode(runnerConfiguration, analyzerRequest);

        Assertions.assertNotNull(codeAnalysisResultDtos);
        Assertions.assertTrue(codeAnalysisResultDtos.size() > 0);

        Map<String, List<CodeAnalysisIssueDto>> issuesByFile = codeAnalysisResultDtos.get(0).getIssuesByFile();
        Assertions.assertTrue(issuesByFile.size()>0);
        for(String file : issuesByFile.keySet()) {
            List<CodeAnalysisIssueDto> issues = issuesByFile.get(file);

            Assertions.assertTrue(issues.size() > 2);
            for (CodeAnalysisIssueDto dto : issues) {
                Assertions.assertTrue(dto.getIssueCode().length() > 0);
                Assertions.assertTrue(dto.getStartLineNumber() >= 0);
                Assertions.assertTrue(dto.getFilePath().length() > 0);
                Assertions.assertTrue(dto.getIssueCategory().length() > 0);
            }
        }

    }
}
