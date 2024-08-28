package io.codety.scanner.reporter.github.dto;

public class GithubWebhookPostDto {

  private String action;
  private Long number;
  GithubWebHookPostPullRequest pull_request;
  private String before;
  private String after;
  GithubWebHookPostRepository repository;
  GithubWebHookPostSender sender;
  GithubWebHookPostInstallation installation;


  public String getAction() {
    return action;
  }

  public Long getNumber() {
    return number;
  }

  public GithubWebHookPostPullRequest getPull_request() {
    return pull_request;
  }

  public String getBefore() {
    return before;
  }

  public String getAfter() {
    return after;
  }

  public GithubWebHookPostRepository getRepository() {
    return repository;
  }

  public GithubWebHookPostSender getSender() {
    return sender;
  }

  public GithubWebHookPostInstallation getInstallation() {
    return installation;
  }

 // Setter Methods 

  public void setAction( String action ) {
    this.action = action;
  }

  public void setNumber( Long number ) {
    this.number = number;
  }

  public void setPull_request( GithubWebHookPostPullRequest pull_requestObject ) {
    this.pull_request = pull_requestObject;
  }

  public void setBefore( String before ) {
    this.before = before;
  }

  public void setAfter( String after ) {
    this.after = after;
  }

  public void setRepository( GithubWebHookPostRepository repositoryObject ) {
    this.repository = repositoryObject;
  }

  public void setSender( GithubWebHookPostSender senderObject ) {
    this.sender = senderObject;
  }

  public void setInstallation( GithubWebHookPostInstallation installationObject ) {
    this.installation = installationObject;
  }
}