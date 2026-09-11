package week5.problems;

public class FieldVisibilityValidator {

    public static class PatientRecord {
        private String patientId;
        protected String wardCode;
        private double vitalsScore;
        public String facilityName;

        // No usable no-argument constructor
        public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
            if (patientId == null) {
                throw new IllegalArgumentException("Patient ID cannot be null.");
            }
            String trimmedId = patientId.trim();
            if (trimmedId.length() < 4) {
                throw new IllegalArgumentException("Invalid patientId: '" + patientId + "'. Must be non-blank and at least 4 characters.");
            }

            this.patientId = trimmedId;
            this.wardCode = wardCode;
            this.vitalsScore = vitalsScore;
            this.facilityName = facilityName;
        }

        public String getPatientId() {
            return patientId;
        }

        public double getVitalsScore() {
            return vitalsScore;
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
                // In the three basic contexts, protected behaves like default
                return ("SAME_CLASS".equals(ctx) || "SAME_PACKAGE".equals(ctx)) ? "ALLOWED" : "DENIED";
            default:
                return "DENIED";
        }
    }

    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String result = classifyAccess(attempt[0], attempt[1]);
                    if ("ALLOWED".equals(result)) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    public static void main(String[] args) {
        System.out.println("classifyAccess(\"private\", \"SAME_CLASS\"): " 
                + classifyAccess("private", "SAME_CLASS"));
        System.out.println("classifyAccess(\"default\", \"DIFFERENT_PACKAGE\"): " 
                + classifyAccess("default", "DIFFERENT_PACKAGE"));

        String[][] batch = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println("summarizeBatch: " + summarizeBatch(batch));

        // Test patient record validation
        try {
            new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
            System.out.println("MT9 construction unexpectedly succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("new PatientRecord(\"MT9\", ...): construction rejected");
        }

        PatientRecord valid = new PatientRecord("MT94", "W3", 98.2, "MediTrack Central");
        System.out.println("new PatientRecord(\"MT94\", ...): construction succeeded, ID = " + valid.getPatientId());
    }
}
