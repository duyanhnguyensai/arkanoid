package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;
import java.util.Map;
import java.util.HashMap;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.25; // TĂNG HỆ SỐ LÊN
    private Map<Ball, Double> originalSpeeds = new HashMap<>(); // LƯU TỐC ĐỘ GỐC

    public FastBallPowerUp(int x, int y) {
        super(x, y, 20, 20, Color.RED);
        this.duration = 600;
    }

    @Override
    public void activateEffect(GameManager gameManager) {
        originalSpeeds.clear();

        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Ball) {
                Ball ball = (Ball) obj;
                originalSpeeds.put(ball, ball.getSpeed()); // LƯU TỐC ĐỘ GỐC
                ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
            }
        }
        System.out.println("Fast Ball PowerUp Activated!");
    }

    @Override
    public void deactivateEffect(GameManager gameManager) {
        // KHÔI PHỤC ĐÚNG TỐC ĐỘ GỐC
        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Ball) {
                Ball ball = (Ball) obj;
                Double originalSpeed = originalSpeeds.get(ball);
                if (originalSpeed != null) {
                    ball.setSpeed(originalSpeed);
                }
            }
        }
        originalSpeeds.clear();
        System.out.println("Fast Ball PowerUp Deactivated!");
    }
}