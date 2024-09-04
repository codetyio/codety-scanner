package io.codety.scanner.reporter.github;

import io.codety.scanner.reporter.github.dto.ValueDistributionDto;
import io.codety.scanner.reporter.IssueToBulletPointTextGenerator;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.CodetyConstant;
import io.codety.scanner.util.DistributionRenderUtil;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class GithubCommentService {

    public boolean addOrUpdatePullRequestComment(AnalyzerRequest analyzerRequest, String pullRequestComment) {
        String gitHubAccessToken = analyzerRequest.getGithubAccessToken();

        GitHub githubClient = null;

        if(analyzerRequest.getExternalPullRequestId() == null){
            CodetyConsoleLogger.debug("Skip posting comment due to the pull request ID is missing");
            return false;
        }
        try {
            githubClient = createGithubClient(gitHubAccessToken);

            GHRepository repository = githubClient.getRepositoryById(analyzerRequest.getExternalGitRepoId());

            GHPullRequest pullRequest = repository.getPullRequest(Integer.valueOf(analyzerRequest.getExternalPullRequestId()));

            List<GHIssueComment> comments = findGhIssueCommentByKeywords(pullRequest, CodetyConstant.githubCommentTagStart);
            GHIssueComment comment = null;
            if(comments == null || comments.isEmpty()){
                comment = pullRequest.comment(pullRequestComment);
            }else{
                comment = comments.get(comments.size()-1);//only keep the last comment if redundant for edge cases.
                comment.update(pullRequestComment);
                for(int i=0; i<comments.size()-1; i++){
                    comments.get(i).delete(); // delete duplicate comments for edge cases.
                }
            }
            String commentUrl = comment == null ? "" : comment.getHtmlUrl().toString();

            CodetyConsoleLogger.info(CodetyConstant.INFO_PR_COMMENT_POSTED + commentUrl);
        } catch (IOException e) {
            CodetyConsoleLogger.debug("Failed to post comment to GitHub", e);
        }

        return true;
    }

    private static List<GHIssueComment> findGhIssueCommentByKeywords(GHPullRequest pullRequest, String bodyKeyword) throws IOException {
        List<GHIssueComment> comments = new ArrayList<>();
        for (GHIssueComment ghIssueComment : pullRequest.listComments()) {
//            GHUser user = ghIssueComment.getUser();
//            if (!user.getType().equalsIgnoreCase("bot")) {
//                continue;
//            }
//            String commentUserLogin = user.getLogin();
            if (ghIssueComment.getBody().contains(bodyKeyword)
//                    || commentUserLogin.equalsIgnoreCase("codety[bot]")
//                    || commentUserLogin.equalsIgnoreCase("codety-test[bot]")
            ) {
                comments.add(ghIssueComment);

            }
        }

        return comments;
    }

    private static GitHub createGithubClient(String gitHubAccessToken) throws IOException {
        return new GitHubBuilder().withOAuthToken(gitHubAccessToken).build();
    }
    int newCommentCountThrotting = 50;
    public void addPullRequestReviews(AnalyzerRequest analyzerRequest, CodeAnalysisResultSetDto codeAnalysisResultSetDto) {
        if (codeAnalysisResultSetDto == null || codeAnalysisResultSetDto.getCodeAnalysisResultDtoList() == null || codeAnalysisResultSetDto.getCodeAnalysisResultDtoList().isEmpty()) {
            return;
        }

        if(analyzerRequest.getExternalPullRequestId() == null){
            CodetyConsoleLogger.debug("Skip posting pull request reviews due to the pull request ID is missing");
            return;
        }
        try {
            GitHub githubClient = createGithubClient(analyzerRequest.getGithubAccessToken());

            List<CodeAnalysisResultDto> codeAnalysisResultDtoList = codeAnalysisResultSetDto.getCodeAnalysisResultDtoList();

            Map<ValueDistributionDto, List<CodeAnalysisIssueDto>> newIssueMap = new HashMap<>();

            for (CodeAnalysisResultDto resultDto : codeAnalysisResultDtoList) {
                for(String filePath : resultDto.getIssuesByFile().keySet()){
                    for (CodeAnalysisIssueDto codeAnalysisIssueDto : resultDto.getIssuesByFile().get(filePath)) {
                        ValueDistributionDto key = new ValueDistributionDto(codeAnalysisIssueDto.getFilePath(), codeAnalysisIssueDto.getStartLineNumber());
                        newIssueMap.putIfAbsent(key, new ArrayList<>());
                        newIssueMap.get(key).add(codeAnalysisIssueDto);
                    }
                }

            }

            GHRepository repository = githubClient.getRepositoryById(analyzerRequest.getExternalGitRepoId());

            GHPullRequest pullRequest = repository.getPullRequest(Integer.valueOf(analyzerRequest.getExternalPullRequestId()));
            String githubReviewCommentTagStart = CodetyConstant.githubReviewCommentTagStart;


            Map<ValueDistributionDto, List<GHPullRequestReviewComment>> existingPullRequestCommentMap = new HashMap<>();

            PagedIterable<GHPullRequestReviewComment> ghPullRequestReviewComments1 = pullRequest.listReviewComments();
            for(GHPullRequestReviewComment comment : ghPullRequestReviewComments1){
                if(comment.getBody().contains(githubReviewCommentTagStart)){
                    ValueDistributionDto prKey = new ValueDistributionDto(comment.getPath(), comment.getLine());
                    existingPullRequestCommentMap.putIfAbsent(prKey, new ArrayList<>());
                    existingPullRequestCommentMap.get(prKey).add(comment);
                }
            }


            PagedIterable<GHPullRequestReview> ghPullRequestReviews = pullRequest.listReviews();
            for(GHPullRequestReview requestReview : ghPullRequestReviews){

                if(requestReview.getState() == GHPullRequestReviewState.PENDING){
                    requestReview.delete();
                }
            }

            CodetyConsoleLogger.debug("Existing issueCommentCount: " + existingPullRequestCommentMap.size() + " newIssueCount: " + newIssueMap.size());

            GHPullRequestReviewBuilder review = pullRequest.createReview();
            review.event(GHPullRequestReviewEvent.PENDING);

            int newlyAddedComment = 0;
            Iterator<ValueDistributionDto> newIssueMapIterator = newIssueMap.keySet().iterator();
            while (newIssueMapIterator.hasNext()) {

                if(newlyAddedComment > newCommentCountThrotting){
                    CodetyConsoleLogger.info("Detected too many issues, throttling the number of comments due to the GitHub maximum comment limit.");
                    break;
                }
                ValueDistributionDto newIssueKey = newIssueMapIterator.next();
                List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = newIssueMap.get(newIssueKey);
                StringBuilder sb = new StringBuilder();
                sb.append(DistributionRenderUtil.getTitleBannerMarkdown(analyzerRequest)).append("\n");
                sb.append("**").append("CodetyBot:").append("**\n");
                for (CodeAnalysisIssueDto issueDto : codeAnalysisIssueDtoList) {
                    IssueToBulletPointTextGenerator.appendIssueText(issueDto, sb, true);
                }
                sb.append("\n");
//                sb.append("(Issues detected with Codety)").append("\n");

                sb.append("\n").append(githubReviewCommentTagStart);
                sb.append(CodetyConstant.githubCommentTagEnd);
                String newCommentBody = sb.toString();
                if(existingPullRequestCommentMap.containsKey(newIssueKey)){
                    List<GHPullRequestReviewComment> ghPullRequestReviewComments = existingPullRequestCommentMap.get(newIssueKey);
                    int size = ghPullRequestReviewComments.size();
                    GHPullRequestReviewComment lastComment = ghPullRequestReviewComments.get(size - 1);
                    String oldCommentBody = lastComment.getBody();

                    if(compareTwoCommentsIgnoringFirstLine(oldCommentBody, newCommentBody)){
                        CodetyConsoleLogger.debug("Skipped unchanged existing comment for: " + newIssueKey.getPath() + ":" + newIssueKey.getStartLineNumber()) ;
                        continue;
                    }else{
                        lastComment.update(newCommentBody); //update the last one
                        CodetyConsoleLogger.debug("Refreshed existing comment for: " + newIssueKey.getPath() + ":" + newIssueKey.getStartLineNumber()) ;
                    }

                    for(int i = 0; i< size - 1; i++){ //delete the first few redundant comments for edge cases.
                        GHPullRequestReviewComment comment = ghPullRequestReviewComments.get(i);
                        deleteComment(comment);
                    }

                    //handle removal at the bottom for preventing NullPointerException
                    existingPullRequestCommentMap.remove(newIssueKey);

                }else{
                    CodetyConsoleLogger.debug("Adding new comment to " + newIssueKey.getPath() + ":" + newIssueKey.getStartLineNumber());
                    review.singleLineComment(newCommentBody, newIssueKey.getPath(), newIssueKey.getStartLineNumber());
                    newlyAddedComment++;
                }

            }

            CodetyConsoleLogger.debug("Existing review comment size: " + existingPullRequestCommentMap.size()) ;
            //remove expired pull request comments.
            Map<ValueDistributionDto, List<GHPullRequestReviewComment>> toBeRemovedCommentMap = new HashMap<>(existingPullRequestCommentMap);
            toBeRemovedCommentMap.keySet().removeAll(newIssueMap.keySet());//
            for(ValueDistributionDto key : toBeRemovedCommentMap.keySet()){
                for(GHPullRequestReviewComment comment : toBeRemovedCommentMap.get(key)){
                    CodetyConsoleLogger.debug("Removing old comment: " + comment.getPath() + ":" + comment.getLine());
                    deleteComment(comment);
                }
            }

            if(newlyAddedComment > 0){
                GHPullRequestReview ghPullRequestReview = review.create();
                ghPullRequestReview.submit("", GHPullRequestReviewState.COMMENTED);
            }

        } catch (IOException e) {
            CodetyConsoleLogger.debug(e);
            throw new RuntimeException(e);
        }

    }



    private boolean compareTwoCommentsIgnoringFirstLine(String oldCommentBody, String newCommentBody) {

        if(oldCommentBody.length() != newCommentBody.length()){
            return false;
        }

        int oldCommentStartPoint = oldCommentBody.indexOf("\n");
        int newCommentStartPoint = newCommentBody.indexOf("\n");
        if(oldCommentStartPoint!=newCommentStartPoint){
            return false;
        }

        for(int i= oldCommentStartPoint; i < oldCommentBody.length(); i++){
            if(oldCommentBody.charAt(i) != newCommentBody.charAt(i)){
                return false;
            }
        }

        return true;
    }

    private static void deleteComment(GHPullRequestReviewComment comment) {
        try {
            if(comment !=null) {
                comment.delete();
            }
        }catch (Exception e){
            CodetyConsoleLogger.debug("Failed during old comment deletion " + comment.getPath() + ":" + comment.getLine());
        }
    }


    public void postPullRequestCommentForTestCase(AnalyzerRequest analyzerRequest) throws Exception {

        GitHub githubClient = createGithubClient(analyzerRequest.getGithubAccessToken());

        GHRepository repository = githubClient.getRepositoryById(analyzerRequest.getExternalGitRepoId());

        GHPullRequest pullRequest = repository.getPullRequest(Integer.valueOf(analyzerRequest.getExternalPullRequestId()));

        List<GHPullRequestReviewComment> ghPullRequestReviewComments = pullRequest.listReviewComments().toList();

        for (GHPullRequestReviewComment comment : ghPullRequestReviewComments) {
            int debugFlag = 1;
        }

        PagedIterable<GHPullRequestReview> ghPullRequestReviews = pullRequest.listReviews();
        for(GHPullRequestReview review: ghPullRequestReviews){
            String body = review.getBody();
            PagedIterable<GHPullRequestReviewComment> ghPullRequestReviewComments1 = review.listReviewComments();
            for (GHPullRequestReviewComment comment : ghPullRequestReviewComments1) {
                //System.out.println(comment.getBody());
                int debugFlag = 1;
            }
        }

//
        PagedIterable<GHPullRequestCommitDetail> ghPullRequestCommitDetails = pullRequest.listCommits();
        for (GHPullRequestCommitDetail commitDetail : ghPullRequestCommitDetails) {

            GHPullRequestCommitDetail.Commit commit = commitDetail.getCommit();
            String msg = commit.getMessage();
            GHPullRequestCommitDetail.Tree tree = commit.getTree();
            String sha = tree.getSha();
        }

        GHPullRequestReviewBuilder review = pullRequest.createReview();
        review.event(GHPullRequestReviewEvent.PENDING);
        review.singleLineComment("blabla", "analyzer/src/main/java/io/codety/analyzer/connectivity/github/GithubCommentService.java", 6);

//        review.singleLineComment();
        GHPullRequestReview ghPullRequestReview = review.create();
        ghPullRequestReview.submit("abc", GHPullRequestReviewState.COMMENTED);



    }

    public static String getGithubPriorityEmoji(Integer priority) {
        if(priority == null){
            return "";
        }
        String priorityEmojo = " :white_circle: ";
        if (priority == null) {
            ;
        } else if (priority >= 4) {
            priorityEmojo = " :red_circle: ";
        } else if (priority >= 3) {
            priorityEmojo = " :yellow_circle: ";
        } else if (priority >= 2) {
            priorityEmojo = " :white_circle: ";
        }
        return priorityEmojo;
    }

    public void addCheckRunAnnotations(AnalyzerRequest analyzerRequest, List<CodeAnalysisResultDto> codeAnalysisResultDtoList) {

        if(codeAnalysisResultDtoList == null || codeAnalysisResultDtoList.isEmpty()){
            return;
        }

        try {
            GitHub githubClient = createGithubClient(analyzerRequest.getGithubAccessToken());

            GHRepository repository = githubClient.getRepositoryById(analyzerRequest.getExternalGitRepoId());


//            String mergeCommitSha = pullRequest.getMergeCommitSha();

            for(CodeAnalysisResultDto resultDto : codeAnalysisResultDtoList) {

                CodetyConsoleLogger.debug("Reporting GitHub Check Run annotation " + analyzerRequest.getGitCommitSha() );

                GHCheckRunBuilder codetyRun = repository.createCheckRun("Codety scanner - " + resultDto.getDisplayTitle(), analyzerRequest.getGitCommitSha());

                String displayTitle = resultDto.getDisplayTitle();
                Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();

                codetyRun.withStatus(GHCheckRun.Status.COMPLETED);
                if(issuesByFile == null || issuesByFile.isEmpty()) {
                    codetyRun.withConclusion(GHCheckRun.Conclusion.SUCCESS);
                }else{
                    GHCheckRunBuilder.Output output = new GHCheckRunBuilder.Output(displayTitle, "found issues in " + issuesByFile.size() + " files");
                    for(String filePath : issuesByFile.keySet()){
                        List<CodeAnalysisIssueDto> codeAnalysisIssueDtos = issuesByFile.get(filePath);
                        for(CodeAnalysisIssueDto issueDto : codeAnalysisIssueDtos){
                            StringBuilder sb = new StringBuilder();
                            IssueToBulletPointTextGenerator.extracted(issueDto,sb );
                            GHCheckRunBuilder.Annotation annotation = new GHCheckRunBuilder.Annotation(filePath, issueDto.getStartLineNumber(), GHCheckRun.AnnotationLevel.WARNING, sb.toString());
                            output.add(annotation);
                        }
                    }

                    codetyRun.add(output);
                    codetyRun.withConclusion(GHCheckRun.Conclusion.ACTION_REQUIRED);
                }

                GHCheckRun ghCheckRun = codetyRun.create();
                CodetyConsoleLogger.debug("Check run URL: " + ghCheckRun.getHtmlUrl());

            }

        }catch (Exception e){
            CodetyConsoleLogger.debug("Failed to post GitHub pull check run annotations" + e.getMessage(), e);
        }

    }
}
