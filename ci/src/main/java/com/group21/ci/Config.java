package com.group21.ci;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * A class holding configuration constants for the server.
 */
public class Config {
    /**
     * Constructs a Config object.
     */
    public Config(){}

    /**
     * A token to verify setting of commit statuses.
     */
    public static final String GITHUB_TOKEN = Dotenv.configure()
        .directory("..")
        .filename("config.env")
        .ignoreIfMissing()
        .load()
        .get("GITHUB_TOKEN", "");
    /**
     * URL to page showing build history.
     */
    public static final String HISTORY_URL = Dotenv.configure()
        .directory("..")
        .filename("config.env")
        .ignoreIfMissing()
        .load()
        .get("HISTORY_URL", "http://localhost:8021/");
    /**
     * Custom description for success commit status.
     */
    public static String CUSTOM_SUCCESS_DESCRIPTION = Dotenv.configure()
        .directory("..")
        .filename("config.env")
        .ignoreIfMissing()
        .load()
        .get("SUCCESS_DESCRIPTION", null);
    /**
     * Custom description for pending commit status.
     */
    public static String CUSTOM_PENDING_DESCRIPTION = Dotenv.configure()
        .directory("..")
        .filename("config.env")
        .ignoreIfMissing()
        .load()
        .get("PENDING_DESCRIPTION", null);
    /**
     * Custom description for error commit status.
     */
    public static String CUSTOM_ERROR_DESCRIPTION = Dotenv.configure()
        .directory("..")
        .filename("config.env")
        .ignoreIfMissing()
        .load()
        .get("ERROR_DESCRIPTION", null);
    /**
     * Custom description for failure commit status.
     */
    public static String CUSTOM_FAILURE_DESCRIPTION = Dotenv.configure()
        .directory("..")
        .filename("config.env")
        .ignoreIfMissing()
        .load()
        .get("FAILURE_DESCRIPTION", null);
    /**
     * The network port used by the server.
     */
    public static final int PORT = 8021;
    /**
     * Path to directory for saving cloned repositories.
     */
    public static final String DIRECTORY_REPOSITORIES = "./repos/";
    /**
     * Path to directory for saving build history.
     */
    public static final String DIRECTORY_BUILD_HISTORY = "./build_history/";
    /**
     * File for saving output from compilation and testing.
     */
    public static final String BUILD_LOG_FILENAME = "build_log.txt";
    /**
     * File to save the commit id (SHA) identifying the commit the repository was built from.
     */
    public static final String BUILD_IDENTIFIER_FILENAME = "commit_identifier.txt";
    /**
     * File to save the name of the branch that was built and tested.
     */
    public static final String BUILD_BRANCH_FILENAME = "branch.txt";
}
