package uet.oop.UA.entites;

import static java.lang.Math.sqrt;

public class MovableObject extends GameObject {
    protected double dx;
    protected double dy;
    protected double speed = 5 * sqrt(2);

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
