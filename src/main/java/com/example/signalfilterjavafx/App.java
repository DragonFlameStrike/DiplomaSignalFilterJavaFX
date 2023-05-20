package com.example.signalfilterjavafx;

import com.example.signalfilterjavafx.Data.Measurement;
import com.example.signalfilterjavafx.Data.Segment;
import com.example.signalfilterjavafx.DataPrep.*;
import com.example.signalfilterjavafx.FileCreator.FileCreator;
import com.example.signalfilterjavafx.Filters.FFT;
import com.example.signalfilterjavafx.Filters.SegmentsSubtractor;
import com.example.signalfilterjavafx.GUI.JavaFXPainter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.example.signalfilterjavafx.GUI.WindowConfig.*;

public class App extends Application {

    private final String input = "signal2.txt";

    private Parser parser;
    private Cleaner cleaner;
    private JavaFXPainter painter;
    private MeasurementSplitter measurementSplitter;
    private LinearInterpolator linearExtrapolator;
    private SegmentsSubtractor segmentsSubtractor;
    private FileCreator fileCreator;

    private void initialize(Stage primaryStage) {
        parser = new FileParser();
        cleaner = new Cleaner();
        painter = new JavaFXPainter(primaryStage);
        measurementSplitter = new MeasurementSplitter();
        linearExtrapolator = new LinearInterpolator();
        segmentsSubtractor = new SegmentsSubtractor();
        fileCreator = new FileCreator();
    }

    public void start(Stage primaryStage) throws IOException {
        //Инициализация JavaFX
        initialize(primaryStage);

        //Подготовка данных - преобразование взодных данных в массив точек
        List<Measurement> signal = parser.parse(input);
        //Удаляем ошибочные данные
        cleaner.clearSignal(signal);
        //Режем весь сигнал на сегменты включающие точки с типом 2 и 3
        List<Segment> segments = measurementSplitter.split(signal);
        // Удаляем  сегменты незначительного размера
        cleaner.clearSegments(segments);
        // Добавляем доп точки в сегменты для однородности сигнала
        List<Segment> extrSegments = new ArrayList<>();
        for (Segment segment: segments) {
            extrSegments.add(linearExtrapolator.interpolateSegment(segment));
        }

        //Фильтруем
        List<Segment> filteredSegments = FFT.filter(extrSegments);
        //Вычитаем из основного сигнала, содержащего обе компоненты, сигнал волнения океана
        List<Segment> tsunamiSegments = segmentsSubtractor.subtract(extrSegments,filteredSegments);

        // Основной сигнал
        painter.addSignal(signal,0);
        // Сегменты
        addSegmentsInPainterWithSignalBorders(extrSegments,signal,1);
        //Отфильтрованные сегменты
        addSegmentsInPainterWithSignalBorders(filteredSegments,signal,2);
        //Цунами сигнал
        addSegmentsInPainterWithSignalWidth(tsunamiSegments,signal,3);

        //Сохраняем рузельтаты в файл
        fileCreator.createFileFromListSegments(tsunamiSegments);

        painter.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void addSegmentsInPainterWithSignalBorders(List<Segment> segments, List<Measurement> signal,int section){
        Double minHeight = Double.MAX_VALUE;
        Double maxHeight = Double.MIN_VALUE;
        Double minTime = Double.MAX_VALUE;
        Double maxTime = Double.MIN_VALUE;
        // Определяем минимальное и максимальное значения времени и высоты.
        for (Measurement point : signal) {
            Double time = point.getTime();
            Double height = point.getHeight();
            minTime = Math.min(minTime, time);
            maxTime = Math.max(maxTime, time);
            minHeight = Math.min(minHeight, height);
            maxHeight = Math.max(maxHeight, height);
        }
        for (Segment segment : segments) {
            for (Measurement point : segment.getMeasurements()) {
                Double height = point.getHeight();
                minHeight = Math.min(minHeight, height);
                maxHeight = Math.max(maxHeight, height);
            }
        }
        for (Segment segment: segments) {
            painter.addSignal(segment.getMeasurements(),SECTION_HEIGHT*section,maxTime,minTime,maxHeight,minHeight);
        }
    }
    public void addSegmentsInPainterWithSignalWidth(List<Segment> segments, List<Measurement> signal,int section){
        Double minHeight = Double.MAX_VALUE;
        Double maxHeight = Double.MIN_VALUE;
        Double minTime = Double.MAX_VALUE;
        Double maxTime = Double.MIN_VALUE;
        // Определяем минимальное и максимальное значения времени и высоты.
        for (Measurement point : signal) {
            Double time = point.getTime();
            minTime = Math.min(minTime, time);
            maxTime = Math.max(maxTime, time);
        }
        for (Segment segment : segments) {
            for (Measurement point : segment.getMeasurements()) {
                Double height = point.getHeight();
                minHeight = Math.min(minHeight, height);
                maxHeight = Math.max(maxHeight, height);
            }
        }
        for (Segment segment: segments) {
            painter.addSignal(segment.getMeasurements(),SECTION_HEIGHT*section,maxTime,minTime,maxHeight,minHeight);
        }
    }

}