package uet.oop.UA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon; // load ảnh
import java.io.File; // tạo file

class GamePanel extends JPanel implements KeyListener {
    // Thông số cơ bản
    private final int GAME_WIDTH = 800;
    private final int GAME_HEIGHT = 600;
    private final int PADDLE_WIDTH = 150;
    private final int PADDLE_HEIGHT = 40;
    private final int BRICK_WIDTH = 90;
    private final int BRICK_HEIGHT = 30;

    // vị trí paddle
    private int paddleX = GAME_WIDTH / 2 - PADDLE_WIDTH / 2;

    // lưu ảnh paddle
    private Image paddleImage;


    // Game state
    private int score = 0;
    private int lives = 3;
    private int level = 1;
    private boolean gameRunning = true;

    // Khởi tạo và vẽ Bricks (hàng x cột)
    private boolean[][] bricks;
    private final int BRICK_ROWS = 4;
    private final int BRICK_COLS = 8;

    //Khởi tạo Game
    public GamePanel() {
        setBackground(Color.BLACK); //màu nền
        initializeBricks(); //vẽ Bricks
        loadPaddleImage(); //load ảnh paddle
        addKeyListener(this); //nhận phím
    }

    //Khởi tạo Bricks
    private void initializeBricks() {
        bricks = new boolean[BRICK_ROWS][BRICK_COLS];
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                bricks[i][j] = true; // true = gạch còn, false = gạch đã phá
            }
        }
    }

    //Load ảnh paddle từ resources
    private void loadPaddleImage() {
        java.net.URL resource = getClass().getClassLoader().getResource("paddleImage/paddle.png");
        if (resource != null) {
            paddleImage = new ImageIcon(resource).getImage();
            System.out.println("Đã load ảnh paddle thành công");
            return;
        }

        File imageFile = new File("res/paddleImage/paddle.png");
        if (imageFile.exists()) {
            paddleImage = new ImageIcon(imageFile.getPath()).getImage();
            System.out.println("Đã load ảnh paddle thành công");
            return;
        }

        System.out.println("Không tìm thấy ảnh paddle");
    }

    //Vẽ Game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameRunning) {
            drawGame(g);
        } else {
            drawGameOver(g);
        }
    }

    private void drawGame(Graphics g) {
        // Vẽ paddle
        drawPaddle(g);

        // Vẽ Bricks
        drawBricks(g);

        // Vẽ thông tin game (score, lives, level)
        drawGameInfo(g);
    }

    //Vẽ Paddle từ ảnh
    private void drawPaddle(Graphics g) {
        int paddleY = GAME_HEIGHT - 60;

        if (paddleImage != null) {
            // Vẽ ảnh paddle
            g.drawImage(paddleImage, paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT, null);
        } else {
            // Vẽ paddle đơn giản nếu không có ảnh
            g.setColor(Color.WHITE);
            g.fillRect(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
        }
    }

    //Vẽ Bricks
    private void drawBricks(Graphics g) {
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                if (bricks[i][j]) {
                    int x = j * BRICK_WIDTH + 20;
                    int y = i * BRICK_HEIGHT + 50;

                    //Màu Bricks
                    switch (i) {
                        case 0 -> g.setColor(Color.RED);
                        case 1 -> g.setColor(Color.YELLOW);
                        case 2 -> g.setColor(Color.BLUE);
                        case 3 -> g.setColor(Color.GREEN);
                    }
                    g.fillRect(x, y, BRICK_WIDTH - 5, BRICK_HEIGHT - 2);
                    g.setColor(Color.WHITE); //màu viền Bricks
                    g.drawRect(x, y, BRICK_WIDTH - 5, BRICK_HEIGHT - 2);
                }
            }
        }
    }

    //Vẽ thông tin game (score, lives, level)
    private void drawGameInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Lives: " + lives, GAME_WIDTH / 2 - 40, 30);
        g.drawString("Level: " + level, GAME_WIDTH - 150, 30);
    }

    //Vẽ Game Over
    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200)); //màu nền Game Over
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(Color.WHITE); //màu text Game Over
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", GAME_WIDTH / 2 - 150, GAME_HEIGHT / 2 - 50); //text Game Over

    }
    
    //Nhận phím
    @Override
    public void keyPressed(KeyEvent e) {
        // Di chuyển sang trái
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (paddleX > 0) {
                paddleX -= 15;
                repaint(); // Vẽ lại paddle sau khi di chuyển
            }
        }
        // Di chuyển sang phải
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (paddleX < GAME_WIDTH - PADDLE_WIDTH) {
                paddleX += 15;
                repaint(); // Vẽ lại paddle sau khi di chuyển
            }
        }
        // Restart game
        if (e.getKeyCode() == KeyEvent.VK_R && !gameRunning) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    //Khởi tạo lại Game sau khi Game Over
    private void restartGame() {
        paddleX = GAME_WIDTH / 2 - PADDLE_WIDTH / 2;
        score = 0;
        lives = 3;
        level = 1;
        gameRunning = true;
        initializeBricks();
        repaint();
    }
}