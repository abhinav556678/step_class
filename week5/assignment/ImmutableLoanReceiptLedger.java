package week5.assignment;

import java.util.Arrays;

public class ImmutableLoanReceiptLedger {

    // One-time shared state initialized in a static block
    private static int totalCirculationBatches;

    static {
        totalCirculationBatches = 0;
    }

    public static class LoanReceipt {
        private final String memberId;
        private final String[] bookIds;

        public LoanReceipt(String memberId, String[] bookIds) {
            if (memberId == null || memberId.trim().isEmpty()) {
                throw new IllegalArgumentException("Member ID cannot be null or empty.");
            }
            if (bookIds == null) {
                throw new IllegalArgumentException("Book IDs array cannot be null.");
            }

            // Every book ID must match the format "BK-" followed by exactly 3 digits
            for (String id : bookIds) {
                if (id == null || !id.matches("^BK-\\d{3}$")) {
                    throw new IllegalArgumentException("Invalid book ID format: '" + id + "'. Must match BK- followed by 3 digits.");
                }
            }

            this.memberId = memberId.trim();
            // Defensive copying on the way in
            this.bookIds = bookIds.clone();
        }

        public String getMemberId() {
            return memberId;
        }

        // Defensive copying on the way out
        public String[] getBookIds() {
            return bookIds.clone();
        }

        // With-style method for immutability
        public LoanReceipt withCorrectedBookId(int index, String newId) {
            if (index < 0 || index >= bookIds.length) {
                throw new IndexOutOfBoundsException("Index " + index + " out of bounds for book IDs.");
            }
            if (newId == null || !newId.matches("^BK-\\d{3}$")) {
                throw new IllegalArgumentException("Invalid replacement book ID: '" + newId + "'.");
            }

            String[] updatedBookIds = bookIds.clone();
            updatedBookIds[index] = newId;
            return new LoanReceipt(this.memberId, updatedBookIds);
        }
    }

    // Reference-only variant subclass
    public static final class ReferenceOnlyLoanReceipt extends LoanReceipt {
        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Room number cannot be null or empty.");
            }
            this.roomNumber = roomNumber.trim();
        }

        public String getRoomNumber() {
            return roomNumber;
        }
    }

    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        totalCirculationBatches++;

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts != null) {
            for (LoanReceipt receipt : receipts) {
                if (receipt == null) {
                    nullSkipped++;
                    continue;
                }

                processed++;

                // instanceof-based settlement dispatch
                if (receipt instanceof ReferenceOnlyLoanReceipt) {
                    referenceOnly++;
                } else {
                    regular++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        // Test format validation rejection
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
            System.out.println("Construction unexpectedly succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("new LoanReceipt(..., bad): construction rejected");
        }

        // Test defensive copying
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println("Defensive copy test: r.getBookIds()[0] = " + r.getBookIds()[0]);

        // Test nightly circulation processing
        LoanReceipt[] batch = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };

        System.out.println("processNightlyCirculation: " + processNightlyCirculation(batch));
    }
}
