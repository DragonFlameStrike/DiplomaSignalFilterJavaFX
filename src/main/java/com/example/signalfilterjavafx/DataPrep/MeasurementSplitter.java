package com.example.signalfilterjavafx.DataPrep;

import com.example.signalfilterjavafx.Data.Measurement;
import com.example.signalfilterjavafx.Data.Segment;

import java.util.ArrayList;
import java.util.List;

public class MeasurementSplitter {
    public List<Segment> split(List<Measurement> measurements){
        Integer prevType = 1;
        Segment tmpSegment = new Segment();
        Boolean record = false;
        List<Segment> output = new ArrayList<>();
        for (int i = 0;i<measurements.size();i++) {
            Measurement data = measurements.get(i);
            if(data.getType() != 1 && prevType == 1){
                tmpSegment.setFirstIndex(i);
                record = true;
            }
            if((data.getType() == 1 && prevType != 1) || (i == measurements.size()-1 && tmpSegment.getFirstIndex()!=null)){
                tmpSegment.setLastIndex(i);
                record = false;
                extensionSegment(tmpSegment, measurements);
                output.add(tmpSegment);
                tmpSegment = new Segment();
            }
            if(record) {
                tmpSegment.addMeasurement(data);
            }
            prevType = data.getType();
        }
        return output;
    }

    private void extensionSegment(Segment tmpSegment,List<Measurement> measurements) {
        //init indexes
        int segmentSize = tmpSegment.getLastIndex()- tmpSegment.getFirstIndex();
        int leftStartIndex = tmpSegment.getFirstIndex() - segmentSize/10;
        int rightStartIndex = tmpSegment.getLastIndex();
        List<Measurement> centralSegment = tmpSegment.getMeasurements();
        List<Measurement> leftSegment = new ArrayList<>();
        List<Measurement> rightSegment = new ArrayList<>();

        //check index is valid
        if(leftStartIndex<0) leftStartIndex=0;

        for (int i = leftStartIndex; i < tmpSegment.getFirstIndex(); i++) {
            leftSegment.add(measurements.get(i));
        }
        for(int i = rightStartIndex;i<rightStartIndex+segmentSize/10 && i<measurements.size()-1;i++){
            rightSegment.add(measurements.get(i));
        }
        List<Measurement> mainSegment = new ArrayList<>();
        mainSegment.addAll(leftSegment);
        mainSegment.addAll(centralSegment);
        mainSegment.addAll(rightSegment);
        tmpSegment.setMeasurements(mainSegment);
    }
}
