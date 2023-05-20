package com.example.signalfilterjavafx.DataPrep;

import com.example.signalfilterjavafx.Data.Measurement;

import java.util.List;

public interface Parser {
    List<Measurement> parse(String filename);
}
