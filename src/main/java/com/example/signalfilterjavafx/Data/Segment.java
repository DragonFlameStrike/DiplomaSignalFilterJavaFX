package com.example.signalfilterjavafx.Data;

import java.util.ArrayList;
import java.util.List;

public class Segment {
    private List<Measurement> measurements;
    private Integer firstIndex;
    private Integer lastIndex;

    public Segment() {
        measurements = new ArrayList<>();
    }

    public void addListMesurments(List<Measurement> measurements) {this.measurements = measurements;}

    public void addMeasurement(Measurement measurement){
        this.measurements.add(measurement);
    }

    public void addAllMeasurements(List<Measurement> measurements){
        this.measurements.addAll(measurements);
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Integer getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(Integer firstIndex) {
        this.firstIndex = firstIndex;
    }

    public Integer getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
    }
}
