package uet.oop.UA;

import uet.oop.UA.entites.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon; // load ảnh
import java.io.File; // tạo file
import java.util.List;


/**
 * Lớp GamePanel để vẽ game
 * - bao gồm các thuộc tính như vị trí paddle, ảnh paddle, di chuyển paddle
 * - thiết kế màn chơi (hiển thị brick, paddle, score, lives, level, game over)
 */
class GamePanel extends JPanel implements KeyListener {
    //Danh sách các vật thể trong một panel
    private List<GameObject> objectList;

    //thêm/bỏ vật thể ra khỏi danh sách
    public void addGameObject(GameObject gameObject){
        this.objectList.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject){
        this.objectList.remove(gameObject);

    //hàm vẽ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gameRunning) {
            for (GameObject obj : objectList) {  //vẽ gameObject trong list
                if (obj.getImage() != null) {
                    g.drawImage(obj.getImage(), obj.getX(), obj.getY(), this);
                }
            }
            drawGameInfo(g);  // vẽ chữ
        }
        else {
            drawGameOver(g); //vẽ màn gameover
        }
    }
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


    //drawgameInfo và drawgameOver được tôi tích hợp vào paintComponent mới của tôi
    //bên trên là phần tôi bổ sung . Bn dưới là phần cũ của ông, tôi đã xóa 1 phần ve obj
    private final int GAME_WIDTH = 800;
    private final int GAME_HEIGHT = 600;
    private final int PADDLE_WIDTH = 150;
    private final int PADDLE_HEIGHT = 40;
    private final int BRICK_WIDTH = 90;
    private final int BRICK_HEIGHT = 30;

    // Game state
    private int score = 0;
    private int lives = 3;
    private int level = 1;
    private boolean gameRunning = true;
    /*
    // Khởi tạo và vẽ Bricks (hàng x cột)
    //private boolean[][] bricks;
    private final int BRICK_ROWS = 4;
    private final int BRICK_COLS = 8;
    */
    //Khởi tạo Game
    public GamePanel(List<GameObject> objects) {
        this.objectList = objects;
        setBackground(Color.BLACK); //màu nền
        this.setLayout(new BorderLayout());
        //initializeBricks(); //vẽ Bricks
        //loadPaddleImage(); //load ảnh paddle
        addKeyListener(this); //nhận phím
    }

    //Vẽ thông tin game (score, lives, level)
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
        repaint();
    }
}