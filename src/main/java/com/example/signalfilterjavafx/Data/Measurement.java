package com.example.signalfilterjavafx.Data;

public class Measurement {

    private Double time;
    private Double height;
    private Integer type;

    public Measurement(Double time, Double height, Integer type) {
        this.time = time;
        this.height = height;
        this.type = type;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
