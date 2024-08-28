package io.codety.scanner.service;

import io.codety.scanner.prework.dto.GitDiffFileChange;
import io.codety.scanner.prework.dto.GitFileChangeList;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CodeAnalysisResultFilterService {
    public void filterAnalysisResultForOnlyChangedCode(CodeAnalysisResultSetDto codeAnalysisResultSetDto, GitFileChangeList fileChangeList) {
        if(fileChangeList == null){
            return;
        }

        List<CodeAnalysisResultDto> codeAnalysisResultDtoList = codeAnalysisResultSetDto.getCodeAnalysisResultDtoList();
        for(CodeAnalysisResultDto resultDto : codeAnalysisResultDtoList){
            Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();

            Iterator<String> keySet = issuesByFile.keySet().iterator();
            while(keySet.hasNext()){
                String filePath = keySet.next();
                if(!fileChangeList.containsCode(filePath)){
                    keySet.remove();
                    continue;
                }

                List<CodeAnalysisIssueDto> codeAnalysisIssueDtos = issuesByFile.get(filePath);
                Iterator<CodeAnalysisIssueDto> issueIterator = codeAnalysisIssueDtos.iterator();
                GitDiffFileChange change = fileChangeList.getChange(filePath);
                while(issueIterator.hasNext()){
                    CodeAnalysisIssueDto next = issueIterator.next();
                    if(!change.includeChange(next.getStartLineNumber())){
                        issueIterator.remove();
                    }
                }

                //remove everything if the no issue in this file after filtering.
                if(issuesByFile.get(filePath).isEmpty()){
                    keySet.remove();
                }
            }
//
//            Iterator<CodeAnalysisIssueDto> iterator = issuesByFile.iterator();
//            while(iterator.hasNext()){
//                CodeAnalysisIssueDto next = iterator.next();
//                String filePath = next.getFilePath();
//                Integer startLineNumber = next.getStartLineNumber();
//                if(!fileChangeList.containsCode(filePath)){
//                    iterator.remove();
//                    continue;
//                }
//                GitDiffFileChange change = fileChangeList.getChange(filePath);
//                if(!change.includeChange(startLineNumber)){
//                    iterator.remove();
//                }
//            }
        }



    }
}
