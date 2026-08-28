package week1.assignment;

public class MovieReviewWordLengthProfiler {

    public static void classifyWordLengths(String review) {
        if (review == null || review.trim().isEmpty()) {
            System.out.println("Short: 0 | Medium: 0 | Long: 0");
            return;
        }

        String[] words = review.trim().split("\\s+");
        int shortCount = 0;   // 1–4 letters
        int mediumCount = 0;  // 5–8 letters
        int longCount = 0;    // 9+ letters

        for (String word : words) {
            // Count letter characters in the word or use raw length
            // Cleaning punctuation if attached to words
            String cleanWord = word.replaceAll("[^a-zA-Z]", "");
            int len = cleanWord.isEmpty() ? word.length() : cleanWord.length();

            if (len >= 1 && len <= 4) {
                shortCount++;
            } else if (len >= 5 && len <= 8) {
                mediumCount++;
            } else if (len >= 9) {
                longCount++;
            }
        }

        System.out.printf("Short: %d | Medium: %d | Long: %d%n", shortCount, mediumCount, longCount);
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        classifyWordLengths("This movie was absolutely fantastic and thrilling");
        // "This" (4) -> Short, "movie" (5) -> Medium, "was" (3) -> Short,
        // "absolutely" (10) -> Long, "fantastic" (9) -> Long, "and" (3) -> Short, "thrilling" (9) -> Long
        // Short: 3 | Medium: 1 | Long: 3
    }
}
