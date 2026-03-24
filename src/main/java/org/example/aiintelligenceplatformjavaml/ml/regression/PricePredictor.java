package org.example.aiintelligenceplatformjavaml.ml.regression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Component
public class PricePredictor {

    private static final Logger log = LoggerFactory.getLogger(PricePredictor.class);

    private double[] weights;
    private double bias;
    private boolean modelTrained = false;

    @PostConstruct
    public void init() {
        try {
            trainModel();
            log.info("Price Prediction model trained successfully.");
        } catch (Exception e) {
            log.warn("Could not train price model on startup: {}", e.getMessage());
            log.info("Price module will use linear estimation fallback.");
        }
    }

    public void trainModel() throws Exception {
        File csvFile = new File("datasets/house_prices.csv");
        if (!csvFile.exists()) {
            throw new RuntimeException("Dataset not found: " + csvFile.getAbsolutePath());
        }

        List<double[]> features = new ArrayList<>();
        List<Double> targets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String header = br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    double size = Double.parseDouble(parts[0].trim());
                    double rooms = Double.parseDouble(parts[1].trim());
                    double age = Double.parseDouble(parts[3].trim());
                    double price = Double.parseDouble(parts[4].trim());

                    features.add(new double[]{size, rooms, age});
                    targets.add(price);
                }
            }
        }

        int n = features.size();
        int m = 3;
        weights = new double[m];
        bias = 0;

        double learningRate = 0.0000001;
        int epochs = 1000;

        for (int epoch = 0; epoch < epochs; epoch++) {
            double[] gradients = new double[m];
            double biasGradient = 0;

            for (int i = 0; i < n; i++) {
                double predicted = predict(features.get(i));
                double error = predicted - targets.get(i);

                for (int j = 0; j < m; j++) {
                    gradients[j] += error * features.get(i)[j];
                }
                biasGradient += error;
            }

            for (int j = 0; j < m; j++) {
                weights[j] -= learningRate * gradients[j] / n;
            }
            bias -= learningRate * biasGradient / n;
        }

        modelTrained = true;
        log.info("Trained linear regression on {} samples", n);
    }

    private double predict(double[] features) {
        double result = bias;
        for (int i = 0; i < features.length && i < weights.length; i++) {
            result += weights[i] * features[i];
        }
        return result;
    }

    public Map<String, Object> predictPrice(double size, int rooms, String location, int age) {
        double predictedPrice;
        String method;

        if (modelTrained) {
            predictedPrice = predict(new double[]{size, rooms, age});
            method = "Linear Regression Model";
        } else {
            predictedPrice = (size * 1200) + (rooms * 15000) - (age * 2000);
            method = "Estimation Fallback";
        }

        predictedPrice = Math.max(0, Math.round(predictedPrice * 100.0) / 100.0);

        return Map.of(
                "predictedPrice", predictedPrice,
                "currency", "USD",
                "method", method,
                "input", Map.of(
                        "size", size,
                        "rooms", rooms,
                        "location", location,
                        "age", age
                )
        );
    }
}
