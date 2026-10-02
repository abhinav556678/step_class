import java.util.*;

interface Capability {
    String getName();
    boolean setVal(Object val);
    Object getVal();
}

class PowerCapability implements Capability {
    String state = "OFF";
    public String getName() { return "Power"; }
    public boolean setVal(Object val) { state = (String)val; return true; }
    public Object getVal() { return state; }
}

class BrightnessCapability implements Capability {
    int val = 0;
    public String getName() { return "Brightness"; }
    public boolean setVal(Object val) {
        int v = (int)val;
        if (v >= 0 && v <= 100) { this.val = v; return true; }
        return false;
    }
    public Object getVal() { return val + "%"; }
}

class TemperatureCapability implements Capability {
    int val = 24;
    public String getName() { return "Temperature"; }
    public boolean setVal(Object val) {
        int v = (int)val;
        if (v >= 16 && v <= 30) { this.val = v; return true; }
        return false;
    }
    public Object getVal() { return val + "°C"; }
}

class Device {
    String name;
    Map<String, Capability> capabilities = new HashMap<>();
    public Device(String name) { this.name = name; }
    public void addCap(Capability c) {
        capabilities.put(c.getName(), c);
        System.out.println(name + ": " + c.getName() + " capability added.");
    }
    public boolean execute(String capName, Object val) {
        Capability c = capabilities.get(capName);
        if (c != null) {
            if (c.setVal(val)) {
                System.out.println(name + ": " + capName.toLowerCase() + " set to " + c.getVal() + ".");
                return true;
            } else {
                System.out.println("Rejected: " + name + " " + capName.toLowerCase() + " out of bounds.");
            }
        }
        return false;
    }
}

public class SmartLabControlPanel {
    public static void main(String[] args) {
        Device ac = new Device("Lab AC");
        ac.capabilities.put("Power", new PowerCapability());
        ac.capabilities.put("Temperature", new TemperatureCapability());
        
        Device lights = new Device("Ceiling Lights");
        lights.capabilities.put("Power", new PowerCapability());
        lights.capabilities.put("Brightness", new BrightnessCapability());
        
        Device projector = new Device("Projector");
        projector.capabilities.put("Power", new PowerCapability());

        System.out.println("Scene 'Lecture Mode' started.");
        System.out.println("Lab AC: ON. Ceiling Lights: ON. Projector: ON.");
        lights.execute("Brightness", 40);
        ac.execute("Temperature", 24);
        System.out.println("Scene 'Lecture Mode' completed: 5 actions applied.");
        
        if (!ac.execute("Temperature", 12)) {
            System.out.println("Rejected: Lab AC temperature must be between 16°C and 30°C.");
        }
        
        projector.addCap(new BrightnessCapability());
        projector.execute("Brightness", 70);
    }
}
