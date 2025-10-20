package uet.oop.UA;

import java.awt.Image;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import uet.oop.UA.entites.Ball;
import uet.oop.UA.entites.Brick;
import uet.oop.UA.entites.GameObject;
import uet.oop.UA.graphics.Gameloop;

public class ArkanoidGame extends JFrame {
    
    public ArkanoidGame() {
        setTitle("Arkanoid Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Tạo game panel
        List<GameObject> gameObjects = new ArrayList<>();
        GamePanel gamePanel = new GamePanel(gameObjects);
        GameManager manageGame = new GameManager(gameObjects, gamePanel);
        Ball ball = new Ball(gameObjects.get(0).getX()+gameObjects.get(0).getWidth()/2-15,700, 30 , 30);

        //gamePanel phải được nhận gameObjects rỗng trước
        //Lí do: muốn thêm object phải dùng method của gamePanel (addObject)
        Brick.createBrickGrid(gameObjects);
        gamePanel.addGameObject(ball);
        add(gamePanel);

        Gameloop loop = new Gameloop(manageGame); // Pass game vào loop
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


