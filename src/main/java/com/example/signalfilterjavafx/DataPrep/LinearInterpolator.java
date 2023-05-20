package com.example.signalfilterjavafx.DataPrep;

import com.example.signalfilterjavafx.Data.Measurement;
import com.example.signalfilterjavafx.Data.Segment;

import java.util.ArrayList;
import java.util.List;

public class LinearInterpolator implements Interpolator {
    @Override
    public Segment interpolateSegment(Segment segment){
        Segment output = new Segment();
        output.setFirstIndex(segment.getFirstIndex());
        output.setLastIndex(segment.getLastIndex());
        for (int i = 0;i<segment.getMeasurements().size()-1;i++) {
            Measurement currentMeasurement = segment.getMeasurements().get(i);
            Measurement nextMeasurement = segment.getMeasurements().get(i+1);
            if(currentMeasurement.getType()==3 || (currentMeasurement.getType()==2 && nextMeasurement.getType()==3)){
                output.addMeasurement(currentMeasurement);
            } else if (currentMeasurement.getType()==2 || (currentMeasurement.getType()==1 && nextMeasurement.getType()==2)){
                output.addAllMeasurements(interpolate(currentMeasurement, nextMeasurement, 4.0, 2));
            } else {
                output.addAllMeasurements(interpolate(currentMeasurement,nextMeasurement, 60.0,1));
            }
        }
        return output;
    }

    public List<Measurement> interpolate(Measurement start, Measurement finish, Double count, int type){
        List<Measurement> output = new ArrayList<>();
        Double startHeight = start.getHeight();
        Double finishHeight = finish.getHeight();
        Double sizeHeight = finishHeight-startHeight;

        Double startTime = start.getTime();
        Double finishTime = finish.getTime();
        Double sizeTime = finishTime-startTime;

        for(int i = 0;i<count;i++){
            output.add(new Measurement(startTime+(sizeTime/count)*i,startHeight+(sizeHeight/count)*i,type));
        }
        return output;
    }
}
