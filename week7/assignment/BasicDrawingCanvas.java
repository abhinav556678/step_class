abstract class Shape {
    private static int counter = 0;
    private final String shapeId;

    public Shape() {
        counter++;
        this.shapeId = "SHAPE-" + counter;
    }

    public abstract double calculateArea();

    void scale(double factor) {
        scale(factor, factor);
    }

    void scale(double xFactor, double yFactor) {
        // Expected to be overridden by subclasses if needed
    }

    String getShapeId() {
        return shapeId;
    }
}

class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        // Output as per example -> 78.54 for r=5
        return Math.round(Math.PI * radius * radius * 100.0) / 100.0;
    }

    @Override
    void scale(double factor) {
        this.radius *= factor;
    }

    @Override
    void scale(double xFactor, double yFactor) {
        scale(xFactor);
        scale(yFactor);
    }
}

class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    void scale(double factor) {
        this.side *= factor;
    }

    @Override
    void scale(double xFactor, double yFactor) {
        scale(xFactor);
        scale(yFactor);
    }
}

public class BasicDrawingCanvas {
    static void printArea(Shape s) {
        System.out.println(s.calculateArea());
    }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        System.out.println(c.calculateArea());

        SquareShape sq = new SquareShape(4.0);
        System.out.println(sq.calculateArea());

        sq.scale(2.0);
        System.out.println(sq.calculateArea());

        printArea(c);
    }
}
