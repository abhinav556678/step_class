package week4.assignment;

import java.util.Arrays;

public class CanteenTrustScoreRankingEngine {

    public static class Canteen implements Comparable<Canteen> {
        private String canteenCode;
        private String canteenName;
        private int trustScore;

        // Constructor 1: Full details, resolving name clashes with 'this'
        public Canteen(String canteenCode, String canteenName, int trustScore) {
            if (canteenCode == null || canteenCode.trim().isEmpty()) {
                throw new IllegalArgumentException("Canteen code cannot be null or blank.");
            }
            if (canteenName == null || canteenName.trim().isEmpty()) {
                throw new IllegalArgumentException("Canteen name cannot be null or blank.");
            }
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        // Constructor 2: Chains via this(...) with default trustScore 3
        public Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, 3);
        }

        @Override
        public int compareTo(Canteen other) {
            if (other == null) {
                return -1;
            }

            // 1. Trust score descending (highest score ranks first)
            int scoreComparison = Integer.compare(other.trustScore, this.trustScore);
            if (scoreComparison != 0) {
                return scoreComparison;
            }

            // 2. Canteen code alphabetical, case-insensitive (without altering display/case)
            int codeComparison = this.canteenCode.compareToIgnoreCase(other.canteenCode);
            if (codeComparison != 0) {
                return codeComparison;
            }

            // 3. Canteen name length (tie-break order from problem hint)
            int lengthComparison = Integer.compare(this.canteenName.length(), other.canteenName.length());
            if (lengthComparison != 0) {
                return lengthComparison;
            }

            // 4. Canteen name alphabetical
            return this.canteenName.compareTo(other.canteenName);
        }

        public String getCanteenCode() {
            return canteenCode;
        }

        public String getCanteenName() {
            return canteenName;
        }

        public int getTrustScore() {
            return trustScore;
        }

        @Override
        public String toString() {
            return "\"" + canteenCode + "\"";
        }
    }

    // Stable sort implementation (Insertion Sort) without calling built-in sort utilities
    public static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null) return null;

        Canteen[] sorted = canteens.clone();
        for (int i = 1; i < sorted.length; i++) {
            Canteen key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].compareTo(key) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats") // defaults to trustScore 3
        };

        Canteen[] ranked = rankCanteens(canteens);
        System.out.println("Ranked canteens: " + Arrays.toString(ranked));
    }
}
