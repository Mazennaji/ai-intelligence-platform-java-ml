package org.example.aiintelligenceplatformjavaml.services;

import org.example.aiintelligenceplatformjavaml.ml.recommender.RecommenderEngine;
import org.example.aiintelligenceplatformjavaml.models.PredictionResponse;
import org.example.aiintelligenceplatformjavaml.models.RecommendRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecommendService {

    private final RecommenderEngine recommenderEngine;

    public RecommendService(RecommenderEngine recommenderEngine) {
        this.recommenderEngine = recommenderEngine;
    }

    public PredictionResponse recommend(RecommendRequest request) {
        Map<String, Object> result = recommenderEngine.recommend(
                request.getUserId(),
                request.getMaxResults()
        );

        return new PredictionResponse(
                "Recommendation System",
                "Recommendations generated",
                null,
                result
        );
    }
}
