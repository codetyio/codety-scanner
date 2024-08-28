package io.codety.scanner.reporter;

import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import io.codety.scanner.service.dto.AnalyzerRequest;

public interface ResultReporter {
    void deliverResult(AnalyzerRequest analyzerRequest, CodeAnalysisResultSetDto codeAnalysisResultSetDto);
}
