package org.example.aiintelligenceplatformjavaml.ml.fraud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.core.converters.CSVLoader;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

@Component
public class FraudDetector {

    private static final Logger log = LoggerFactory.getLogger(FraudDetector.class);
    private Classifier classifier;
    private Instances datasetStructure;

    @PostConstruct
    public void init() {
        try {
            trainModel();
            log.info("Fraud Detection model trained successfully.");
        } catch (Exception e) {
            log.warn("Could not train fraud model on startup: {}", e.getMessage());
            log.info("Fraud module will use rule-based fallback.");
        }
    }

    public void trainModel() throws Exception {
        File csvFile = new File("datasets/fraud_transactions.csv");
        if (!csvFile.exists()) {
            throw new RuntimeException("Dataset not found: " + csvFile.getAbsolutePath());
        }

        CSVLoader loader = new CSVLoader();
        loader.setSource(csvFile);
        Instances data = loader.getDataSet();

        // Set class attribute (is_fraud — last column)
        data.setClassIndex(data.numAttributes() - 1);

        // Train J48 Decision Tree
        classifier = new J48();
        classifier.buildClassifier(data);
        datasetStructure = new Instances(data, 0);

        log.info("Trained J48 on {} instances with {} attributes", data.numInstances(), data.numAttributes());
    }

    public Map<String, Object> predict(double amount, String transactionType) {
        try {
            if (classifier != null && datasetStructure != null) {
                return modelPredict(amount, transactionType);
            }
        } catch (Exception e) {
            log.error("Model prediction failed, falling back to rules: {}", e.getMessage());
        }

        // Rule-based fallback
        return ruleBasedPredict(amount, transactionType);
    }

    private Map<String, Object> modelPredict(double amount, String transactionType) throws Exception {
        Instance instance = new DenseInstance(datasetStructure.numAttributes());
        instance.setDataset(datasetStructure);
        instance.setValue(0, amount);

        double classification = classifier.classifyInstance(instance);
        double[] distribution = classifier.distributionForInstance(instance);

        boolean isFraud = classification == 1.0;
        double confidence = distribution[(int) classification];

        return Map.of(
                "prediction", isFraud ? "FRAUD" : "LEGITIMATE",
                "confidence", Math.round(confidence * 10000.0) / 100.0,
                "method", "J48 Decision Tree",
                "amount", amount,
                "transactionType", transactionType
        );
    }

    private Map<String, Object> ruleBasedPredict(double amount, String transactionType) {
        double riskScore = 0.0;

        // Amount-based risk
        if (amount > 10000) riskScore += 0.4;
        else if (amount > 5000) riskScore += 0.25;
        else if (amount > 2000) riskScore += 0.1;

        // Transaction type risk
        if ("online".equalsIgnoreCase(transactionType)) riskScore += 0.2;

        boolean isFraud = riskScore >= 0.5;

        return Map.of(
                "prediction", isFraud ? "FRAUD" : "LEGITIMATE",
                "confidence", Math.round(riskScore * 10000.0) / 100.0,
                "method", "Rule-Based Engine",
                "amount", amount,
                "transactionType", transactionType,
                "riskScore", riskScore
        );
    }
}
