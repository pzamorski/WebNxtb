package com.webnxtb.helpers;

public class ScalingIn {
    public static int[] scal(int[] values) {


        return scaleValuesToInteger(values);

    }

    // Funkcja do przeskalowania wartości na liczbę całkowitą
    private static int[] scaleValuesToInteger(int[] values) {
        int[] scaledValues = new int[values.length];
        int oldMin = getMin(values);
        int oldMax = getMax(values);

        for (int i = 0; i < values.length; i++) {
            scaledValues[i] = values[i]-oldMin;
        }

        return scaledValues;
    }

    // Funkcja do znalezienia minimalnej wartości w tablicy
    private static int getMin(int[] arr) {
        int min = arr[0];
        for (int value : arr) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    // Funkcja do znalezienia maksymalnej wartości w tablicy
    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
