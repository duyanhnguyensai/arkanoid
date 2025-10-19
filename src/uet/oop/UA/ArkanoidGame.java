package uet.oop.UA;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
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
        GamePanel gamePanel = new GamePanel(gameObjects);
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
