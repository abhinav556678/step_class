package week4.practice;

public final class TieredBoardingPenaltyCalculator {

    public static final class BoardingPenaltyCalculator {
        // Field locked against modification
        private final double minimumPenaltyPercent;

        public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
            if (minimumPenaltyPercent < 0) {
                throw new IllegalArgumentException("Minimum penalty percent cannot be negative: " + minimumPenaltyPercent);
            }
            this.minimumPenaltyPercent = minimumPenaltyPercent;
        }

        // Method locked against overriding (final)
        public final double calculatePenalty(double ticketFare, int minutesLate) {
            if (ticketFare < 0) {
                throw new IllegalArgumentException("Ticket fare cannot be negative: " + ticketFare);
            }
            if (minutesLate < 0) {
                throw new IllegalArgumentException("Minutes late cannot be negative: " + minutesLate);
            }

            // On-time passengers must never trigger the floor or penalty
            if (minutesLate == 0) {
                return 0.0;
            }

            // Closed-form O(1) tiered rate calculation
            int b1Minutes = Math.min(minutesLate, 5);
            int b2Minutes = Math.max(0, Math.min(minutesLate - 5, 10));
            int b3Minutes = Math.max(0, minutesLate - 15);

            double tieredPenaltyRate = (b1Minutes * 0.005) + (b2Minutes * 0.010) + (b3Minutes * 0.020);
            double tieredPenalty = tieredPenaltyRate * ticketFare;

            // Minimum flat-fee floor applies only when passenger is genuinely late
            double floorPenalty = (minimumPenaltyPercent / 100.0) * ticketFare;

            return Math.max(tieredPenalty, floorPenalty);
        }

        public double getMinimumPenaltyPercent() {
            return minimumPenaltyPercent;
        }
    }

    public static void main(String[] args) {
        BoardingPenaltyCalculator calculator = new BoardingPenaltyCalculator(1.0);

        double p0 = calculator.calculatePenalty(1000, 0);
        double p1 = calculator.calculatePenalty(1000, 1);
        double p16 = calculator.calculatePenalty(1000, 16);

        System.out.printf("ticketFare = 1000, minutesLate = 0  -> Rs %.1f%n", p0);
        System.out.printf("ticketFare = 1000, minutesLate = 1  -> Rs %.1f%n", p1);
        System.out.printf("ticketFare = 1000, minutesLate = 16 -> Rs %.1f%n", p16);
    }
}
