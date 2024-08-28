package io.codety.scanner.reporter.github.dto;

import java.util.ArrayList;

public class GithubWebHookPostPullRequest {
  private String url;
  private Long id;
  private String node_id;
  private String html_url;
  private String diff_url;
  private String patch_url;
  private String issue_url;
  private Long number;
  private String state;
  private boolean locked;
  private String title;
  GithubWebHookPostUser UserObject;
  private String body = null;
  private String created_at;
  private String updated_at;
  private String closed_at = null;
  private String merged_at = null;
  private String merge_commit_sha;
  private String assignee = null;
  ArrayList<Object> assignees = new ArrayList<Object>();
  ArrayList<Object> requested_reviewers = new ArrayList<Object>();
  ArrayList<Object> requested_teams = new ArrayList<Object>();
  ArrayList<Object> labels = new ArrayList<Object>();
  private String milestone = null;
  private boolean draft;
  private String commits_url;
  private String review_comments_url;
  private String review_comment_url;
  private String comments_url;
  private String statuses_url;
  GithubWebHookPostHead head;
  GithubWebHookPostPullRequestBase base;
//  _links _linksObject;
  private String author_association;
  private String auto_merge = null;
  private String active_lock_reason = null;
  private boolean merged;
  private String mergeable = null;
  private String rebaseable = null;
  private String mergeable_state;
  private String merged_by = null;
  private Long comments;
  private Long review_comments;
  private boolean maintainer_can_modify;
  private Long commits;
  private Long additions;
  private Long deletions;
  private Long changed_files;


 // Getter Methods 

  public GithubWebHookPostPullRequestBase getBase() {
    return base;
  }

  public void setBase(GithubWebHookPostPullRequestBase base) {
    this.base = base;
  }

  public String getUrl() {
    return url;
  }

  public Long getId() {
    return id;
  }

  public String getNode_id() {
    return node_id;
  }

  public String getHtml_url() {
    return html_url;
  }

  public String getDiff_url() {
    return diff_url;
  }

  public String getPatch_url() {
    return patch_url;
  }

  public String getIssue_url() {
    return issue_url;
  }

  public Long getNumber() {
    return number;
  }

  public String getState() {
    return state;
  }

  public boolean getLocked() {
    return locked;
  }

  public String getTitle() {
    return title;
  }

  public GithubWebHookPostUser getUser() {
    return UserObject;
  }

  public String getBody() {
    return body;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public String getClosed_at() {
    return closed_at;
  }

  public String getMerged_at() {
    return merged_at;
  }

  public String getMerge_commit_sha() {
    return merge_commit_sha;
  }

  public String getAssignee() {
    return assignee;
  }

  public String getMilestone() {
    return milestone;
  }

  public boolean getDraft() {
    return draft;
  }

  public String getCommits_url() {
    return commits_url;
  }

  public String getReview_comments_url() {
    return review_comments_url;
  }

  public String getReview_comment_url() {
    return review_comment_url;
  }

  public String getComments_url() {
    return comments_url;
  }

  public String getStatuses_url() {
    return statuses_url;
  }


  public String getAuthor_association() {
    return author_association;
  }

  public String getAuto_merge() {
    return auto_merge;
  }

  public String getActive_lock_reason() {
    return active_lock_reason;
  }

  public boolean getMerged() {
    return merged;
  }

  public String getMergeable() {
    return mergeable;
  }

  public String getRebaseable() {
    return rebaseable;
  }

  public String getMergeable_state() {
    return mergeable_state;
  }

  public String getMerged_by() {
    return merged_by;
  }

  public Long getComments() {
    return comments;
  }

  public Long getReview_comments() {
    return review_comments;
  }

  public boolean getMaintainer_can_modify() {
    return maintainer_can_modify;
  }

  public Long getCommits() {
    return commits;
  }

  public Long getAdditions() {
    return additions;
  }

  public Long getDeletions() {
    return deletions;
  }

  public Long getChanged_files() {
    return changed_files;
  }

 // Setter Methods 

  public void setUrl( String url ) {
    this.url = url;
  }

  public void setId( Long id ) {
    this.id = id;
  }

  public void setNode_id( String node_id ) {
    this.node_id = node_id;
  }

  public void setHtml_url( String html_url ) {
    this.html_url = html_url;
  }

  public void setDiff_url( String diff_url ) {
    this.diff_url = diff_url;
  }

  public void setPatch_url( String patch_url ) {
    this.patch_url = patch_url;
  }

  public void setIssue_url( String issue_url ) {
    this.issue_url = issue_url;
  }

  public void setNumber( Long number ) {
    this.number = number;
  }

  public void setState( String state ) {
    this.state = state;
  }

  public void setLocked( boolean locked ) {
    this.locked = locked;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public void setUser( GithubWebHookPostUser userObject ) {
    this.UserObject = userObject;
  }

  public void setBody( String body ) {
    this.body = body;
  }

  public void setCreated_at( String created_at ) {
    this.created_at = created_at;
  }

  public void setUpdated_at( String updated_at ) {
    this.updated_at = updated_at;
  }

  public void setClosed_at( String closed_at ) {
    this.closed_at = closed_at;
  }

  public void setMerged_at( String merged_at ) {
    this.merged_at = merged_at;
  }

  public void setMerge_commit_sha( String merge_commit_sha ) {
    this.merge_commit_sha = merge_commit_sha;
  }

  public void setAssignee( String assignee ) {
    this.assignee = assignee;
  }

  public void setMilestone( String milestone ) {
    this.milestone = milestone;
  }

  public void setDraft( boolean draft ) {
    this.draft = draft;
  }

  public void setCommits_url( String commits_url ) {
    this.commits_url = commits_url;
  }

  public boolean isLocked() {
    return locked;
  }

  public GithubWebHookPostHead getHead() {
    return head;
  }

  public void setHead(GithubWebHookPostHead head) {
    this.head = head;
  }

  public void setReview_comments_url(String review_comments_url ) {
    this.review_comments_url = review_comments_url;
  }

  public void setReview_comment_url( String review_comment_url ) {
    this.review_comment_url = review_comment_url;
  }

  public void setComments_url( String comments_url ) {
    this.comments_url = comments_url;
  }

  public void setStatuses_url( String statuses_url ) {
    this.statuses_url = statuses_url;
  }

  public void setAuthor_association( String author_association ) {
    this.author_association = author_association;
  }

  public void setAuto_merge( String auto_merge ) {
    this.auto_merge = auto_merge;
  }

  public void setActive_lock_reason( String active_lock_reason ) {
    this.active_lock_reason = active_lock_reason;
  }

  public void setMerged( boolean merged ) {
    this.merged = merged;
  }

  public void setMergeable( String mergeable ) {
    this.mergeable = mergeable;
  }

  public void setRebaseable( String rebaseable ) {
    this.rebaseable = rebaseable;
  }

  public void setMergeable_state( String mergeable_state ) {
    this.mergeable_state = mergeable_state;
  }

  public void setMerged_by( String merged_by ) {
    this.merged_by = merged_by;
  }

  public void setComments( Long comments ) {
    this.comments = comments;
  }

  public void setReview_comments( Long review_comments ) {
    this.review_comments = review_comments;
  }

  public void setMaintainer_can_modify( boolean maintainer_can_modify ) {
    this.maintainer_can_modify = maintainer_can_modify;
  }

  public void setCommits( Long commits ) {
    this.commits = commits;
  }

  public void setAdditions( Long additions ) {
    this.additions = additions;
  }

  public void setDeletions( Long deletions ) {
    this.deletions = deletions;
  }

  public void setChanged_files( Long changed_files ) {
    this.changed_files = changed_files;
  }
}