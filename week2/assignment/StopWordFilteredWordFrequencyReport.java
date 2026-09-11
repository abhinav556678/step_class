package week2.assignment;

import java.util.*;

public class StopWordFilteredWordFrequencyReport {

    public static void printFilteredWordFrequency(String feedback) {
        if (feedback == null || feedback.trim().isEmpty()) {
            return;
        }

        Set<String> stopWords = new HashSet<>(Arrays.asList("the", "was", "and", "a", "is", "of", "in"));

        // Normalize: convert to lowercase and remove punctuation like periods and commas
        String cleaned = feedback.toLowerCase().replace(".", "").replace(",", "").replace("!", "").replace("?", "");

        // Split into words by whitespace
        String[] words = cleaned.trim().split("\\s+");

        Map<String, Integer> freqMap = new HashMap<>();
        for (String w : words) {
            if (w.isEmpty() || stopWords.contains(w)) {
                continue;
            }
            freqMap.put(w, freqMap.getOrDefault(w, 0) + 1);
        }

        // Sort by frequency descending
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(freqMap.entrySet());
        entryList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.printf("%s: %d%n", entry.getKey(), entry.getValue());
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        printFilteredWordFrequency("The mentor was great, the session was great and clear.");
    }
}
