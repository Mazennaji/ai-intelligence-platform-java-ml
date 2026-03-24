package org.example.aiintelligenceplatformjavaml.services;

import org.example.aiintelligenceplatformjavaml.ml.regression.PricePredictor;
import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.example.aiintelligenceplatformjavaml.models.PriceRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PriceService {

    private final PricePredictor pricePredictor;

    public PriceService(PricePredictor pricePredictor) {
        this.pricePredictor = pricePredictor;
    }

    public PredictionResponse predict(PriceRequest request) {
        Map<String, Object> result = pricePredictor.predictPrice(
                request.getSize(),
                request.getRooms(),
                request.getLocation(),
                request.getAge()
        );

        double predictedPrice = ((Number) result.get("predictedPrice")).doubleValue();

        return new PredictionResponse(
                "Price Prediction",
                String.format("$%.2f", predictedPrice),
                null,
                result
        );
    }
}