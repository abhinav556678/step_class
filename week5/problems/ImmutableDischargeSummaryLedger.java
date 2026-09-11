package week5.problems;

import java.util.Arrays;

public class ImmutableDischargeSummaryLedger {

    // One-time shared state initialized in a static block
    private static int totalBatchesProcessed;

    static {
        totalBatchesProcessed = 0;
    }

    public static class DischargeSummary {
        private final String patientId;
        private final String[] medicationCodes;

        public DischargeSummary(String patientId, String[] medicationCodes) {
            if (patientId == null || patientId.trim().isEmpty()) {
                throw new IllegalArgumentException("Patient ID cannot be null or empty.");
            }
            if (medicationCodes == null) {
                throw new IllegalArgumentException("Medication codes cannot be null.");
            }

            // Validate every code must match 'MED-' followed by exactly one uppercase letter
            for (String code : medicationCodes) {
                if (code == null || !code.matches("^MED-[A-Z]$")) {
                    throw new IllegalArgumentException("Invalid medication code format: '" + code + "'. Must match MED-[A-Z].");
                }
            }

            this.patientId = patientId.trim();
            // Defensive copying on the way in
            this.medicationCodes = medicationCodes.clone();
        }

        public String getPatientId() {
            return patientId;
        }

        // Defensive copying on the way out
        public String[] getMedicationCodes() {
            return medicationCodes.clone();
        }

        // With-style method for immutability
        public DischargeSummary withCorrectedMedication(int index, String newCode) {
            if (index < 0 || index >= medicationCodes.length) {
                throw new IndexOutOfBoundsException("Index " + index + " out of bounds for medication codes.");
            }
            if (newCode == null || !newCode.matches("^MED-[A-Z]$")) {
                throw new IllegalArgumentException("Invalid replacement medication code: '" + newCode + "'.");
            }

            String[] updatedCodes = medicationCodes.clone();
            updatedCodes[index] = newCode;
            return new DischargeSummary(this.patientId, updatedCodes);
        }
    }

    // Critical-care variant subclass
    public static final class CriticalCareDischargeSummary extends DischargeSummary {
        private final int icuDays;

        public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
            super(patientId, medicationCodes);
            if (icuDays < 0) {
                throw new IllegalArgumentException("ICU days cannot be negative.");
            }
            this.icuDays = icuDays;
        }

        public int getIcuDays() {
            return icuDays;
        }
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        totalBatchesProcessed++;

        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries != null) {
            for (DischargeSummary summary : summaries) {
                if (summary == null) {
                    nullSkipped++;
                    continue;
                }

                processed++;

                // instanceof-based settlement dispatch
                if (summary instanceof CriticalCareDischargeSummary) {
                    criticalCare++;
                } else {
                    routine++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + criticalCare + " critical-care | " + routine + " routine";
    }

    public static void main(String[] args) {
        // Test validation rejection
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
            System.out.println("Construction unexpectedly succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("new DischargeSummary(..., bad): construction rejected");
        }

        // Test immutability & defensive copying
        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println("Immutability test: d.getMedicationCodes()[0] = " + d.getMedicationCodes()[0]);

        // Test with-style correction
        DischargeSummary corrected = d.withCorrectedMedication(0, "MED-Z");
        System.out.println("Original code: " + d.getMedicationCodes()[0]);
        System.out.println("Corrected code: " + corrected.getMedicationCodes()[0]);

        // Test nightly batch reconciliation
        DischargeSummary[] batch = {
            new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[]{"MED-Y"})
        };

        System.out.println("processNightlyBatch: " + processNightlyBatch(batch));
    }
}
