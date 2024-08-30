package io.codety.scanner;

import io.codety.scanner.analyzer.scalastyle.ScalastyleCodeAnalyzer;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ScalastyleCodeAnalyzerTest {

    @Autowired
    ScalastyleCodeAnalyzer scalastyleCodeAnalyzer;

    @Test
    void testAnalyze(){
        String s = "../code-issue-examples";
        String absolutePath = Path.of("../","code-issue-examples").toAbsolutePath().toFile().getAbsolutePath();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(new HashMap<>(), new String[]{absolutePath});
        List<CodeAnalysisResultDto> codeAnalysisResultDtos = scalastyleCodeAnalyzer.analyzeCode(analyzerRequest);
        Assertions.assertTrue(codeAnalysisResultDtos.size() == 1);

        Map<String, List<CodeAnalysisIssueDto>> issuesByFile = codeAnalysisResultDtos.get(0).getIssuesByFile();
        for(String file : issuesByFile.keySet()){
            List<CodeAnalysisIssueDto> codeAnalysisIssueDtos = issuesByFile.get(file);
            Assertions.assertTrue(codeAnalysisResultDtos.size() > 0);
            for(CodeAnalysisIssueDto issueDto : codeAnalysisIssueDtos){
                Assertions.assertTrue(issueDto.getStartLineNumber() != null);
                Assertions.assertTrue(issueDto.getDescription().length() > 4);
                Assertions.assertTrue(issueDto.getIssueCategory().length() > 4);
                Assertions.assertTrue(issueDto.getIssueCode().length() > 4);
                Assertions.assertTrue(issueDto.getPriority()>0);
                Assertions.assertTrue(issueDto.getFilePath().length()>5);
            }

        }
    }

}
