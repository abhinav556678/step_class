package week4.assignment;

public final class ExamWeekSurgeFeeCalculator {

    public static final class SurgeFeeCalculator {
        // Field locked against modification
        private final double minimumSurgePercent;

        public SurgeFeeCalculator(double minimumSurgePercent) {
            if (minimumSurgePercent < 0) {
                throw new IllegalArgumentException("Minimum surge percent cannot be negative: " + minimumSurgePercent);
            }
            this.minimumSurgePercent = minimumSurgePercent;
        }

        // Method locked against overriding (final)
        public final double calculateSurgeFee(double orderValue, int delayMinutes) {
            if (orderValue < 0) {
                throw new IllegalArgumentException("Order value cannot be negative: " + orderValue);
            }
            if (delayMinutes < 0) {
                throw new IllegalArgumentException("Delay minutes cannot be negative: " + delayMinutes);
            }

            // On-time orders must never trigger the floor or surge fee
            if (delayMinutes == 0) {
                return 0.0;
            }

            // Closed-form O(1) tiered rate calculation
            int b1Minutes = Math.min(delayMinutes, 5);
            int b2Minutes = Math.max(0, Math.min(delayMinutes - 5, 10));
            int b3Minutes = Math.max(0, delayMinutes - 15);

            double tieredRate = (b1Minutes * 0.005) + (b2Minutes * 0.010) + (b3Minutes * 0.020);
            double tieredFee = tieredRate * orderValue;

            // Minimum surge floor applies only once an order is genuinely delayed
            double floorFee = (minimumSurgePercent / 100.0) * orderValue;

            return Math.max(tieredFee, floorFee);
        }

        public double getMinimumSurgePercent() {
            return minimumSurgePercent;
        }
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calculator = new SurgeFeeCalculator(1.0);

        double fee0 = calculator.calculateSurgeFee(500, 0);
        double fee1 = calculator.calculateSurgeFee(500, 1);
        double fee16 = calculator.calculateSurgeFee(500, 16);

        System.out.printf("orderValue = 500, delayMinutes = 0  -> Rs %.1f%n", fee0);
        System.out.printf("orderValue = 500, delayMinutes = 1  -> Rs %.1f%n", fee1);
        System.out.printf("orderValue = 500, delayMinutes = 16 -> Rs %.1f%n", fee16);
    }
}
