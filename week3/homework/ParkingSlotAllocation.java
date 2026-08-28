package week3.homework;

public class ParkingSlotAllocation {

    public static class ParkingSlot {
        private String slotNo;
        private int capacity;
        private int occupiedCount;

        public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        public String getSlotNo() {
            return slotNo;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getOccupiedCount() {
            return occupiedCount;
        }

        public boolean allot(String vehicleNo) {
            if (occupiedCount < capacity) {
                occupiedCount++;
                return true;
            }
            return false;
        }
    }

    /*
     * NOTE ON OBJECT REFERENCES:
     * In Java, passing an array of objects passes references to the underlying objects on the heap.
     * It does not create copies of the ParkingSlot instances. Thus, slot.allot(vehicleNo) mutates
     * the actual slot object stored in the array.
     */
    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) {
            return null;
        }
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.getOccupiedCount() < slot.getCapacity()) {
                return slot;
            }
        }
        return null;
    }

    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);
        if (slot != null) {
            slot.allot(vehicleNo);
            System.out.printf("%s allotted to slot %s%n", vehicleNo, slot.getSlotNo());
        } else {
            System.out.printf("No slots available for %s%n", vehicleNo);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 (Available Slot) ---");
        ParkingSlot[] slots1 = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots1, "TN09AB1234");

        System.out.println("\n--- Test 2 (All Full) ---");
        ParkingSlot[] slots2 = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots2, "TN09AB1234");
    }
}
