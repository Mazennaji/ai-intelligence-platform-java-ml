package org.example.aiintelligenceplatformjavaml.ml.recommender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RecommenderEngine {

    private static final Logger log = LoggerFactory.getLogger(RecommenderEngine.class);

    private final Map<Long, Map<Long, Double>> userRatings = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            loadData();
            log.info("Recommender Engine loaded with {} users", userRatings.size());
        } catch (Exception e) {
            log.warn("Could not load recommendations dataset: {}", e.getMessage());
        }
    }

    private void loadData() throws Exception {
        File csvFile = new File("datasets/recommendations.csv");
        if (!csvFile.exists()) {
            throw new RuntimeException("Dataset not found: " + csvFile.getAbsolutePath());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    long userId = Long.parseLong(parts[0].trim());
                    long itemId = Long.parseLong(parts[1].trim());
                    double rating = Double.parseDouble(parts[2].trim());

                    userRatings.computeIfAbsent(userId, k -> new HashMap<>()).put(itemId, rating);
                }
            }
        }
    }

    public Map<String, Object> recommend(long userId, int maxResults) {
        Map<Long, Double> targetUserRatings = userRatings.get(userId);

        if (targetUserRatings == null || targetUserRatings.isEmpty()) {
            return Map.of(
                    "userId", userId,
                    "recommendations", Collections.emptyList(),
                    "message", "No rating history found for user",
                    "method", "Collaborative Filtering"
            );
        }

        Map<Long, Double> similarityScores = new HashMap<>();
        for (Map.Entry<Long, Map<Long, Double>> entry : userRatings.entrySet()) {
            long otherUserId = entry.getKey();
            if (otherUserId == userId) continue;

            double similarity = cosineSimilarity(targetUserRatings, entry.getValue());
            if (similarity > 0) {
                similarityScores.put(otherUserId, similarity);
            }
        }

        Map<Long, Double> itemScores = new HashMap<>();
        for (Map.Entry<Long, Double> simEntry : similarityScores.entrySet()) {
            Map<Long, Double> otherRatings = userRatings.get(simEntry.getKey());
            double similarity = simEntry.getValue();

            for (Map.Entry<Long, Double> ratingEntry : otherRatings.entrySet()) {
                long itemId = ratingEntry.getKey();
                if (!targetUserRatings.containsKey(itemId)) {
                    itemScores.merge(itemId, ratingEntry.getValue() * similarity, Double::sum);
                }
            }
        }

        List<Map<String, Object>> recommendations = itemScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(maxResults)
                .map(e -> {
                    Map<String, Object> rec = new LinkedHashMap<>();
                    rec.put("itemId", e.getKey());
                    rec.put("score", Math.round(e.getValue() * 100.0) / 100.0);
                    return rec;
                })
                .collect(Collectors.toList());

        return Map.of(
                "userId", userId,
                "recommendations", recommendations,
                "totalCandidates", itemScores.size(),
                "method", "Collaborative Filtering (Cosine Similarity)"
        );
    }

    private double cosineSimilarity(Map<Long, Double> a, Map<Long, Double> b) {
        Set<Long> commonItems = new HashSet<>(a.keySet());
        commonItems.retainAll(b.keySet());

        if (commonItems.isEmpty()) return 0.0;

        double dotProduct = 0, normA = 0, normB = 0;
        for (Long item : commonItems) {
            dotProduct += a.get(item) * b.get(item);
            normA += a.get(item) * a.get(item);
            normB += b.get(item) * b.get(item);
        }

        if (normA == 0 || normB == 0) return 0.0;
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
