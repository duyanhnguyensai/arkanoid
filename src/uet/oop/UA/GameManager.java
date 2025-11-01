package uet.oop.UA;
import java.util.*;
import uet.oop.UA.entites.*;

import static uet.oop.UA.GamePanel.GAME_HEIGHT;


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
        if (gameStarted) {
            List<GameObject> removingObjects = new ArrayList<>();
            List<PowerUp> collectedPowerUps = new ArrayList<>();
            List<GameObject> addingObjects = new ArrayList<>();

            // Đếm số bóng còn active
            int activeBalls = 0;
            List<Ball> ballsToRemove = new ArrayList<>();

            List<GameObject> objectsToProcess = new ArrayList<>(objectList);

            for (GameObject object : objectsToProcess) {
                if (object instanceof Ball) {
                    Ball ball = (Ball) object;

                    // Kiểm tra nếu bóng đã rơi xuống dưới
                    if (ball.getY() >= GAME_HEIGHT - ball.getHeight()) {
                        ballsToRemove.add(ball);
                        continue; // Bỏ qua xử lý cho bóng đã rơi
                    }

                    // Chỉ xử lý bóng còn active
                    ball.move(ball.getMotionAngle());
                    ball.handleWallCollision();
                    activeBalls++;

                    for (GameObject obj_ : objectsToProcess) {
                        if (obj_ instanceof Brick) {
                            if (ball.iscollision(obj_)) {
                                if (ball.handleBrickCollision(obj_) == 1) {
                                    ((Brick) obj_).setHitPoints(((Brick) obj_).getHitPoints() - 1);
                                    ((Brick) obj_).low_health_brick();
                                    if (((Brick) obj_).getHitPoints() == 0) {
                                        PowerUp powerUp = ((Brick) obj_).createRandomPowerUp();
                                        if (powerUp != null) {
                                            addingObjects.add(powerUp);
                                        }
                                        if (!removingObjects.contains(obj_)) {
                                            removingObjects.add(obj_);
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    ball.handlePadCollision(objectList.get(0));
                }

                // Xử lý power-up
                if (object instanceof PowerUp) {
                    PowerUp powerUp = (PowerUp) object;
                    powerUp.move();

                    if (powerUp.getY() > 800) {
                        if (!removingObjects.contains(powerUp)) {
                            removingObjects.add(powerUp);
                        }
                    }

                    if (powerUp.isCollidingWithPaddle(objectList.get(0))) {
                        powerUp.activateEffect(this);

                        if (powerUp.getDuration() > 0) {
                            activePowerUps.add(powerUp);
                            powerUpTimers.put(powerUp, powerUp.getDuration());
                        }

                        if (!collectedPowerUps.contains(powerUp)) {
                            collectedPowerUps.add(powerUp);
                        }
                    }
                }
            }

            // XỬ LÝ MẤT MẠNG: chỉ khi KHÔNG CÒN BÓNG NÀO ACTIVE
            if (activeBalls == 0 && !ballsToRemove.isEmpty()) {
                GamePanel.lives = GamePanel.lives - 1;
                GamePanel.score = GamePanel.score - 100;
                System.out.println("All balls lost! Lives: " + GamePanel.lives);

                // Tạo lại bóng mới
                Paddle paddle = (Paddle) objectList.get(0);
                Ball newBall = new Ball(
                        paddle.getX() + paddle.getWidth() / 2 - 15,
                        paddle.getY() - 30,
                        30,
                        30
                );
                addingObjects.add(newBall);

                // Reset game state
                gameStarted = false;
            }

            // Xóa các bóng đã rơi
            objectList.removeAll(ballsToRemove);

            // Xử lý timer của power-up
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

            // Thực hiện tất cả thay đổi
            objectList.removeAll(removingObjects);
            objectList.removeAll(collectedPowerUps);
            objectList.addAll(addingObjects);

        } else {
            // Di chuyển tất cả bóng theo paddle khi chưa bắt đầu
            for (GameObject object : objectList) {
                if (object instanceof Ball) {
                    ((Ball) object).MoveBeforeStart((objectList.get(0)));
                }
            }
        }
    }
    public void draw() {
        gamePanel.repaint();
    }
    //các phương thức xử lí va chạm, điểm số, cấp độ, v.v.
}