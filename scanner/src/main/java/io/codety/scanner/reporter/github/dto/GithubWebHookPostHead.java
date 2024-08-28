package io.codety.scanner.reporter.github.dto;

public class GithubWebHookPostHead {
  private String label;
  private String ref;
  private String sha;
//  User UserObject;
//  Repo RepoObject;


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
}