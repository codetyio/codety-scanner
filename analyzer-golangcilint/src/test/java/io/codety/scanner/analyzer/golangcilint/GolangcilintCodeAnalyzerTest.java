package io.codety.scanner.analyzer.golangcilint;

import io.codety.common.dto.CodeAnalyzerType;
import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class GolangcilintCodeAnalyzerTest {

    @Autowired
    GolangcilintCodeAnalyzer golangcilintCodeAnalyzer;

    @Test
    void testAnalyze(){

        String path = Paths.get("../","code-issue-examples", "go").toFile().getAbsoluteFile().getPath();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(new HashMap<>(), new String[]{path});
        List<CodeAnalysisResultDto> codeAnalysisResultDtos = golangcilintCodeAnalyzer.analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.go, CodeAnalyzerType.golangcilint), analyzerRequest);

        Assertions.assertTrue(codeAnalysisResultDtos != null);
        Assertions.assertTrue(codeAnalysisResultDtos.size() > 0);
    }

}
