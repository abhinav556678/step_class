import java.util.*;

class Student {
    String name;
    String type;
    int credits;
    int limit;
    
    public Student(String name, String type, int credits, int limit) {
        this.name = name;
        this.type = type;
        this.credits = credits;
        this.limit = limit;
    }
}

class Elective {
    String name;
    int credits;
    int capacity;
    List<Student> enrolled = new ArrayList<>();
    Queue<Student> waitlist = new LinkedList<>();

    public Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    public void enroll(Student s) {
        if (s.credits + credits > s.limit) {
            System.out.println("Enrollment failed: " + s.name + " would exceed the " + s.type + " credit limit (" + (s.credits+credits) + "/" + s.limit + ").");
            return;
        }
        if (enrolled.size() < capacity) {
            enrolled.add(s);
            s.credits += credits;
            System.out.println(s.name + " enrolled in " + name + " (credits: " + s.credits + "/" + s.limit + ").");
            if (enrolled.size() == capacity) System.out.println(name + " is full.");
        } else {
            waitlist.add(s);
            System.out.println(s.name + " added to waitlist (position " + waitlist.size() + ").");
        }
    }

    public void drop(Student s) {
        if (enrolled.remove(s)) {
            s.credits -= credits;
            System.out.println(s.name + " dropped " + name + " (credits: " + s.credits + "/" + s.limit + ").");
            promote();
        }
    }

    private void promote() {
        while (!waitlist.isEmpty() && enrolled.size() < capacity) {
            Student next = waitlist.poll();
            if (next.credits + credits <= next.limit) {
                enrolled.add(next);
                next.credits += credits;
                System.out.println(next.name + " promoted from waitlist and enrolled in " + name + " (credits: " + next.credits + "/" + next.limit + ").");
            }
        }
    }
}

public class ElectiveSeatRush {
    public static void main(String[] args) {
        Elective cloud = new Elective("Cloud Computing", 4, 2);
        
        Student asha = new Student("Asha", "Regular", 20, 24);
        Student ravi = new Student("Ravi", "Honors", 22, 28);
        Student neha = new Student("Neha", "Exchange", 12, 20);
        Student kiran = new Student("Kiran", "Regular", 22, 24);
        
        cloud.enroll(asha);
        cloud.enroll(ravi);
        cloud.enroll(neha);
        cloud.enroll(kiran);
        
        cloud.drop(asha);
    }
}
