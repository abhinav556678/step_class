package week1.problems;

public class BmiCalculatorTeam {

    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        if (heights == null || weights == null || heights.length != weights.length) {
            System.out.println("Invalid height and weight datasets");
            return;
        }

        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-10s | %-12s | %-12s | %-8s | %-14s%n",
                "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < heights.length; i++) {
            double h = heights[i];
            double w = weights[i];
            double bmi = (h > 0) ? (w / (h * h)) : 0.0;
            String status = getBmiStatus(bmi);

            System.out.printf("Person %-3d | %-12.2f | %-12.1f | %-8.2f | %-14s%n",
                    i + 1, h, w, bmi, status);
        }
        System.out.println("----------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        // Sample demonstration with 10 people
        double[] heights = {1.75, 1.60, 1.82, 1.55, 1.68, 1.72, 1.90, 1.58, 1.77, 1.65};
        double[] weights = {70.0, 90.0, 65.0, 42.0, 80.0, 68.0, 110.0, 52.0, 76.0, 95.0};

        System.out.println("--- Corporate Wellness Program BMI Report ---");
        printWellnessReport(heights, weights);
    }
}
