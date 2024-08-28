package io.codety.scanner.analyzer;

import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.service.dto.AnalyzerRequest;

import java.util.List;

public interface CodeAnalyzerInterface {
    List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request);
    List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request);
}
