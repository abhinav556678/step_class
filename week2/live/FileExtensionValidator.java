package week2.live;

public class FileExtensionValidator {

    public static String validateFileExtension(String filename) {
        if (filename == null) {
            String result = "Rejected — invalid file type";
            System.out.println(result);
            return result;
        }

        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            String result = "Rejected — invalid file type";
            System.out.println(result);
            return result;
        }

        String extension = filename.substring(lastDotIndex + 1);
        if (extension.equalsIgnoreCase("pdf") ||
            extension.equalsIgnoreCase("docx") ||
            extension.equalsIgnoreCase("zip")) {
            String result = "Accepted";
            System.out.println(result);
            return result;
        } else {
            String result = "Rejected — invalid file type";
            System.out.println(result);
            return result;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        validateFileExtension("Assignment1.PDF");

        System.out.println("\n--- Test 2 ---");
        validateFileExtension("notes.txt");
    }
}
