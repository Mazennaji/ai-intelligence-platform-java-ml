package org.example.aiintelligenceplatformjavaml.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FraudRequest {

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    private String transactionType;

    private String location;
    private String time;

    public FraudRequest() {}

    public FraudRequest(Double amount, String transactionType, String location, String time) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.location = location;
        this.time = time;
    }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
