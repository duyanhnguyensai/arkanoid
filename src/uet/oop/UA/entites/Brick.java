package uet.oop.UA.entites;

import java.awt.*;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Brick extends GameObject {
    private static final int BRICK_WIDTH = 80;
    private static final int BRICK_HEIGHT = 40;
    private int hitPoints;
    private Random random = new Random();
    public int getHitPoints() {
        return hitPoints;
    }
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    public Brick(int x, int y, Color color) {
        super(x , y, BRICK_WIDTH, BRICK_HEIGHT, color);
        this.set_Drawed_Paddle_image();
        this.hitPoints = 2;
    }

    public static void createBrickGrid(List<GameObject> Brick_List) {
        Brick[][] bricks = new Brick[5][10];
        int startX = 100;
        int startY = 100;
        Color brickColor;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                switch (row % 4) {
                    case 0 -> brickColor = Color.RED; // red
                    case 1 -> brickColor = Color.BLUE;  // blue
                    case 2 -> brickColor = Color.GREEN;  // green
                    case 3 -> brickColor = Color.YELLOW; // yellow
                    default -> brickColor = Color.WHITE; // white
                }
                //conditions for row and col can be used to create not-rectangular patterns of bricks
                //for example, skip bricks at (1,1), (2,2), (3,3)
                bricks[row][col] = new Brick(startX + col * (BRICK_WIDTH + 1), startY + row * (BRICK_HEIGHT + 1), brickColor);
            }
        }
        // Add bricks to the GamePanel's objectList
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                Brick_List.add(bricks[row][col]);
            }
        }
    }
    public void low_health_brick() {
        if(this.getHitPoints() == 1) {
            Color c = this.getColor();
            if (c.equals(Color.RED)) {
                this.setNewColor(135, 64, 64);
                this.set_Drawed_Paddle_image();
            } else if (c.equals(Color.YELLOW)) {
                this.setNewColor(140, 140, 65);
                this.set_Drawed_Paddle_image();
            } else if (c.equals(Color.GREEN)) {
                this.setNewColor(38, 80, 38);
                this.set_Drawed_Paddle_image();
            } else if (c.equals(Color.BLUE)) {
                this.setNewColor(30, 30, 110);
                this.set_Drawed_Paddle_image();
            }
        }
    }

    // THÊM MỚI: Tạo power-up ngẫu nhiên khi brick bị phá hủy
    public PowerUp createRandomPowerUp() {
        Random rand = new Random();
        if (rand.nextDouble() < 0.3) { // 30% cơ hội tạo power-up
            int powerUpType = rand.nextInt(3); // THAY ĐỔI: từ 2 thành 3
            switch (powerUpType) {
                case 0:
                    return new FastBallPowerUp(this.getCentralX() - 10, this.getCentralY());
                case 1:
                    return new ExpandPaddlePowerUp(this.getCentralX() - 10, this.getCentralY());
                case 2: // THÊM MỚI: Triple Ball PowerUp
                    return new TripleBallPowerUp(this.getCentralX() - 10, this.getCentralY());
                default:
                    return null;
            }
        }
        return null;
    }
}
