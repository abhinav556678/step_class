package week5.assignment;

public class ReferenceDeskSubclassReach {

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
                // Protected member access by a subclass in another package is valid only
                // through a reference of the subclass's own type (JLS §6.6.2)
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

        String[] tokens = accessorContext.trim().split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].trim();
            if (!token.isEmpty()) {
                sb.append(Character.toUpperCase(token.charAt(0)));
                if (token.length() > 1) {
                    sb.append(token.substring(1).toLowerCase());
                }
                if (i < tokens.length - 1) {
                    sb.append(" ");
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\"): "
                + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));

        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE\"): "
                + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));

        System.out.println("describeContext(\"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\"): \""
                + describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE") + "\"");
    }
}
