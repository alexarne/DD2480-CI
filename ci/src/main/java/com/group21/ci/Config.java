package com.group21.ci;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    public static final String GITHUB_TOKEN = Dotenv.configure()
        .directory("..")
        .filename("config.env")
        .load()
        .get("GITHUB_TOKEN", "");
    public static final int PORT = 8021;
    public static final String DIRECTORY_REPOSITORIES = "./repos/";
    public static final String DIRECTORY_HISTORY = "./history/";
}
