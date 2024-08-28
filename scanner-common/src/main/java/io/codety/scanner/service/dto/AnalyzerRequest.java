package io.codety.scanner.service.dto;

import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.CodetyConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AnalyzerRequest {

    private Map<String, String> envMap = new HashMap();

    private String codetyToken;
    private String codetyHost;
    private String codetyTimeout;
    private boolean filterByGitDiff = true;

    private boolean enablePostingPullRequestComment = true;
    private boolean enablePostingPullRequestReviews = true;
    private boolean enablePostingGitHubPullRequestCheckRunAnnotations = true;
    private boolean enableConsoleIssueReporter = true;

    private GitProviderType gitProviderType;
    private ComputeRunnerType computeRunnerType;


    private String localGitRepoPath;
    private String gitRepoFullName;
    private String externalGitRepoOwnerId;
    private String externalGitRepoOwnerName;
    private String externalGitRepoId;
    private String externalPullRequestId;
    private String externalPullRequestMergeTargetBranchRef;
    private String gitBaseHttpsUrl;
    private String gitBranchRef;
    private String gitCommitSha;
    private String githubAccessToken;
    private Boolean privateRepo;

    private boolean enableSlackNotification;
    private String slackOauthToken;
    private String slackConversationId;

    private Integer jobRunningStatus;//0:todo, 1:running, 200:succeed, 500:failed.
    private String jobRunnerIdentifier;
    private Date taskStartedTime;
    private Date taskEndedTime;

    int codetyAccountType = -1;

    Date createdTime = new Date();


    private static final String githubAction = "GITHUB_ACTION";
    private static final String gitlabCi = "GITLAB_CI";
    private static final String circleci = "CIRCLECI";
    private static final String harnessProjectId = "HARNESS_PROJECT_ID";
    private AnalyzerRequest() {

    }

    public static AnalyzerRequest processSystemVariablesToRequest(Map<String, String> envMap, String[] args) {

        //keep this att he top.
        String printDebugInfo = envMap.get(CodetyConstant.ENV_CODETY_RUNNER_DEBUG);
        if(printDebugInfo!=null && ("1".equalsIgnoreCase(printDebugInfo) || "true".equalsIgnoreCase(printDebugInfo.trim()))){
            CodetyConsoleLogger.setPrintDebugInfo(true);
        }

//        if(CodetyConsoleLogger.isDebugMode()) {
//            CodetyConsoleLogger.debug("========ENV VARIABLES===========");
//            envMap.forEach((k, v) -> {
//                String value = v;
//                String lowerCase = k.toLowerCase();
//                if (lowerCase.contains("token") || lowerCase.contains("password")) {
//                    value = "******MASKED******";
//                }
//                CodetyConsoleLogger.debug(k + "=" + value);
//            });
//            CodetyConsoleLogger.debug("====================");
//        }

        AnalyzerRequest analyzerRequest = new AnalyzerRequest();
        analyzerRequest.setLocalGitRepoPath(args[0]);
        analyzerRequest.setCodetyToken(envMap.get(CodetyConstant.ENV_CODETY_TOKEN));
        analyzerRequest.setCodetyHost(envMap.get(CodetyConstant.ENV_CODETY_HOST));

        analyzerRequest.enableSlackNotification = "true".equals(envMap.get(CodetyConstant.ENV_CODETY_ISSUE_REPORTER_SLACK));
        if(analyzerRequest.enableSlackNotification) {
            analyzerRequest.slackOauthToken = (envMap.get(CodetyConstant.ENV_SLACK_OAUTH_TOKEN));
            analyzerRequest.slackConversationId = (envMap.get(CodetyConstant.ENV_SLACK_CONVERSATION_ID));
        }

        analyzerRequest.enableConsoleIssueReporter = !"false".equals(envMap.get(CodetyConstant.ENV_CODETY_ISSUE_REPORTER_CONSOLE));
        analyzerRequest.enablePostingPullRequestComment = !"false".equals(envMap.get(CodetyConstant.ENV_CODETY_ISSUE_REPORTER_GITHUB_PR_COMMENT));
        analyzerRequest.enablePostingPullRequestReviews = !"false".equals(envMap.get(CodetyConstant.ENV_CODETY_ISSUE_REPORTER_GITHUB_PR_REVIEW));
        analyzerRequest.enablePostingGitHubPullRequestCheckRunAnnotations = !"false".equals(envMap.get(CodetyConstant.ENV_CODETY_ISSUE_REPORTER_GITHUB_PR_CHECK_RUN_ANNOTATION));



        String handleDiff = envMap.get(CodetyConstant.ENV_CODETY_REPORT_ALL_ISSUES);
        if(handleDiff == null || "0".equals(handleDiff)){
            analyzerRequest.setFilterByGitDiff(true);
        }else{
            analyzerRequest.setFilterByGitDiff(false);
        }

        if(envMap.containsKey(githubAction)){
            CodetyConsoleLogger.info("Preparing for GitHub environment.");
            analyzerRequest.setGitProviderType(GitProviderType.GITHUB);
            analyzerRequest.setComputeRunnerType(ComputeRunnerType.GITHUB_ACTIONS);

            analyzerRequest.setGitBaseHttpsUrl(envMap.get("GITHUB_SERVER_URL")); //e.g. https://github.com
            analyzerRequest.setGitRepoFullName(envMap.get("GITHUB_REPOSITORY"));// e.g. random1223/gs-spring-boot
            analyzerRequest.setGitCommitSha(envMap.get("GITHUB_SHA")); //e.g. a2d8f6a49854bed81bb05bf58d3d4ac49f6e9abc
            analyzerRequest.setGithubAccessToken(envMap.get("GITHUB_TOKEN"));// e.g. fiosoiefwoije
            analyzerRequest.setExternalGitRepoId(envMap.get("GITHUB_REPOSITORY_ID")); //e.g 815334739
            analyzerRequest.setExternalGitRepoOwnerId(envMap.get("GITHUB_REPOSITORY_OWNER_ID")); //e.g. 3987237
            analyzerRequest.setExternalPullRequestMergeTargetBranchRef(envMap.get("GITHUB_BASE_REF")); //main, etc.
            String githubRefName = envMap.get("GITHUB_REF_NAME");
            if(githubRefName.contains("/merge")){
                analyzerRequest.setExternalPullRequestId(githubRefName.replace("/merge", "")); // e.g. 19/merge
            }else{
                analyzerRequest.setExternalPullRequestId(null);
            }


        }else {

            if(envMap.containsKey(gitlabCi)){
                //it is gitlab CI repo.
                analyzerRequest.setGitProviderType(GitProviderType.GITLAB);
                analyzerRequest.setComputeRunnerType(ComputeRunnerType.GITLAB_CI);

                analyzerRequest.setGitCommitSha(envMap.get("CI_COMMIT_SHA")); //e.g. https://github.com
                analyzerRequest.setGitBaseHttpsUrl(envMap.get("CI_SERVER_HOST")); //e.g. https://github.com
                analyzerRequest.setGitRepoFullName(envMap.get("CI_PROJECT_NAMESPACE") + "/" + envMap.get("CI_PROJECT_NAME"));// e.g. random1223/gs-spring-boot
                //check more https://docs.gitlab.com/ee/ci/variables/predefined_variables.html
                analyzerRequest.setExternalGitRepoId(envMap.get("CI_PROJECT_ID")); //e.g 815334739

                CodetyConsoleLogger.info("Preparing for GitLab environment.");
            }else {

                if(envMap.containsKey(circleci)){
                    //it is gitlab CI repo. //check more here: https://circleci.com/docs/variables/

                    analyzerRequest.setComputeRunnerType(ComputeRunnerType.CIRCLECI);

                    analyzerRequest.setGitBaseHttpsUrl(envMap.get("CIRCLE_REPOSITORY_URL"));
                    analyzerRequest.setGitRepoFullName(envMap.get("CIRCLE_PROJECT_REPONAME"));
                    analyzerRequest.setExternalPullRequestId(envMap.get("CIRCLE_PR_NUMBER"));
                    analyzerRequest.setGitCommitSha(envMap.get("CIRCLE_SHA1"));
                    analyzerRequest.setGitBranchRef(envMap.get("CIRCLE_BRANCH"));

                    //CIRCLE_REPOSITORY_URL

                    CodetyConsoleLogger.info("Preparing for CircleCI environment.");
                }else {

                    if(envMap.containsKey(harnessProjectId)){
                        analyzerRequest.setGitProviderType(GitProviderType.GITHUB);
                        analyzerRequest.setComputeRunnerType(ComputeRunnerType.HARNESS);
                        //from github action.
                        analyzerRequest.setGitCommitSha(envMap.get("DRONE_COMMIT_SHA")); //e.g. 4fb2b0d39f5ed9db4a5d1f8293198eb627fedc3d
                        String droneRepoLink = envMap.get("DRONE_REPO_LINK");//e.g. https://github.com/random1223/gs-spring-boot
                        String droneRepo = envMap.get("DRONE_REPO");// e.g. random1223/gs-spring-boot
                        analyzerRequest.setGitRepoFullName(droneRepo);
                        //Ge the base URL by removing the length of characters from the end.
                        analyzerRequest.setGitBaseHttpsUrl(droneRepoLink == null ? null : droneRepoLink.substring(0, droneRepoLink.length() - droneRepo.length() - 1));
                        analyzerRequest.setExternalGitRepoId(null);
                        analyzerRequest.setExternalGitRepoOwnerName(envMap.get("DRONE_REPO_OWNER"));//e.g. random1223
                        analyzerRequest.setExternalPullRequestMergeTargetBranchRef(envMap.get("DRONE_TARGET_BRANCH")); //e.g. main
                        analyzerRequest.setExternalPullRequestId(envMap.get("DRONE_PULL_REQUEST"));//e.g. 26

                        CodetyConsoleLogger.info("Preparing for Harness environment.");
                    }else{
            //            throw new RuntimeException("Not supported. ");
                        CodetyConsoleLogger.info("Preparing for container environment.");
                    }
                }
            }
        }

        return analyzerRequest;
    }

    public ComputeRunnerType getComputeRunnerType() {
        return computeRunnerType;
    }

    public void setComputeRunnerType(ComputeRunnerType computeRunnerType) {
        this.computeRunnerType = computeRunnerType;
    }

    public String getExternalGitRepoOwnerName() {
        return externalGitRepoOwnerName;
    }

    public void setExternalGitRepoOwnerName(String externalGitRepoOwnerName) {
        this.externalGitRepoOwnerName = externalGitRepoOwnerName;
    }

    public String getLocalGitRepoPath() {
        return localGitRepoPath;
    }

    public void setLocalGitRepoPath(String localGitRepoPath) {
        this.localGitRepoPath = localGitRepoPath;
    }

    public Map<String, String> getEnvMap() {
        return envMap;
    }

    public void setEnvMap(Map<String, String> envMap) {
        this.envMap = envMap;
    }

    public String getGithubAccessToken() {
        return githubAccessToken;
    }

    public void setGithubAccessToken(String githubAccessToken) {
        this.githubAccessToken = githubAccessToken;
    }

    public GitProviderType getGitProviderType() {
        return gitProviderType;
    }

    public String getExternalGitRepoOwnerId() {
        return externalGitRepoOwnerId;
    }

    public void setExternalGitRepoOwnerId(String externalGitRepoOwnerId) {
        this.externalGitRepoOwnerId = externalGitRepoOwnerId;
    }

    public void setGitProviderType(GitProviderType gitProviderType) {
        this.gitProviderType = gitProviderType;
    }

    public String getExternalGitRepoId() {
        return externalGitRepoId;
    }

    public String getGitRepoFullName() {
        return gitRepoFullName;
    }

    public void setGitRepoFullName(String gitRepoFullName) {
        this.gitRepoFullName = gitRepoFullName;
    }

    public void setExternalGitRepoId(String externalGitRepoId) {
        this.externalGitRepoId = externalGitRepoId;
    }

    public String getExternalPullRequestId() {
        return externalPullRequestId;
    }

    public String getCodetyToken() {
        return codetyToken;
    }

    public void setCodetyToken(String codetyToken) {
        this.codetyToken = codetyToken;
    }

    public void setExternalPullRequestId(String externalPullRequestId) {
        this.externalPullRequestId = externalPullRequestId;
    }

    public String getExternalPullRequestMergeTargetBranchRef() {
        return externalPullRequestMergeTargetBranchRef;
    }

    public void setExternalPullRequestMergeTargetBranchRef(String externalPullRequestMergeTargetBranchRef) {
        this.externalPullRequestMergeTargetBranchRef = externalPullRequestMergeTargetBranchRef;
    }

    public String getGitBaseHttpsUrl() {
        return gitBaseHttpsUrl;
    }

    public void setGitBaseHttpsUrl(String gitBaseHttpsUrl) {
        this.gitBaseHttpsUrl = gitBaseHttpsUrl;
    }

    public String getGitBranchRef() {
        return gitBranchRef;
    }

    public void setGitBranchRef(String gitBranchRef) {
        this.gitBranchRef = gitBranchRef;
    }

    public String getGitCommitSha() {
        return gitCommitSha;
    }

    public void setGitCommitSha(String gitCommitSha) {
        this.gitCommitSha = gitCommitSha;
    }

    public Boolean getPrivateRepo() {
        return privateRepo;
    }

    public void setPrivateRepo(Boolean privateRepo) {
        this.privateRepo = privateRepo;
    }

    public Integer getJobRunningStatus() {
        return jobRunningStatus;
    }

    public void setJobRunningStatus(Integer jobRunningStatus) {
        this.jobRunningStatus = jobRunningStatus;
    }

    public String getJobRunnerIdentifier() {
        return jobRunnerIdentifier;
    }

    public void setJobRunnerIdentifier(String jobRunnerIdentifier) {
        this.jobRunnerIdentifier = jobRunnerIdentifier;
    }

    public Date getTaskStartedTime() {
        return taskStartedTime;
    }

    public void setTaskStartedTime(Date taskStartedTime) {
        this.taskStartedTime = taskStartedTime;
    }

    public Date getTaskEndedTime() {
        return taskEndedTime;
    }

    public void setTaskEndedTime(Date taskEndedTime) {
        this.taskEndedTime = taskEndedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCodetyHost() {
        return codetyHost;
    }

    public void setCodetyHost(String codetyHost) {
        this.codetyHost = codetyHost;
    }

    public String getCodetyTimeout() {
        return codetyTimeout;
    }

    public void setCodetyTimeout(String codetyTimeout) {
        this.codetyTimeout = codetyTimeout;
    }

    public boolean isFilterByGitDiff() {
        return filterByGitDiff;
    }

    public void setFilterByGitDiff(boolean filterByGitDiff) {
        this.filterByGitDiff = filterByGitDiff;
    }

    public boolean isEnablePostingPullRequestComment() {
        return enablePostingPullRequestComment;
    }

    public void setEnablePostingPullRequestComment(boolean enablePostingPullRequestComment) {
        this.enablePostingPullRequestComment = enablePostingPullRequestComment;
    }

    public boolean isEnablePostingPullRequestReviews() {
        return enablePostingPullRequestReviews;
    }

    public void setEnablePostingPullRequestReviews(boolean enablePostingPullRequestReviews) {
        this.enablePostingPullRequestReviews = enablePostingPullRequestReviews;
    }

    public int getCodetyAccountType() {
        return codetyAccountType;
    }

    public void setCodetyAccountType(int codetyAccountType) {
        this.codetyAccountType = codetyAccountType;
    }

    public String getSlackOauthToken() {
        return slackOauthToken;
    }

    public String getSlackConversationId() {
        return slackConversationId;
    }

    public boolean isEnableSlackNotification() {
        return enableSlackNotification;
    }

    public boolean isEnablePostingGitHubPullRequestCheckRunAnnotations() {
        return enablePostingGitHubPullRequestCheckRunAnnotations;
    }

    public void setEnablePostingGitHubPullRequestCheckRunAnnotations(boolean enablePostingGitHubPullRequestCheckRunAnnotations) {
        this.enablePostingGitHubPullRequestCheckRunAnnotations = enablePostingGitHubPullRequestCheckRunAnnotations;
    }

    public void setEnableSlackNotification(boolean enableSlackNotification) {
        this.enableSlackNotification = enableSlackNotification;
    }

    public void setSlackOauthToken(String slackOauthToken) {
        this.slackOauthToken = slackOauthToken;
    }

    public void setSlackConversationId(String slackConversationId) {
        this.slackConversationId = slackConversationId;
    }

    public boolean isEnableConsoleIssueReporter() {
        return enableConsoleIssueReporter;
    }

    public void setEnableConsoleIssueReporter(boolean enableConsoleIssueReporter) {
        this.enableConsoleIssueReporter = enableConsoleIssueReporter;
    }
}
