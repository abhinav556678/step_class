package week2.assignment;

public class WordReversalEncoder {

    public static String reverseEachWord(String sentence) {
        if (sentence == null) {
            return "";
        }

        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            StringBuilder reversedWord = new StringBuilder();
            String w = words[i];
            for (int j = w.length() - 1; j >= 0; j--) {
                reversedWord.append(w.charAt(j));
            }
            result.append(reversedWord);
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        String output = result.toString();
        System.out.println(output);
        return output;
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        reverseEachWord("hello club");
    }
}
