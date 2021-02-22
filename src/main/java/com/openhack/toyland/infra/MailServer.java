package com.openhack.toyland.infra;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openhack.toyland.dto.EmailParticipant;
import com.openhack.toyland.dto.EmailRequestBody;
import com.openhack.toyland.util.JsonTransformer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j(topic = "[mail server]")
@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MailServer {

    @Value("${app.access-key-id}")
    private String accessKey;
    @Value("${app.secret-key}")
    private String secretKey;

    private final RestTemplate restTemplate;
    private final static String mailUrl = "https://mail.apigw.ntruss.com/api/v1/mails";
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public void sendMail(List<EmailParticipant> emailParticipantList)
        throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        if (emailParticipantList.isEmpty()) {
            return;
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        log.info(accessKey + " " + secretKey);
        String timestamp = String.valueOf(System.currentTimeMillis());

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("x-ncp-apigw-timestamp", timestamp);
        httpHeaders.set("x-ncp-iam-access-key", accessKey);
        httpHeaders.set("x-ncp-apigw-signature-v2", makeSignature(timestamp, accessKey, secretKey));

        EmailRequestBody emailRequestBody = new EmailRequestBody(emailParticipantList);
        String requestBody = JsonTransformer.toJson(emailRequestBody);
        HttpEntity entity = new HttpEntity(requestBody, httpHeaders);

        log.info(requestBody);
        log.info(entity.toString());

        ResponseEntity<String> response = restTemplate.exchange(mailUrl, HttpMethod.POST, entity, String.class);
    }

    private String makeSignature(String timestamp, String accessKey, String secretKey)
        throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String space = " ";  // 공백
        String newLine = "\n";  // 줄바꿈
        String method = "POST";  // HTTP 메소드
        String url = "/api/v1/mails";  // 도메인을 제외한 "/" 아래 전체 url (쿼리스트링 포함)

        String message = new StringBuilder()
            .append(method)
            .append(space)
            .append(url)
            .append(newLine)
            .append(timestamp)
            .append(newLine)
            .append(accessKey)
            .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeBase64String(rawHmac);
    }
}
