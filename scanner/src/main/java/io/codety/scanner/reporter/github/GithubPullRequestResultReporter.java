package io.codety.scanner.reporter.github;

import io.codety.scanner.reporter.IssueToBulletPointTextGenerator;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.reporter.ResultReporter;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.CodetyConstant;
import io.codety.scanner.util.DistributionRenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GithubPullRequestResultReporter implements ResultReporter {

    @Autowired
    GithubCommentService githubCommentService;

    @Override
    public void deliverResult(AnalyzerRequest analyzerRequest, CodeAnalysisResultSetDto codeAnalysisResultSetDto) {
        boolean postComment = false;
        if (analyzerRequest.getGithubAccessToken() == null && !postComment) { //Skip pull request comment update.
            return;
        }

        List<CodeAnalysisResultDto> codeAnalysisResultDtoList = codeAnalysisResultSetDto.getCodeAnalysisResultDtoList();


        if (analyzerRequest.getGithubAccessToken() != null) {


            if (analyzerRequest.isEnablePostingPullRequestReviews()) {
                try {
                    githubCommentService.addPullRequestReviews(analyzerRequest, codeAnalysisResultSetDto);
                } catch (Exception e) {
                    CodetyConsoleLogger.debug("Failed to post pull request reviews into gitlab with error: " + e.getMessage());
                }
            }

            if (analyzerRequest.isEnablePostingPullRequestComment()) {
                StringBuilder pullRequestComment = createPullRequestComment(analyzerRequest, codeAnalysisResultDtoList);
                githubCommentService.addOrUpdatePullRequestComment(analyzerRequest, pullRequestComment.toString());
            }
//            if(analyzerRequest.isEnablePostingGitHubPullRequestCheckRunAnnotations()){
//                githubCommentService.addCheckRunAnnotations(analyzerRequest, codeAnalysisResultDtoList);
//            }
        }

    }


    private StringBuilder createPullRequestComment(AnalyzerRequest analyzerRequest, List<CodeAnalysisResultDto> codeAnalysisResultDtoList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DistributionRenderUtil.getTitleBannerMarkdown(analyzerRequest)).append("\n");


        StringBuilder analysisResultCommentTablePayload = new StringBuilder();
        for (CodeAnalysisResultDto resultDto : codeAnalysisResultDtoList) {
            String pullRequestComment = convertPullRequestComment(resultDto, analyzerRequest);
            analysisResultCommentTablePayload.append(pullRequestComment);
        }

        if (analysisResultCommentTablePayload.isEmpty()) {
            stringBuilder.append("> [!NOTE]").append("\n");
            if(analyzerRequest.getCodetyAccountType()==-1) {
                stringBuilder.append("> No issue was found based on Codety's default factory settings! :tada: :tada: ").append("\n");
            }else {
                stringBuilder.append("> No issue was found based on the custom Code Standards settings! :tada: :tada: ").append("\n");
            }

        }else{
            if(analyzerRequest.getCodetyAccountType()==-1) {
                stringBuilder.append("Issues found in this pull request based on Codety's default factory settings:").append("\n");
            }else {
                stringBuilder.append("Issues found in this pull request based on the custom Code Standards settings:").append("\n");
            }
            stringBuilder.append(analysisResultCommentTablePayload);
            stringBuilder.append("\n").append("> The above content will be updated automatically after a scan pushed into this pull request.").append("\n");
        }

        stringBuilder.append(CodetyConstant.githubCommentTagStart);
        stringBuilder.append(CodetyConstant.githubCommentTagEnd);
        return stringBuilder;
    }

    String lineBreak = "\n";

    public String convertPullRequestComment(CodeAnalysisResultDto codeAnalysisResultDto, AnalyzerRequest
            analyzerRequest) {
        if (codeAnalysisResultDto == null || codeAnalysisResultDto.getIssuesByFile().isEmpty()) {
            CodetyConsoleLogger.debug("Skipping pr comment for " + codeAnalysisResultDto.getDisplayTitle() + " due to there's no violations in this category.");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("### " + codeAnalysisResultDto.getDisplayTitle()).append(lineBreak);
        sb.append("| File | Issue |" + lineBreak)
                .append("| --- | --- |" + lineBreak)
        ;
        boolean truncate =false;
        Map<String, List<CodeAnalysisIssueDto>> issuesByFile = codeAnalysisResultDto.getIssuesByFile();
        for(String filePathKey : issuesByFile.keySet()) {
            List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = issuesByFile.get(filePathKey);
            Collections.sort(codeAnalysisIssueDtoList, (a, b) -> (b.getStartLineNumber() - a.getStartLineNumber()));
//            StringBuilder issueCode = new StringBuilder();

            StringBuilder issueStr = new StringBuilder();
            for(int i=0; i<codeAnalysisIssueDtoList.size(); i++){
                if(i > 0){
                    issueStr.append("</br>");
                }
                CodeAnalysisIssueDto dto = codeAnalysisIssueDtoList.get(i);
                String githubPriorityEmoji = GithubCommentService.getGithubPriorityEmoji(dto.getPriority());
                issueStr.append(githubPriorityEmoji);

                String tmpSbStr = IssueToBulletPointTextGenerator.createSingleEscapedMarkdownIssueStr(dto);

                issueStr.append(tmpSbStr);
                String filePathRichFormat = " [" + "view" + "](../blob/" + analyzerRequest.getGitCommitSha() + "/" + filePathKey + "#L" + dto.getStartLineNumber() +  ") ";
                issueStr.append(filePathRichFormat);

            }

            String filePath = filePathKey;
            filePath = filePath.length() > 50 ? ".." + filePath.substring(filePath.length() - 50) : filePath;
            String filePathRichFormat = "[" + filePath + "](../blob/" + analyzerRequest.getGitCommitSha() + "/" + filePathKey + ")";
//            String filePathRichFormat = "[" + filePath + "](../blob/" + analyzerRequest.getGitCommitSha() + "/" + filePathKey + "#L" + dto.getStartLineNumber() + ")";

            sb.append("|").append(filePathRichFormat).append("|")
                    .append(issueStr).append("|")
                    .append(lineBreak);

            if (sb.length() > 10000) { //Github's limit is 65536.
                truncate = true;
                break;
            }
        }

        if(truncate) {
            sb.append("> The issue list was truncated due to too many issues were found in this pull request").append(lineBreak);
        }
        return sb.toString();
    }




}
