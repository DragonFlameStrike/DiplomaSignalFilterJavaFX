package com.example.signalfilterjavafx.Filters;

import com.example.signalfilterjavafx.Data.Measurement;
import com.example.signalfilterjavafx.Data.Segment;

import java.util.ArrayList;
import java.util.List;

public class SegmentsSubtractor {
    public List<Segment> subtract(List<Segment> reduced,List<Segment> subtracted) {
        List<Segment> out = new ArrayList<>();
        for (int i = 0; i < reduced.size(); i++) {
            Segment outSegment = new Segment();
            Segment reducedSegment = reduced.get(i);
            Segment subtractedSegment = subtracted.get(i);
            for (int j = 0; j < subtractedSegment.getMeasurements().size(); j++) {
                if(reducedSegment.getMeasurements().get(j).getType()==1) continue;
                double subtractedHeight = (reducedSegment.getMeasurements().get(j).getHeight() - subtractedSegment.getMeasurements().get(j).getHeight());

                outSegment.addMeasurement(new Measurement(reducedSegment.getMeasurements().get(j).getTime(),subtractedHeight,reducedSegment.getMeasurements().get(j).getType()));
            }
            outSegment.setFirstIndex(reducedSegment.getFirstIndex());
            outSegment.setLastIndex(reducedSegment.getLastIndex());
            out.add(outSegment);
        }
        return out;
    }
}
