package com.kintai.kintai.domain.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ApiUtils {

    public HttpResponse<String> get(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return send(request);
    }

    private HttpResponse<String> send(final HttpRequest request) {
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            validateResponse(response);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private  void validateResponse(final HttpResponse<String > response) {
        HttpStatus status = HttpStatus.resolve(response.statusCode());
        if (status != HttpStatus.OK) throw new IllegalStateException("API 応答失敗。。");
    }
}
