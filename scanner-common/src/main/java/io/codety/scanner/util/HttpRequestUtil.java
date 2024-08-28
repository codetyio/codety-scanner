package io.codety.scanner.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

import static java.time.temporal.ChronoUnit.SECONDS;

public class HttpRequestUtil {
    private static final String INFO_gotResponse = "The length of settings response: ";
    public static String post(String url, String payload, int timeout) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.of(timeout, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        HttpResponse<String> send = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String body = send.body();

        CodetyConsoleLogger.debug(INFO_gotResponse + body.length());
        return body;
    }

    public static String post(String url, String payload, Map<String, String> header, int timeout) throws Exception {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.of(timeout, SECONDS));

        if (header != null) {
            for (String key : header.keySet()) {
                httpRequestBuilder.header(key, header.get(key));
            }
        }

        HttpRequest.BodyPublisher postPayload = null;
        if (payload == null) {
            postPayload = HttpRequest.BodyPublishers.noBody();
        } else {
            postPayload = HttpRequest.BodyPublishers.ofString(payload);
        }

        HttpRequest request = httpRequestBuilder
                .POST(postPayload)
                .build();
        HttpResponse<String> send = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String body = send.body();

        return body;
    }

    public static String get(String url, Map<String, String> header, int timeout) throws Exception {

        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.of(timeout, SECONDS));

        if (header != null) {
            for (String key : header.keySet()) {
                httpRequestBuilder.header(key, header.get(key));
            }
        }

        HttpRequest request = httpRequestBuilder
                .GET()
                .build();
        HttpResponse<String> send = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String body = send.body();

        return body;

    }
}
