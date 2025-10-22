package uet.oop.UA;
import java.util.*;
import uet.oop.UA.entites.*;


public class GameManager {
    private List<GameObject> objectList;
    private GamePanel gamePanel;
    static public boolean gameStarted = false;

    public void SetGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean IsGameStarted() {
        return this.gameStarted;
    }

    public GameManager(List<GameObject> objects, GamePanel panel) {
        this.objectList = objects;
        this.gamePanel = panel;
    }
    public void update() {
        //System.out.println("Updating game logic...");
        //Cập nhật vị trí các đối tượng di chuyển
        if (gameStarted) {
            for (GameObject object : objectList) {
                if (object instanceof Ball) {
                    ((Ball) object).move(((Ball) object).getMotionAngle());
                    ((Ball) object).handleWallCollision();
                    ((Ball) object).handlePadCollision(objectList.get(0));
                }
            }
        } else  {
            for (GameObject object : objectList) {
                if (object instanceof Ball) {
                    ((Ball) object).MoveBeforeStart((objectList.get(0)));
                    System.out.println(((Ball) object).getMotionAngle()*180/Math.PI);
                }
            }
        }
    }
    public void draw() {
        gamePanel.repaint();
    }
    //các phương thức xử lí va chạm, điểm số, cấp độ, v.v.
}
