package io.codety.scanner.reporter.github.dto;

public class GithubWebHookPostInstallation {
  private Long id;
  private String node_id;

 // Getter Methods 

  public Long getId() {
    return id;
  }

  public String getNode_id() {
    return node_id;
  }

 // Setter Methods 

  public void setId( Long id ) {
    this.id = id;
  }

  public void setNode_id( String node_id ) {
    this.node_id = node_id;
  }
}