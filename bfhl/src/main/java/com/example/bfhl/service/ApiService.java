package com.example.bfhl.service;

import com.example.bfhl.model.FinalQueryRequest;
import com.example.bfhl.model.GenerateWebhookRequest;
import com.example.bfhl.model.GenerateWebhookResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    public void generateWebhook() {

        RestTemplate restTemplate = new RestTemplate();

        String generateWebhookUrl =
                "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        GenerateWebhookRequest request =
                new GenerateWebhookRequest(
                        "shikhi",
                        "0827",
                        "shikhisingh12@gmail.com"
                );

        ResponseEntity<GenerateWebhookResponse> response =
                restTemplate.postForEntity(
                        generateWebhookUrl,
                        request,
                        GenerateWebhookResponse.class
                );

        GenerateWebhookResponse body = response.getBody();
        if (body == null) {
            System.out.println("Response body is null");
            return;
        }

        String webhookUrl = body.getWebhook();
        String accessToken = body.getAccessToken();

        System.out.println("WEBHOOK URL:");
        System.out.println(webhookUrl);

        System.out.println("\nACCESS TOKEN:");
        System.out.println(accessToken);

        // SAMPLE SQL QUERY
        String finalSqlQuery =
                "SELECT * FROM EMPLOYEE WHERE SALARY > 50000;";

        // REQUEST BODY
        FinalQueryRequest finalQueryRequest =
                new FinalQueryRequest(finalSqlQuery);

        // HEADERS
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(accessToken);

        // COMBINE BODY + HEADERS
        HttpEntity<FinalQueryRequest> entity =
                new HttpEntity<>(finalQueryRequest, headers);

        // FINAL SUBMISSION API CALL
        ResponseEntity<String> finalResponse =
                restTemplate.exchange(
                        "https://postman-echo.com/post",
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        System.out.println("\nFINAL API RESPONSE:");
        System.out.println(finalResponse.getBody());
    }
}