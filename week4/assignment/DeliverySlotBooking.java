package week4.assignment;

import java.util.Set;

public class DeliverySlotBooking {

    public static class DeliverySlot {
        // "ASAP" is typed only once in the entire class definition
        private static final String DEFAULT_SLOT = "ASAP";

        private static final Set<String> PEAK_HOURS = Set.of(
            "12:00-13:00",
            "13:00-14:00",
            "19:00-20:00",
            "20:00-21:00"
        );

        private final String orderId;
        private final String timeSlot;

        // Constructor 1: Full details
        public DeliverySlot(String orderId, String timeSlot) {
            if (orderId == null || orderId.trim().isEmpty()) {
                throw new IllegalArgumentException("Order ID cannot be null or empty.");
            }
            if (timeSlot == null || timeSlot.trim().isEmpty()) {
                throw new IllegalArgumentException("Time slot cannot be null or empty.");
            }
            this.orderId = orderId.trim();
            this.timeSlot = timeSlot.trim();
        }

        // Constructor 2: Chains via this(...) to Constructor 1
        public DeliverySlot(String orderId) {
            this(orderId, DEFAULT_SLOT);
        }

        public boolean isPeakHour() {
            return PEAK_HOURS.contains(timeSlot);
        }

        public String getOrderId() {
            return orderId;
        }

        public String getTimeSlot() {
            return timeSlot;
        }
    }

    public static void main(String[] args) {
        DeliverySlot slot1 = new DeliverySlot("ORD101", "13:00-14:00");
        System.out.println("ORD101 (13:00-14:00) isPeakHour: " + slot1.isPeakHour());

        DeliverySlot slot2 = new DeliverySlot("ORD102");
        System.out.println("ORD102 (default: " + slot2.getTimeSlot() + ") isPeakHour: " + slot2.isPeakHour());
    }
}
