package week4.practice;

import java.util.Arrays;

public class RemainderFairFareSplitter {

    public static class FareSplitter {
        private final String tripId;
        private final double totalFare;
        private final int passengerCount;

        // Constructor 1: Canonical constructor with full validation
        public FareSplitter(String tripId, double totalFare, int passengerCount) {
            if (tripId == null || tripId.trim().isEmpty()) {
                throw new IllegalArgumentException("Trip ID must not be null or empty.");
            }
            if (totalFare < 0) {
                throw new IllegalArgumentException("Total fare cannot be negative: " + totalFare);
            }
            if (passengerCount <= 0) {
                throw new IllegalArgumentException("Passenger count must be greater than zero: " + passengerCount);
            }

            this.tripId = tripId.trim();
            this.totalFare = totalFare;
            this.passengerCount = passengerCount;
        }

        // Constructor 2: Total fare only, defaults passengerCount to 2
        public FareSplitter(String tripId, double totalFare) {
            this(tripId, totalFare, 2);
        }

        // Constructor 3: ID only (provisional split), defaults fare to 0.0 and passengerCount to 2
        public FareSplitter(String tripId) {
            this(tripId, 0.0, 2);
        }

        public double[] fareBreakdown() {
            double[] breakdown = new double[passengerCount];

            // Convert to smallest currency unit (paise / cents) to prevent floating-point rounding loss
            long totalInPaise = Math.round(totalFare * 100.0);
            long baseShareInPaise = totalInPaise / passengerCount;
            long remainderPaise = totalInPaise % passengerCount;

            // Distribute base share, and absorb leftover paise across the last passengers
            long cutoffIndex = passengerCount - remainderPaise;
            for (int i = 0; i < passengerCount; i++) {
                long share = (i >= cutoffIndex) ? (baseShareInPaise + 1) : baseShareInPaise;
                breakdown[i] = share / 100.0;
            }

            return breakdown;
        }

        public boolean isConfirmationOverdue(int confirmed, int expected) {
            if (confirmed < 0 || expected < 0) {
                throw new IllegalArgumentException("Confirmation counts cannot be negative.");
            }
            // A group is behind schedule if confirmed count is less than expected count
            return confirmed < expected;
        }

        public String getTripId() {
            return tripId;
        }

        public double getTotalFare() {
            return totalFare;
        }

        public int getPassengerCount() {
            return passengerCount;
        }
    }

    public static void main(String[] args) {
        FareSplitter trip1 = new FareSplitter("TRIP001", 100000, 3);
        System.out.println("Trip 1 breakdown: " + Arrays.toString(trip1.fareBreakdown()));

        FareSplitter trip3 = new FareSplitter("TRIP003");
        System.out.println("Trip 3 breakdown (provisional): " + Arrays.toString(trip3.fareBreakdown()));

        System.out.println("Is confirmation overdue (2 confirmed out of 3 expected): " 
                + trip1.isConfirmationOverdue(2, 3));
        System.out.println("Is confirmation overdue (3 confirmed out of 3 expected): " 
                + trip1.isConfirmationOverdue(3, 3));
    }
}
