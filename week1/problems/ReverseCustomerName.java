package week1.problems;

public class ReverseCustomerName {

    public static String reverseCustomerName(String customerName) {
        if (customerName == null) {
            return null;
        }

        char[] chars = customerName.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }

        return new String(chars);
    }

    public static void displayReversal(String name) {
        String reversed = reverseCustomerName(name);
        System.out.printf("Original Name: %s%n", name);
        System.out.printf("Reversed Name: %s%n%n", reversed);
    }

    public static void main(String[] args) {
        displayReversal("Sunil");
        displayReversal("Abhinav");
        displayReversal("Anita");
    }
}
