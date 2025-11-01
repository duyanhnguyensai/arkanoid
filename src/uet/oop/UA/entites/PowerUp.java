package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;

public abstract class PowerUp extends MovableObject {
    protected boolean active;
    protected int duration; // Thời gian hiệu lực (tính bằng frames)

    public PowerUp(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        this.active = true;
        this.speed = 2; // Tốc độ rơi của power-up
        this.set_Drawed_PowerUp_image();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Di chuyển power-up xuống dưới
    public void move() {
        this.setY(this.getY() + (int) this.speed);
    }

    // Kiểm tra va chạm với paddle
    public boolean isCollidingWithPaddle(GameObject paddle) {
        return this.getX() < paddle.getX() + paddle.getWidth() &&
                this.getX() + this.getWidth() > paddle.getX() &&
                this.getY() < paddle.getY() + paddle.getHeight() &&
                this.getY() + this.getHeight() > paddle.getY();
    }

    // Phương thức trừu tượng để kích hoạt hiệu ứng
    public abstract void activateEffect(GameManager gameManager);

    // Phương thức trừu tượng để hủy hiệu ứng
    public abstract void deactivateEffect(GameManager gameManager);

    // Tạo hình ảnh cho power-up
    public Image set_Drawed_PowerUp_image() {
        java.awt.image.BufferedImage powerUpImage = new java.awt.image.BufferedImage(
                this.getWidth(), this.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = powerUpImage.createGraphics();

        // Vẽ hình viên kim cương
        g.setColor(this.getColor());
        int[] xPoints = {this.getWidth()/2, this.getWidth()-1, this.getWidth()/2, 0};
        int[] yPoints = {0, this.getHeight()/2, this.getHeight()-1, this.getHeight()/2};
        g.fillPolygon(xPoints, yPoints, 4);

        // Viền đen
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 4);

        g.dispose();
        this.setImage(powerUpImage);
        return powerUpImage;
    }
}