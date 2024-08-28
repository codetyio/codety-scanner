package io.codety.scanner.util;

public class CodetyConstant {
    public static final String INFO_CANNOT_LOCATE_GIT_PATH = "The given git repo path does not valid or not exist. ";
    public static final String INFO_INVALID_MAIN_PROCESS_INPUT = "No valid input, if you are running it locally, please run something like this: `docker run --rm -v $(pwd):/src -w /src codetyio/codety`  ";
    public static final String ENV_CODETY_RUNNER_DEBUG = "CODETY_RUNNER_DEBUG"; // 1 or 0
    public static final String ENV_CODETY_TOKEN = "CODETY_TOKEN";
    public static final String ENV_SLACK_OAUTH_TOKEN = "SLACK_OAUTH_TOKEN";
    public static final String ENV_SLACK_CONVERSATION_ID = "SLACK_CONVERSATION_ID";
    public static final String ENV_CODETY_ISSUE_REPORTER_SLACK = "CODETY_ISSUE_REPORTER_SLACK";
    public static final String ENV_CODETY_ISSUE_REPORTER_CONSOLE = "CODETY_ISSUE_REPORTER_CONSOLE";
    public static final String ENV_CODETY_ISSUE_REPORTER_GITHUB_PR_COMMENT = "CODETY_ISSUE_REPORTER_GITHUB_PR_COMMENT";
    public static final String ENV_CODETY_ISSUE_REPORTER_GITHUB_PR_REVIEW = "CODETY_ISSUE_REPORTER_GITHUB_PR_REVIEW";
    public static final String ENV_CODETY_ISSUE_REPORTER_GITHUB_PR_CHECK_RUN_ANNOTATION = "CODETY_ISSUE_REPORTER_GITHUB_PR_CHECK_RUN_ANNOTATION";
    public static final String ENV_CODETY_HOST = "CODETY_HOST";
    public static final String ENV_CODETY_REPORT_ALL_ISSUES = "CODETY_REPORT_ALL_ISSUES";


    public static final String COMMAND_GIT = "git";
    public static final String[] COMMAND_GIT_FETCH = new String[]{COMMAND_GIT, "fetch"};
    public static final String INFO_PR_COMMENT_POSTED = "Posted the code analysis result to ";
    public static final String INFO_FINISHED_RESULT_FILTERING = "Finished result filtering.";
    public static final String INFO_SKIP_RESULT_FILTERING = "Skip result filtering.";
    public static final String INFO_SHOW_ANALYSIS_RESULT = "====== Codety code analysis result: ======";
    public static final String INFO_CODETY_TOKEN_NOT_FOUND = "CODETY_TOKEN environment variable not found, starting Codety Scanner in offline mode";
    public static final String BASE_PACKAGE = "io.codety.scanner";
    public static final String PMD_BIN_PATH = "${codety.analyzer.java.pmd.path}";


    public static String githubCommentTagStart = "<!--=codety=-comment-";
    public static String githubReviewCommentTagStart = "<!--=codety=-review-";
    public static String githubCommentTagEnd = "-->";
}
