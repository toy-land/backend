package com.openhack.toyland.infra;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openhack.toyland.exception.JsonParsingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiParser {
    private final RestTemplate restTemplate;

    public boolean checkHealth(String link) {
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

        ObjectNode jsonNodes = null;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(actualUrl, String.class);
            jsonNodes = new ObjectMapper().readValue(response.getBody(), ObjectNode.class);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("해당 링크의 Json을 파싱할 수 없습니다.");
        }

        if (jsonNodes.has("pushed_at")) {
            log.warn("[fetch link] " + jsonNodes.get("pushed_at").asText());
            return LocalDateTime.parse(jsonNodes.get("pushed_at").asText());
        }
        return active;
    }
}
