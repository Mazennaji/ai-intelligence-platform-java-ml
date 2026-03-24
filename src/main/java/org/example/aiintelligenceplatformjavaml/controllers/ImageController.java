package org.example.aiintelligenceplatformjavaml.controllers;

import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.example.aiintelligenceplatformjavaml.services.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponse> predict(@RequestBody Map<String, double[]> request) {
        double[] pixels = request.get("pixels");
        if (pixels == null || pixels.length != 784) {
            return ResponseEntity.badRequest().body(
                    new PredictionResponse("Image Classification", "Invalid input: expected 784 pixel values", 0.0)
            );
        }

        PredictionResponse response = imageService.predict(pixels);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> modelInfo() {
        return ResponseEntity.ok(imageService.getModelInfo());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Image Classification module is running");
    }
}
