package io.codety.scanner.analyzer.cppcheck;

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
public class CppcheckCodeAnalyzerTest {

    @Autowired
    CppcheckCodeAnalyzer cppcheckCodeAnalyzer;

    @Test
    void testBasicRunWithIssueDetected() throws IOException {

        Map<String, String> env = new HashMap<>();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{"./"});
        String path = Path.of(this.getClass().getResource("/cppcheck/cpp-code-with-issues/example.cpp").getFile()).getParent().toAbsolutePath().toString();
        analyzerRequest.setLocalGitRepoPath(path);
        AnalyzerConfigurationDetailDto runnerConfiguration = new AnalyzerConfigurationDetailDto("cpp", CodeAnalyzerType.cppcheck);

        List<CodeAnalysisResultDto> codeAnalysisResultDtos = cppcheckCodeAnalyzer.analyzeCode(runnerConfiguration, analyzerRequest);

        Assertions.assertNotNull(codeAnalysisResultDtos);
        Assertions.assertTrue(codeAnalysisResultDtos.size() == 1);

        CodeAnalysisResultDto resultDto = codeAnalysisResultDtos.get(0);
        Assertions.assertTrue(resultDto.getDisplayTitle().length() > 0);
        Assertions.assertTrue(resultDto.getLanguage().length() > 0);
        Assertions.assertTrue(resultDto.getCodeAnalyzerType() == CodeAnalyzerType.cppcheck);
        Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
        Assertions.assertTrue(issuesByFile.size() > 0);
        for(String file : issuesByFile.keySet()) {
            List<CodeAnalysisIssueDto> issues = issuesByFile.get(file);
            Assertions.assertTrue(issues.size() > 2);
            for (CodeAnalysisIssueDto dto : issues) {
                Assertions.assertTrue(dto.getFilePath().length() > 5);
                Assertions.assertTrue(dto.getIssueCode().length() > 5);
                Assertions.assertTrue(dto.getStartLineNumber() > 0);
                Assertions.assertTrue(dto.getIssueCategory().length() > 1);
                Assertions.assertTrue(dto.getDescription().length() > 1);
            }
        }

    }

    @Test
    void testDeserialize() throws Exception {

        String s = Files.readString(Path.of(this.getClass().getResource("/cppcheck/error-out.txt").getFile()).toAbsolutePath());
        List<CodeAnalysisIssueDto> recordDtos = CppcheckResultConverter.convertResult(s);
        Assertions.assertTrue(recordDtos.size() > 0);

    }

}
