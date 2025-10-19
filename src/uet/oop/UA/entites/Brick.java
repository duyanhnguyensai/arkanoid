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
    public static void createBrickGrid(List<GameObject> Brick_List) {
        Brick[][] bricks = new Brick[4][8];
        int startX = 100;
        int startY = 100;
        int colorcode;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 8; col++) {
                switch (col % 4) {
                    case 0 -> colorcode = 11; // red
                    case 1 -> colorcode = 2;  // blue
                    case 2 -> colorcode = 6;  // green
                    case 3 -> colorcode = 13; // yellow
                    default -> colorcode = 12; // white
                }
                //conditions for row and col can be used to create not-rectangular patterns of bricks
                //for example, skip bricks at (1,1), (2,2), (3,3)
                bricks[row][col] = new Brick(startX + col * BRICK_WIDTH, startY + row * BRICK_HEIGHT, colorcode);
            }
        }
        // Add bricks to the GamePanel's objectList
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 8; col++) {
                Brick_List.add(bricks[row][col]);
            }
        }
    }
}
