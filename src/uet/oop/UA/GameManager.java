package uet.oop.UA;
import java.util.*;
import uet.oop.UA.entites.*;


public class GameManager {
    private List<GameObject> objectList;
    private GamePanel gamePanel;
    static public boolean gameStarted = false;

    // THÊM MỚI: Danh sách các power-up đang active
    private List<PowerUp> activePowerUps = new ArrayList<>();
    private Map<PowerUp, Integer> powerUpTimers = new HashMap<>();

    public void SetGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean IsGameStarted() {
        return this.gameStarted;
    }

    // THÊM MỚI: Getter cho objectList
    public List<GameObject> getObjectList() {
        return this.objectList;
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
            List<PowerUp> collectedPowerUps = new ArrayList<>();
            List<GameObject> addingObjects = new ArrayList<>(); // THÊM MỚI: Danh sách object cần thêm

            for (GameObject object : objectList) {
                if (object instanceof Ball) {
                    ((Ball) object).move(((Ball) object).getMotionAngle());
                    ((Ball) object).handleWallCollision();
                    for (GameObject obj_ : objectList) {
                        if (obj_ instanceof Brick) {
                            if (((Ball) object).iscollision(obj_)) {
                                if (((Ball) object).handleBrickCollision(obj_) == 1) {
                                    ((Brick) obj_).setHitPoints( ((Brick) obj_).getHitPoints() - 1 );
                                    ((Brick) obj_).low_health_brick();
                                    if(((Brick) obj_).getHitPoints() == 0) {
                                        // THÊM MỚI: Tạo power-up khi brick bị phá hủy
                                        PowerUp powerUp = ((Brick) obj_).createRandomPowerUp();
                                        if (powerUp != null) {
                                            addingObjects.add(powerUp); // THÊM VÀO DANH SÁCH TẠM
                                        }
                                        removingObjects.add(obj_);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    ((Ball) object).handlePadCollision(objectList.get(0));
                }

                // THÊM MỚI: Xử lý power-up
                if (object instanceof PowerUp) {
                    PowerUp powerUp = (PowerUp) object;
                    powerUp.move();

                    // Kiểm tra nếu power-up ra khỏi màn hình
                    if (powerUp.getY() > 800) {
                        removingObjects.add(powerUp);
                    }

                    // Kiểm tra va chạm với paddle
                    if (powerUp.isCollidingWithPaddle(objectList.get(0))) {
                        powerUp.activateEffect(this);
                        activePowerUps.add(powerUp);
                        powerUpTimers.put(powerUp, powerUp.getDuration());
                        collectedPowerUps.add(powerUp);
                    }
                }
            }

            // THÊM MỚI: Xử lý timer của power-up
            Iterator<Map.Entry<PowerUp, Integer>> iterator = powerUpTimers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<PowerUp, Integer> entry = iterator.next();
                PowerUp powerUp = entry.getKey();
                int timeLeft = entry.getValue() - 1;

                if (timeLeft <= 0) {
                    powerUp.deactivateEffect(this);
                    iterator.remove();
                    activePowerUps.remove(powerUp);
                } else {
                    powerUpTimers.put(powerUp, timeLeft);
                }
            }

            // Xóa các object cần xóa
            objectList.removeAll(removingObjects);
            objectList.removeAll(collectedPowerUps);

            // THÊM MỚI: Thêm các object mới (SAU KHI DUYỆT XONG)
            objectList.addAll(addingObjects);

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