package uet.oop.UA;
import java.util.*;
import uet.oop.UA.entites.*;


public class GameManager {
    private List<GameObject> objectList;
    private GamePanel gamePanel;

    public GameManager(List<GameObject> objects, GamePanel panel) {
        this.objectList = objects;
        this.gamePanel = panel;
    }
    public void update() {
        System.out.println("Updating game logic...");
        //Cập nhật vị trí các đối tượng di chuyển
    }
    public void draw() {
        gamePanel.repaint();
    }
    //các phương thức xử lí va chạm, điểm số, cấp độ, v.v.
}
