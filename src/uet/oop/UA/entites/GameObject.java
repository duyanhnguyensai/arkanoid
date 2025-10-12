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

    public int GetX() {
        return this.x;
    }

    public void SetX(int x) {
        this.x = x;
    }

    public int GetY() {
        return this.y;
    }

    public void SetY(int y) {
        this.y = y;
    }

    public Image GetImage() {
        return this.image;
    }

    public void SetImage(Image image) {
        this.image = image;
    }

    public int GetWidth() {
        return this.image.getWidth(null);
    }

    public int GetHeight() {
        return this.image.getHeight(null);
    }

    public int GetCentralX() {
        return this.x + this.width / 2;
    }

    public int GetCentralY() {
        return this.y + this.height / 2;
    }

    public void UpdateCentral() {
        this.centralX = this.x + this.width / 2;
        this.centralY = this.y + this.height / 2;
    }
}
