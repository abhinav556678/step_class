package week6.practice;

import java.util.Arrays;

public class LateRegistrationPenaltyOverride {

    public static class EventTicket {
        private final String attendeeId;
        private final double basePrice;
        private double balanceDue;
        private final double[] lateFeeHistory;
        private int lateFeeCount;

        public EventTicket(double basePrice) {
            this("TCK-DEFAULT", basePrice);
        }

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().isEmpty()) {
                this.attendeeId = "TCK-DEFAULT";
            } else {
                this.attendeeId = attendeeId.trim();
            }
            this.basePrice = Math.max(0, basePrice);
            this.balanceDue = this.basePrice;
            this.lateFeeHistory = new double[10];
            this.lateFeeCount = 0;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.balanceDue -= amount;
            }
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public String getAttendeeId() {
            return attendeeId;
        }

        public double getBasePrice() {
            return basePrice;
        }

        protected void applyLateFee(double amount) {
            if (amount > 0) {
                this.balanceDue += amount;
                if (lateFeeCount < lateFeeHistory.length) {
                    lateFeeHistory[lateFeeCount++] = amount;
                }
            }
        }

        public double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }
    }

    public static class WorkshopTicket extends EventTicket {

        public WorkshopTicket(double basePrice) {
            super(basePrice);
        }

        public WorkshopTicket(String attendeeId, double basePrice) {
            super(attendeeId, basePrice);
        }

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println("Balance after doubled late fee: " + w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println("History before tampering: " + Arrays.toString(history));
        if (history.length > 0) {
            history[0] = 999;
        }
        System.out.println("History after modifying caller's array: " + Arrays.toString(w.getLateFeeHistory()));
    }
}
