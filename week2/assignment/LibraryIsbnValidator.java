package week2.assignment;

public class LibraryIsbnValidator {

    public static String normalizeCode(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    public static String validateAndFormat(String code) {
        if (code == null || code.length() != 13) {
            String error = "Invalid: wrong length";
            System.out.println(error);
            return error;
        }

        // Validate first 3 characters are letters
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                String error = "Invalid: publisher code must be 3 letters";
                System.out.println(error);
                return error;
            }
        }

        // Validate next 10 characters are digits (4 year + 6 catalog)
        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                String error = "Invalid: non-digit body";
                System.out.println(error);
                return error;
            }
        }

        String pubCode = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalog = code.substring(7, 13);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(pubCode).append("] ");
        sb.append("YEAR: ").append(year);
        sb.append(" | CATALOG: ").append(catalog);

        String result = sb.toString();
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        String norm1 = normalizeCode(" pen2026004251 ");
        validateAndFormat(norm1);

        System.out.println("\n--- Test 2 ---");
        String norm2 = normalizeCode("12N2026004251");
        validateAndFormat(norm2);
    }
}
