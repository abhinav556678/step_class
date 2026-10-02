import java.util.*;

interface TrackScoringRule {
    double calculate(int idea, int exec, int pres);
    String getName();
}

class InnovationTrack implements TrackScoringRule {
    public double calculate(int idea, int exec, int pres) {
        return idea * 0.5 + exec * 0.3 + pres * 0.2;
    }
    public String getName() { return "Innovation track"; }
}

class OpenTrack implements TrackScoringRule {
    public double calculate(int idea, int exec, int pres) {
        return (idea + exec + pres) / 3.0;
    }
    public String getName() { return "Open track"; }
}

class Team {
    String name;
    int memberCount;
    TrackScoringRule track;
    public Team(String name, int memberCount, TrackScoringRule track) {
        this.name = name;
        this.memberCount = memberCount;
        this.track = track;
    }
}

class Project {
    String name;
    Team team;
    public Project(String name, Team team) {
        this.name = name;
        this.team = team;
    }
}

class Hackathon {
    boolean resultsPublished = false;
    Map<String, Project> projects = new HashMap<>();
    Map<String, Double> scores = new HashMap<>();

    public void register(Team t) {
        if (t.memberCount >= 2 && t.memberCount <= 4) {
            System.out.println("Team " + t.name + " registered (" + t.memberCount + " members, " + t.track.getName() + ").");
        } else {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
        }
    }

    public void submitProject(Project p) {
        projects.put(p.name, p);
        System.out.println("Project '" + p.name + "' submitted by " + p.team.name + ".");
    }

    public void scoreProject(String pName, int idea, int exec, int pres) {
        if (resultsPublished) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }
        Project p = projects.get(pName);
        double finalScore = p.team.track.calculate(idea, exec, pres);
        scores.put(pName, finalScore);
        System.out.printf("Score recorded for '%s'. Final score: %.2f.\n", pName, finalScore);
    }

    public void publishResults() {
        resultsPublished = true;
        System.out.println("Results published.");
    }
}

public class CodeSprintJudging {
    public static void main(String[] args) {
        Hackathon h = new Hackathon();
        Team t1 = new Team("ByteBusters", 3, new InnovationTrack());
        Team t2 = new Team("SoloCoder", 1, new OpenTrack());
        
        h.register(t1);
        h.register(t2);
        
        Project p1 = new Project("SmartAttend", t1);
        h.submitProject(p1);
        
        h.scoreProject("SmartAttend", 8, 7, 9);
        h.publishResults();
        
        h.scoreProject("SmartAttend", 10, 7, 9);
    }
}
