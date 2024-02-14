package com.group21.ci;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * A class holding configurations for the server.
 */
public class Config {
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

    public static final int PORT = 8021;
    public static final String DIRECTORY_REPOSITORIES = "./repos/";
    public static final String DIRECTORY_BUILD_HISTORY = "./build_history/";
    public static final String BUILD_LOG_FILENAME = "build_log.txt";
    public static final String BUILD_IDENTIFIER_FILENAME = "commit_identifier.txt";
    public static final String BUILD_BRANCH_FILENAME = "branch.txt";
}
