package io.codety.scanner.analyzer.dto.materialized;

public class CodetyConfigPullApiRequestDto {
    String token;
    String repo;
    String url;

    public CodetyConfigPullApiRequestDto(String token, String repo, String url) {
        this.token = token;
        this.repo = repo;
        this.url = url;
    }

    public CodetyConfigPullApiRequestDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
