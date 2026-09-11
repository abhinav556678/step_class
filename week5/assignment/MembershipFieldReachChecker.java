package week5.assignment;

import java.util.LinkedHashMap;
import java.util.Map;

public class MembershipFieldReachChecker {

    public static class LibraryMember {
        private String membershipId;
        protected String branchCode;
        private double finesOwed;
        public String displayName;

        // No usable no-argument constructor
        public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
            if (membershipId == null) {
                throw new IllegalArgumentException("Membership ID cannot be null.");
            }
            String trimmedId = membershipId.trim();
            if (trimmedId.length() < 4) {
                throw new IllegalArgumentException("Invalid membershipId: '" + membershipId + "'. Must be non-blank and at least 4 characters.");
            }

            this.membershipId = trimmedId;
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }

        public String getMembershipId() {
            return membershipId;
        }

        public double getFinesOwed() {
            return finesOwed;
        }
    }

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
            case "protected":
                return ("SAME_CLASS".equals(ctx) || "SAME_PACKAGE".equals(ctx)) ? "ALLOWED" : "DENIED";
            default:
                return "DENIED";
        }
    }

    public static String summarizeByModifier(String[][] attempts) {
        // LinkedHashMap keeps modifier order: private, default, protected, public
        Map<String, int[]> stats = new LinkedHashMap<>();
        stats.put("private", new int[]{0, 0});
        stats.put("default", new int[]{0, 0});
        stats.put("protected", new int[]{0, 0});
        stats.put("public", new int[]{0, 0});

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String mod = attempt[0].toLowerCase().trim();
                    String ctx = attempt[1].trim();
                    if (stats.containsKey(mod)) {
                        String result = classifyAccess(mod, ctx);
                        if ("ALLOWED".equals(result)) {
                            stats.get(mod)[0]++;
                        } else {
                            stats.get(mod)[1]++;
                        }
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, int[]> entry : stats.entrySet()) {
            if (!first) {
                sb.append(" | ");
            }
            sb.append(entry.getKey()).append(": ")
              .append(entry.getValue()[0]).append(" allowed / ")
              .append(entry.getValue()[1]).append(" denied");
            first = false;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("classifyAccess(\"private\", \"SAME_CLASS\"): " 
                + classifyAccess("private", "SAME_CLASS"));
        System.out.println("classifyAccess(\"protected\", \"DIFFERENT_PACKAGE\"): " 
                + classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeByModifier(attempts));

        // Test LibraryMember validation
        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
            System.out.println("LB9 construction unexpectedly succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("new LibraryMember(\"LB9\", ...): construction rejected");
        }

        LibraryMember valid = new LibraryMember("LB94", "BR1", 0, "Priya Nair");
        System.out.println("new LibraryMember(\"LB94\", ...): construction succeeded, ID = " + valid.getMembershipId());
    }
}
