package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a power-up that triples the number of balls in the game when activated.
 * For each existing ball, two additional balls are created with different motion angles.
 * This power-up has an instant effect and does not require deactivation.
 */
public class TripleBallPowerUp extends PowerUp {

    /**
     * Constructs a TripleBallPowerUp at the specified position.
     *
     * @param x the x-coordinate of the power-up
     * @param y the y-coordinate of the power-up
     */
    public TripleBallPowerUp(int x, int y) {
        super(x, y, 20, 20, Color.MAGENTA);
        this.duration = 0;
    }

    /**
     * Activates the triple ball effect by creating two additional balls for each existing ball.
     * The new balls are created with motion angles offset by +30 and -30 degrees from the original ball.
     * Maintains the original speed and position of the base ball.
     * Limits the maximum number of new balls created to prevent excessive ball count.
     *
     * @param gameManager the GameManager instance containing game objects
     */
    @Override
    public void activateEffect(GameManager gameManager) {
        List<GameObject> objectList = gameManager.getObjectList();
        List<Ball> existingBalls = new ArrayList<>();
        List<GameObject> currentObjects = new ArrayList<>(objectList);

        for (GameObject obj : currentObjects) {
            if (obj instanceof Ball) {
                existingBalls.add((Ball) obj);
            }
        }

        List<Ball> newBalls = new ArrayList<>();

        for (Ball originalBall : existingBalls) {
            if (newBalls.size() < 6) {
                int newBallY = originalBall.getY() - 50;
                double originalSpeed = originalBall.getSpeed();

                Ball ball2 = new Ball(
                        originalBall.getX(),
                        newBallY,
                        originalBall.getWidth(),
                        originalBall.getHeight()
                );
                ball2.setMotionAngle(originalBall.getMotionAngle() + Math.toRadians(30));
                ball2.setSpeed(originalSpeed);
                ball2.set_File_image("res/ballImage/balln30.png");

                if (ball2.getImage() == null) {
                    ball2.setColor(Color.ORANGE);
                    ball2.setDrawedBallImage();
                }

                Ball ball3 = new Ball(
                        originalBall.getX(),
                        newBallY,
                        originalBall.getWidth(),
                        originalBall.getHeight()
                );
                ball3.setMotionAngle(originalBall.getMotionAngle() - Math.toRadians(30));
                ball3.setSpeed(originalSpeed);
                ball3.set_File_image("res/ballImage/balln30.png");

                if (ball3.getImage() == null) {
                    ball3.setColor(Color.ORANGE);
                    ball3.setDrawedBallImage();
                }

                newBalls.add(ball2);
                newBalls.add(ball3);
            }
        }

        objectList.addAll(newBalls);
    }

    /**
     * Deactivates the triple ball effect.
     * This power-up has instant effect and does not require deactivation,
     * so this method is intentionally left empty.
     *
     * @param gameManager the GameManager instance containing game objects
     */
    @Override
    public void deactivateEffect(GameManager gameManager) {
        // No deactivation needed for instant effect power-up
    }
}