package week4.practice;

import java.util.Arrays;

public class BusRouteRankingEngine {

    public static class BusRoute implements Comparable<BusRoute> {
        private String routeCode;
        private String routeName;
        private int priority;

        // Constructor 1: Resolves field/parameter naming clashes with 'this'
        public BusRoute(String routeCode, String routeName, int priority) {
            if (routeCode == null || routeCode.trim().isEmpty()) {
                throw new IllegalArgumentException("Route code must not be null or blank.");
            }
            if (routeName == null || routeName.trim().isEmpty()) {
                throw new IllegalArgumentException("Route name must not be null or blank.");
            }
            this.routeCode = routeCode;
            this.routeName = routeName;
            this.priority = priority;
        }

        // Constructor 2: Chains to 3-argument constructor with default priority 1
        public BusRoute(String routeCode, String routeName) {
            this(routeCode, routeName, 1);
        }

        @Override
        public int compareTo(BusRoute other) {
            if (other == null) {
                return -1;
            }

            // 1. Priority descending (higher priority comes first)
            int priorityComparison = Integer.compare(other.priority, this.priority);
            if (priorityComparison != 0) {
                return priorityComparison;
            }

            // 2. Route code alphabetical, case-insensitive (without altering stored code)
            int codeComparison = this.routeCode.compareToIgnoreCase(other.routeCode);
            if (codeComparison != 0) {
                return codeComparison;
            }

            // 3. Route name alphabetical
            return this.routeName.compareTo(other.routeName);
        }

        public String getRouteCode() {
            return routeCode;
        }

        public String getRouteName() {
            return routeName;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public String toString() {
            return "\"" + routeCode + "\"";
        }
    }

    // Stable O(n^2) sort implementation (Insertion Sort) to preserve relative arrival order on ties
    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) return null;

        BusRoute[] sorted = routes.clone();
        for (int i = 1; i < sorted.length; i++) {
            BusRoute current = sorted[i];
            int j = i - 1;
            // Shift elements that compare greater than current (compareTo > 0)
            while (j >= 0 && sorted[j].compareTo(current) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = current;
        }
        return sorted;
    }

    public static void main(String[] args) {
        BusRoute[] sampleRoutes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] ranked = rankRoutes(sampleRoutes);
        System.out.println("Ranked routes: " + Arrays.toString(ranked));
    }
}
