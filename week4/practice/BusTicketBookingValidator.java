package week4.practice;

import java.util.HashSet;
import java.util.Set;

public class BusTicketBookingValidator {

    public static class BusTicket {
        private final String passengerName;
        private final String destination;
        private boolean isCheckedIn;

        // No usable no-argument constructor - only parameterized constructor
        public BusTicket(String passengerName, String destination) {
            if (!isValidName(passengerName)) {
                throw new IllegalArgumentException("Invalid passenger name: '" + passengerName + "'. Must be non-empty alphabetic characters only.");
            }
            if (!isValidDestination(destination)) {
                throw new IllegalArgumentException("Invalid destination: '" + destination + "'. Must be non-empty alphabetic characters only.");
            }

            this.passengerName = passengerName.trim();
            this.destination = destination.trim();
            this.isCheckedIn = false;
        }

        private static boolean isValidName(String name) {
            if (name == null) return false;
            String trimmed = name.trim();
            if (trimmed.isEmpty()) return false;
            // Must contain only letters and spaces, no digits or punctuation
            return trimmed.matches("^[a-zA-Z\\s]+$");
        }

        private static boolean isValidDestination(String destination) {
            if (destination == null) return false;
            String trimmed = destination.trim();
            if (trimmed.isEmpty()) return false;
            return trimmed.matches("^[a-zA-Z\\s]+$");
        }

        public void markCheckedIn() {
            if (this.isCheckedIn) {
                System.out.println("Warning: Ticket for " + passengerName + " to " + destination + " is already checked in. Duplicate check-in rejected.");
                return;
            }
            this.isCheckedIn = true;
            System.out.println("Passenger " + passengerName + " successfully checked in for destination " + destination + ".");
        }

        public boolean isCheckedIn() {
            return isCheckedIn;
        }

        public String getPassengerName() {
            return passengerName;
        }

        public String getDestination() {
            return destination;
        }
    }

    public static void processBatch(String[][] rawBookings) {
        int validCount = 0;
        int rejectedCount = 0;
        int duplicatesSkipped = 0;

        Set<String> acceptedPairs = new HashSet<>();

        if (rawBookings == null) {
            System.out.println("Valid: 0 | Rejected: 0 | Duplicates skipped: 0");
            return;
        }

        for (String[] booking : rawBookings) {
            if (booking == null || booking.length < 2) {
                rejectedCount++;
                continue;
            }

            String name = booking[0];
            String destination = booking[1];

            try {
                // Attempt to construct ticket to trigger validation
                BusTicket ticket = new BusTicket(name, destination);
                String uniqueKey = ticket.getPassengerName().toLowerCase() + "|" + ticket.getDestination().toLowerCase();

                if (acceptedPairs.contains(uniqueKey)) {
                    duplicatesSkipped++;
                } else {
                    acceptedPairs.add(uniqueKey);
                    validCount++;
                }
            } catch (IllegalArgumentException e) {
                rejectedCount++;
            }
        }

        System.out.println("Valid: " + validCount + " | Rejected: " + rejectedCount + " | Duplicates skipped: " + duplicatesSkipped);
    }

    public static void main(String[] args) {
        String[][] sampleBatch = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };

        System.out.println("Processing sample batch:");
        processBatch(sampleBatch);

        System.out.println("\nTesting check-in idempotency:");
        BusTicket ticket = new BusTicket("Divya", "Chennai");
        ticket.markCheckedIn();
        ticket.markCheckedIn();
    }
}
