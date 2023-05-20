package com.example.signalfilterjavafx.Data;

public class Frequency {
    private Double frequency;
    private Double time;

    public Frequency(Double frequency, Double time) {
        this.frequency = frequency;
        this.time = time;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
