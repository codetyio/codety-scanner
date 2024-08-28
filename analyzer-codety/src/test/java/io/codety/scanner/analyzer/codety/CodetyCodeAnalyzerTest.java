package io.codety.scanner.analyzer.codety;

import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.codety.dto.CodetyRegexAnalyzerRule;
import io.codety.scanner.analyzer.codety.dto.CodetyRegexAnalyzerRuleList;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.JsonFactoryUtil;
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
public class CodetyCodeAnalyzerTest {

    @Autowired
    CodetyRegexCodeAnalyzer codetyRegexCodeAnalyzer;
    @Test
    void testRun() throws IOException {
        String path = Paths.get("../","code-issue-examples").toFile().getAbsoluteFile().getPath();
        String language = "all";
        AnalyzerConfigurationDetailDto runnerConfiguration = new AnalyzerConfigurationDetailDto(language, CodeAnalyzerType.codety);
        Map<String, String> env = new HashMap<>();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{path});


        CodetyRegexAnalyzerRuleList regexAnalyzerRuleList = createCodetyRegexAnalyzerRuleList();
        String s = JsonFactoryUtil.objectMapper.writeValueAsString(regexAnalyzerRuleList);
        runnerConfiguration.setPayload(s);


        List<CodeAnalysisResultDto> codeAnalysisResultDtos = codetyRegexCodeAnalyzer.analyzeCode(runnerConfiguration, analyzerRequest);

        Assertions.assertTrue(codeAnalysisResultDtos.size() == 1);
        CodeAnalysisResultDto resultDto = codeAnalysisResultDtos.get(0);
        Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
        Assertions.assertTrue(issuesByFile.size() > 0);
        for(String file : issuesByFile.keySet()) {
            List<CodeAnalysisIssueDto> issues = issuesByFile.get(file);
            Assertions.assertTrue(issues.size() > 0);
            for (CodeAnalysisIssueDto issueDto : issues) {
                Assertions.assertTrue(issueDto.getIssueCategory().length() > 0);
                Assertions.assertTrue(issueDto.getDescription().length() > 0);
                Assertions.assertTrue(issueDto.getPriority() > 0);
                Assertions.assertTrue(issueDto.getStartLineNumber() > 0);
            }
        }

        Assertions.assertTrue(true);

    }

    private CodetyRegexAnalyzerRuleList createCodetyRegexAnalyzerRuleList() {
        CodetyRegexAnalyzerRuleList codetyRegexAnalyzerRuleList = new CodetyRegexAnalyzerRuleList();
        CodetyRegexAnalyzerRule rule = new CodetyRegexAnalyzerRule();
        rule.setRegex("ghp_[0-9a-zA-Z]{36}");
        rule.setDescription("Detected codety");
        rule.setIssueCategory("security");
        rule.setIssueCode("github-token");
        rule.setPriority(5);

        codetyRegexAnalyzerRuleList.getRules().add(rule);

        return codetyRegexAnalyzerRuleList;
    }
}
