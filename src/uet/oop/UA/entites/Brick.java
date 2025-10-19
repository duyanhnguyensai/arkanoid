package uet.oop.UA.entites;

public class Brick extends GameObject {
    private static final int BRICK_WIDTH = 100;
    private static final int BRICK_HEIGHT = 50
    private int hitPoints;
    public Brick(int x, int y, int colorcode) {
        super(x , y, BRICK_WIDTH, BRICK_HEIGHT, colorcode);
        this.set_Drawed_Paddle_image();
        this.hitPoints = 2;
    }
}
