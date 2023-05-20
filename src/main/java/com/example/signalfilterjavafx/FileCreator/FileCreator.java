package com.example.signalfilterjavafx.FileCreator;

import com.example.signalfilterjavafx.Data.Measurement;
import com.example.signalfilterjavafx.Data.Segment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class FileCreator {
    public void createFileFromListSegments(List<Segment> input) throws IOException {
        boolean inputOpen = true;
        int segmentNumber = 0;
        int stringCounter = 0;

        Segment segment = input.get(segmentNumber);
        String fileData = "#YY  MM DD hh mm ss | Type | HEIGHT in cm\n";
        FileOutputStream fos = new FileOutputStream("D:\\Program Files\\JetBrains\\IntelliJ IDEA 2021.3.1\\IdeaProjects\\SignalFilterJavaFX\\out.txt");
        fos.write(fileData.getBytes());
        while(inputOpen){
//            if(stringCounter < segment.getFirstIndex()){
//
//            }
            for (Measurement measurement: segment.getMeasurements()) {
                Double time = measurement.getTime();
                // Создаем объект Date на основе значения Double-времени
                Date date = new Date(time.longValue());
                // Создаем объект SimpleDateFormat для форматирования даты
                SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");
                // Преобразуем дату в нужный формат
                String formattedTime = format.format(date);
                String type = measurement.getType().toString();
                Double height = measurement.getHeight();
                double heightInCm = height * 100.0;
                heightInCm = Math.round(heightInCm * 1000.0) / 1000.0;
                String out = formattedTime + "     " + type + "       " + heightInCm + "\n";
                fos.write(out.getBytes());
            }
            segmentNumber++;
            if(input.size()-1<segmentNumber){
                inputOpen = false;
            } else {
               segment = input.get(segmentNumber);
            }
        }
        fos.flush();
        fos.close();
    }
}
