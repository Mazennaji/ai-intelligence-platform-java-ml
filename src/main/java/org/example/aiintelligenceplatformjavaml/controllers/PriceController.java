package org.example.aiintelligenceplatformjavaml.controllers;

import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.example.aiintelligenceplatformjavaml.models.PriceRequest;
import org.example.aiintelligenceplatformjavaml.services.PriceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/price")
@CrossOrigin(origins = "*")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponse> predict(@Valid @RequestBody PriceRequest request) {
        PredictionResponse response = priceService.predict(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Price Prediction module is running");
    }
}
