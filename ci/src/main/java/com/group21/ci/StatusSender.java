package com.group21.ci;

import java.net.URI;
import java.net.http.*;
import java.util.concurrent.CompletableFuture;

public class StatusSender {
    private String owner;
    private String repositoryName;
    private String SHA;
    private String statusUrl;
    private HttpClient statusHttpClient;
    CompletableFuture<HttpResponse<String>> response;

    private static final String STATE_ERROR = "error";
    private static final String STATE_FAILURE = "failure";
    private static final String STATE_PENDING = "pending";
    private static final String STATE_SUCCESS = "success";
    
    public StatusSender(String owner, String repositoryName, String SHA) {
        this.owner = owner;
        this.repositoryName = repositoryName;
        this.SHA = SHA;
        statusUrl = getStatusUrl();
        statusHttpClient = HttpClient.newHttpClient();
    }

    public void sendErrorStatus() {
        String sanitizedDescription = TextSanitizer.sanitize("An error occurred during the build");
        sendStatus(STATE_ERROR, sanitizedDescription);
    }

    public void sendFailureStatus() {
        String sanitizedDescription = TextSanitizer.sanitize("Build has failed");
        sendStatus(STATE_FAILURE, sanitizedDescription);
    }

    public void sendPendingStatus() {
        String sanitizedDescription = TextSanitizer.sanitize("Build has begun on the CI server");
        sendStatus(STATE_PENDING, sanitizedDescription);
    }

    public void sendSuccessStatus() {
        String sanitizedDescription = TextSanitizer.sanitize("Build success");
        sendStatus(STATE_SUCCESS, sanitizedDescription);
    }


    // Helper methods

    private void sendStatus(String state, String description) {
        try {
            response = statusHttpClient.sendAsync(requestBuilder(state, description),
                    HttpResponse.BodyHandlers.ofString());
            // Manejo de excepciones
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores al enviar el estado
        }
    }


    private String getStatusUrl() {
        return "https://api.github.com/repos/" + owner + "/" + repositoryName + "/statuses/" + SHA;
    }

    public HttpRequest requestBuilder(String status, String description) {

        // Add a target_url?
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(getStatusUrl()))
            .header("Authorization", "Bearer " + Config.GITHUB_TOKEN)
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .POST(HttpRequest.BodyPublishers.ofString("{\"state\":\"" + status + "\"" 
                + "," + "\"description\":" + "\"" + description + "\""
                + "," + "\"context\":\"project-continuous-integration-server\""
                + "}"))
            .build();
            return request;
    }
    
}
