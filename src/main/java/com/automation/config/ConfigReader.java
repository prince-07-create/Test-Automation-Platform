package com.automation.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigReader {
    private static Dotenv dotenv;

    static {
        try {
            dotenv = Dotenv.configure().ignoreIfMissing().load();
        } catch (Exception e) {
            System.err.println("Could not load .env file. Proceeding with system environment variables.");
        }
    }

    public static String get(String key) {
        if (dotenv != null && dotenv.get(key) != null) {
            return dotenv.get(key);
        }
        return System.getenv(key);
    }
}
