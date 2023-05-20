package com.example.signalfilterjavafx.Filters;

import com.example.signalfilterjavafx.Data.Measurement;
import com.example.signalfilterjavafx.Data.Segment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FFT {

    public static List<Segment> filter(List<Segment> segments){
        List<Segment> out = new ArrayList<>();
        for (Segment segment: segments) {

            int originalSize = segment.getMeasurements().size();
            int paddedSize = nextPowerOfTwo(originalSize); // Вычисляем ближайшую степень двойки
            int currSize = paddedSize/2;
            double[] a = new double[currSize];
            double[] b = new double[currSize];

            // Заполните массив 'a' значениями сигнала измерения высоты океана
            for (int i = 0; i < currSize; i++) {
                a[i] = segment.getMeasurements().get(i).getHeight();
            }

            FFT.fft(a, b, false); // Применяем прямое преобразование Фурье

            double thresholdFrequency = 0.001; // Задаем пороговую частоту 0.000978
            filterFrequencies(a, b, thresholdFrequency); // Фильтруем частоты

            FFT.fft(a, b, true); // Применяем обратное преобразование Фурье

            // Теперь массив 'a' содержит отфильтрованный сигнал
            Segment outSegment = new Segment();
            for (int i = 0; i < currSize; i++) {
                outSegment.addMeasurement(new Measurement(segment.getMeasurements().get(i).getTime(),a[i],segment.getMeasurements().get(i).getType()));
            }
            out.add(outSegment);
        }
        return out;
    }
    private static int nextPowerOfTwo(int n) {
        int power = 1;
        while (power < n) {
            power *= 2;
        }
        return power;
    }

    private static void fft(double[] a, double[] b, boolean invert) {
        int count = a.length;
        reverse(a, count);
        reverse(b, count);

        for (int len = 2; len <= count; len <<= 1) {
            double angle = 2 * Math.PI / len * (invert ? -1 : 1);
            double wlen_a = Math.cos(angle);
            double wlen_b = Math.sin(angle);

            for (int i = 0; i < count; i += len) {
                double w_a = 1;
                double w_b = 0;

                for (int j = 0; j < len / 2; j++) {
                    double u_a = a[i + j];
                    double u_b = b[i + j];
                    double v_a = a[i + j + len / 2] * w_a - b[i + j + len / 2] * w_b;
                    double v_b = a[i + j + len / 2] * w_b + b[i + j + len / 2] * w_a;
                    a[i + j] = u_a + v_a;
                    b[i + j] = u_b + v_b;
                    a[i + j + len / 2] = u_a - v_a;
                    b[i + j + len / 2] = u_b - v_b;

                    double next_w_a = w_a * wlen_a - w_b * wlen_b;
                    double next_w_b = w_a * wlen_b + w_b * wlen_a;
                    w_a = next_w_a;
                    w_b = next_w_b;
                }
            }
        }

        if (invert) {
            for (int i = 0; i < count; i++) {
                a[i] /= count;
                b[i] /= count;
            }
        }
    }

    private static void reverse(double[] a, int n) {
        int bits = (int) Math.round(Math.log(n) / Math.log(2));
        int[] rev = new int[n];
        for (int i = 0; i < n; i++) {
            rev[i] = (rev[i >> 1] >> 1) | ((i & 1) << (bits - 1));
            if (i < rev[i]) {
                double temp = a[i];
                a[i] = a[rev[i]];
                a[rev[i]] = temp;
            }
        }
    }
    private static void filterFrequencies(double[] a, double[] b, double thresholdFrequency) {
        int n = a.length;
        for (int i = 1; i < n / 2; i++) {
            double freq = (double) i / n;
            if (freq > thresholdFrequency) {
                a[i] = 0;
                b[i] = 0;
                a[n - i] = 0;
                b[n - i] = 0;
            }
        }
    }
}
