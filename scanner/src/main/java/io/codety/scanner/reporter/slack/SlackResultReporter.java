package io.codety.scanner.reporter.slack;

import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import io.codety.scanner.reporter.slack.dto.SlackMessageBlock;
import io.codety.scanner.reporter.slack.dto.SlackPostMessageRequestDto;
import io.codety.scanner.reporter.slack.dto.SlackPostMessageResponseDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.reporter.ResultReporter;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.CodetyConstant;
import io.codety.scanner.util.HttpRequestUtil;
import io.codety.scanner.util.JsonFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SlackResultReporter implements ResultReporter {

    @Autowired
    SlackResultGenerator slackResultGenerator;

    private static final String slackPostUrl = "https://slack.com/api/chat.postMessage";
    @Override
    public void deliverResult(AnalyzerRequest analyzerRequest, CodeAnalysisResultSetDto codeAnalysisResultSetDto) {

        if(!analyzerRequest.isEnableSlackNotification()){
            CodetyConsoleLogger.info("Skipping slack notification since the feature is disabled");
            return;
        }
        if(analyzerRequest.getExternalPullRequestId() == null){
            CodetyConsoleLogger.debug("Skip sending msg to Slack due to the pull request ID is missing");
            return;
        }

        String slackConversationId = analyzerRequest.getSlackConversationId();
        String slackOauthToken = analyzerRequest.getSlackOauthToken();
        if(slackOauthToken==null || slackOauthToken.isEmpty()){
            CodetyConsoleLogger.info("Skipping slack notification due to the " + CodetyConstant.ENV_SLACK_OAUTH_TOKEN + " is missing");
            return;
        }
        if(slackConversationId==null || slackConversationId.isEmpty()){
            CodetyConsoleLogger.info("Skipping slack notification due to the " + CodetyConstant.ENV_SLACK_CONVERSATION_ID + " is missing");
            return;
        }

        try {
            List<SlackMessageBlock> slackMessageBlocks = slackResultGenerator.generateRichTextPayloadResult(analyzerRequest, codeAnalysisResultSetDto);
            postSlackMessage(slackOauthToken, slackConversationId, slackMessageBlocks);
        }catch (Exception e){
            CodetyConsoleLogger.info("Failed drafting slack message block.");
        }

    }

    public static boolean postSlackMessage(String slackOauthToken, String slackConversationId, List<SlackMessageBlock> slackMessageBlocks) {
        if(slackMessageBlocks == null || slackMessageBlocks.isEmpty()){
            return false;
        }
        try {
            Map<String, String> header = new HashMap();
            header.put("Content-Type", "application/json; charset=utf-8");
            header.put("Authorization", "Bearer " + slackOauthToken);

            SlackPostMessageRequestDto slackPostMessageRequestDto = new SlackPostMessageRequestDto(slackConversationId);
            slackPostMessageRequestDto.setBlocks(slackMessageBlocks);

            String payload = JsonFactoryUtil.objectMapper.writeValueAsString(slackPostMessageRequestDto);
            CodetyConsoleLogger.debug("Slack notification post payload: "  + payload);
            String post = HttpRequestUtil.post(slackPostUrl, payload, header, 30);
            SlackPostMessageResponseDto slackPostMessageResponseDto = JsonFactoryUtil.objectMapper.readValue(post, SlackPostMessageResponseDto.class);
            if(!slackPostMessageResponseDto.isOk()){
                CodetyConsoleLogger.info("Failed sending slack notification due to error " + slackPostMessageResponseDto.getError());
            }else{
                CodetyConsoleLogger.info("Successfully delivered slack notification.");
                return true;
            }

        } catch (Exception e) {
            CodetyConsoleLogger.info("Failed sending slack notification due to error " + e.getMessage());
        }
        return false;
    }

}
