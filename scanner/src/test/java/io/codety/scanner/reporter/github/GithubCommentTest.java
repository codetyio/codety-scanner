package io.codety.scanner.reporter.github;

import io.codety.scanner.reporter.github.GithubCommentService;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.test.util.TestCaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@SpringBootTest
@ActiveProfiles(value = "dev")
public class GithubCommentTest {

    @Autowired
    GithubCommentService githubCommentService;

//    @Test
    void postPullRequestReviews() throws Exception {
        String githubToken = "";
        Long pullRequestId = 1L;
        Map<String, String> env = TestCaseUtil.createEnvironmentVariableMapsForTestCase();
        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{"./"});

        analyzerRequest.setGithubAccessToken("x");
        analyzerRequest.setExternalGitRepoId("x");
        analyzerRequest.setExternalPullRequestId("5");

        githubCommentService.postPullRequestCommentForTestCase(analyzerRequest);

    }

}
