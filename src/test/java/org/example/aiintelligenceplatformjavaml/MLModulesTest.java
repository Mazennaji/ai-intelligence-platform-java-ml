package org.example.aiintelligenceplatformjavaml;

import org.example.aiintelligenceplatformjavaml.ml.fraud.FraudDetector;
import org.example.aiintelligenceplatformjavaml.ml.nlp.SentimentAnalyzer;
import org.example.aiintelligenceplatformjavaml.ml.nlp.SpamDetector;
import org.example.aiintelligenceplatformjavaml.ml.regression.PricePredictor;
import org.example.aiintelligenceplatformjavaml.ml.recommender.RecommenderEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MLModulesTest {

    // ========== FRAUD DETECTION ==========

    @Test
    @DisplayName("Fraud: High amount online transaction should be flagged")
    void testFraudHighAmount() {
        FraudDetector detector = new FraudDetector();
        Map<String, Object> result = detector.predict(15000, "online");

        assertEquals("FRAUD", result.get("prediction"));
        assertNotNull(result.get("confidence"));
    }

    @Test
    @DisplayName("Fraud: Low amount offline transaction should be legitimate")
    void testFraudLowAmount() {
        FraudDetector detector = new FraudDetector();
        Map<String, Object> result = detector.predict(50, "offline");

        assertEquals("LEGITIMATE", result.get("prediction"));
    }

    // ========== SENTIMENT ANALYSIS ==========

    @Test
    @DisplayName("NLP: Positive text should return positive sentiment")
    void testPositiveSentiment() {
        SentimentAnalyzer analyzer = new SentimentAnalyzer();
        analyzer.init();
        Map<String, Object> result = analyzer.analyze("I love this amazing product");

        assertEquals("positive", result.get("sentiment"));
    }

    @Test
    @DisplayName("NLP: Negative text should return negative sentiment")
    void testNegativeSentiment() {
        SentimentAnalyzer analyzer = new SentimentAnalyzer();
        analyzer.init();
        Map<String, Object> result = analyzer.analyze("This is terrible and horrible");

        assertEquals("negative", result.get("sentiment"));
    }

    @Test
    @DisplayName("NLP: Neutral text should return neutral sentiment")
    void testNeutralSentiment() {
        SentimentAnalyzer analyzer = new SentimentAnalyzer();
        analyzer.init();
        Map<String, Object> result = analyzer.analyze("The meeting is at 3pm");

        assertEquals("neutral", result.get("sentiment"));
    }

    // ========== SPAM DETECTION ==========

    @Test
    @DisplayName("Spam: Promotional text should be detected as spam")
    void testSpamDetection() {
        SpamDetector detector = new SpamDetector();
        detector.init();
        Map<String, Object> result = detector.detect("WIN FREE MONEY NOW!!! Click here for PRIZE");

        assertEquals("SPAM", result.get("classification"));
    }

    @Test
    @DisplayName("Spam: Normal text should be classified as ham")
    void testHamDetection() {
        SpamDetector detector = new SpamDetector();
        detector.init();
        Map<String, Object> result = detector.detect("Meeting at 5pm in the conference room");

        assertEquals("HAM", result.get("classification"));
    }

    // ========== PRICE PREDICTION ==========

    @Test
    @DisplayName("Price: Should return positive predicted price")
    void testPricePrediction() {
        PricePredictor predictor = new PricePredictor();
        Map<String, Object> result = predictor.predictPrice(120, 3, "Beirut", 10);

        double price = ((Number) result.get("predictedPrice")).doubleValue();
        assertTrue(price > 0, "Predicted price should be positive");
        assertNotNull(result.get("method"));
    }

    @Test
    @DisplayName("Price: Larger house should cost more")
    void testPriceScaling() {
        PricePredictor predictor = new PricePredictor();
        Map<String, Object> small = predictor.predictPrice(60, 1, "Beirut", 10);
        Map<String, Object> large = predictor.predictPrice(200, 5, "Beirut", 2);

        double smallPrice = ((Number) small.get("predictedPrice")).doubleValue();
        double largePrice = ((Number) large.get("predictedPrice")).doubleValue();
        assertTrue(largePrice > smallPrice, "Larger house should be more expensive");
    }

    // ========== RECOMMENDATION ==========

    @Test
    @DisplayName("Recommend: Unknown user should return empty recommendations")
    void testRecommendUnknownUser() {
        RecommenderEngine engine = new RecommenderEngine();
        Map<String, Object> result = engine.recommend(9999, 5);

        assertNotNull(result.get("message"));
    }
}
