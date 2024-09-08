package io.codety.scanner.analyzer.shellcheck;

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
public class ShellcheckCodeAnalyzerTest {

    @Autowired
    ShellcheckCodeAnalyzer shellcheckCodeAnalyzer;

    @Test
    void testScanCode(){
        String path = Path.of("./").toAbsolutePath().getParent().toAbsolutePath().getParent().toAbsolutePath() + "/code-issue-examples";
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(new HashMap<>(), new String[]{path});
        List<CodeAnalysisResultDto> codeAnalysisResultDtos = shellcheckCodeAnalyzer.analyzeCode(analyzerRequest);
        Assertions.assertTrue(codeAnalysisResultDtos.size() > 0);

        
        for(CodeAnalysisResultDto codeAnalysisResultDto : codeAnalysisResultDtos){
            Map<String, List<CodeAnalysisIssueDto>> issuesByFile = codeAnalysisResultDto.getIssuesByFile();
            Assertions.assertTrue(issuesByFile.size() > 0);
            for(String file : issuesByFile.keySet()){
                List<CodeAnalysisIssueDto> codeAnalysisIssueDtos = issuesByFile.get(file);
                Assertions.assertTrue(codeAnalysisIssueDtos.size() > 0);
                for(CodeAnalysisIssueDto issueDto : codeAnalysisIssueDtos){
                    Assertions.assertTrue(issueDto.getFilePath().length() > 0);
                    Assertions.assertTrue(issueDto.getIssueCode().length() > 0);
                    Assertions.assertTrue(issueDto.getDescription().length() > 0);
                    Assertions.assertTrue(issueDto.getStartLineNumber() > 0);
                    Assertions.assertTrue(issueDto.getPriority() > 0);

                }
            }
        }


    }

}
