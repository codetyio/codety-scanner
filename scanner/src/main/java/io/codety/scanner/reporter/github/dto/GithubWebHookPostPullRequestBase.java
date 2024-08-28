package io.codety.scanner.reporter.github.dto;

public class GithubWebHookPostPullRequestBase {
  private String label;
  private String ref;
  private String sha;
//  User UserObject;
  GithubWebHookPostPullRequestBaseRepo repo;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getRef() {
    return ref;
  }

  public void setRef(String ref) {
    this.ref = ref;
  }

  public String getSha() {
    return sha;
  }

  public void setSha(String sha) {
    this.sha = sha;
  }

  public GithubWebHookPostPullRequestBaseRepo getRepo() {
    return repo;
  }

  public void setRepo(GithubWebHookPostPullRequestBaseRepo repo) {
    this.repo = repo;
  }
}