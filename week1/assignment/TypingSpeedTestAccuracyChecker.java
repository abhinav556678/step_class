package week1.assignment;

public class TypingSpeedTestAccuracyChecker {

    public static void checkTypingAccuracy(String original, String typed) {
        if (original == null || typed == null) {
            System.out.println("Invalid input strings");
            return;
        }

        int total = original.length();
        if (total == 0) {
            System.out.println("Matched: 0/0 | Accuracy: 100.00% | No Mismatches");
            return;
        }

        int matched = 0;
        int firstMismatchPos = -1;
        char origChar = ' ';
        char typedChar = ' ';

        for (int i = 0; i < total; i++) {
            char o = original.charAt(i);
            char t = (i < typed.length()) ? typed.charAt(i) : '\0';

            if (o == t) {
                matched++;
            } else {
                if (firstMismatchPos == -1) {
                    firstMismatchPos = i + 1; // 1-based index
                    origChar = o;
                    typedChar = t;
                }
            }
        }

        double accuracy = ((double) matched / total) * 100.0;

        if (firstMismatchPos != -1) {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | First Mismatch at position %d ('%c' vs '%c')%n",
                    matched, total, accuracy, firstMismatchPos, origChar, typedChar);
        } else {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | No Mismatches%n", matched, total, accuracy);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        checkTypingAccuracy("hello world", "hello worlt");

        System.out.println("\n--- Test 2 ---");
        checkTypingAccuracy("coding", "coding");
    }
}
