package week5.problems;

public class CrossPackageInheritanceReach {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        String mod = fieldModifier.toLowerCase().trim();
        String ctx = accessorContext.trim();

        switch (mod) {
            case "public":
                return "ALLOWED";

            case "private":
                return "SAME_CLASS".equals(ctx) ? "ALLOWED" : "DENIED";

            case "default":
                return ("SAME_CLASS".equals(ctx) || "SAME_PACKAGE".equals(ctx)) ? "ALLOWED" : "DENIED";

            case "protected":
                if ("SAME_CLASS".equals(ctx) || "SAME_PACKAGE".equals(ctx)) {
                    return "ALLOWED";
                }
                // Under Java Language Specification §6.6.2:
                // Subclass in different package can access protected member only via reference of subclass itself (or sub-subclass),
                // not through a reference of the superclass (parent) type.
                if ("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(ctx)) {
                    return "ALLOWED";
                }
                if ("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE".equals(ctx) || "DIFFERENT_PACKAGE".equals(ctx)) {
                    return "DENIED";
                }
                return "DENIED";

            default:
                return "DENIED";
        }
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.trim().isEmpty()) {
            return "";
        }

        String[] parts = accessorContext.trim().split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String word = parts[i].trim();
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    sb.append(word.substring(1).toLowerCase());
                }
                if (i < parts.length - 1) {
                    sb.append(" ");
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("protected + SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE: "
                + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));

        System.out.println("protected + SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE: "
                + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));

        System.out.println("describeContext(\"SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE\"): \""
                + describeContext("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE") + "\"");
        System.out.println("describeContext(\"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\"): \""
                + describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE") + "\"");
    }
}
