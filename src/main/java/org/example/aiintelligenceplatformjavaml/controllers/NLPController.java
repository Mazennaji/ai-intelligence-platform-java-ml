package org.example.aiintelligenceplatformjavaml.controllers;

import org.example.aiintelligenceplatformjavaml.models.NLPRequest;
import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.example.aiintelligenceplatformjavaml.services.NLPService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nlp")
@CrossOrigin(origins = "*")
public class NLPController {

    private final NLPService nlpService;

    public NLPController(NLPService nlpService) {
        this.nlpService = nlpService;
    }

    @PostMapping("/sentiment")
    public ResponseEntity<PredictionResponse> sentiment(@Valid @RequestBody NLPRequest request) {
        PredictionResponse response = nlpService.analyzeSentiment(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/spam")
    public ResponseEntity<PredictionResponse> spam(@Valid @RequestBody NLPRequest request) {
        PredictionResponse response = nlpService.detectSpam(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("NLP Engine is running");
    }
}
