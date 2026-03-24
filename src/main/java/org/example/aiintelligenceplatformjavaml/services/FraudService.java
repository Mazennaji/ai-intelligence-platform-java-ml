package org.example.aiintelligenceplatformjavaml.services;


import org.example.aiintelligenceplatformjavaml.ml.fraud.FraudDetector;
import org.example.aiintelligenceplatformjavaml.models.FraudRequest;
import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FraudService {

    private final FraudDetector fraudDetector;

    public FraudService(FraudDetector fraudDetector) {
        this.fraudDetector = fraudDetector;
    }

    public PredictionResponse predict(FraudRequest request) {
        Map<String, Object> result = fraudDetector.predict(
                request.getAmount(),
                request.getTransactionType()
        );

        String prediction = (String) result.get("prediction");
        double confidence = ((Number) result.get("confidence")).doubleValue();

        return new PredictionResponse("Fraud Detection", prediction, confidence, result);
    }
}