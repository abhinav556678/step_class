package week5.problems;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PatientProfileJavaBean {

    public static class PatientProfile {
        private String patientId;
        private String name;
        private boolean discharged;
        private String hashedLockerPin; // Write-only field

        // Constructor 1: JavaBean-required public no-argument constructor
        public PatientProfile() {
            this(null, null);
        }

        // Constructor 2: Name-only constructor
        public PatientProfile(String name) {
            this(null, name);
        }

        // Constructor 3: Canonical constructor linking all three
        public PatientProfile(String patientId, String name) {
            this.patientId = patientId;
            this.name = name;
            this.discharged = false;
        }

        public String getPatientId() {
            return patientId;
        }

        // Write-once property: only takes effect once ever
        public void setPatientId(String id) {
            if (this.patientId == null && id != null) {
                this.patientId = id;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isDischarged() {
            return discharged;
        }

        public void setDischarged(boolean discharged) {
            this.discharged = discharged;
        }

        // Write-only property: No matching getter exists anywhere
        public void setLockerPin(String pin) {
            if (pin == null || !pin.matches("^\\d{4,6}$")) {
                throw new IllegalArgumentException("Locker PIN must be a 4 to 6 digit numeric string.");
            }
            this.hashedLockerPin = hashOneWay(pin);
        }

        private static String hashOneWay(String input) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    hexString.append(String.format("%02x", b));
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                // Fallback deterministic one-way transformation
                return Integer.toHexString(input.hashCode());
            }
        }
    }

    public static void main(String[] args) {
        PatientProfile p1 = new PatientProfile("Arjun Iyer");
        System.out.println("new PatientProfile(\"Arjun Iyer\").getPatientId(): " + p1.getPatientId());

        PatientProfile p2 = new PatientProfile("MT2026-0142", "Arjun Iyer");
        System.out.println("new PatientProfile(\"MT2026-0142\", \"Arjun Iyer\").getPatientId(): " + p2.getPatientId());

        PatientProfile p = new PatientProfile();
        p.setPatientId("MT2026-0142");
        p.setPatientId("HACKED-0000"); // Second write is silently ignored
        System.out.println("Write-once test: " + p.getPatientId());

        p.setLockerPin("1234");
        System.out.println("Locker PIN set successfully (write-only).");
    }
}
