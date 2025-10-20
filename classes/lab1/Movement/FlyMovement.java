package classes.lab1.Movement;

public class FlyMovement implements MoveMovement {
    @Override
    public String move(String from, String to) {
        return "Герой использует полёт и летит из " + from + " в " + to;
    }
}