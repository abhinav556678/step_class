package week1.assignment;

public class ExamHallSeatDuplicationChecker {

    public static void checkDuplicateSeats(int[] seatNumbers) {
        if (seatNumbers == null || seatNumbers.length == 0) {
            System.out.println("No Duplicate Seats Found");
            return;
        }

        boolean foundDuplicate = false;
        // Tracking visited indices or printing duplicates using nested loops without collections
        for (int i = 0; i < seatNumbers.length; i++) {
            // Check if seatNumbers[i] already appeared earlier to avoid printing the same duplicate multiple times
            boolean alreadyReported = false;
            for (int k = 0; k < i; k++) {
                if (seatNumbers[k] == seatNumbers[i]) {
                    alreadyReported = true;
                    break;
                }
            }
            if (alreadyReported) {
                continue;
            }

            // Check if there is another instance of seatNumbers[i] after i
            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                    foundDuplicate = true;
                    break;
                }
            }
        }

        if (!foundDuplicate) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 (Duplicates present) ---");
        int[] hall1 = {101, 102, 103, 102, 105};
        checkDuplicateSeats(hall1);

        System.out.println("\n--- Test 2 (No duplicates) ---");
        int[] hall2 = {101, 102, 103, 104, 105};
        checkDuplicateSeats(hall2);
    }
}
