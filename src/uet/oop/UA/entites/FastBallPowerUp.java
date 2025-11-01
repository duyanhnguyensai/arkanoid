package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;
import java.util.Map;
import java.util.HashMap;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.25;
    private Map<Ball, Double> originalSpeeds = new HashMap<>();
    private static boolean ballSpedUp = false; // Biến static để theo dõi trạng thái toàn cục

    public FastBallPowerUp(int x, int y) {
        super(x, y, 20, 20, Color.RED);
        this.duration = 600;
    }

    @Override
    public void activateEffect(GameManager gameManager) {
        // Nếu bóng đã được tăng tốc, chỉ cần reset timer chứ không tăng tốc thêm
        if (ballSpedUp) {
            System.out.println("Ball already sped up, resetting timer only");
            return;
        }

        originalSpeeds.clear();

        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Ball) {
                Ball ball = (Ball) obj;
                originalSpeeds.put(ball, ball.getSpeed()); // LƯU TỐC ĐỘ GỐC
                ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
            }
        }

        ballSpedUp = true; // Đánh dấu bóng đã được tăng tốc
        System.out.println("Fast Ball PowerUp Activated! Speed multiplier: " + SPEED_MULTIPLIER);
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
        ballSpedUp = false; // Reset trạng thái khi deactivate
        System.out.println("Fast Ball PowerUp Deactivated!");
    }

    // Thêm getter để kiểm tra trạng thái
    public static boolean isBallSpedUp() {
        return ballSpedUp;
    }
}