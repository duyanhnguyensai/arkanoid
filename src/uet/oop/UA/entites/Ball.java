package uet.oop.UA.entites;

import static java.lang.Math.*;


public class Ball extends MovableObject {
    double directionX;
    double directionY;
    double motionAngle;
    double cosaAngle;
    double sinAngle;

    public void move(double angle) {
        this.motionAngle = angle;
        this.cosaAngle = cos(this.motionAngle);
        this.sinAngle = sin(this.motionAngle);
        this.directionX = this.speed
                * (this.cosaAngle / sqrt(this.cosaAngle * this.cosaAngle
                + this.sinAngle * this.sinAngle));
        this.directionY = this.speed
                * (this.sinAngle / sqrt(this.sinAngle * this.sinAngle
                + this.cosaAngle * this.cosaAngle));
        this.setX(this.getX()+ (int) this.directionX);
        this.setY(this.getY()+ (int) this.directionY);
    }
}
