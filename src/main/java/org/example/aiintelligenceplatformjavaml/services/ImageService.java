package org.example.aiintelligenceplatformjavaml.services;

import org.example.aiintelligenceplatformjavaml.ml.deep_learning.ImageClassifier;
import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageService {

    private final ImageClassifier imageClassifier;

    public ImageService(ImageClassifier imageClassifier) {
        this.imageClassifier = imageClassifier;
    }

    public PredictionResponse predict(double[] pixelData) {
        Map<String, Object> result = imageClassifier.predict(pixelData);

        if (result.containsKey("error")) {
            return new PredictionResponse("Image Classification", "Error: " + result.get("error"), 0.0, result);
        }

        int digit = ((Number) result.get("predictedDigit")).intValue();
        double confidence = ((Number) result.get("confidence")).doubleValue();

        return new PredictionResponse("Image Classification", "Digit: " + digit, confidence, result);
    }

    public Map<String, Object> getModelInfo() {
        return imageClassifier.getModelInfo();
    }
}