package uet.oop.UA;

import uet.oop.UA.entites.GameObject;
import uet.oop.UA.entites.Paddle;

// load ảnh
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File; // tạo file
import java.util.List;

/**
 * Lớp GamePanel để vẽ game
 * - bao gồm các thuộc tính như vị trí paddle, ảnh paddle, di chuyển paddle
 * - thiết kế màn chơi (hiển thị brick, paddle, score, lives, level, game over)
 */
public class GamePanel extends JPanel implements KeyListener {
    //Danh sách các vật thể trong một panel
    private List<GameObject> objectList;

    //thêm/bỏ vật thể ra khỏi danh sách
    public void addGameObject(GameObject gameObject){
        this.objectList.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject) {
        this.objectList.remove(gameObject);
    }
    //hàm vẽ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.drawRect(0, 0, 1000, 800);
        for (GameObject obj : objectList) {  //vẽ gameObject trong list
            if (obj != null && obj.getImage() != null) {
                g.drawImage(obj.getImage(), obj.getX(), obj.getY(), this);
            }
        }
        drawGameInfo(g);  //vẽ thông tin game
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
        g.setColor(new Color(0, 0, 0, 200)); 
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(Color.WHITE); 
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", GAME_WIDTH / 2 - 150, GAME_HEIGHT / 2 - 50); 

    }


    //drawgameInfo và drawgameOver được tôi tích hợp vào paintComponent mới của tôi
    //bên trên là phần tôi bổ sung . Bn dưới là phần cũ của ông, tôi đã xóa 1 phần ve obj
    private final int GAME_WIDTH = 1000;
    private final int GAME_HEIGHT = 800;
    private final int PADDLE_WIDTH = 180;
    private final int PADDLE_HEIGHT = 30;
;

    // Game state
    //vì gamestate không cần gắn vào class nào, không có method riêng, và cần thay đổi trong nhiểu trường hợp
    //nên biến nó thành static và public
    public static int score = 500;
    public static int lives = 3;
    public static int level = 1;

   
    //Khởi tạo Game
    public GamePanel(List<GameObject> objects) {
        this.objectList = objects;
        setBackground(Color.BLACK); 
        this.setLayout(new BorderLayout());
        //initializeBricks(); //vẽ Bricks
        //loadPaddleImage(); //load ảnh paddle
        
        // Thêm paddle vào danh sách vật thể
        Paddle paddle = new Paddle(
            GAME_WIDTH / 2 - PADDLE_WIDTH / 2, 
            GAME_HEIGHT - PADDLE_HEIGHT ,
            PADDLE_WIDTH,
            PADDLE_HEIGHT
        );
        this.objectList.add(paddle);
        
        addKeyListener(this); 
    }

    //Vẽ thông tin game (score, lives, level)
    //Nhận phím
    @Override
    public void keyPressed(KeyEvent e) {
        // Lấy đối tượng paddle
        Paddle paddle = (Paddle)objectList.get(0);

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            GameManager.gameStarted = true;
        }
            
        // Di chuyển sang trái
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (paddle.getX() > 0) {
                paddle.setX(paddle.getX() - 15);
                repaint();
            }
        }
        // Di chuyển sang phải
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (paddle.getX() < GAME_WIDTH - PADDLE_WIDTH) {
                paddle.setX(paddle.getX() + 15);
                repaint();
            }
        }
        /*
        // Restart game
        if (e.getKeyCode() == KeyEvent.VK_R && !gameRunning) {
            restartGame();
        }
        */
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    //Khởi tạo lại Game sau khi Game Over
    /*
    private void restartGame() {
        //paddleX = GAME_WIDTH / 2 - PADDLE_WIDTH / 2;
        score = 0;
        lives = 3;
        level = 1;
        gameRunning = true;
        repaint();
    }
    */
}