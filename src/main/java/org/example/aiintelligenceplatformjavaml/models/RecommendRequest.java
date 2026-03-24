package org.example.aiintelligenceplatformjavaml.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RecommendRequest {

    @NotNull
    @Positive
    private Long userId;

    private Integer maxResults = 5;

    public RecommendRequest() {}

    public RecommendRequest(Long userId, Integer maxResults) {
        this.userId = userId;
        this.maxResults = maxResults;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getMaxResults() { return maxResults; }
    public void setMaxResults(Integer maxResults) { this.maxResults = maxResults; }
}
