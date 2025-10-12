import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class GamePanel extends JPanel implements KeyListener {
    // Thông số cơ bản 
    private final int GAME_WIDTH = 800;
    private final int GAME_HEIGHT = 600;
    private final int PADDLE_WIDTH = 150;
    private final int PADDLE_HEIGHT = 20;
    private final int BALL_SIZE = 20;
    private final int BRICK_WIDTH = 90;
    private final int BRICK_HEIGHT = 30;

    // Game objects positions
    private int paddleX = GAME_WIDTH / 2 - PADDLE_WIDTH / 2;
    private int ballX = GAME_WIDTH / 2;
    private int ballY = GAME_HEIGHT - 100;

    // Game state
    private int score = 0;
    private int lives = 3;
    private int level = 1;
    private boolean gameRunning = true;
    
    // Bricks (hàng x cột)
    private boolean[][] bricks;
    private final int BRICK_ROWS = 4;
    private final int BRICK_COLS = 8;
    
    public GamePanel() {
        setBackground(Color.GRAY);
        initializeBricks();
    }
    
    private void initializeBricks() {
        bricks = new boolean[BRICK_ROWS][BRICK_COLS];
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                bricks[i][j] = true; // true = gạch còn, false = gạch đã phá
            }
        }
    }
    
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
        g.setColor(Color.WHITE);
        g.fillRect(paddleX, GAME_HEIGHT - 60, PADDLE_WIDTH, PADDLE_HEIGHT);
        
        // Vẽ ball
        g.setColor(Color.CYAN);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
        
        // Vẽ bricks
        drawBricks(g);
        
        // Vẽ thông tin game (score, lives, level)
        drawGameInfo(g);
    }
    
    private void drawBricks(Graphics g) {
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                if (bricks[i][j]) {
                    int x = j * BRICK_WIDTH + 20;
                    int y = i * BRICK_HEIGHT + 50;
                    
                    switch (i) {
                        case 0 -> g.setColor(Color.RED);
                        case 1 -> g.setColor(Color.YELLOW);
                        case 2 -> g.setColor(Color.BLUE);
                        case 3 -> g.setColor(Color.GREEN);
                    }
                    g.fillRect(x, y, BRICK_WIDTH - 5, BRICK_HEIGHT - 2);
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y, BRICK_WIDTH - 5, BRICK_HEIGHT - 2);
                }
            }
        }
    }
    
    private void drawGameInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Lives: " + lives, GAME_WIDTH / 2 - 40, 30);
        g.drawString("Level: " + level, GAME_WIDTH - 150, 30);
    }
    
    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", GAME_WIDTH / 2 - 150, GAME_HEIGHT / 2 - 50);

    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // Di chuyển sang trái
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (paddleX > 0) {
                paddleX -= 15;
            }
        }
        // Di chuyển sang phải
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (paddleX < GAME_WIDTH - PADDLE_WIDTH) {
                paddleX += 15;
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
    
    private void restartGame() {
        paddleX = GAME_WIDTH / 2 - PADDLE_WIDTH / 2;
        ballX = GAME_WIDTH / 2;
        ballY = GAME_HEIGHT - 100;
        score = 0;
        lives = 3;
        level = 1;
        gameRunning = true;
        initializeBricks();
        repaint();
    }
}