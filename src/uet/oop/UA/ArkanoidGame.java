package uet.oop.UA;

import java.awt.Image;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import uet.oop.UA.entites.Ball;
import uet.oop.UA.entites.Brick;
import uet.oop.UA.entites.GameObject;

public class ArkanoidGame extends JFrame {
    
    public ArkanoidGame() {
        setTitle("Arkanoid Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Tạo game panel
        List<GameObject> gameObjects = new ArrayList<>();
        Ball ball = new Ball();

        GamePanel gamePanel = new GamePanel(gameObjects);
        //gamePanel phải được nhận gameObjects rỗng trước
        //Lí do: muốn thêm object phải dùng method của gamePanel (addObject)
        Brick.createBrickGrid(gameObjects);
        gamePanel.addGameObject(ball);
        add(gamePanel);
        
        // Set focusable để nhận phím
        gamePanel.setFocusable(true); 
        gamePanel.requestFocus(); 
        
        // Hiển thị cửa sổ game
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArkanoidGame());
    }
}


