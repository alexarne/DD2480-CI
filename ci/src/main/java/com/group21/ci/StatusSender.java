package com.group21.ci;

import java.net.URI;
import java.net.http.*;
import java.util.concurrent.CompletableFuture;

public class StatusSender {
    private String owner;
    private String repositoryName;
    private String SHA;
    private HttpClient statusHttpClient;
    CompletableFuture<HttpResponse<String>> response;
    
    /**
     * StatusSender constructor
     * @param owner             the owner of the repository
     * @param repositoryName    name of the repository 
     * @param SHA               SHA of the commit to change status of
     */
    public StatusSender(String owner, String repositoryName, String SHA) {
        this.owner = owner;
        this.repositoryName = repositoryName;
        this.SHA = SHA;
        statusHttpClient = HttpClient.newHttpClient();
    }

    /**
     *  sendErrorStatus
     *      - send a POST request to the repo to set commit status to error,
     *      with a short description.
     *      - the error status indicates the CI crashed and the job
     *      could not be finished.
     */
    public void sendErrorStatus() {
        response = statusHttpClient.sendAsync(requestBuilder("error", 
                                                "An error occured during the build"),
                                                HttpResponse.BodyHandlers.ofString());
    }

    /**
     *  sendFailureStatus
     *      - send a POST request to the repo to set commit status to failure,
     *      with a short description.
     *      - the failure status indicates the CI did not crash but a problem occured
     *      (such as a test failling).
     */
    public void sendFailureStatus() {
        response = statusHttpClient.sendAsync(requestBuilder("failure", 
                                                "Build has failed"),
                                                HttpResponse.BodyHandlers.ofString());
    }

    /**
     *  sendPendingStatus
     *      - send a POST request to the repo to set commit status to pending,
     *      with a short description.
     *      - the pending status indicates the CI is currently running and will update the
     *      status when it finishes.
     */
    public void sendPendingStatus() {
        response = statusHttpClient.sendAsync(requestBuilder("pending", 
                                                "Build has begun on the CI server"),
                                                HttpResponse.BodyHandlers.ofString());
    }

    /**
     *  sendSuccessStatus
     *      - send a POST request to the repo to set commit status to success,
     *      with a short description.
     *      - the success status indicates the CI finished its work and returned normally
     */
    public void sendSuccessStatus() {
        response = statusHttpClient.sendAsync(requestBuilder("success", "Build success"), 
                                                HttpResponse.BodyHandlers.ofString());
    }


    // Helper methods
    /**
     * getStatusUrl
     *      - a helper method that creates the URL of the API to update the commit status 
     * @return the URL of the API
     */
    private String getStatusUrl() {
        return "https://api.github.com/repos/" + owner + "/" + repositoryName + "/statuses/" + SHA;
    }

    /**
     * requestBuilder
     *      - creates, builds and returns a POST request to set the status 
     * @param status    the status to set for the commit, must be "success",
     *                  "pending", "error" or "failure"
     * @param description   a short description associated with the commit status
     * @return the built request
     */
    public HttpRequest requestBuilder(String status, String description) {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(getStatusUrl()))
            // Headers
            .header("Authorization", "Bearer " + Config.GITHUB_TOKEN)
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            // Body (inside a function declaring the HTTP method, here POST)
            .POST(HttpRequest.BodyPublishers.ofString("{\"state\":\"" + status + "\"" 
                + "," + "\"description\":" + "\"" + description + "\""
                + "," + "\"context\":\"project-continuous-integration-server\""
                + "}"))
            .build();
            return request;
    }
    
}
