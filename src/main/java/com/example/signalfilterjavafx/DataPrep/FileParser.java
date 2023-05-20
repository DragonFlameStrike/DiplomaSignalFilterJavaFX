package com.example.signalfilterjavafx.DataPrep;

import com.example.signalfilterjavafx.Data.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileParser implements Parser{
    @Override
    public List<Measurement> parse(String filename) {
        List<Measurement> measurements = new ArrayList<>(); // Список измерений
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount <= 2) {
                    continue; // Пропускаем первые две строки
                }
                String[] tokens = line.split("\\s+"); // Разделяем строку на токены
                SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");
                Date date = format.parse(line.substring(0, 19));
                Integer type = Integer.parseInt(tokens[6]);
                Double time = (double) date.getTime();
                Double height = Double.parseDouble(tokens[tokens.length - 1]);
                Measurement measurement = new Measurement(time,height,type);
                measurements.add(measurement);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return measurements;
    }
}