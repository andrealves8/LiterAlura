package com.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultorApi {

    private final HttpClient client;

    public ConsultorApi() {
        this.client = HttpClient.newHttpClient();
    }

    public String obterDados(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode >= 200 && statusCode < 300) {
                return response.body();
            } else {
                throw new RuntimeException("Falha na requisição HTTP. Código: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();  // resetar o status de interrupção
            throw new RuntimeException("Erro ao obter dados da API: " + e.getMessage(), e);
        }
    }
}
