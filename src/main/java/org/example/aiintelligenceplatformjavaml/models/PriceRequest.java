package org.example.aiintelligenceplatformjavaml.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PriceRequest {

    @NotNull
    @Positive
    private Double size;

    @NotNull
    @Positive
    private Integer rooms;

    private String location;

    @Positive
    private Integer age;

    public PriceRequest() {}

    public PriceRequest(Double size, Integer rooms, String location, Integer age) {
        this.size = size;
        this.rooms = rooms;
        this.location = location;
        this.age = age;
    }

    public Double getSize() { return size; }
    public void setSize(Double size) { this.size = size; }

    public Integer getRooms() { return rooms; }
    public void setRooms(Integer rooms) { this.rooms = rooms; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}
