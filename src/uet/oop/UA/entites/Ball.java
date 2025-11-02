package uet.oop.UA.entites;

import uet.oop.UA.GameManager;
import uet.oop.UA.GamePanel;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.*;
import static uet.oop.UA.GamePanel.GAME_WIDTH;
import static uet.oop.UA.GamePanel.GAME_HEIGHT;

/**
 * class bóng.
 * */
public class Ball extends MovableObject {
    public static final double aCircle = 2 * PI;
    protected double directionX;
    protected double directionY;
    protected double motionAngle = (270+45-90)*PI/180;
    protected double cosaAngle;
    protected double sinAngle;
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 800;
    protected double radius;
    protected double preCentralX = this.getCentralX();
    protected double preCentralY = this.getCentralY();
    protected double collisionCentralX;
    protected double collisionCentralY;
    protected LinearEquation motionPath;

    // THÊM: Biến theo dõi va chạm paddle
    private boolean wasCollidingWithPaddle = false;
    private GameObject lastPaddle = null;

    public Ball(int x, int y, int width, int height) {
        super(x,y,width,height);
        this.radius = (double) width / 2;
        // Đường dẫn tới ảnh
        this.set_File_image("res/ballImage/balln30.png");
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

    // THAY ĐỔI: Trả về boolean để biết có va chạm tường không
    public boolean handleWallCollision() {
        boolean hitWall = false;

        // tường 2 bên
        if (this.getX() <= 0) {
            hitWall = true;
            if (this.motionAngle >=90*PI/180 && this.motionAngle <= 180*PI/180) {
                this.motionAngle = (PI-(this.motionAngle));
                System.out.println(this.motionAngle*180/PI + "j97");
            } else if (this.motionAngle >= 180*PI/180 && this.motionAngle <= 270*PI/180) {
                this.motionAngle =  (PI*2-(this.motionAngle-180*PI/180));
                System.out.println(this.motionAngle*180/PI + "j79");
            }
            this.setX(0);
            if (motionAngle >= 0 * PI / 180 && motionAngle <= 10 * PI / 180) {
                motionAngle += 5 * PI / 180;
            }
        } else if (this.getX() >= GAME_WIDTH - this.getWidth()) {
            hitWall = true;
            if (this.motionAngle >=0*PI/180 && this.motionAngle <= 90*PI/180) {
                this.motionAngle =  (PI-this.motionAngle);
                System.out.println(this.motionAngle*180/PI + "j97");
            } else if (this.motionAngle >= 270*PI/180 && this.motionAngle <= 360*PI/180) {
                this.motionAngle = (PI+(2*PI-this.motionAngle));
                System.out.println(this.motionAngle*180/PI + "j79");
            }
            this.setX(GAME_WIDTH - this.getWidth());
            if (motionAngle >= 170*PI/180 && motionAngle <= 190*PI/180) {
                motionAngle -= 5 * PI/180;
            }
        }
        // tường trên
        if (this.getY() <= 0) {
            hitWall = true;
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

        return hitWall;
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

    public int isUpCollisionp(GameObject obj) {
        if (this.getCentralX() > obj.getX() - this.getWidth() / 2 && this.getCentralX() < obj.getX() + obj.getWidth() + this.getWidth() / 2) {
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

    // SỬA HOÀN TOÀN: Sử dụng AABB collision detection thay vì isPossibleToCollision
    public boolean handlePadCollision(GameObject obj) {
        boolean hitPaddle = false;
        boolean isNewCollision = false;

        // SỬ DỤNG AABB COLLISION DETECTION - chỉ phát hiện khi thực sự chạm nhau
        boolean isActuallyColliding = this.getX() < obj.getX() + obj.getWidth() &&
                this.getX() + this.getWidth() > obj.getX() &&
                this.getY() < obj.getY() + obj.getHeight() &&
                this.getY() + this.getHeight() > obj.getY();

        if (isActuallyColliding) {
            hitPaddle = true;

            // KIỂM TRA: Đây có phải là va chạm mới không?
            if (!wasCollidingWithPaddle || lastPaddle != obj) {
                isNewCollision = true;
                wasCollidingWithPaddle = true;
                lastPaddle = obj;
                System.out.println("🔄 NEW paddle collision detected!");
            }

            // Xử lý va chạm dựa trên vị trí tương đối
            int upcollision = this.isUpCollisionp(obj);
            int leftcollision = this.isLeftCollisionp(obj);

            if (upcollision != -1) {
                if (upcollision == 1) {
                    // Va chạm từ trên xuống (bóng chạm đỉnh paddle)
                    this.setY(obj.getY() - this.getWidth());
                    double cAngle = this.getCentralX() - obj.getCentralX();
                    System.out.println("Paddle hit - top, angle: " + cAngle);
                    this.motionAngle = (cAngle+90)*PI/(2*180) + (45+180)*PI/(180);
                    System.out.println("New motion angle: " + this.motionAngle*180/PI + " degrees");
                }
            } else if (leftcollision != -1) {
                if (leftcollision == 1) {
                    // Va chạm từ bên trái
                    this.setX(obj.getX() - this.getWidth());
                    if (this.getMotionAngle() >=0*PI/180 && this.getMotionAngle() <= 90*PI/180) {
                        this.setMotionAngle(PI-this.getMotionAngle());
                    }
                } else if (leftcollision == 0) {
                    // Va chạm từ bên phải
                    this.setX(obj.getX() + obj.getWidth());
                    if (this.getMotionAngle() >=90*PI/180 && this.getMotionAngle() <= 180*PI/180) {
                        this.setMotionAngle(PI-(this.getMotionAngle()));
                    }
                }
            }
        } else {
            // Reset trạng thái khi không còn va chạm
            wasCollidingWithPaddle = false;
            lastPaddle = null;
        }

        // CHỈ trả về true nếu đây là va chạm mới
        return isNewCollision;
    }

    public int findPosibleCollisionPoint(double pX, double pY, double cX1, double cY1, double cX2, double cY2) {
        int motionCase = motionPath.nearStart(pX, pY, cX1, cY1, cX2, cY2
                , true, this.getCentralX(), this.getCentralY());
        if (motionCase == 1) {
            return 1;
        } else if (motionCase == 2) {
            return 0;
        }  else {
            return -1;
        }
    }

    public int isLeftCollision(GameObject obj) {
        int leftcollision = -1;
        double pY = this.preCentralY;
        double pX = this.preCentralX;
        motionPath.calculateCoefficient(pX, pY, this.getCentralX(), this.getCentralY());
        double cX;
        double cY;

        if (abs(this.getCentralY() - pY)  <= 0.0001 && abs(this.getCentralX() - pX)  <= 0.0001) {
            return -1;
        } else if (abs(this.getCentralX() - pX)  <= 0.0001) {
            return -1;
        } else if (abs(this.getCentralY() - pY)  <= 0.0001) {
            double cX1 = obj.getX() - (double) this.getWidth() /2;
            double cX2 = obj.getX() + obj.getWidth() + (double) this.getHeight() /2;
            cY = this.getCentralY();
            int motionCase = motionPath.nearStart(pX, pY, cX1, cY, cX2, cY
                    , false, this.getCentralX(), this.getCentralY());
            if (motionCase == 1) {
                cX = cX1;
                leftcollision = 1;
            } else if (motionCase == 2) {
                cX = cX2;
                leftcollision = 0;
            } else {
                return -1;
            }
        } else {
            double cX1 = obj.getX() - (double) this.getWidth() /2;
            double cY1 = motionPath.getA() * cX1 + motionPath.getB();
            double cX2 = obj.getX() + obj.getWidth() + (double) this.getHeight() /2;
            double cY2 = motionPath.getA() * cX2 + motionPath.getB();
            int posibleCase = findPosibleCollisionPoint(pX, pY, cX1, cY1, cX2, cY2);
            if (posibleCase == 1) {
                cX = cX1;
                cY = cY1;
                leftcollision = 1;
            } else if (posibleCase == 2) {
                cX = cX2;
                cY = cY2;
                leftcollision = 0;
            }  else {
                return -1;
            }
        }
        if (cY >= obj.getY() && cY <= obj.getY() + obj.getHeight()) {
            this.collisionCentralX = cX;
            this.collisionCentralY = cY;
            return leftcollision;
        }
        return -1;
    }

    public int isUpCollision(GameObject obj) {
        int upcollision = -1;
        double pY = this.preCentralY;
        double pX = this.preCentralX;
        System.out.println("pY: " + pY + " pX: " + pX + this.getCentralX() + " " + this.getCentralY());
        motionPath.calculateCoefficient(pX, pY, this.getCentralX(), this.getCentralY());
        double cX;
        double cY;

        if (abs(this.getCentralY() - pY)  <= 0.0001 && abs(this.getCentralX() - pX)  <= 0.0001) {
            return -1;
        } else if (abs(this.getCentralX() - pX)  <= 0.0001) {
            double cY1 = obj.getY() - (double) this.getWidth() /2;
            double cY2 = obj.getY() + obj.getHeight() + (double) this.getHeight() /2;
            cX = this.getCentralX();
            int motionCase = motionPath.nearStart(pX, pY, cX, cY1, cX, cY2
                    , false, this.getCentralX(), this.getCentralY());
            if (motionCase == 1) {
                cY = cY1;
                upcollision = 1;
            } else if (motionCase == 2) {
                cY = cY2;
                upcollision = 0;
            } else {
                return -1;
            }
        } else if (abs(this.getCentralY() - pY)  <= 0.0001) {
            return -1;
        } else {
            double cY1 = obj.getY() - (double) this.getHeight() /2;
            double cX1 = (cY1 - motionPath.getB()) / motionPath.getA();
            double cY2 = obj.getY() + obj.getHeight() + (double) this.getHeight() /2;
            double cX2 = (cY2 - motionPath.getB()) / motionPath.getA();
            int posibleCase = findPosibleCollisionPoint(pX, pY, cX1, cY1, cX2, cY2);

            if (posibleCase == 1) {
                cX = cX1;
                cY = cY1;
                upcollision = 1;
            } else if (posibleCase == 2) {
                cX = cX2;
                cY = cY2;
                upcollision = 0;
            }  else {
                return -1;
            }
        }

        if (cX >= obj.getX() && cX <= obj.getX() + obj.getWidth()) {
            this.collisionCentralX = cX;
            this.collisionCentralY = cY;
            return upcollision;
        }

        return -1;
    }

    public boolean inCorner(GameObject obj, double cX, double cY, int corner) {

        if (corner == 1) {
            return cX < obj.getX() && cY < obj.getY();
        } else if (corner == 2) {
            return cX > obj.getX() + obj.getWidth() && cY < obj.getY();
        } else if (corner == 3) {
            return cX < obj.getX() && cY > obj.getY() +  obj.getHeight();
        } else if (corner == 4) {
            return  cX > obj.getX() + obj.getWidth() && cY > obj.getY() +  obj.getHeight();
        }

        return false;
    }

    public int isCornerCollision_(GameObject obj, double X0, double Y0, int corner) {

        int cornercollision = -1;
        double pY = this.preCentralY;
        double pX = this.preCentralX;
        motionPath.calculateCoefficient(pX, pY, this.getCentralX(), this.getCentralY());
        double cX;
        double cY;

        if (abs(this.getCentralY() - pY)  <= 0.0001 && abs(this.getCentralX() - pX)  <= 0.0001) {
            return -1;
        } else if (abs(this.getCentralX() - pX)  <= 0.0001) {
            cX = this.getCentralX();
            double cY1;
            double cY2;

            if ((double) (this.getWidth() * this.getWidth()) / 4
                    - (cX - X0) * (cX - X0) >= 0) {
                if (abs(cX - X0 + (double) this.getWidth() / 2) <= 0.0001) {
                    return -1;
                }
                cY1 = sqrt((double) (this.getWidth() * this.getWidth()) / 4
                        - (cX - X0) * (cX - X0)) + Y0;
                cY2 = -sqrt((double) (this.getWidth() * this.getWidth()) / 4
                        - (cX - X0) * (cX - X0)) + Y0;
                if (!inCorner(obj, cX, cY1, corner) && !inCorner(obj, cX, cY2, corner)) {
                    return -1;
                }
            } else {
                return -1;
            }
            int motionCase = motionPath.nearStart(pX, pY, cX, cY1, cX, cY2
                    , false, this.getCentralX(), this.getCentralY());

            if (motionCase == 1 && inCorner(obj, cX, cY1, corner)) {
                cY = cY1;
                cornercollision = 1;
            } else if (motionCase == 2 && inCorner(obj, cX, cY2, corner)) {
                cY = cY2;
                cornercollision = 0;
            } else {
                return -1;
            }
        } else if (abs(this.getCentralY() - pY)  <= 0.0001) {
            cY = this.getCentralY();
            double cX1;
            double cX2;

            if ((double) (this.getWidth() * this.getWidth()) / 4
                    - (cY - Y0) * (cY - Y0) >= 0) {
                if (abs(cY - Y0 + (double) this.getWidth() /2) <= 0.0001) {
                    return -1;
                }
                cX1 = sqrt((double) (this.getWidth() * this.getWidth()) / 4
                        - (cY - Y0) * (cY - Y0)) + X0;
                cX2 = -sqrt((double) (this.getWidth() * this.getWidth()) / 4
                        - (cY - Y0) * (cY - Y0)) + X0;
                if (!inCorner(obj, cX1, cY, corner) && !inCorner(obj, cX2, cY, corner)) {
                    return -1;
                }
            } else {
                return -1;
            }
            int motionCase = motionPath.nearStart(pX, pY, cX1, cY, cX2, cY
                    , false, this.getCentralX(), this.getCentralY());
            if (motionCase == 1 && inCorner(obj, cX1, cY, corner)) {
                cX = cX1;
                cornercollision = 1;
            } else if (motionCase == 2 && inCorner(obj, cX2, cY, corner)) {
                cX = cX2;
                cornercollision = 0;
            } else {
                return -1;
            }
        } else {
            CollisionEquation collisionEquation = new CollisionEquation(motionPath, X0, Y0
                    , (double) this.getWidth() /2);
            double cX1;
            double cY1;
            double cX2;
            double cY2;
            collisionEquation.solve();

            if (collisionEquation.getDelta() < 0) {
                return -1;
            }

            cX1 = collisionEquation.getX1();
            cY1 = motionPath.getA() * cX1 + motionPath.getB();
            cX2 = collisionEquation.getX2();
            cY2 = motionPath.getA() * cX2 + motionPath.getB();
            int motionCase = motionPath.nearStart(pX, pY, cX1, cY1, cX2, cY2
                    , true, this.getCentralX(), this.getCentralY());

            if (motionCase == 1 && inCorner(obj, cX1, cY1, corner)) {
                cX = cX1;
                cY = cY1;
                cornercollision = 1;
            } else if (motionCase == 2 && inCorner(obj, cX2, cY2, corner)) {
                cX = cX2;
                cY = cY2;
                cornercollision = 0;
            }  else {
                return -1;
            }
        }

        if (inCorner(obj, cX, cY, corner)) {
            this.collisionCentralX = cX;
            this.collisionCentralY = cY;
        }

        return cornercollision;
    }

    public int isCornerCollision(GameObject obj) {
        if (isCornerCollision_(obj, obj.getX(), obj.getY(),1) != -1) {
            return 1;
        } else if (isCornerCollision_(obj, obj.getX() + obj.getWidth(), obj.getY(), 2) != -1) {
            return 2;
        } else if (isCornerCollision_(obj, obj.getX(), obj.getY() + obj.getHeight(), 3) != -1) {
            return 3;
        } else if (isCornerCollision_(obj, obj.getX() + obj.getWidth(), obj.getY() + obj.getHeight(), 4) != -1) {
            return 4;
        }

        return -1;
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

    public boolean isCollision(GameObject obj) {
        if (this.isPossibleToCollision(obj)) {
            return this.isLeftCollision(obj) != -1 || this.isUpCollision(obj) != -1 || this.isCornerCollision(obj) != -1;
        }
        return false;
    }
}