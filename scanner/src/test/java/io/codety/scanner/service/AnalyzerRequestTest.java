package io.codety.scanner.service;

import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.service.dto.GitProviderType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class AnalyzerRequestTest {

    @Test
    public void testInit() throws IOException {
        String s = Files.readString(Path.of(this.getClass().getResource("/container-env/github_actions_predefined_env.txt").getFile()).toAbsolutePath());
        Map<String, String> env = new HashMap();
        for(String e : s.split("\n")){
            String[] split = e.split("=");
            if(split.length == 2)
                env.put(split[0], split[1]);
        };

        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{"./"});
        Assertions.assertTrue(analyzerRequest.getGitProviderType() == GitProviderType.GITHUB);
        Assertions.assertEquals("a2d8f6a49854bed81bb05bf58d3d4ac49f6e9c67", analyzerRequest.getGitCommitSha());
        Assertions.assertEquals("19", analyzerRequest.getExternalPullRequestId());
        Assertions.assertEquals("random1223/gs-spring-boot", analyzerRequest.getGitRepoFullName());
        Assertions.assertEquals("https://github.com", analyzerRequest.getGitBaseHttpsUrl());
    }

    @Test
    public void testHarness() throws IOException {
        String s = Files.readString(Path.of(this.getClass().getResource("/container-env/harness_container_predefined_env.txt").getFile()).toAbsolutePath());
        Map<String, String> env = new HashMap();
        for(String e : s.split("\n")){
            String[] split = e.split("=");
            if(split.length == 2)
                env.put(split[0], split[1]);
        };

        AnalyzerRequest analyzerRequest = AnalyzerRequest.processSystemVariablesToRequest(env, new String[]{"./"});
        Assertions.assertTrue(analyzerRequest.getGitProviderType() == GitProviderType.GITHUB);
        Assertions.assertEquals("4fb2b0d39f5ed9db4a5d1f8293198eb627fedc3d", analyzerRequest.getGitCommitSha());
        Assertions.assertEquals("26", analyzerRequest.getExternalPullRequestId());
        Assertions.assertEquals("random1223/gs-spring-boot", analyzerRequest.getGitRepoFullName());
        Assertions.assertEquals("https://github.com", analyzerRequest.getGitBaseHttpsUrl());
    }

}
