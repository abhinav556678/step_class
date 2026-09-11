package week5.assignment;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LibraryMemberJavaBean {

    public static class LibraryMember {
        private String membershipId;
        private String name;
        private boolean premiumMember;
        private String hashedSecurityAnswer; // Write-only field

        // Constructor 1: JavaBean-required public no-arg constructor
        public LibraryMember() {
            this(null, null);
        }

        // Constructor 2: Name-only constructor
        public LibraryMember(String name) {
            this(null, name);
        }

        // Constructor 3: Canonical constructor linking all three
        public LibraryMember(String membershipId, String name) {
            this.membershipId = membershipId;
            this.name = name;
            this.premiumMember = false;
        }

        public String getMembershipId() {
            return membershipId;
        }

        // Write-once property: second call is silently ignored
        public void setMembershipId(String id) {
            if (this.membershipId == null && id != null) {
                this.membershipId = id;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Boolean JavaBean convention: isX()
        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            this.premiumMember = premium;
        }

        // Write-only property: No matching getter exists anywhere
        public void setSecurityAnswer(String answer) {
            if (answer == null || answer.trim().isEmpty()) {
                throw new IllegalArgumentException("Security answer cannot be null or empty.");
            }
            this.hashedSecurityAnswer = hashOneWay(answer.trim());
        }

        private static String hashOneWay(String input) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
                StringBuilder hex = new StringBuilder();
                for (byte b : hash) {
                    hex.append(String.format("%02x", b));
                }
                return hex.toString();
            } catch (NoSuchAlgorithmException e) {
                return Integer.toHexString(input.hashCode());
            }
        }
    }

    public static void main(String[] args) {
        LibraryMember m1 = new LibraryMember("Priya Nair");
        System.out.println("m1.getMembershipId(): " + m1.getMembershipId());

        LibraryMember m2 = new LibraryMember("LIB-8841", "Priya Nair");
        System.out.println("m2.getMembershipId(): " + m2.getMembershipId());

        LibraryMember m3 = new LibraryMember();
        m3.setMembershipId("LIB-8841");
        m3.setMembershipId("FAKE-0000"); // Silently ignored
        System.out.println("m3 write-once test: " + m3.getMembershipId());

        m3.setSecurityAnswer("secretPetName");
        System.out.println("Security answer set successfully (write-only).");
    }
}
