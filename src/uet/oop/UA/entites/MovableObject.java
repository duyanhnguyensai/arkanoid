package uet.oop.UA.entites;

public class MovableObject extends GameObject {
    public double dx;
    public double dy;
    double speed;

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
