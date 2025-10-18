package classes.lab1.Movement;

public class TeleportMovement implements MoveMovement {
    @Override
    public String move(String from, String to) {
        return "Герой использует телепортацию из " + from + " в " + to;
    }
}