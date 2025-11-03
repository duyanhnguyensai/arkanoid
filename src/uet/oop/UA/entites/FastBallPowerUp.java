package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a power-up that increases the speed of all balls in the game when activated.
 * The speed increase is maintained globally across all instances of this power-up.
 */
public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.25;
    private Map<Ball, Double> originalSpeeds = new HashMap<>();
    private static boolean ballSpedUp = false;

    /**
     * Constructs a FastBallPowerUp at the specified position.
     *
     * @param x the x-coordinate of the power-up
     * @param y the y-coordinate of the power-up
     */
    public FastBallPowerUp(int x, int y) {
        super(x, y, 20, 20, Color.RED);
        this.duration = 600;
    }

    /**
     * Activates the fast ball effect on all balls in the game.
     * If balls are already sped up, only resets the timer without applying the effect again.
     * Stores original speeds for proper restoration when deactivated.
     *
     * @param gameManager the GameManager instance containing game objects
     */
    @Override
    public void activateEffect(GameManager gameManager) {
        if (ballSpedUp) {
            return;
        }

        originalSpeeds.clear();

        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Ball) {
                Ball ball = (Ball) obj;
                originalSpeeds.put(ball, ball.getSpeed());
                ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
            }
        }

        ballSpedUp = true;
    }

    /**
     * Deactivates the fast ball effect and restores all balls to their original speeds.
     *
     * @param gameManager the GameManager instance containing game objects
     */
    @Override
    public void deactivateEffect(GameManager gameManager) {
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
        ballSpedUp = false;
    }

    /**
     * Checks if balls are currently in a sped-up state.
     *
     * @return true if balls are sped up, false otherwise
     */
    public static boolean isBallSpedUp() {
        return ballSpedUp;
    }
}