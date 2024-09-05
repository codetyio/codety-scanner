package io.codety.scanner.analyzer.rubocop;

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
public class RubocopCodeAnalyzer implements CodeAnalyzerInterface {
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        ArrayList<CodeAnalysisResultDto> codeAnalysisResultDtos = new ArrayList<>();
        return codeAnalysisResultDtos;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.ruby, CodeAnalyzerType.rubocop), request);
    }
}
