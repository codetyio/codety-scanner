package io.codety.scanner.reporter.sarif;

import com.contrastsecurity.sarif.Run;
import com.contrastsecurity.sarif.SarifSchema210;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.JsonFactoryUtil;
import io.codety.test.util.TestCaseUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class SarifResultReporterTest {

    @Autowired
    SarifResultReporter sarifResultReporter;

    @Test
    void testGenerateSarifFile() throws IOException {


        Map<String, String> env = new HashMap<>();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{"./"});
        String path = "/tmp/test";
        analyzerRequest.setLocalGitRepoPath(path);


        CodeAnalysisResultSetDto codeAnalysisResultSetDto = new CodeAnalysisResultSetDto();
        CodeAnalysisResultDto test = new CodeAnalysisResultDto("test", CodeAnalyzerType.codety);
        test.addIssue(new CodeAnalysisIssueDto("a/b/test.txt", 10, "security", "found-github-secrets", 4, "shouldn't expose credential in the code"));
        test.addIssue(new CodeAnalysisIssueDto("a/b/wfjeoijfiewjofijwoifjwjeof.txt", 10, "error-prone", "check-undef", 4, "check whether the variable is null/undef before reading"));
        test.addIssue(new CodeAnalysisIssueDto("a/b/weojfiwejfijwoe.txt", 10, "styling", "no-indent", 1, "Indent is recommended"));
        codeAnalysisResultSetDto.getCodeAnalysisResultDtoList().add(test);

        sarifResultReporter.deliverResult(analyzerRequest, codeAnalysisResultSetDto);

        File file = new File(path + "/" + sarifResultReporter.sarifFileName);
        Assertions.assertTrue(file.exists());
        String sarifPayload = Files.readString(file.toPath());
        SarifSchema210 sarifSchema210 = JsonFactoryUtil.objectMapper.readValue(sarifPayload, SarifSchema210.class);
        Assertions.assertNotNull(sarifSchema210);
        Assertions.assertNotNull(sarifSchema210.getRuns());
        Assertions.assertTrue(sarifSchema210.getRuns().size() > 0);
        Run run = sarifSchema210.getRuns().get(0);
        Assertions.assertTrue(run.getResults().size() > 0);

    }

}
