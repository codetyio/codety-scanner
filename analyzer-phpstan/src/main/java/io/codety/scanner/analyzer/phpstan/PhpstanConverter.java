package io.codety.scanner.analyzer.phpstan;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.phpstan.dto.PhpstanIssueDto;
import io.codety.scanner.analyzer.phpstan.dto.PhpstanRoot;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.JsonFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhpstanConverter {
    public static List<CodeAnalysisIssueDto> convertResult(String successOutput, String localGitRepoPath) throws JsonProcessingException {
        List<CodeAnalysisIssueDto> result = new ArrayList<>();
        PhpstanRoot phpstanRoot = JsonFactoryUtil.objectMapper.readValue(successOutput, PhpstanRoot.class);

        Map<String, PhpstanIssueDto> files = phpstanRoot.getFiles();
        for(String file : files.keySet()){
            if(file.startsWith(localGitRepoPath)){

            }

        }


        return result;
    }
}
