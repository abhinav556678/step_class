package week1.assignment;

public class WarehouseInventoryBalancer {

    public static void analyzeInventory(int[] sectionA, int[] sectionB) {
        if (sectionA == null || sectionB == null || sectionA.length == 0 || sectionB.length == 0) {
            System.out.println("Invalid inventory data");
            return;
        }

        int totalA = 0;
        int totalB = 0;

        int highestQuantity = Integer.MIN_VALUE;
        String highestSection = "";
        int highestItemIndex = -1; // 1-based index

        for (int i = 0; i < sectionA.length; i++) {
            totalA += sectionA[i];
            if (sectionA[i] > highestQuantity) {
                highestQuantity = sectionA[i];
                highestSection = "Section A";
                highestItemIndex = i + 1;
            }
        }

        for (int j = 0; j < sectionB.length; j++) {
            totalB += sectionB[j];
            if (sectionB[j] > highestQuantity) {
                highestQuantity = sectionB[j];
                highestSection = "Section B";
                highestItemIndex = j + 1;
            }
        }

        String status = (totalA == totalB) ? "Balanced" : "Not Balanced";

        System.out.printf("Section A Total: %d | Section B Total: %d | Status: %s | Highest Quantity: %d (%s, Item %d)%n",
                totalA, totalB, status, highestQuantity, highestSection, highestItemIndex);
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        int[] sectionA = {20, 15, 30};
        int[] sectionB = {25, 10, 30};
        analyzeInventory(sectionA, sectionB);

        System.out.println("\n--- Test 2 (Unbalanced) ---");
        int[] secA2 = {10, 20, 30};
        int[] secB2 = {5, 10, 15};
        analyzeInventory(secA2, secB2);
    }
}
