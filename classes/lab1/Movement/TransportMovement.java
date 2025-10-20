package classes.lab1.Movement;

public class TransportMovement implements MoveMovement {
    @Override
    public String move(String from, String to) {
        return "Герой использует средство передвижения для перемещения из " + from + " в " + to;
    }
}