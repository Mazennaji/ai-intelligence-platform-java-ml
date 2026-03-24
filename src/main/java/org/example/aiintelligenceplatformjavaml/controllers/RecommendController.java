package org.example.aiintelligenceplatformjavaml.controllers;

import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.example.aiintelligenceplatformjavaml.models.RecommendRequest;
import org.example.aiintelligenceplatformjavaml.services.RecommendService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommend")
@CrossOrigin(origins = "*")
public class RecommendController {

    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @PostMapping
    public ResponseEntity<PredictionResponse> recommend(@Valid @RequestBody RecommendRequest request) {
        PredictionResponse response = recommendService.recommend(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Recommendation Engine is running");
    }
}
