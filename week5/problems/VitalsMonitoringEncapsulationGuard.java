package week5.problems;

import java.util.Arrays;

public class VitalsMonitoringEncapsulationGuard {

    public static class PatientVitals {
        private static final int MAX_CAPACITY = 500;
        private double[] readings;
        private int count;

        public PatientVitals(double[] initialReadings) {
            this.readings = new double[MAX_CAPACITY];
            this.count = 0;

            if (initialReadings != null) {
                // Reuses recordReading to avoid duplicate validation logic
                for (double reading : initialReadings) {
                    recordReading(reading);
                }
            }
        }

        public void recordReading(double reading) {
            // Silently reject impossible readings (<= 0 or > 45 °C) or if capacity reached
            if (reading <= 0 || reading > 45.0) {
                return;
            }
            if (count < MAX_CAPACITY) {
                readings[count++] = reading;
            }
        }

        public double getAverage() {
            if (count == 0) {
                return 0.0;
            }
            double sum = 0;
            for (int i = 0; i < count; i++) {
                sum += readings[i];
            }
            return sum / count;
        }

        // Defensive copying on every call to prevent external mutation
        public double[] getAllReadings() {
            return Arrays.copyOf(readings, count);
        }
    }

    public static void main(String[] args) {
        PatientVitals v = new PatientVitals(new double[]{36.5, -2, 37.1});
        System.out.println("Valid readings: " + Arrays.toString(v.getAllReadings()));

        // Verify defensive copying
        double[] copy = v.getAllReadings();
        copy[0] = 999;
        System.out.println("Reading after modifying copy: " + v.getAllReadings()[0]);
        System.out.println("Average reading: " + v.getAverage());
    }
}
