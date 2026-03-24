package org.example.aiintelligenceplatformjavaml.ml.nlp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Component
public class SentimentAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(SentimentAnalyzer.class);

    private final Set<String> positiveWords = new HashSet<>();
    private final Set<String> negativeWords = new HashSet<>();
    private final Map<String, String> trainingData = new HashMap<>();

    @PostConstruct
    public void init() {
        positiveWords.addAll(Arrays.asList(
                "love", "great", "amazing", "excellent", "fantastic", "wonderful",
                "good", "best", "awesome", "happy", "perfect", "beautiful",
                "outstanding", "brilliant", "superb", "enjoy", "nice", "like",
                "recommend", "impressive", "pleased", "satisfied", "delightful"
        ));

        negativeWords.addAll(Arrays.asList(
                "bad", "terrible", "horrible", "awful", "worst", "hate",
                "poor", "disappointing", "disgusting", "annoying", "ugly",
                "boring", "useless", "waste", "broken", "fail", "angry",
                "frustrated", "unhappy", "regret", "scam", "dislike"
        ));

        try {
            loadTrainingData();
        } catch (Exception e) {
            log.warn("Could not load sentiment dataset: {}", e.getMessage());
        }

        log.info("Sentiment Analyzer initialized with {} positive and {} negative words",
                positiveWords.size(), negativeWords.size());
    }

    private void loadTrainingData() throws Exception {
        File csvFile = new File("datasets/sentiment_data.csv");
        if (!csvFile.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                int lastComma = line.lastIndexOf(',');
                if (lastComma > 0) {
                    String text = line.substring(0, lastComma).replaceAll("^\"|\"$", "").toLowerCase();
                    String label = line.substring(lastComma + 1).trim().toLowerCase();
                    trainingData.put(text, label);

                    String[] words = text.split("\\s+");
                    for (String word : words) {
                        word = word.replaceAll("[^a-z]", "");
                        if (word.length() > 2) {
                            if ("positive".equals(label)) positiveWords.add(word);
                            else negativeWords.add(word);
                        }
                    }
                }
            }
        }
        log.info("Loaded {} training samples for sentiment analysis", trainingData.size());
    }

    public Map<String, Object> analyze(String text) {
        String lower = text.toLowerCase();
        String[] words = lower.split("\\s+");

        int positiveCount = 0;
        int negativeCount = 0;
        List<String> positiveFound = new ArrayList<>();
        List<String> negativeFound = new ArrayList<>();

        for (String word : words) {
            String clean = word.replaceAll("[^a-z]", "");
            if (positiveWords.contains(clean)) {
                positiveCount++;
                positiveFound.add(clean);
            }
            if (negativeWords.contains(clean)) {
                negativeCount++;
                negativeFound.add(clean);
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String w = words[i].replaceAll("[^a-z]", "");
            if ("not".equals(w) || "no".equals(w) || "never".equals(w) || w.endsWith("n't")) {
                positiveCount = Math.max(0, positiveCount - 1);
                negativeCount++;
            }
        }

        int total = positiveCount + negativeCount;
        String sentiment;
        double confidence;

        if (total == 0) {
            sentiment = "neutral";
            confidence = 50.0;
        } else {
            double positiveRatio = (double) positiveCount / total;
            if (positiveRatio > 0.6) {
                sentiment = "positive";
                confidence = positiveRatio * 100;
            } else if (positiveRatio < 0.4) {
                sentiment = "negative";
                confidence = (1 - positiveRatio) * 100;
            } else {
                sentiment = "neutral";
                confidence = 50.0;
            }
        }

        return Map.of(
                "sentiment", sentiment,
                "confidence", Math.round(confidence * 100.0) / 100.0,
                "positiveWords", positiveFound,
                "negativeWords", negativeFound,
                "method", "Lexicon-Based Analysis"
        );
    }
}
