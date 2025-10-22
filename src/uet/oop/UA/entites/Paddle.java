package uet.oop.UA.entites;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Paddle extends GameObject {
    public Paddle(int x, int y, int width, int height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setColor(Color.WHITE);
        this.set_File_image("res/paddleImage/pad180.png"); // Đường dẫn tới ảnh paddle
    }

    // Tạo hình ảnh paddle
    public void createPaddleImage() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(getColor());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        this.image = image;
    }
}

