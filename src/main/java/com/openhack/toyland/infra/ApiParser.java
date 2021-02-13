package com.openhack.toyland.infra;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiParser {
    private final RestTemplate restTemplate;

    @Value("${app.api-token}")
    private String apiToken;

    public boolean checkHealth(String link) {
        log.info("[health-check]:" + link);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(link, String.class);
        } catch (Exception e) {
            return false;
        }
        return !response.getStatusCode().isError();
    }

    public LocalDateTime fetchActive(String githubLink, LocalDateTime active) {
        String repositoryEndpoint = githubLink.replace("https://github.com/", "");
        String actualUrl = "https://api.github.com/repos/" + repositoryEndpoint;

        HttpHeaders httpHeaders = new HttpHeaders();

        log.error(apiToken);
        httpHeaders.setBearerAuth(apiToken);
        HttpEntity entity = new HttpEntity(httpHeaders);

        ObjectNode jsonNodes = null;
        try {
            ResponseEntity<String> response = restTemplate.exchange(actualUrl, HttpMethod.GET, entity, String.class);
            jsonNodes = new ObjectMapper().readValue(response.getBody(), ObjectNode.class);
        } catch (JsonProcessingException | HttpClientErrorException e) {
            log.error("[Fetch not working] " + e.getMessage());
            return active;
        }

        log.info("[Fetch working]");
        if (jsonNodes.has("pushed_at")) {
            String pushedAt = jsonNodes.get("pushed_at").asText();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.KOREA);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000000", Locale.KOREA);
            LocalDateTime date = LocalDateTime.parse(pushedAt, inputFormatter);
            String formattedDate = outputFormatter.format(date);
            return LocalDateTime.parse(formattedDate, outputFormatter);
        }
        return active;
    }
}
