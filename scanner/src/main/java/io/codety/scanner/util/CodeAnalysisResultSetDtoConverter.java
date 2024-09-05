package io.codety.scanner.util;

import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeAnalysisResultSetDtoConverter {
    public static void mergeByLanguage(CodeAnalysisResultSetDto codeAnalysisResultSetDto) {

        List<CodeAnalysisResultDto> codeAnalysisResultDtoList = codeAnalysisResultSetDto.getCodeAnalysisResultDtoList();

        Map<String, List<CodeAnalysisResultDto>> map = new HashMap<>();
        



    }
}
