package uet.oop.UA.entites;

import static java.lang.Math.sqrt;
import java.awt.*;
public class MovableObject extends GameObject {
    protected double dx;
    protected double dy;
    protected double speed = 7.0; // TĂNG TỐC ĐỘ LÊN

    public MovableObject(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public MovableObject(int x, int y, int width, int height, Color color) {
        super(x,y,width,height,color);
    }

    public MovableObject() {
        super();
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