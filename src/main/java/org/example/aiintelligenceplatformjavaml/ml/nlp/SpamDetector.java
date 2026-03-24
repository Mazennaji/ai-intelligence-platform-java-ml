package org.example.aiintelligenceplatformjavaml.ml.nlp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Component
public class SpamDetector {

    private static final Logger log = LoggerFactory.getLogger(SpamDetector.class);

    private final Set<String> spamKeywords = new HashSet<>();
    private final Map<String, Double> spamWordWeights = new HashMap<>();

    @PostConstruct
    public void init() {
        spamKeywords.addAll(Arrays.asList(
                "win", "free", "money", "click", "prize", "congratulations",
                "urgent", "act now", "limited time", "offer", "deal",
                "buy now", "discount", "cash", "winner", "selected",
                "lottery", "credit", "subscribe", "unsubscribe", "viagra"
        ));

        try {
            loadTrainingData();
        } catch (Exception e) {
            log.warn("Could not load spam dataset: {}", e.getMessage());
        }

        log.info("Spam Detector initialized with {} keywords", spamKeywords.size());
    }

    private void loadTrainingData() throws Exception {
        File csvFile = new File("datasets/spam_emails.csv");
        if (!csvFile.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                int lastComma = line.lastIndexOf(',');
                if (lastComma > 0) {
                    String text = line.substring(0, lastComma).replaceAll("^\"|\"$", "").toLowerCase();
                    String label = line.substring(lastComma + 1).trim().toLowerCase();

                    if ("spam".equals(label)) {
                        for (String word : text.split("\\s+")) {
                            word = word.replaceAll("[^a-z]", "");
                            if (word.length() > 2) {
                                spamWordWeights.merge(word, 1.0, Double::sum);
                            }
                        }
                    }
                }
            }
        }
        log.info("Loaded spam word weights from training data");
    }

    public Map<String, Object> detect(String text) {
        String lower = text.toLowerCase();
        String[] words = lower.split("\\s+");

        double spamScore = 0.0;
        List<String> triggers = new ArrayList<>();

        for (String word : words) {
            String clean = word.replaceAll("[^a-z]", "");
            if (spamKeywords.contains(clean)) {
                spamScore += 0.15;
                triggers.add(clean);
            }
            if (spamWordWeights.containsKey(clean)) {
                spamScore += 0.05;
            }
        }

        if (text.matches(".*[!]{2,}.*")) { spamScore += 0.1; triggers.add("multiple exclamations"); }
        if (text.equals(text.toUpperCase()) && text.length() > 5) { spamScore += 0.15; triggers.add("ALL CAPS"); }
        if (text.matches(".*\\$\\d+.*")) { spamScore += 0.1; triggers.add("dollar amounts"); }
        if (text.matches(".*https?://.*")) { spamScore += 0.05; triggers.add("contains URL"); }

        spamScore = Math.min(1.0, spamScore);
        boolean isSpam = spamScore >= 0.4;

        return Map.of(
                "classification", isSpam ? "SPAM" : "HAM",
                "spamScore", Math.round(spamScore * 10000.0) / 100.0,
                "triggers", triggers,
                "method", "Keyword + Pattern Analysis"
        );
    }
}
