package com.maybank.assessment.controller;

import com.maybank.assessment.dto.ExternalApiResponse;
import com.maybank.assessment.dto.ProductResponse;
import com.maybank.assessment.service.ExternalApiService;
import com.maybank.assessment.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
public class IntegrationController {

    private final ExternalApiService externalApiService;
    private final ProductService productService;

    /**
     * GET /api/integration/external-posts : Fetch posts from external API
     * This endpoint demonstrates nested API call to third-party service
     * 
     * @return the ResponseEntity with status 200 (OK) and list of posts from external API
     */
    @GetMapping("/external-posts")
    public ResponseEntity<Map<String, Object>> fetchExternalPosts() {
        log.info("REST request to fetch posts from external API");
        
        // Call external API (3rd party - JSONPlaceholder)
        List<ExternalApiResponse> externalPosts = externalApiService.fetchPosts();
        
        Map<String, Object> response = new HashMap<>();
        response.put("source", "JSONPlaceholder API");
        response.put("totalPosts", externalPosts != null ? externalPosts.size() : 0);
        response.put("posts", externalPosts);
        response.put("message", "Successfully fetched posts from external API");
        
        log.info("Successfully returned {} posts from external API", 
                externalPosts != null ? externalPosts.size() : 0);
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/integration/product-with-external/{id} : Get product and external post
     * This endpoint demonstrates nested API calls: 
     * 1. Fetch product from internal database
     * 2. Call external API to fetch additional data
     * 
     * @param id the product ID
     * @return the ResponseEntity with status 200 (OK) and combined data
     */
    @GetMapping("/product-with-external/{id}")
    public ResponseEntity<Map<String, Object>> getProductWithExternalData(@PathVariable Long id) {
        log.info("REST request to get product {} with external API data", id);
        
        // Step 1: Fetch product from our database
        ProductResponse product = productService.getProductById(id);
        log.info("Fetched product: {}", product.getName());
        
        // Step 2: Call external API (using product ID as post ID for demonstration)
        ExternalApiResponse externalPost = externalApiService.fetchPostById(id);
        log.info("Fetched external post with ID: {}", externalPost.getId());
        
        // Step 3: Combine the data
        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("externalData", externalPost);
        response.put("message", "Product data enriched with external API information");
        
        log.info("Successfully returned combined data for product ID: {}", id);
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/integration/external-post/{id} : Fetch single post from external API
     * 
     * @param id the post ID
     * @return the ResponseEntity with status 200 (OK) and the post data
     */
    @GetMapping("/external-post/{id}")
    public ResponseEntity<ExternalApiResponse> fetchExternalPostById(@PathVariable Long id) {
        log.info("REST request to fetch post {} from external API", id);
        
        ExternalApiResponse post = externalApiService.fetchPostById(id);
        
        return ResponseEntity.ok(post);
    }
}
