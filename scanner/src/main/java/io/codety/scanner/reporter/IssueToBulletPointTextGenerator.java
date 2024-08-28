package io.codety.scanner.reporter;

import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;

import static io.codety.scanner.reporter.github.GithubCommentService.getGithubPriorityEmoji;

public class IssueToBulletPointTextGenerator {

    public static void appendIssueText(CodeAnalysisIssueDto issueDto, StringBuilder sb, boolean escape) {
        sb.append("* ");
        String priorityEmoji = getGithubPriorityEmoji(issueDto.getPriority());
        sb.append(priorityEmoji);

        if(escape) {
            sb.append(createSingleEscapedMarkdownIssueStr(issueDto));
        }else{
            extracted(issueDto, sb);
        }

        sb.append("\n");
    }

    public static String createSingleEscapedMarkdownIssueStr(CodeAnalysisIssueDto dto) {
        StringBuilder tmpSb = new StringBuilder();
        extracted(dto, tmpSb);
        String tmpSbStr = tmpSb.toString();
        tmpSbStr = tmpSbStr.replaceAll("\\`", "");
        tmpSbStr = tmpSbStr.replaceAll("\\<", "&lt;");
        tmpSbStr = tmpSbStr.replaceAll("\\>", "&gt;");
        return tmpSbStr;
    }

    public static void extracted(CodeAnalysisIssueDto issueDto, StringBuilder sb) {
        if (issueDto.getIssueCategory() != null)
            sb.append("[").append(issueDto.getIssueCategory()).append("] ");
        if (issueDto.getIssueCode() != null)
            sb.append("[").append(issueDto.getIssueCode()).append("] ");
        if (issueDto.getCweId() != null) {
            sb.append("[").append(issueDto.getCweId()).append("] ");
        }
        if (issueDto.getDescription() != null)
            sb.append(" ").append(issueDto.getDescription());
    }
}
