package io.codety.scanner.reporter.dto;

import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.service.dto.GitProviderType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CodeAnalysisResultSetDto {

    List<CodeAnalysisResultDto> codeAnalysisResultDtoList = new ArrayList();

    List<CodeAnalysisResultDto> mergedByLanguage = new ArrayList();

    public List<CodeAnalysisResultDto> getCodeAnalysisResultDtoList() {
        return codeAnalysisResultDtoList;
    }

    public void setCodeAnalysisResultDtoList(List<CodeAnalysisResultDto> codeAnalysisResultDtoList) {
        this.codeAnalysisResultDtoList = codeAnalysisResultDtoList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CodeAnalysisResultDto resultDto : codeAnalysisResultDtoList) {
            Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
            for(String filePath : issuesByFile.keySet()) {
                List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = issuesByFile.get(filePath);
                for (CodeAnalysisIssueDto resultRecordDto : codeAnalysisIssueDtoList) {
                    String name = resultRecordDto.getIssueCode() + " " + resultRecordDto.getStartLineNumber() + " " + resultRecordDto.getFilePath() + resultRecordDto.getDescription();
                    sb.append(name);
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    public String toConsoleOutputString(AnalyzerRequest analyzerRequest) {
        StringBuilder sb = new StringBuilder();
        StringBuilder baseGitUrl = new StringBuilder();
        if(analyzerRequest.getGitBaseHttpsUrl()!=null){
            baseGitUrl.append( analyzerRequest.getGitBaseHttpsUrl()).append("/");
        }
        if(analyzerRequest.getGitRepoFullName() != null){
            baseGitUrl.append( analyzerRequest.getGitRepoFullName()).append("/");

            if(analyzerRequest.getGitProviderType() !=null && analyzerRequest.getGitProviderType() == GitProviderType.GITHUB
                    &&  analyzerRequest.getGitCommitSha() !=null){
                baseGitUrl.append( "blob/").append( analyzerRequest.getGitCommitSha()).append("/");
            }
        }

        for (CodeAnalysisResultDto resultDto : this.getCodeAnalysisResultDtoList()) {
            if (resultDto == null) {
                continue;
            }
            Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
            for(String filePath : issuesByFile.keySet()) {
                List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = issuesByFile.get(filePath);
                if (codeAnalysisIssueDtoList == null || codeAnalysisIssueDtoList.isEmpty()) {
                    continue;
                }
                sb.append(resultDto.getDisplayTitle()).append("\n");
                for (CodeAnalysisIssueDto resultRecordDto : codeAnalysisIssueDtoList) {
                    if (resultRecordDto == null) {
                        continue;
                    }
                    sb.append(baseGitUrl.toString() + resultRecordDto.getFilePath() + "#L" + resultRecordDto.getStartLineNumber());
                    sb.append(" | ").append(resultRecordDto.getIssueCategory());
                    sb.append(" | ").append(resultRecordDto.getIssueCode());
                    sb.append(" | ").append(resultRecordDto.getDescription());
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    public List<CodeAnalysisResultDto> getMergedByLanguage() {
        return mergedByLanguage;
    }

    public void setMergedByLanguage(List<CodeAnalysisResultDto> mergedByLanguage) {
        this.mergedByLanguage = mergedByLanguage;
    }
}
