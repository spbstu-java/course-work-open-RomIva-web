package classes.lab1.Hero;

import classes.lab1.Movement.MoveMovement;
import classes.lab1.Movement.WalkMovement;

public class Hero {
    private MoveMovement movement;
    public Hero() {
        this.movement = new WalkMovement();
    }
    public void setMovement(MoveMovement movement) {
        this.movement = movement;
    }
    public String move(String from, String to) {
        return movement.move(from, to);
    }
}