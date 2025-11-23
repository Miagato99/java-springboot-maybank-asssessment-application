package com.maybank.assessment.service;

import com.maybank.assessment.dto.ExternalApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestTemplate restTemplate;

    @Value("${external.api.url}")
    private String externalApiUrl;

    public List<ExternalApiResponse> fetchPosts() {
        log.info("Calling external API to fetch posts");
        
        try {
            String url = externalApiUrl + "/posts";
            ResponseEntity<List<ExternalApiResponse>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ExternalApiResponse>>() {}
            );
            
            log.info("Successfully fetched {} posts from external API", 
                    response.getBody() != null ? response.getBody().size() : 0);
            
            return response.getBody();
        } catch (Exception e) {
            log.error("Error calling external API: ", e);
            throw new RuntimeException("Failed to fetch data from external API: " + e.getMessage());
        }
    }

    public ExternalApiResponse fetchPostById(Long id) {
        log.info("Calling external API to fetch post with ID: {}", id);
        
        try {
            String url = externalApiUrl + "/posts/" + id;
            ResponseEntity<ExternalApiResponse> response = restTemplate.getForEntity(url, ExternalApiResponse.class);
            
            log.info("Successfully fetched post from external API");
            return response.getBody();
        } catch (Exception e) {
            log.error("Error calling external API: ", e);
            throw new RuntimeException("Failed to fetch post from external API: " + e.getMessage());
        }
    }
}
