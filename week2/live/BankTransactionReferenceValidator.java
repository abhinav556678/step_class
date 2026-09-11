package week2.live;

public class BankTransactionReferenceValidator {

    public static String normalizeReference(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    public static String validateAndFormat(String reference) {
        if (reference == null || reference.length() != 14) {
            String error = "Invalid: wrong length";
            System.out.println(error);
            return error;
        }

        // First 3 characters must be letters
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                String error = "Invalid: bank code must be 3 letters";
                System.out.println(error);
                return error;
            }
        }

        // Remaining 11 characters must be digits (6 date + 5 seq)
        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                String error = "Invalid: non-digit body";
                System.out.println(error);
                return error;
            }
        }

        String bankCode = reference.substring(0, 3);
        String day = reference.substring(3, 5);
        String month = reference.substring(5, 7);
        String year = reference.substring(7, 9);
        String seq = reference.substring(9, 14);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(bankCode).append("] ");
        sb.append("DATE: ").append(day).append("/").append(month).append("/").append(year);
        sb.append(" | SEQ: ").append(seq);

        String formatted = sb.toString();
        System.out.println(formatted);
        return formatted;
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        String norm1 = normalizeReference(" hdf03022600042 ");
        validateAndFormat(norm1);

        System.out.println("\n--- Test 2 ---");
        String norm2 = normalizeReference("12F03022600042");
        validateAndFormat(norm2);
    }
}
