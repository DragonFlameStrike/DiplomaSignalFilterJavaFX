package com.example.signalfilterjavafx.DataPrep;

import com.example.signalfilterjavafx.Data.Segment;

public interface Interpolator {
    Segment interpolateSegment(Segment segment);
}
