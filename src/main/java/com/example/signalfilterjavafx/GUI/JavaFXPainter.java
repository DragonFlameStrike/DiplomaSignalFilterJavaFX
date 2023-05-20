package com.example.signalfilterjavafx.GUI;

import com.example.signalfilterjavafx.Data.Frequency;
import com.example.signalfilterjavafx.Data.Measurement;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.example.signalfilterjavafx.GUI.WindowConfig.*;

public class JavaFXPainter {

    private final Group group;
    private final Stage pStage;
    private Scene scene;

    public JavaFXPainter(Stage pStage){
         this.group = new Group();
         this.pStage = pStage;
         createScene();
    }

    public void createScene(){
        Line line1 = new Line(0, SECTION_HEIGHT, SECTION_WIDTH, SECTION_HEIGHT);
        Line line2 = new Line(0, SECTION_HEIGHT*2, SECTION_WIDTH, SECTION_HEIGHT*2);
        Line line3 = new Line(0, SECTION_HEIGHT*3, SECTION_WIDTH, SECTION_HEIGHT*3);
        group.getChildren().add(line1);
        group.getChildren().add(line2);
        group.getChildren().add(line3);
    }

    public void addSignal(List<Measurement> signal, Integer position){
        if (signal == null) {
            throw new IllegalArgumentException("Signal cannot be null");
        }
        List<Measurement> formattedSignal  = formatSignal(signal);
        drawSignal(group,formattedSignal ,position);
    }

    public void addSignal(List<Measurement> signal, Integer position, Double maxTime,Double minTime,Double maxHeight,Double minHeight){
        if (signal == null) {
            throw new IllegalArgumentException("Signal cannot be null");
        }
        List<Measurement> formattedSignal  = formatSignal(signal,maxTime,minTime,maxHeight,minHeight);
        drawSignal(group,formattedSignal ,position);
    }





    public void show(){
        this.scene = new Scene(this.group, WINDOW_WIDTH, WINDOW_HEIGHT);
        pStage.setScene(scene);
        pStage.show();
    }

    private void drawSignal(Group group, List<Measurement> data, int offset) {
        Line line = new Line(data.get(0).getTime(), data.get(0).getHeight() + offset, data.get(1).getTime(), data.get(1).getHeight() + offset); // первая линия
        group.getChildren().add(line); // добавляем нашу первую линию в группу

        for (int i = 1; i < data.size() - 1; i++) {
            line = new Line(data.get(i).getTime(), data.get(i).getHeight() + offset, data.get(i+1).getTime(), data.get(i+1).getHeight() + offset); // следующая линия
            group.getChildren().add(line); // добавляем ее в группу
        }
    }

    /**
     * Функция форматирует сигнал, приводя его к размерам WIDTH x HEIGHT.
     * @param signal исходный сигнал, представленный в виде трехменого массива (время, высота, тип).
     * @return сигнал, отформатированный в соответствии с размерами WIDTH x HEIGHT.
     */
    private List<Measurement> formatSignal(List<Measurement> signal) {
        List<Measurement> formattedSignal = new ArrayList<>();
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

        // Форматируем сигнал, приводя его к размерам WIDTH x HEIGHT.
        Double timeRange = maxTime - minTime;
        Double heightRange = maxHeight - minHeight;
        for (Measurement point : signal) {
            Double time = point.getTime();
            Double height = point.getHeight();

            Double formattedTime = (time - minTime) * SECTION_WIDTH / timeRange;
            Double formattedHeight = (Math.abs(height - maxHeight)) * SECTION_HEIGHT / heightRange;
            Measurement formattedPoint = new Measurement(formattedTime,formattedHeight,point.getType());
            formattedSignal.add(formattedPoint);
        }
        return formattedSignal;
    }
    private List<Measurement> formatSignal(List<Measurement> signal, Double maxTime, Double minTime, Double maxHeight, Double minHeight) {
        List<Measurement> formattedSignal = new ArrayList<>();
        // Форматируем сигнал, приводя его к размерам WIDTH x HEIGHT.
        Double timeRange = maxTime - minTime;
        Double heightRange = maxHeight - minHeight;
        for (Measurement point : signal) {
            Double time = point.getTime();
            Double height = point.getHeight();

            Double formattedTime = (time - minTime) * SECTION_WIDTH / timeRange;
            Double formattedHeight = (Math.abs(height - maxHeight)) * SECTION_HEIGHT / heightRange;
            Measurement formattedPoint = new Measurement(formattedTime,formattedHeight,point.getType());
            formattedSignal.add(formattedPoint);
        }
        return formattedSignal;
    }
}
