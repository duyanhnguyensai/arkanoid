package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;

/**
 * Abstract base class representing a power-up in the game.
 * Power-ups are movable objects that fall downward and can be collected by the paddle.
 * Each power-up has a specific effect and duration when activated.
 */
public abstract class PowerUp extends MovableObject {
    protected boolean active;
    protected int duration;

    /**
     * Constructs a PowerUp at the specified position with given dimensions and color.
     *
     * @param x the x-coordinate of the power-up
     * @param y the y-coordinate of the power-up
     * @param width the width of the power-up
     * @param height the height of the power-up
     * @param color the color of the power-up
     */
    public PowerUp(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        this.active = true;
        this.speed = 2;
        this.set_Drawed_PowerUp_image();
    }

    /**
     * Checks if the power-up is currently active.
     *
     * @return true if the power-up is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active state of the power-up.
     *
     * @param active the new active state
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the duration of the power-up effect in frames.
     *
     * @return the duration in frames
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the power-up effect in frames.
     *
     * @param duration the duration in frames
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Moves the power-up downward at its current speed.
     */
    public void move() {
        this.setY(this.getY() + (int) this.speed);
    }

    /**
     * Checks if the power-up is colliding with a paddle.
     *
     * @param paddle the paddle to check collision with
     * @return true if colliding with the paddle, false otherwise
     */
    public boolean isCollidingWithPaddle(GameObject paddle) {
        return this.getX() < paddle.getX() + paddle.getWidth() &&
                this.getX() + this.getWidth() > paddle.getX() &&
                this.getY() < paddle.getY() + paddle.getHeight() &&
                this.getY() + this.getHeight() > paddle.getY();
    }

    /**
     * Activates the power-up's special effect.
     *
     * @param gameManager the GameManager instance to apply the effect to
     */
    public abstract void activateEffect(GameManager gameManager);

    /**
     * Deactivates the power-up's special effect.
     *
     * @param gameManager the GameManager instance to remove the effect from
     */
    public abstract void deactivateEffect(GameManager gameManager);

    /**
     * Creates and sets a diamond-shaped image for the power-up.
     *
     * @return the created power-up image
     */
    public Image set_Drawed_PowerUp_image() {
        java.awt.image.BufferedImage powerUpImage = new java.awt.image.BufferedImage(
                this.getWidth(), this.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = powerUpImage.createGraphics();

        g.setColor(this.getColor());
        int[] xPoints = {this.getWidth()/2, this.getWidth()-1, this.getWidth()/2, 0};
        int[] yPoints = {0, this.getHeight()/2, this.getHeight()-1, this.getHeight()/2};
        g.fillPolygon(xPoints, yPoints, 4);

        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 4);

        g.dispose();
        this.setImage(powerUpImage);
        return powerUpImage;
    }
}