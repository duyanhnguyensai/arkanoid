package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.5;

    public FastBallPowerUp(int x, int y) {
        super(x, y, 20, 20, Color.RED);
        this.duration = 600; // 10 giây với 60 FPS
    }

    @Override
    public void activateEffect(GameManager gameManager) {
        // Tăng tốc độ của tất cả các bóng
        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Ball) {
                Ball ball = (Ball) obj;
                ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
            }
        }
        System.out.println("Fast Ball PowerUp Activated!");
    }

    @Override
    public void deactivateEffect(GameManager gameManager) {
        // Khôi phục tốc độ bóng về bình thường
        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Ball) {
                Ball ball = (Ball) obj;
                ball.setSpeed(ball.getSpeed() / SPEED_MULTIPLIER);
            }
        }
        System.out.println("Fast Ball PowerUp Deactivated!");
    }
}