package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;

/**
 * Represents a power-up that expands the player's paddle when activated.
 * The paddle expansion is maintained globally across all instances of this power-up.
 */
public class ExpandPaddlePowerUp extends PowerUp {
    private static final int EXPAND_AMOUNT = 90;
    private int originalWidth;
    private boolean isActivated = false;
    private static boolean paddleExpanded = false;

    /**
     * Constructs an ExpandPaddlePowerUp at the specified position.
     *
     * @param x the x-coordinate of the power-up
     * @param y the y-coordinate of the power-up
     */
    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, 20, 20, Color.BLUE);
        this.duration = 900;
    }

    /**
     * Activates the expand paddle effect on the player's paddle.
     * If the paddle is already expanded, only resets the timer without applying the effect again.
     * The expansion is applied evenly on both sides while maintaining the paddle's center position.
     *
     * @param gameManager the GameManager instance containing game objects
     */
    @Override
    public void activateEffect(GameManager gameManager) {
        if (paddleExpanded) {
            return;
        }

        if (isActivated) return;

        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Paddle) {
                Paddle paddle = (Paddle) obj;
                this.originalWidth = paddle.getWidth();

                int newWidth = this.originalWidth + EXPAND_AMOUNT;
                int centerX = paddle.getX() + paddle.getWidth() / 2;
                int newX = centerX - newWidth / 2;

                if (newX < 0) {
                    newX = 0;
                }
                if (newX + newWidth > 1000) {
                    newX = 1000 - newWidth;
                }

                paddle.setWidth(newWidth);
                paddle.setX(newX);
                paddle.set_File_image("res/paddleImage/pad270.png");

                if (paddle.getImage() == null) {
                    paddle.setColor(Color.BLUE);
                    paddle.set_Drawed_Paddle_image();
                }

                isActivated = true;
                paddleExpanded = true;
                break;
            }
        }
    }

    /**
     * Deactivates the expand paddle effect and restores the paddle to its original size.
     * The paddle's center position is maintained during the restoration process.
     *
     * @param gameManager the GameManager instance containing game objects
     */
    @Override
    public void deactivateEffect(GameManager gameManager) {
        if (!isActivated) return;

        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Paddle) {
                Paddle paddle = (Paddle) obj;

                int centerX = paddle.getX() + paddle.getWidth() / 2;
                int newX = centerX - this.originalWidth / 2;

                if (newX < 0) {
                    newX = 0;
                }
                if (newX + this.originalWidth > 1000) {
                    newX = 1000 - this.originalWidth;
                }

                paddle.setWidth(this.originalWidth);
                paddle.setX(newX);
                paddle.set_File_image("res/paddleImage/pad180.png");

                if (paddle.getImage() == null) {
                    paddle.setColor(Color.WHITE);
                    paddle.set_Drawed_Paddle_image();
                }

                isActivated = false;
                paddleExpanded = false;
                break;
            }
        }
    }

    /**
     * Checks if the paddle is currently in an expanded state.
     *
     * @return true if the paddle is expanded, false otherwise
     */
    public static boolean isPaddleExpanded() {
        return paddleExpanded;
    }
}