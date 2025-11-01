package uet.oop.UA;

//import java.awt.Image;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import uet.oop.UA.entites.Ball;
import uet.oop.UA.entites.Brick;
import uet.oop.UA.entites.GameObject;
import uet.oop.UA.graphics.Gameloop;
import static uet.oop.UA.GamePanel.GAME_WIDTH;
import static uet.oop.UA.GamePanel.GAME_HEIGHT;

public class ArkanoidGame extends JFrame {
    private static final int WIDTDCOMPLENMENT = 15;
    private static final int HEIGHTCOMPLENMENT = 30;

    public ArkanoidGame() {
        setTitle("Arkanoid Game");
        setSize(GAME_WIDTH + WIDTDCOMPLENMENT, GAME_HEIGHT + HEIGHTCOMPLENMENT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Tạo game panel
        List<GameObject> gameObjects = new ArrayList<>();
        GamePanel gamePanel = new GamePanel(gameObjects);
        GameManager manageGame = new GameManager(gameObjects, gamePanel);
        Ball ball = new Ball(gameObjects.getFirst().getX()+gameObjects.getFirst().getWidth()/2-15,
                gameObjects.getFirst().getY()-30, 30 , 30);

        //gamePanel phải được nhận gameObjects rỗng trước
        //Lí do: muốn thêm object phải dùng method của gamePanel (addObject)
        Brick.createBrickGrid(gameObjects);
        gamePanel.addGameObject(ball);
        add(gamePanel);

        Gameloop loop = new Gameloop(manageGame, gamePanel); // Pass game vào loop
        Thread thread = new Thread(loop);
        thread.start();
        
        // Set focusable để nhận phím
        gamePanel.setFocusable(true); 
        gamePanel.requestFocus(); 
        
        // Hiển thị cửa sổ game
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new  ArkanoidGame();
    }
}


