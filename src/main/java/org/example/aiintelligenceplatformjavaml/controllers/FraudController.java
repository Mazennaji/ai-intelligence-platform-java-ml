package org.example.aiintelligenceplatformjavaml.controllers;

import org.example.aiintelligenceplatformjavaml.models.FraudRequest;
import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.example.aiintelligenceplatformjavaml.services.FraudService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud")
@CrossOrigin(origins = "*")
public class FraudController {

    private final FraudService fraudService;

    public FraudController(FraudService fraudService) {
        this.fraudService = fraudService;
    }

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponse> predict(@Valid @RequestBody FraudRequest request) {
        PredictionResponse response = fraudService.predict(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Fraud Detection module is running");
    }
}