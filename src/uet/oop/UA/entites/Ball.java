package uet.oop.UA.entites;

import uet.oop.UA.GameManager;
import uet.oop.UA.GamePanel;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Math.*;

/**
 * class bóng.
 * */
public class Ball extends MovableObject {
    protected double directionX;
    protected double directionY;
    protected double motionAngle = (270+45-90)*PI/180;
    protected double cosaAngle;
    protected double sinAngle;
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 800;

    /**
     * Constructor có 4 parameter,
     * 2 vị trí x,y;
     *
     * */
    public Ball(int x, int y, int width, int height) {
        super(x,y,width,height);
        this.set_File_image("res/ballImage/ball30.png");
    }

    public double getMotionAngle() {
        return this.motionAngle;
    }

    public void setMotionAngle(double motionAngle) {
        this.motionAngle = motionAngle;
    }

    public void MoveBeforeStart(GameObject obj) {
        this.setX(obj.getX()+obj.getWidth()/2-this.getWidth()/2);
        this.setY(obj.getY()-this.getHeight());
    }

    public void move(double angle) {
        this.motionAngle = angle;
        this.cosaAngle = cos(this.motionAngle);
        this.sinAngle = sin(this.motionAngle);

        double magnitude = sqrt(this.cosaAngle * this.cosaAngle + this.sinAngle * this.sinAngle);

        if (magnitude < 0.0001) {
            this.cosaAngle = cos(PI/4);
            this.sinAngle = sin(PI/4);
            magnitude = sqrt(this.cosaAngle * this.cosaAngle + this.sinAngle * this.sinAngle);
        }

        this.directionX = this.speed * (this.cosaAngle / magnitude);
        this.directionY = this.speed * (this.sinAngle / magnitude);

        // DEBUG: In tốc độ để kiểm tra
        if (Math.random() < 0.01) { // Chỉ in thỉnh thoảng để tránh spam
            System.out.println("Ball speed: " + this.speed + ", directionX: " + this.directionX + ", directionY: " + this.directionY);
        }

        this.setX(this.getX() + (int) this.directionX);
        this.setY(this.getY() + (int) this.directionY);
    }

    public void handleWallCollision() {
        // tường 2 bên
        if (this.getX() <= 0) {
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
        // tường trên
        if (this.getY() <= 0) {
            if (this.motionAngle >=180*PI/180 && this.motionAngle <= 270*PI/180) {
                this.motionAngle = (2*PI-(this.motionAngle));
            } else if (this.motionAngle >= 270*PI/180 && this.motionAngle <= 320*PI/180) {
                this.motionAngle =  (2*PI-(this.motionAngle));
                System.out.println(this.motionAngle*180/PI + "j79");
            }
            this.setY(0);
        }
        // TƯỜNG DƯỚI - KHÔNG xử lý mất mạng ở đây nữa
        // Chỉ đánh dấu bóng là đã rơi
        else if (this.getY() >= GAME_HEIGHT - this.getHeight()) {
            this.setActive(false); // Đánh dấu bóng không còn active
        }
    }

    // Thêm phương thức để kiểm tra bóng có active không
    public boolean isActive() {
        return this.getY() < GAME_HEIGHT - this.getHeight();
    }

    public void setActive(boolean active) {
        // Không cần set cụ thể, vì active phụ thuộc vào vị trí Y
    }

    public boolean isPossibleToCollision(GameObject obj) { // chỉ kiểm tra va chạm của bóng với vật hình chữ nhật
        return this.distant2CentralObj(obj)
                <= (double) this.getWidth() / 2 + sqrt((obj.getWidth() * obj.getWidth())
                + (obj.getHeight() * obj.getHeight())) / 2;
    }

    public int isLeftCollision(GameObject obj) {
        if (this.getCentralY() >= obj.getY() && this.getCentralY() <= obj.getY() + obj.getHeight()) {

            if (abs(obj.getX() - this.getCentralX()) <= abs(obj.getX() + obj.getWidth() - this.getCentralX())) {
                if (abs(obj.getX() - this.getCentralX()) <= this.getWidth()/2) {
                    return 1;
                }
                return -1;
            } else if (abs(obj.getX() - this.getCentralX()) > abs(obj.getX() + obj.getWidth() - this.getCentralX())) {
                if (abs(obj.getX() + obj.getWidth() - this.getCentralX()) <= this.getWidth()/2) {
                    return 0;
                }
                return -1;
            }
        }
        return -1;
    }

    public int isUpCollision(GameObject obj) {
        if (this.getCentralX() >= obj.getX() && this.getCentralX() <= obj.getX() + obj.getWidth()) {
            if (abs(obj.getY() - this.getCentralY()) <= abs(obj.getY() + obj.getHeight() - this.getCentralY())) {
                if (abs(obj.getY() - this.getCentralY()) <= this.getWidth()/2) {
                    return 1;
                }
                return -1;
            } else if (abs(obj.getY() - this.getCentralY()) > abs(obj.getY() + obj.getHeight() - this.getCentralY())) {
                if (abs(obj.getY() + obj.getHeight() - this.getCentralY()) <= this.getWidth()/2) {
                    return 0;
                }
                return -1;
            }
        }
        return -1;
    }

    public int isCornerCollision(GameObject obj) {
        if ((this.getCentralX() - obj.getX())*(this.getCentralX() - obj.getX())
                + (this.getCentralY() - obj.getY()) * (this.getCentralY() - obj.getY()) <= (this.getWidth()/2) * this.getWidth()/2) {
            return 1;
        }
        return -1;
    }

    public void handlePadCollision(GameObject obj) {
        if (this.isPossibleToCollision(obj)) {
             // dieu kien cac gach sap va cham
            int upcollision = this.isUpCollision(obj);
            int leftcollision = this.isLeftCollision(obj);
                if (upcollision != -1) {
                    if (upcollision == 1) {
                                this.setY(obj.getY() - this.getWidth());
                                double cAngle = this.getCentralX() - obj.getCentralX();
                                System.out.println(cAngle);
                                this.motionAngle = (cAngle+90)*PI/(2*180) + (45+180)*PI/(180);
                                System.out.println(this.motionAngle*180/PI + "j97");
                    }
                } else if (leftcollision != -1) {
                    if (leftcollision == 1) {
                        this.setX(this.getX() - this.getWidth());
                        if (this.getMotionAngle() >=0*PI/180 && this.getMotionAngle() <= 90*PI/180) {
                            this.setMotionAngle(PI-this.getMotionAngle());
                        }
                    } else if (leftcollision == 0) {
                        this.setX(this.getX() + this.getWidth());
                        if (this.getMotionAngle() >=90*PI/180 && this.getMotionAngle() <= 180*PI/180) {
                            this.setMotionAngle(PI-(this.getMotionAngle()));
                        }
                    }
                }
        }
    }

    public int handleBrickCollision(GameObject obj) {
        if (this.isPossibleToCollision(obj)) {
            int upcollisionb = this.isUpCollision(obj);
            int leftcollisionb = this.isLeftCollision(obj);
            if (upcollisionb != -1) {
                if (upcollisionb == 1) {
                    this.setY(obj.getY() - this.getWidth());
                    if (this.getMotionAngle() >=0*PI/180 && this.getMotionAngle() <= 90*PI/180) {
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    } else if (this.getMotionAngle() >= 90*PI/180 && this.getMotionAngle() <= 180*PI/180) {
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    }
                } else if (upcollisionb == 0) {
                    this.setY(obj.getY() + obj.getHeight());
                    if (this.getMotionAngle() >=180*PI/180 && this.getMotionAngle() <= 270*PI/180) {
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    } else if (this.getMotionAngle() >= 270*PI/180 && this.getMotionAngle() <= 320*PI/180) {
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    }
                }
                return 1;
            } else if (leftcollisionb != -1) {
                if (leftcollisionb == 1) {
                    this.setX(obj.getX() - this.getWidth());
                    if (this.getMotionAngle() >=0*PI/180 && this.getMotionAngle() <= 90*PI/180) {
                        this.setMotionAngle(PI-this.getMotionAngle());
                    } else if (this.getMotionAngle() >= 270*PI/180 && this.getMotionAngle() <= 360*PI/180) {
                        this.setMotionAngle(PI+(2*PI-this.getMotionAngle()));
                        System.out.println(this.getMotionAngle()*180/PI + "j79");
                    }
                } else if (leftcollisionb == 0) {
                    this.setX(obj.getX() + obj.getWidth());
                    if (this.getMotionAngle() >=90*PI/180 && this.getMotionAngle() <= 180*PI/180) {
                        this.setMotionAngle(PI-(this.getMotionAngle()));
                    } else if (this.getMotionAngle() >= 180*PI/180 && this.getMotionAngle() <= 270*PI/180) {
                        this.setMotionAngle(PI*2-(this.getMotionAngle()-180*PI/180));
                        System.out.println(this.getMotionAngle()*180/PI + "j79");
                    }
                }
                return 1;
            } else if (isCornerCollision(obj) == 1) {
                this.setMotionAngle(PI * 2 - this.getMotionAngle());
            }
        }
        return 0;
    }

    public boolean iscollision(GameObject obj) {
        if (this.isPossibleToCollision(obj)) {
            return this.isLeftCollision(obj) != -1 || this.isUpCollision(obj) != -1 || this.isCornerCollision(obj) != -1;
        }
        return false;
    }
}
