package uet.oop.UA.entites;

public class Brick extends GameObject {
    int hitPoints;
    String type;
    public Brick(int x, int y, String color) {
        super(x , y, Brick_WIDTH, Brick_HEIGHT, "red");
        this.hitPoints = 1;
    }
}
