package uet.oop.UA.entites;

import java.awt.*;

import static java.lang.Math.*;


public class Ball extends MovableObject {
    protected double directionX;
    protected double directionY;
    protected double motionAngle = (270+45)*PI/180;
    protected double cosaAngle;
    protected double sinAngle;
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 800;

    public Ball(int x, int y, int width, int height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setColor(Color.WHITE);
        //this.set_Drawed_Ball_image();
        this.set_File_image("res/ballImage/ball30.png");
         // Đường dẫn tới ảnh
    }

    public double getMotionAngle() {
        return this.motionAngle;
    }

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
            if (this.motionAngle >=90*PI/180 && this.motionAngle <= 180*PI/180) {
                this.motionAngle = (PI-(this.motionAngle));
                System.out.println(this.motionAngle*180/PI + "j97");
            } else if (this.motionAngle >= 180*PI/180 && this.motionAngle <= 270*PI/180) {
                this.motionAngle =  (PI*2-(this.motionAngle-180*PI/180));
                System.out.println(this.motionAngle*180/PI + "j79");
            }
            this.setX(0);
        } else if (this.getX() >= GAME_WIDTH - this.getWidth()) {
            if (this.motionAngle >=0*PI/180 && this.motionAngle <= 90*PI/180) {
                this.motionAngle =  (PI-this.motionAngle);
                System.out.println(this.motionAngle*180/PI + "j97");
            } else if (this.motionAngle >= 270*PI/180 && this.motionAngle <= 360*PI/180) {
                this.motionAngle = (PI+(2*PI-this.motionAngle));
                System.out.println(this.motionAngle*180/PI + "j79");
            }
            this.setX(GAME_WIDTH - this.getWidth());
        }
        // tường trên, dưới
        if (this.getY() <= 0) {
            if (this.motionAngle >=180*PI/180 && this.motionAngle <= 270*PI/180) {
                this.motionAngle = (2*PI-(this.motionAngle));
                System.out.println(this.motionAngle*180/PI + "j97");
            } else if (this.motionAngle >= 270*PI/180 && this.motionAngle <= 320*PI/180) {
                this.motionAngle =  (2*PI-(this.motionAngle));
                System.out.println(this.motionAngle*180/PI + "j79");
            }
            this.setY(0);
        } else if (this.getY() >= GAME_HEIGHT - this.getHeight()) { // -1 tim
            if (this.motionAngle >=0*PI/180 && this.motionAngle <= 90*PI/180) {
                this.motionAngle = (2*PI-(this.motionAngle));
                System.out.println(this.motionAngle*180/PI + "j97");
            } else if (this.motionAngle >= 90*PI/180 && this.motionAngle <= 180*PI/180) {
                this.motionAngle = (2*PI-(this.motionAngle));
                System.out.println(this.motionAngle*180/PI + "j79");
            }
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

    public int isUpCollision(GameObject obj) {
        if (this.getCentralX() >= obj.getX() && this.getCentralX() <= obj.getX() + obj.getWidth()) {
            if (abs(obj.getY() - this.getCentralY()) <= abs(obj.getY() + obj.getHeight() - this.getCentralY())) {
                return 1;
            } else if (abs(obj.getY() - this.getCentralY()) > abs(obj.getY() + obj.getHeight() - this.getCentralY())) {
                return 0;
            }
        }
        return -1;
    }

}
