package com.rraiz.movie_critic.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApiUtil {

    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private final String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(TMDBApiService.class);

    public ApiUtil(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, @Value("${TMDB_API_KEY}") String apiKey) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
    }

    public <T> T fetchFromApi(String endpoint, Function<JsonNode, T> mappingFunction) {
        String url = TMDB_API_BASE_URL + endpoint + "?api_key=" + apiKey;
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            return mappingFunction.apply(root);
        } catch (Exception e) {
            logger.error("Error fetching data from URL {}: {}", url, e.getMessage());
            return null;
        }
    }

    // Helper methods to extract values from JSON nodes
    public String getValueAsTitleOrName(JsonNode node) {
        if (node.hasNonNull("title")) {
            return getValueAsText(node.get("title"));
        }
        return getValueAsText(node.get("name"));
    }

    public String getValueAsText(JsonNode node) {
        return node != null && !node.isNull() ? node.asText() : null;
    }

    public Integer getValueAsInt(JsonNode node) {
        return node != null && !node.isNull() ? node.asInt() : null;
    }

    public Long getValueAsLong(JsonNode node) {
        return node != null && !node.isNull() ? node.asLong() : null;
    }

    public Double getValueAsDouble(JsonNode node) {
        return node != null && !node.isNull() ? node.asDouble() : null;
    }

    public Boolean getValueAsBoolean(JsonNode node) {
        return node != null && !node.isNull() ? node.asBoolean() : null;
    }

    public LocalDate getValueAsLocalDate(JsonNode node) {

        if (node != null && !node.isNull()) {
            String date = node.asText();
            if (date.equals(""))
                return null;
            return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        }
        return null;

    }
}
