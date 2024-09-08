package io.codety.scanner.analyzer.shellcheck;

import io.codety.common.dto.CodeAnalyzerType;
import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShellcheckCodeAnalyzer implements CodeAnalyzerInterface {
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        return new ArrayList<>();
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.shell, CodeAnalyzerType.shellcheck), request);
    }
}
