package uet.oop.UA.entites;

import static java.lang.Math.*;


public class Ball extends MovableObject {
    protected double directionX;
    protected double directionY;
    protected double motionAngle;
    protected double cosaAngle;
    protected double sinAngle;
    public static final int GAME_WIDTH = 900;
    public static final int GAME_HEIGHT = 800;

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

    public void handleWallCollision() {
        // tường 2 bên
        if (this.getX() <= 0) { //kiểm tra tọa độ x của bóng có vượt qua biên trái không
            this.cosaAngle *= -1;
            this.setX(0);
        } else if (this.getX() >= GAME_WIDTH - this.getWidth()) {
            this.cosaAngle *= -1;
            this.setX(GAME_WIDTH - this.getWidth());
        }
        // tường trên, dưới
        if (this.getY() <= 0) {
            this.sinAngle *= -1;
            this.setY(0);
        } else if (this.getY() >= GAME_HEIGHT - this.getHeight()) {
            this.sinAngle *= -1;
            this.setY(GAME_HEIGHT - this.getHeight());
        }
    }

    public boolean isPossibleToCollision(GameObject obj) { // chỉ kiểm tra va chạm của bóng với vật hình chữ nhật
        return this.distant2CentralObj(obj)
                <= (double) this.getWidth() / 2 + sqrt((obj.getWidth() * obj.getWidth())
                + (obj.getHeight() * obj.getHeight())) / 2;
    }

    public int isLeftCollision(GameObject obj) {
        if (this.getCentralY() >= obj.getY() && this.getCentralY() <= obj.getY() + obj.getHeight()) {

            if (abs(obj.getX() - this.getCentralX()) <= abs(obj.getX() + obj.getWidth() - this.getCentralX())) {
                return 1;

            } else if (abs(obj.getX() - this.getCentralX()) > abs(obj.getX() + obj.getWidth() - this.getCentralX())) {
                return 0;
            }
        }
        return -1;
    }

}
