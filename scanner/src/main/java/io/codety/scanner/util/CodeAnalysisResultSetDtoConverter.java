package io.codety.scanner.util;

import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeAnalysisResultSetDtoConverter {
    public static List<CodeAnalysisResultDto> mergeByLanguage(CodeAnalysisResultSetDto codeAnalysisResultSetDto) {

        List<CodeAnalysisResultDto> codeAnalysisResultDtoList = codeAnalysisResultSetDto.getCodeAnalysisResultDtoList();
        List<CodeAnalysisResultDto> mergedResultList = new ArrayList<>();
        Map<String, CodeAnalysisResultDto> map = new HashMap<>();
        for(CodeAnalysisResultDto resultDto : codeAnalysisResultDtoList){
            String language = resultDto.getLanguage();
            if(map.containsKey(language)) {
                Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
                for(String file : issuesByFile.keySet()){
                    map.get(language).addIssues(issuesByFile.get(file));
                }

            }else{
                map.put(language, resultDto);
                mergedResultList.add(resultDto);
            }
        }
        return mergedResultList;

    }
}
