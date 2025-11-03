package uet.oop.UA.entites;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Brick extends GameObject {
    private static final int BRICK_WIDTH = 76;
    private static final int BRICK_HEIGHT = 38;
    private int hitPoints;
    private Random random = new Random();
    public int getHitPoints() {
        return hitPoints;
    }
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    public Brick(int x, int y, Color color, int hitPoints) {
        super(x , y, BRICK_WIDTH, BRICK_HEIGHT, color);
        this.set_Drawed_Paddle_image();
        this.hitPoints = hitPoints;
    }

    /**
     * method tạo lưới gạch 5*10 viên
     * */
    public static void createBrickGrid(List<GameObject> Brick_List, int[][] brickIntGrid) {
        Brick[][] bricks = new Brick[20][20];
        int startX = 100;
        int startY = 100;
        Color brickColor;
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 20; col++) {
                if(brickIntGrid[row][col] == 1 || brickIntGrid[row][col] == 2) {
                    switch (row % 4) {
                        case 0 -> brickColor = Color.RED; // red
                        case 1 -> brickColor = Color.BLUE;  // blue
                        case 2 -> brickColor = Color.GREEN;  // green
                        case 3 -> brickColor = Color.YELLOW; // yellow
                        default -> brickColor = Color.WHITE; // white
                    }
                    //conditions for row and col can be used to create not-rectangular patterns of bricks
                    //for example, skip bricks at (1,1), (2,2), (3,3)
                    bricks[row][col] = new Brick(startX + col * (BRICK_WIDTH + 4), startY + row * (BRICK_HEIGHT + 2), brickColor, brickIntGrid[row][col]);
                    bricks[row][col].lowHealthBrick();
                }
            }
        }
        // Add bricks to the GamePanel's objectList
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 20; col++) {
                Brick_List.add(bricks[row][col]);
            }
        }
    }

    /**
     * method đổi màu lưới gạch khi hp =1.
     * */
    public Color lowHealthBrick() {
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
        return this.getColor();
    }

    public static int[][] createBrickGridFromFiles(String gridFileName)  {
        int[][] brickGrid = new int[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                brickGrid[i][j] = 0;
            }
        }
        List<List<Character>> brickCharGrid = new ArrayList<>();
        try (BufferedReader readbrick = new BufferedReader(new FileReader(gridFileName))) {
            char brickChar;
            String line;
            //đọc file đến khi file rỗng, đưa vào list
            while ((line = readbrick.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                //mảng lưu dãy char trong 1 dòng
                List<Character> brickCharLine = new ArrayList<>();
                //đưa từng char trong dòng vào mảng
                for (int i = 0; i < line.length(); i++) {
                    brickChar =  line.charAt(i);
                    brickCharLine.add(brickChar);
                }
                //đưa mảng vào mảng lớn (grid)
                brickCharGrid.add(brickCharLine);
            }
            //đưa kí tư trong list<list<char>> vào int[20][20]
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    try {
                        brickGrid[i][j] = brickCharGrid.get(i).get(j) - '0';
                    } catch (IndexOutOfBoundsException e) {
                        brickGrid[i][j] = 0;
                    }
                }
            }
            //debug: print to check grid
            System.out.println("input succeed");
            System.out.println("filename" + gridFileName);
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    System.out.print(brickGrid[i][j] + " ");
                }
                System.out.println();
            }
            return brickGrid;
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("input exception");
            return brickGrid;
        }
    }


    /**
     * Method tạo power-up ngẫu nhiên khi brick bị phá hủy.
     * */
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
