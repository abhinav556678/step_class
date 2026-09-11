package week2.live;

public class MaskedPhoneNumberFormatter {

    public static String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() != 10) {
            String error = "Invalid phone number";
            System.out.println(error);
            return error;
        }

        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                String error = "Invalid phone number";
                System.out.println(error);
                return error;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("XXXXXX");
        sb.append("-");
        sb.append(phone.substring(6));

        String result = sb.toString();
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        maskPhoneNumber("9876543210");

        System.out.println("\n--- Test 2 ---");
        maskPhoneNumber("98765");
    }
}
