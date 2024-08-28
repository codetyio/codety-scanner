package io.codety.scanner.analyzer.pylint;

import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class PylintResultsConverterTest {

    @Test
    void testConvert() throws IOException {
        String issuePayload = Files.readString(Path.of(this.getClass().getResource("/pylint/pylint.issue.output").getPath()));
        CodeAnalysisResultDto resultDto = PylintResultsConverter.convertFormat(issuePayload, new AnalyzerConfigurationDetailDto("python", CodeAnalyzerType.pylint));
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
//            Assertions.assertTrue(issue.getPackagePath().length()>3);
            }
        }

    }

}
