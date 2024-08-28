package io.codety.scanner.source.dto;

public class GitRepoDownloadRequestDto {

    boolean authenticationRequired;

    String accountId;
    Long githubInstallationId;
    Integer externalGitProviderType; //1:github, 2:gitlab, 3:bitbucket.
    String externalGitRepoId;
    String gitRepoFullName;
    String externalPullRequestId;
    String externalPullRequestMergeTargetRef;
    String cloneHttpsUrl;
    String cloneSshUrl;
    String gitBranchRef;
    String gitCommitSha;

    public boolean isAuthenticationRequired() {
        return authenticationRequired;
    }

    public void setAuthenticationRequired(boolean authenticationRequired) {
        this.authenticationRequired = authenticationRequired;
    }

    public String getExternalPullRequestMergeTargetRef() {
        return externalPullRequestMergeTargetRef;
    }

    public void setExternalPullRequestMergeTargetRef(String externalPullRequestMergeTargetRef) {
        this.externalPullRequestMergeTargetRef = externalPullRequestMergeTargetRef;
    }

    public String getGitCommitSha() {
        return gitCommitSha;
    }

    public void setGitCommitSha(String gitCommitSha) {
        this.gitCommitSha = gitCommitSha;
    }

    public Integer getExternalGitProviderType() {
        return externalGitProviderType;
    }

    public void setExternalGitProviderType(Integer externalGitProviderType) {
        this.externalGitProviderType = externalGitProviderType;
    }

    public String getExternalGitRepoId() {
        return externalGitRepoId;
    }

    public void setExternalGitRepoId(String externalGitRepoId) {
        this.externalGitRepoId = externalGitRepoId;
    }

    public String getGitRepoFullName() {
        return gitRepoFullName;
    }

    public void setGitRepoFullName(String gitRepoFullName) {
        this.gitRepoFullName = gitRepoFullName;
    }

    public String getExternalPullRequestId() {
        return externalPullRequestId;
    }

    public void setExternalPullRequestId(String externalPullRequestId) {
        this.externalPullRequestId = externalPullRequestId;
    }

    public Long getGithubInstallationId() {
        return githubInstallationId;
    }

    public void setGithubInstallationId(Long githubInstallationId) {
        this.githubInstallationId = githubInstallationId;
    }

    public String getCloneHttpsUrl() {
        return cloneHttpsUrl;
    }

    public void setCloneHttpsUrl(String cloneHttpsUrl) {
        this.cloneHttpsUrl = cloneHttpsUrl;
    }

    public String getCloneSshUrl() {
        return cloneSshUrl;
    }

    public void setCloneSshUrl(String cloneSshUrl) {
        this.cloneSshUrl = cloneSshUrl;
    }

    public String getGitBranchRef() {
        return gitBranchRef;
    }

    public void setGitBranchRef(String gitBranchRef) {
        this.gitBranchRef = gitBranchRef;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
