package io.codety.scanner.reporter.slack;

import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.reporter.slack.dto.SlackMessageBlock;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.HttpRequestUtil;
import io.codety.test.util.TestCaseUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SlackResultReporterTest {

    @Autowired
    SlackResultGenerator slackResultGenerator;

    @Autowired
    SlackResultReporter slackResultReporter;

    @Test
    void generateSlackResultReport() throws IOException {


        Map<String, String> environmentVariableMapsForTestCase = TestCaseUtil.createEnvironmentVariableMapsForTestCase();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(environmentVariableMapsForTestCase, new String[]{"./"});

        CodeAnalysisResultSetDto codeAnalysisResultSetDto = new CodeAnalysisResultSetDto();
        CodeAnalysisResultDto test = new CodeAnalysisResultDto("test", CodeAnalyzerType.codety);
        test.addIssue(new CodeAnalysisIssueDto("a/b/test.txt", 10, "security", "found-github-secrets", 4, "shouldn't expose credential in the code"));
        test.addIssue(new CodeAnalysisIssueDto("a/b/wfjeoijfiewjofijwoifjwjeof.txt", 10, "error-prone", "check-undef", 4, "check whether the variable is null/undef before reading"));
        test.addIssue(new CodeAnalysisIssueDto("a/b/weojfiwejfijwoe.txt", 10, "styling", "no-indent", 1, "Indent is recommended"));
        codeAnalysisResultSetDto.getCodeAnalysisResultDtoList().add(test);

        List<SlackMessageBlock> slackMessageBlocks = slackResultGenerator.generateRichTextPayloadResult(analyzerRequest, codeAnalysisResultSetDto);
        String slackToken = System.getenv("SLACK_TOKEN");

        try (MockedStatic<HttpRequestUtil> utilities = Mockito.mockStatic(HttpRequestUtil.class)) {
            utilities.when(() -> HttpRequestUtil.post(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
                    .thenReturn("{\"ok\":true}");
            boolean success = slackResultReporter.postSlackMessage(slackToken, "C07J17C50DN", slackMessageBlocks);
//            Assertions.assertTrue(success);
        }


    }
}
