package uet.oop.UA.entites;

import static java.lang.Math.sqrt;

public class MovableObject extends GameObject {
    protected double dx;
    protected double dy;
    protected double speed = 5 * sqrt(2); // TĂNG TỐC ĐỘ LÊN
    protected double previousX;
    protected double previousY;

    public double getPreviousX() {
        return this.previousX;
    }

    public double getPreviousY() {
        return this.previousY;
    }

    public void  setPreviousX(double previousX) {
        this.previousX = previousX;
    }

    public void  setPreviousY(double previousY) {
        this.previousY = previousY;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDx() {
        return this.dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return this.dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}