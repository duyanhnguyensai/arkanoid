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
            List<GameObject> removingObjects = new ArrayList<>();
            //boolean removed_brick = false;
            for (GameObject object : objectList) {
                if (object instanceof Ball) {
                    ((Ball) object).move(((Ball) object).getMotionAngle());
                    ((Ball) object).handleWallCollision();
                    for (GameObject obj_ : objectList) {
                        if (obj_ instanceof Brick) {
                            if (((Ball) object).iscollision(((Brick) obj_))) {
                                ((Ball) object).handleBrickCollision((Brick) obj_);
                                ((Brick) obj_).setHitPoints( ((Brick) obj_).getHitPoints() - 1 );
                                ((Brick) obj_).low_health_brick();
                                if(((Brick) obj_).getHitPoints() == 0) {
                                    removingObjects.add(obj_);
                                }
                                break;
                            }
                        }
                    }
                    ((Ball) object).handlePadCollision(objectList.get(0));
                }
            }
            //duyệt qua objectList và xóa hết các phần tử cũng thuộc removingObjects
            /*Lí do phải tạo list chứa obj sẽ xóa thay vì dùng .remove(), là vì xài for each không cho phép
            * xóa phần tử khi đang duyệt, nếu không sẽ lỗi */
            objectList.removeAll(removingObjects);
        } else  {
            for (GameObject object : objectList) {
                if (object instanceof Ball) {
                    ((Ball) object).MoveBeforeStart((objectList.get(0)));
                    //System.out.println(((Ball) object).getMotionAngle()*180/Math.PI);
                }
            }
        }

    }
    public void draw() {
        gamePanel.repaint();
    }
    //các phương thức xử lí va chạm, điểm số, cấp độ, v.v.
}
