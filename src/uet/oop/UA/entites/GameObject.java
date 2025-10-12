package uet.oop.UA.entites;

import java.awt.*;


public abstract class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;
    private int centralX;
    private int centralY;
    private Image image;

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getWidth() {
        return this.image.getWidth(null);
    }

    public int getHeight() {
        return this.image.getHeight(null);
    }

    public int getCentralX() {
        return this.x + this.width / 2;
    }

    public int getCentralY() {
        return this.y + this.height / 2;
    }

    public void updateCentral() {
        this.centralX = this.x + this.width / 2;
        this.centralY = this.y + this.height / 2;
    }
}
