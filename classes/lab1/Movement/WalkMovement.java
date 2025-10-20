package classes.lab1.Movement;

public class WalkMovement implements MoveMovement {
    @Override
    public String move(String from, String to) {
        return "Герой ничего не использует и идёт пешком из " + from + " в " + to;
    }
}