package week4.assignment;

public class NightlyKitchenReconciliationEngine {

    public static class DeliveryAccount {
        private static double defaultBaseSurgeFloorPercent;

        // Static block for one-time class-level initialization
        static {
            defaultBaseSurgeFloorPercent = 1.0;
        }

        private final String studentId;
        private final double orderValue;

        // Full constructor
        public DeliveryAccount(String studentId, double orderValue) {
            if (studentId == null || studentId.trim().isEmpty()) {
                throw new IllegalArgumentException("Student ID cannot be null or empty.");
            }
            if (orderValue < 0) {
                throw new IllegalArgumentException("Order value cannot be negative: " + orderValue);
            }
            this.studentId = studentId.trim();
            this.orderValue = orderValue;
        }

        // Provisional constructor chaining via this(...)
        public DeliveryAccount(String studentId) {
            this(studentId, 0.0);
        }

        // Final surge fee calculation adhering to Problem 4 tiered rule
        public final double calculateSurgeFee(int delayMinutes) {
            if (delayMinutes < 0) {
                throw new IllegalArgumentException("Delay minutes cannot be negative: " + delayMinutes);
            }
            if (delayMinutes == 0) {
                return 0.0;
            }

            int b1 = Math.min(delayMinutes, 5);
            int b2 = Math.max(0, Math.min(delayMinutes - 5, 10));
            int b3 = Math.max(0, delayMinutes - 15);

            double tieredRate = (b1 * 0.005) + (b2 * 0.010) + (b3 * 0.020);
            double tieredFee = tieredRate * orderValue;
            double floorFee = (defaultBaseSurgeFloorPercent / 100.0) * orderValue;

            return Math.max(tieredFee, floorFee);
        }

        public String getStudentId() {
            return studentId;
        }

        public double getOrderValue() {
            return orderValue;
        }
    }

    // Subclass for premium student delivery accounts
    public static class PremiumDeliveryAccount extends DeliveryAccount {
        private static final double PREMIUM_SURGE_DISCOUNT = 0.50; // 50% discount on surge fees

        public PremiumDeliveryAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        public PremiumDeliveryAccount(String studentId) {
            super(studentId);
        }

        public double getDiscountRate() {
            return PREMIUM_SURGE_DISCOUNT;
        }
    }

    // Alias Premium to PremiumDeliveryAccount for convenience matching example signature
    public static class Premium extends PremiumDeliveryAccount {
        public Premium(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        public Premium(String studentId) {
            super(studentId);
        }
    }

    public static void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) {
            System.out.println("Cannot process null delivery account entry.");
            return;
        }

        double surgeFee = account.calculateSurgeFee(delayMinutes);

        if (account instanceof PremiumDeliveryAccount) {
            PremiumDeliveryAccount premium = (PremiumDeliveryAccount) account;
            double discountedSurgeFee = surgeFee * (1.0 - premium.getDiscountRate());
            System.out.printf("Processed Premium Account [%s]: Order Rs %.2f, Settled Rs %.2f, Delay %d min (Base Surge Rs %.2f -> Discounted Surge Rs %.2f)%n",
                    account.getStudentId(), account.getOrderValue(), amount, delayMinutes, surgeFee, discountedSurgeFee);
        } else {
            System.out.printf("Processed Regular Account [%s]: Order Rs %.2f, Settled Rs %.2f, Delay %d min (Surge Rs %.2f)%n",
                    account.getStudentId(), account.getOrderValue(), amount, delayMinutes, surgeFee);
        }
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        // Validation: Length mismatch corrupts reconciliation data
        if (accounts == null || amounts == null || delayMinutesArray == null) {
            throw new IllegalArgumentException("Batch inputs cannot be null.");
        }
        if (accounts.length != amounts.length || accounts.length != delayMinutesArray.length) {
            throw new IllegalArgumentException("Batch rejected: Input array lengths do not match (accounts: "
                    + accounts.length + ", amounts: " + amounts.length + ", delayMinutes: " + delayMinutesArray.length + ").");
        }

        int processedCount = 0;
        int nullSkippedCount = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotalSurgeFees = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            DeliveryAccount account = accounts[i];
            double amount = amounts[i];
            int delayMinutes = delayMinutesArray[i];

            if (account == null) {
                nullSkippedCount++;
                continue;
            }

            processedCount++;
            double baseSurge = account.calculateSurgeFee(delayMinutes);

            // Dispatch using instanceof
            if (account instanceof PremiumDeliveryAccount) {
                premiumCount++;
                PremiumDeliveryAccount premium = (PremiumDeliveryAccount) account;
                grandTotalSurgeFees += baseSurge * (1.0 - premium.getDiscountRate());
            } else {
                regularCount++;
                grandTotalSurgeFees += baseSurge;
            }

            processAccount(account, amount, delayMinutes);
        }

        System.out.printf("%n%d processed | %d null skipped | %d premium | %d regular | grand total surge fees = Rs %.2f%n",
                processedCount, nullSkippedCount, premiumCount, regularCount, grandTotalSurgeFees);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new Premium("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        System.out.println("Starting Nightly Multi-Kitchen Reconciliation:");
        processBatch(accounts, amounts, delayMinutesArray);
    }
}
