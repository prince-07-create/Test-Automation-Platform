package com.automation.ai;

import com.automation.config.ConfigReader;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class OpenRouterClient {
    private static final String API_KEY = ConfigReader.get("OPENROUTER_API_KEY");
    private static final String URL = "https://openrouter.ai/api/v1/chat/completions";

    public static String analyzeFailure(String testName, String errorMessage) {
        if (API_KEY == null || API_KEY.isEmpty()) {
            return "OpenRouter API key not configured. Skipping AI analysis.";
        }

        try {
            String prompt = "You are an expert QA Automation Engineer. " +
                    "The automated test '" + testName + "' failed with the following error/exception:\n" +
                    errorMessage + "\n\n" +
                    "Please provide a brief, professional, one-paragraph analysis of what likely went wrong and a suggested fix.";

            JsonObject message = new JsonObject();
            message.addProperty("role", "user");
            message.addProperty("content", prompt);

            JsonArray messagesArray = new JsonArray();
            messagesArray.add(message);

            JsonObject body = new JsonObject();
            // Using a high-quality free model from OpenRouter
            body.addProperty("model", "nvidia/nemotron-3.5-lightning:free");
            body.add("messages", messagesArray);

            Gson gson = new Gson();
            String requestBody = gson.toJson(body);

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(15))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                return jsonResponse.getAsJsonArray("choices")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("message")
                        .get("content").getAsString();
            } else {
                return "Failed to get AI analysis from OpenRouter. Status: " + response.statusCode() + "\nResponse: " + response.body();
            }

        } catch (Exception e) {
            return "Error while calling OpenRouter API: " + e.getMessage();
        }
    }
}
