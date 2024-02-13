package com.group21.ci;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class StatusSender {
    private String owner;
    private String repositoryName;
    private String SHA;
    private String statusUrl;
    private HttpClient statusHttpClient;
    private String buildIdentifier;

    private static final String STATE_ERROR = "error";
    private static final String STATE_FAILURE = "failure";
    private static final String STATE_PENDING = "pending";
    private static final String STATE_SUCCESS = "success";
    
    public StatusSender(RepositoryInfo repo, String id) {
        this.owner = repo.owner;
        this.repositoryName = repo.name;
        this.SHA = repo.commitId;
        statusUrl = getStatusUrl();
        statusHttpClient = HttpClient.newHttpClient();
        this.buildIdentifier = id;
    }

    public void sendErrorStatus() {
        System.out.println("Sent ERROR status");
        String sanitizedDescription = TextSanitizer.sanitize("An error occurred during the build");
        sendStatus(STATE_ERROR, sanitizedDescription);
    }

    public void sendFailureStatus() {
        System.out.println("Sent FAILURE status");
        String sanitizedDescription = TextSanitizer.sanitize("Build has failed");
        sendStatus(STATE_FAILURE, sanitizedDescription);
    }

    public void sendPendingStatus() {
        System.out.println("Sent PENDING status");
        String sanitizedDescription = TextSanitizer.sanitize("Build has begun on the CI server");
        sendStatus(STATE_PENDING, sanitizedDescription);
    }

    public void sendSuccessStatus() {
        System.out.println("Sent SUCCESS status");
        String sanitizedDescription = TextSanitizer.sanitize("Build success");
        sendStatus(STATE_SUCCESS, sanitizedDescription);
    }


    // Helper methods
    private void sendStatus(String state, String description) {
        try {
            HttpResponse<String> response = statusHttpClient.send(
                    requestBuilder(state, description),
                    HttpResponse.BodyHandlers.ofString()
            );
            System.out.println("Received code: " + response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    
    }


    private String getStatusUrl() {
        return "https://api.github.com/repos/" + owner + "/" + repositoryName + "/statuses/" + SHA;
    }

    public HttpRequest requestBuilder(String status, String description) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(statusUrl))
            .header("Authorization", "Bearer " + Config.GITHUB_TOKEN)
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .POST(HttpRequest.BodyPublishers.ofString("{\"state\":\"" + status + "\"" 
                + "," + "\"description\":" + "\"" + description + "\""
                + "," + "\"context\":\"project-continuous-integration-server\""
                + "," + "\"target_url\":\"" + Config.HISTORY_URL + "/" + buildIdentifier + "\""
                + "}"))
            .build();
            return request;
    }
    
}
