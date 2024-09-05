package io.codety.scanner.reporter.slack;

import io.codety.scanner.reporter.IssueToBulletPointTextGenerator;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import io.codety.scanner.reporter.slack.dto.SlackMessageBlock;
import io.codety.scanner.reporter.slack.dto.SlackMessageElement;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.service.dto.GitProviderType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SlackResultGenerator {
    public String generatePayloadResult(AnalyzerRequest analyzerRequest, CodeAnalysisResultSetDto codeAnalysisResultSetDto) {


        StringBuilder sb = new StringBuilder("");
        List<CodeAnalysisResultDto> codeAnalysisResultDtoList = codeAnalysisResultSetDto.getCodeAnalysisResultDtoList();
        if(codeAnalysisResultSetDto == null || codeAnalysisResultDtoList.isEmpty()){
            return sb.toString();
        }
        for(CodeAnalysisResultDto resultDto : codeAnalysisResultDtoList){

            Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
            if(issuesByFile == null || issuesByFile.isEmpty()){
                continue;
            }

            sb.append(resultDto.getDisplayTitle()).append("\n");
            for(String filePath : issuesByFile.keySet()) {
                for (CodeAnalysisIssueDto issueDto : issuesByFile.get(filePath)) {
                    IssueToBulletPointTextGenerator.appendIssueText(issueDto, sb, false);
                }
            }

        }


        return sb.toString();
    }

    public List<SlackMessageBlock> generateRichTextPayloadResult(AnalyzerRequest analyzerRequest, CodeAnalysisResultSetDto codeAnalysisResultSetDto) {
        List<SlackMessageBlock> blocks = new ArrayList<>();
        List<CodeAnalysisResultDto> codeAnalysisResultDtoList = codeAnalysisResultSetDto.getMergedByLanguage();
        if(codeAnalysisResultSetDto == null || codeAnalysisResultDtoList.size() ==0 ){
            return blocks;
        }

        SlackMessageBlock rootBlock = new SlackMessageBlock();
        rootBlock.setType("rich_text");
        ArrayList<SlackMessageElement> rootElementList = new ArrayList<>();
        rootBlock.setElements(rootElementList);


        SlackMessageElement e = new SlackMessageElement();
        e.setType("rich_text_section");//  or use rich_text_preformatted
        e.setElements(new ArrayList<>());
        e.getElements().add(new SlackMessageElement("text", "Found some code issues against code standards from Codety:"));
        rootElementList.add(e);

        String gitBaseHttpsUrl = analyzerRequest.getGitBaseHttpsUrl() + "/" + analyzerRequest.getGitRepoFullName();
        if(analyzerRequest.getGitProviderType()!=null && analyzerRequest.getGitProviderType()== GitProviderType.GITHUB && analyzerRequest.getExternalPullRequestId()!=null){
            gitBaseHttpsUrl+= "/pull/" +analyzerRequest.getExternalPullRequestId() + "/files";
        }

        for(CodeAnalysisResultDto resultDto : codeAnalysisResultDtoList) {

            if(resultDto.getIssuesByFile() == null || resultDto.getIssuesByFile().isEmpty()){
                continue;
            }

            SlackMessageElement eachFileSection = new SlackMessageElement();
            eachFileSection.setType("rich_text_quote");
            eachFileSection.setElements(new ArrayList<>());
            eachFileSection.getElements().add(new SlackMessageElement("text", resultDto.getDisplayTitle()));
            rootElementList.add(eachFileSection);

            Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
            for(String file : issuesByFile.keySet()) {
                List<CodeAnalysisIssueDto> issues = issuesByFile.get(file);
                SlackMessageElement indentIssueSection = new SlackMessageElement();
                indentIssueSection.setType("rich_text_list");
                indentIssueSection.setElements(new ArrayList<>());
                indentIssueSection.setIndent(1);
                indentIssueSection.setStyle("bullet");
                rootElementList.add(indentIssueSection);
                for (CodeAnalysisIssueDto issueDto : issues) {
                    StringBuilder sb = new StringBuilder();
                    SlackMessageElement issueElement = new SlackMessageElement();
                    issueElement.setType("rich_text_section");
                    issueElement.setElements(new ArrayList<>());
                    IssueToBulletPointTextGenerator.extracted(issueDto, sb);

                    SlackMessageElement link = new SlackMessageElement("link", issueDto.getFilePath());
                    link.setUrl(gitBaseHttpsUrl);
                    issueElement.getElements().add(link);
                    issueElement.getElements().add(new SlackMessageElement("text", sb.toString()));
                    indentIssueSection.getElements().add(issueElement);
                }
            }
        }

        SlackMessageElement endingSection = new SlackMessageElement();
        endingSection.setType("rich_text_section");
        endingSection.setElements(new ArrayList<>());
        endingSection.getElements().add(new SlackMessageElement("text", "(Issues detected with Codety)"));
        rootElementList.add(endingSection);

        blocks.add(rootBlock);
        return blocks;
    }
}
