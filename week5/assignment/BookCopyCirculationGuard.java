package week5.assignment;

public class BookCopyCirculationGuard {

    public static class BookInventory {
        private final int copiesTotal;
        private int copiesAvailable;

        public BookInventory(int copiesTotal) {
            if (copiesTotal <= 0) {
                throw new IllegalArgumentException("Total copies must be a positive integer, received: " + copiesTotal);
            }
            this.copiesTotal = copiesTotal;
            this.copiesAvailable = copiesTotal;
        }

        public void checkOut() {
            // Silently reject checkout if no copies are available
            if (copiesAvailable > 0) {
                copiesAvailable--;
            }
        }

        public void checkIn() {
            // Silently reject check-in if inventory is already at maximum capacity
            if (copiesAvailable < copiesTotal) {
                copiesAvailable++;
            }
        }

        public int getCopiesAvailable() {
            return copiesAvailable;
        }

        public int getCopiesTotal() {
            return copiesTotal;
        }
    }

    public static void main(String[] args) {
        // Test zero/negative rejection
        try {
            new BookInventory(0);
            System.out.println("Construction unexpectedly succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("new BookInventory(0): construction rejected");
        }

        // Test checkout boundary guard
        BookInventory b = new BookInventory(3);
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut(); // 4th checkout silently rejected
        System.out.println("Copies after 4 checkouts from 3: " + b.getCopiesAvailable());

        // Test check-in boundary guard
        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn(); // 4th check-in silently rejected
        System.out.println("Copies after 4 check-ins from 0: " + b.getCopiesAvailable());
    }
}
