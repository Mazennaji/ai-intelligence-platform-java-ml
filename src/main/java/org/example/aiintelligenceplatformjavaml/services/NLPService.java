package org.example.aiintelligenceplatformjavaml.services;


import org.example.aiintelligenceplatformjavaml.ml.nlp.SentimentAnalyzer;
import org.example.aiintelligenceplatformjavaml.ml.nlp.SpamDetector;
import org.example.aiintelligenceplatformjavaml.models.NLPRequest;
import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NLPService {

    private final SentimentAnalyzer sentimentAnalyzer;
    private final SpamDetector spamDetector;

    public NLPService(SentimentAnalyzer sentimentAnalyzer, SpamDetector spamDetector) {
        this.sentimentAnalyzer = sentimentAnalyzer;
        this.spamDetector = spamDetector;
    }

    public PredictionResponse analyzeSentiment(NLPRequest request) {
        Map<String, Object> result = sentimentAnalyzer.analyze(request.getText());

        String sentiment = (String) result.get("sentiment");
        double confidence = ((Number) result.get("confidence")).doubleValue();

        return new PredictionResponse("Sentiment Analysis", sentiment, confidence, result);
    }

    public PredictionResponse detectSpam(NLPRequest request) {
        Map<String, Object> result = spamDetector.detect(request.getText());

        String classification = (String) result.get("classification");
        double score = ((Number) result.get("spamScore")).doubleValue();

        return new PredictionResponse("Spam Detection", classification, score, result);
    }
}
