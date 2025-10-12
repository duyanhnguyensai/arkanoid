import javax.swing.*;

public class ArkanoidGame extends JFrame {
    
    public ArkanoidGame() {
        setTitle("Arkanoid Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Tạo game panel
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArkanoidGame());
    }
}
