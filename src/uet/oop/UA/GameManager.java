package uet.oop.UA;
import java.util.*;
import uet.oop.UA.entites.*;

import static uet.oop.UA.GamePanel.GAME_HEIGHT;

public class GameManager {
    private List<GameObject> objectList;
    private GamePanel gamePanel;
    static public boolean gameStarted = false;
    /**
     flag dùng để đảm bảo saveScore trong GamePanel chỉ chạy đúng 1 lần ngay sau khi game kết thúc.
     Cơ chế: saveScore chạy khi gameStarted = false && flag = true.
     Khi gameStarted = true, flag = true --> saveScore không chạy.
     Khi gameStarted = false, flag = true, cho saveScore chạy 1 lần, sau đó flag = false.
     Khi đó gameStarted = false và flag = false --> saveScore không chạy.
     */
    public static boolean flag = true;
    // THÊM MỚI: Danh sách các power-up đang active
    private List<PowerUp> activePowerUps = new ArrayList<>();
    private Map<PowerUp, Integer> powerUpTimers = new HashMap<>();

    // THÊM: Sound Manager
    private SoundManager soundManager;

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
        this.soundManager = SoundManager.getInstance(); // THÊM DÒNG NÀY
    }

    public void update() {
        if (gameStarted) {
            List<GameObject> removingObjects = new ArrayList<>();
            List<PowerUp> collectedPowerUps = new ArrayList<>();
            List<GameObject> addingObjects = new ArrayList<>();
            flag = true;
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

                    // THÊM SOUND EFFECT CHO VA CHẠM TƯỜNG
                    boolean wallHit = ball.handleWallCollision();
                    if (wallHit) {
                        System.out.println("Wall collision detected - playing sound");
                        soundManager.playSound("wall_hit");
                    }

                    activeBalls++;

                    for (GameObject obj_ : objectsToProcess) {
                        if (obj_ instanceof Brick) {
                            if (ball.isCollision(obj_)) {
                                if (ball.handleBrickCollision(obj_) == 1) {
                                    // THÊM SOUND EFFECT CHO VA CHẠM GẠCH
                                    soundManager.playSound("wall_hit");
                                    System.out.println("Brick collision - playing sound");

                                    ((Brick) obj_).setHitPoints(((Brick) obj_).getHitPoints() - 1);
                                    ((Brick) obj_).lowHealthBrick();
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

                    // THÊM SOUND EFFECT CHO VA CHẠM PADDLE
                    boolean paddleHit = ball.handlePadCollision(objectList.get(0));
                    if (paddleHit) {
                        System.out.println("Paddle collision detected - playing sound");
                        soundManager.playSound("paddle_hit");
                    }
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
                        // THÊM SOUND EFFECT CHO POWER-UP
                        soundManager.playSound("powerup");

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
                // THÊM SOUND EFFECT CHO MẤT MẠNG
                soundManager.playSound("life_lost");

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

            // THÊM: Kiểm tra game over và phát sound
            if (GamePanel.lives <= 0) {
                soundManager.playSound("game_over");
                // THÊM: Dừng nhạc nền khi game over
                soundManager.stopSound("background");
                if(flag) {
                    GamePanel.saveScore(GamePanel.score);
                    flag = false;
                }
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

            // Kiểm tra còn gạch
            boolean anyBricksLeft = false;
            for (GameObject object : objectList) {
                if (object instanceof Brick) {
                    anyBricksLeft = true;
                    break;
                }
            }

            // Sau khi phá hết gạch
            if(!anyBricksLeft) {
                // Kiểm tra nếu đang ở level 2 và phá hết gạch --> Victory!
                if(GamePanel.level == 2) {
                    GamePanel.isVictory = true;
                    gameStarted = false;
                    soundManager.stopSound("background");
                    soundManager.playSound("victory");
                    if(flag) {
                        GamePanel.saveScore(GamePanel.score);
                        flag = false;
                    }
                    return;
                }
                else {
                GamePanel.level++;
                objectList.clear();
                activePowerUps.clear();
                powerUpTimers.clear();
                gameStarted = false;

                // Tạo lại paddle
                Paddle paddle = new Paddle(
                GamePanel.GAME_WIDTH / 2 - GamePanel.PADDLE_WIDTH / 2,
                GAME_HEIGHT - GamePanel.PADDLE_HEIGHT,
                GamePanel.PADDLE_WIDTH,
                GamePanel.PADDLE_HEIGHT
            );
                objectList.add(paddle);

                // Tạo lại bricks

                Brick.createBrickGrid(objectList,Brick.createBrickGridFromFiles("res/Brickgrid2.txt"));

                // Tạo lại ball
                Ball ball = new Ball(objectList.get(0).getX()+objectList.get(0).getWidth()/2-15,
                objectList.get(0).getY()-30, 30 , 30);
                objectList.add(ball);
            }
        }

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

    // THÊM: Method để bắt đầu nhạc nền
    public void startBackgroundMusic() {
        soundManager.playSound("background", true);
    }

    // THÊM: Method để dừng nhạc nền
    public void stopBackgroundMusic() {
        soundManager.stopSound("background");
    }
}