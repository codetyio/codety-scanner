package io.codety.scanner.analyzer.phpstan;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.phpstan.dto.PhpstanIssueDto;
import io.codety.scanner.analyzer.phpstan.dto.PhpstanMessage;
import io.codety.scanner.analyzer.phpstan.dto.PhpstanRoot;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.JsonFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhpstanConverter {

    private static final int defaultPriority = 3;

    public static List<CodeAnalysisIssueDto> convertResult(String successOutput, String localGitRepoPath) throws JsonProcessingException {
        List<CodeAnalysisIssueDto> result = new ArrayList<>();
        PhpstanRoot phpstanRoot = JsonFactoryUtil.objectMapper.readValue(successOutput, PhpstanRoot.class);

        Map<String, PhpstanIssueDto> files = phpstanRoot.getFiles();
        for(String file : files.keySet()){
            String filePath = file;
            if(filePath.startsWith(localGitRepoPath)){
                filePath = filePath.substring(localGitRepoPath.length()+1);
            }
            PhpstanIssueDto phpstanIssueDto = files.get(file);
            ArrayList<PhpstanMessage> messages = phpstanIssueDto.getMessages();
            if(messages== null){
                continue;
            }
            for(PhpstanMessage phpstanMessage : messages){
                String message = phpstanMessage.getTip();
                CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
                issueDto.setFilePath(filePath);
                issueDto.setStartLineNumber(phpstanMessage.getLine());
                String identifier = phpstanMessage.getIdentifier();
                issueDto.setIssueCategory(identifier);
                issueDto.setDescription(message);
                issueDto.setIssueCode(identifier);
                issueDto.setPriority(defaultPriority);
                result.add(issueDto);
            }
        }

        return result;
    }
}
