class Fan {
    int speed;
    String colour;
    boolean power;

    public Fan() {
        this.speed = 1;
        this.colour = "Black";
        this.power = false;
    }

    public Fan(String colour) {
        this.speed = 1;
        this.colour = colour;
        this.power = false;
    }

    void turnOn() { this.power = true; }
    void turnOff() { this.power = false; }

    void displayAll() {
        if(this.power) {
            System.out.printf("Speed: %d; Colour: %s%n", speed, colour);
        } else {
            System.out.println("Fan is off!");
        }
    }
}

public class carodc3_31OP_02L2 {
    public static void main(String[] args) {
        Fan fan1 = new Fan();
        Fan fan2 = new Fan("Red");
        fan2.turnOn();
        fan1.displayAll();
        fan2.displayAll();
    }
}