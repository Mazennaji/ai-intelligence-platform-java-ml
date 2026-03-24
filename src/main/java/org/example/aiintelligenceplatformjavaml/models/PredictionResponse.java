package org.example.aiintelligenceplatformjavaml.models;

import java.time.LocalDateTime;
import java.util.Map;

public class PredictionResponse {

    private String module;
    private String result;
    private Double confidence;
    private Map<String, Object> details;
    private LocalDateTime timestamp;

    public PredictionResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public PredictionResponse(String module, String result, Double confidence) {
        this.module = module;
        this.result = result;
        this.confidence = confidence;
        this.timestamp = LocalDateTime.now();
    }

    public PredictionResponse(String module, String result, Double confidence, Map<String, Object> details) {
        this(module, result, confidence);
        this.details = details;
    }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }

    public Map<String, Object> getDetails() { return details; }
    public void setDetails(Map<String, Object> details) { this.details = details; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}