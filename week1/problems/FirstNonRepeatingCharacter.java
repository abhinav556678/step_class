package week1.problems;

public class FirstNonRepeatingCharacter {

    public static char findFirstNonRepeatingChar(String text) {
        if (text == null || text.isEmpty()) {
            return '\0';
        }

        // Using character frequency array for ASCII characters
        int[] freq = new int[256];
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 256) {
                freq[c]++;
            }
        }

        // Scan left-to-right to find the first character with frequency 1
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 256 && freq[c] == 1) {
                return c;
            }
        }

        return '\0'; // Sentinel value when no non-repeating character exists
    }

    public static void testAndDisplay(String input) {
        char result = findFirstNonRepeatingChar(input);
        if (result != '\0') {
            System.out.printf("\"%s\" -> First Non-Repeating Character: '%c'%n", input, result);
        } else {
            System.out.printf("\"%s\" -> No Non-Repeating Character Found%n", input);
        }
    }

    public static void main(String[] args) {
        testAndDisplay("swiss");
        testAndDisplay("aabbcc");
        testAndDisplay("stepcoding");
        testAndDisplay("stress");
    }
}
