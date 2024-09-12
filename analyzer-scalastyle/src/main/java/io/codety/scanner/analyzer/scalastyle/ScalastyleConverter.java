package io.codety.scanner.analyzer.scalastyle;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.scalastyle.dto.ScalastyleCheckstyle;
import io.codety.scanner.analyzer.scalastyle.dto.ScalastyleError;
import io.codety.scanner.analyzer.scalastyle.dto.ScalastyleFile;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.XmlFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class ScalastyleConverter {

    private static final String genericCategory = "generic";
    public static List<CodeAnalysisIssueDto> convertResult(String payload) {
        ArrayList<CodeAnalysisIssueDto> codeAnalysisIssueDtos = new ArrayList<>();
        try {
            ScalastyleCheckstyle scalastyleCheckstyle = XmlFactoryUtil.xmlMapper.readValue(payload, ScalastyleCheckstyle.class);
            List<ScalastyleFile> files = scalastyleCheckstyle.getFile();
            if(files!=null) {
                for (ScalastyleFile scalastyleFile : files) {

                    String fileName = scalastyleFile.getName();
                    if (fileName.startsWith("/")) {
                        fileName = fileName.substring(1);
                    }
                    List<ScalastyleError> errors = scalastyleFile.getError();
                    for (ScalastyleError error : errors) {
                        CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
                        issueDto.setFilePath(fileName);
                        String severity = error.getSeverity();
                        issueDto.setPriority(3);
                        issueDto.setIssueCategory(genericCategory);
                        String source = error.getSource();
                        if (source != null) {
                            String[] split = source.split("\\.");
                            source = split[split.length - 1];
                        }
                        issueDto.setIssueCode(source);
                        issueDto.setStartLineNumber(error.getLine() == null ? 1 : error.getLine());
                        issueDto.setDescription(error.getMessage());
                        codeAnalysisIssueDtos.add(issueDto);
                    }
                }
            }


        } catch (JsonProcessingException e) {
            CodetyConsoleLogger.debug("Failed to parse scalatyle result", e);
        }


        return codeAnalysisIssueDtos;
    }
}
