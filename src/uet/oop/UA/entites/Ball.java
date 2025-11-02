package uet.oop.UA.entites;

import uet.oop.UA.logic.CollisionEquation;
import uet.oop.UA.logic.LinearEquation;

import java.awt.*;
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
    protected double motionAngle = toRadians(270);
    protected double cosaAngle;
    protected double sinAngle;
    protected double radius;
    protected double preCentralX = this.getCentralX();
    protected double preCentralY = this.getCentralY();
    protected double collisionCentralX;
    protected double collisionCentralY;
    protected LinearEquation motionPath = new LinearEquation();

    // THÊM: Biến theo dõi va chạm paddle
    private boolean wasCollidingWithPaddle = false;
    private GameObject lastPaddle = null;


    /**
     * constructor khoi tao ball.
     * @param x tọa độ x.
     * @param y tọa đọ y.
     * @param width chiều rộng.
     * @param height chiều dài.
     */
    public Ball(int x, int y, int width, int height) {
        super(x,y,width,height);
        this.radius = (double) width / 2;
        // Đường dẫn tới ảnh
        this.set_File_image("res/ballImage/balln30.png");
    }

    /**
     * phương thức lấy motionAngle.
     * @return motionAngle là góc di chuyển.
     */
    public double getMotionAngle() {
        return this.motionAngle;
    }

    /**
     * setter cho motionAngle.
     * @param motionAngle góc di chuyển.
     */
    public void setMotionAngle(double motionAngle) {
        this.motionAngle = motionAngle;
    }

    /**
     * phương thức di chuyển trước khi bắn bóng.
     * @param obj bóng.
     */
    public void MoveBeforeStart(GameObject obj) {
        this.setX(obj.getX()+obj.getWidth()/2-this.getWidth()/2);
        this.setY(obj.getY()-this.getHeight());
    }

    /**
     * chuẩn hóa motionAngle về từ 0 -> 2*Pi(aCircle).
     */
    public void toStandardizeMotionAngle() {

        while (this.motionAngle < 0) {
            this.motionAngle += aCircle;
        }

        while (this.motionAngle > aCircle) {
            this.motionAngle -= aCircle;
        }
    }

    /**
     * cập nhật tọa độ tâm trước khi di chuyển.
     */
    public void updatePreCentral() {
        this.preCentralX = this.getCentralX();
        this.preCentralY = this.getCentralY();
    }

    /**
     * phương thức di chuyển với góc angle.
     * @param angle góc di chuyển.
     */
    public void move(double angle) {
        updateCentral();
        updatePreCentral();
        this.motionAngle = angle;
        this.cosaAngle = cos(this.motionAngle);
        this.sinAngle = sin(this.motionAngle);

        this.directionX = this.speed * (this.cosaAngle);
        this.directionY = this.speed * (this.sinAngle);

        // DEBUG: In tốc độ để kiểm tra
        if (Math.random() < 0.01) { // Chỉ in thỉnh thoảng để tránh spam
            System.out.println("Ball speed: " + this.speed + ", directionX: " + this.directionX + ", directionY: " + this.directionY);
        }

        this.setX(this.getX() + (int) this.directionX);
        this.setY(this.getY() + (int) this.directionY);
    }

    /**
     * xử lý va chạm tường.
     * theo quy tắc tính góc dựa trên đường tròn đơn vị với trục y hướng xuông.
     * x bóng < biên trái thì góc va chạm sẽ nằm từ 90-180 hoặc 180-270
     * ngoại lệ thì cho góc va chạm thành 270 + 45 = 315.
     * x lớn hơn biên phải - bán kính thì
     * góc va chạm từ 0-90 hoặc 270-360, ngoại lệ = 270 - 45 = 225.
     *tương tự với tường trên.
     * @return true nếu va chạm.
     */
    // THAY ĐỔI: Trả về boolean để biết có va chạm tường không
    public boolean handleWallCollision() {
        boolean hitWall = false;
        toStandardizeMotionAngle();
        // tường 2 bên
        if (this.getX() <= 0) {
            hitWall = true;
            if (this.motionAngle >= toRadians(90) && this.motionAngle <= toRadians(180)) {
                this.motionAngle = (PI-(this.motionAngle));
            } else if (this.motionAngle >= 180*PI/180 && this.motionAngle <= 270*PI/180) {
                this.motionAngle = (aCircle-(this.motionAngle - toRadians(180)));
            } else {
                this.motionAngle = toRadians(315);
            }

            this.setX(0);
            /*
            căn chỉnh để không loop bật qua lại 2 bên tường.
             */
            if (motionAngle >= toRadians(0) && motionAngle <= toRadians(10)) {
                motionAngle += toRadians(5);
            }

        } else if (this.getX() >= GAME_WIDTH - this.getWidth()) {
            hitWall = true;
            if (this.motionAngle >= toRadians(0) && this.motionAngle <= toRadians(90)) {
                this.motionAngle =  (PI-this.motionAngle);
            } else if (this.motionAngle >= toRadians(270) && this.motionAngle <= toRadians(360)) {
                this.motionAngle = (PI+(2*PI-this.motionAngle));
            } else {
                this.motionAngle = toRadians(225);
            }
            this.setX(GAME_WIDTH - this.getWidth());

            /*
            căn chỉnh tương tự.
             */
            if (motionAngle >= toRadians(170) && motionAngle <= toRadians(190)) {
                motionAngle -= toRadians(5);
            }
        }
        // tường trên
        if (this.getY() <= 0) {
            hitWall = true;
            if (this.motionAngle >= toRadians(180) && this.motionAngle <= toRadians(270)) {
                this.motionAngle = (2*PI-(this.motionAngle));
            } else if (this.motionAngle >= toRadians(270) && this.motionAngle <= toRadians(360)) {
                this.motionAngle =  (2*PI-(this.motionAngle));
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

    /**
     * phương thức để kiểm tra bóng có active không.
      */

    public boolean isActive() {
        return this.getY() < GAME_HEIGHT - this.getHeight();
    }

    /**
     * method setActive.
     * @param active giá trị active.
     */
    public void setActive(boolean active) {
        // Không cần set cụ thể, vì active phụ thuộc vào vị trí Y
    }

    /**
     * điều kiện có thể va chạm.
     * @param obj vật kiểm tra.
     * @return nếu tâm bóng thuộc đường tròn có bán kính là
     * đường chéo vật + bán kính bóng
     * (khoảng cách lớn nhất để 1 hình tròn tiếp xúc với hình chữ nhật).
     */
    public boolean isPossibleToCollision(GameObject obj) { // chỉ kiểm tra va chạm của bóng với vật hình chữ nhật
        return this.distant2CentralObj(obj)
                <= this.radius + sqrt((obj.getWidth() * obj.getWidth()) //sqrt() là đường chéo / 2.
                + (obj.getHeight() * obj.getHeight())) / 2;
    }

    /**
     * kiểm tra va chạm trái.
     * bằng cách kiểm tra xem tâm bóng có nằm trên các cạnh
     * bên tình chữ nhật tịnh tiến 1 khoảng  bán kính.
     * @param obj vật kiểm tra.
     * @return 1 nếu va chạm trái 0 nếu va chạm phải
     * -1 nếu ko va chạm.
     */
    public int isLeftCollisionp(GameObject obj) {
        if (this.getCentralY() >= obj.getY() && this.getCentralY() <= obj.getY() + obj.getHeight()) {

            if (abs(obj.getX() - this.getCentralX()) <= abs(obj.getX() + obj.getWidth() - this.getCentralX())) {
                if (abs(obj.getX() - this.getCentralX()) <= radius) {
                    return 1;
                }
                return -1;
            } else if (abs(obj.getX() - this.getCentralX()) > abs(obj.getX() + obj.getWidth() - this.getCentralX())) {
                if (abs(obj.getX() + obj.getWidth() - this.getCentralX()) <= radius) {
                    return 0;
                }
                return -1;
            }
        }
        return -1;
    }

    /**
     * kiểm tra va chạm trên.
     * bằng cách kiểm tra xem tâm bóng có nằm trên các cạnh
     * trên dưới tình chữ nhật tịnh tiến 1 khoảng  bán kính.
     * @param obj vật kiểm tra.
     * @return 1 nếu va chạm trên 0 nếu va chạm dưới
     * -1 nếu ko va chạm.
     */
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


    // SỬA HOÀN TOÀN: Sử dụng AABB collision detection thay vì isPossibleToCollision

    /**
     * xử lý va chạm với pad bằng kt va chạm cũ.
     * @param obj pad.
     * @return true nếu va chạm, false nếu khác.
     */
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
                /*
                xử lý va chạm trên.
                 */
                if (upcollision == 1) {
                    // Va chạm từ trên xuống (bóng chạm đỉnh paddle)
                    this.setY(obj.getY() - this.getWidth());    //cho bóng nằm trên pad
                    double cAngle = this.getCentralX() - obj.getCentralX(); //hiệu tâm bóng đến tâm theo x.
                    double scale = obj.getWidth() / Paddle.originWidth;
                    System.out.println("Paddle hit - top, angle: " + cAngle);
                    this.motionAngle = toRadians(cAngle + (double) obj.getWidth() / 2)
                            / (2 * scale) + toRadians(45 + 180);
                    /*cAngle + pad.width / 2 để tâm bóng = x pad thì góc di chuyển
                    = 0 + 45 + 180(45 độ so với trục x hướng sang trái)
                    chia cho 2 * scale để chia tỉ lệ theo độ dài pad
                     */
                }
            } else if (leftcollision != -1) {
                if (leftcollision == 1) {
                    // Va chạm từ bên trái
                    this.setX(obj.getX() - this.getWidth());
                    if (this.getMotionAngle() >= toRadians(0) && this.getMotionAngle() <= toRadians(90)) {
                        this.setMotionAngle(PI-this.getMotionAngle());
                    }
                } else if (leftcollision == 0) {
                    // Va chạm từ bên phải
                    this.setX(obj.getX() + obj.getWidth());
                    if (this.getMotionAngle() >= toRadians(90) && this.getMotionAngle() <= toRadians(180)) {
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

    /**
     * tìm điểm giao của quỹ đạo chuyển dộng với các biên của gạch.
     * @param pX tọa độ tâm X trước khi di chuyển.
     * @param pY tọa độ tâm Y trước.
     * @param cX1 tọa độ giao X1.
     * @param cY1 tọa độ giao Y1.
     * @param cX2 tọa độ giao X2.
     * @param cY2 tọa độ giao Y2.
     * @return 1 nếu là điểm 1(X1, Y1); 2 nếu là điểm 2 -1 nếu không có.
     */
    public int findPosibleCollisionPoint(double pX, double pY, double cX1
            , double cY1, double cX2, double cY2) {
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

    /**
     * kiểm tra va chạm trái.
     * @param obj vật kiểm tra.
     * @return 1 nếu trái, 0 nếu phải, ko va chạm -1;
     */
    public int isLeftCollision(GameObject obj) {
        int leftcollision;
        double pY = this.preCentralY;   //lấy tọa độ trước
        double pX = this.preCentralX;
        motionPath.calculateCoefficient(pX, pY, this.getCentralX(),
                this.getCentralY());    //tính các hệ số của quỹ đạo cd.
        double cX; // tọa độ giao
        double cY;

        //vật đứng yên;(0.0001 là độ chính xác)
        if (abs(this.getCentralY() - pY)  <= 0.0001
                && abs(this.getCentralX() - pX)  <= 0.0001) {
            return -1;
        } else if (abs(this.getCentralX() - pX)  <= 0.0001) {
            //quỹ đạo cd có dạng y = B chỉ đi trên bề mặt gạch ko va chạm
            return -1;
        } else if (abs(this.getCentralY() - pY)  <= 0.0001) {
            //quỹ đạo cd có dạng X = B nên Y giao luôn bằng Y.
            double cX1 = obj.getX() - radius;   //giao trái
            double cX2 = obj.getX() + obj.getWidth() + radius;  //giao phải
            cY = this.getCentralY();
            // kiểm tra thuộc quỹ đạo di chuyển và trong đoạn di chuyển được.
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
            // trường hợp di chuyển xiên.
            double cX1 = obj.getX() - radius;
            double cY1 = motionPath.getA() * cX1 + motionPath.getB();
            double cX2 = obj.getX() + obj.getWidth() + radius;
            double cY2 = motionPath.getA() * cX2 + motionPath.getB();
            int posibleCase = findPosibleCollisionPoint(pX, pY, cX1, cY1, cX2, cY2);
            if (posibleCase == 1) {
                cX = cX1;
                cY = cY1;
                leftcollision = 1;
            } else if (posibleCase == 0) {
                cX = cX2;
                cY = cY2;
                leftcollision = 0;
            }  else {
                return -1;
            }
        }
        if (cY >= obj.getY() && cY <= obj.getY() + obj.getHeight()) {
            //kiểm tra xem thuộc quỹ đạo va chạm không
            this.collisionCentralX = cX;
            this.collisionCentralY = cY;
            return leftcollision;
        }
        return -1;
    }

    /**
     * kiểm tra va chạm trên.
     * @param obj vật kiểm tra.
     * @return 1 nếu trên, 0 nếu dưới, ko va chạm -1;
     */
    public int isUpCollision(GameObject obj) {
        int upcollision;
        double pY = this.preCentralY;
        double pX = this.preCentralX;
        // lấy tọa độ trc, quỹ đạo dc
        motionPath.calculateCoefficient(pX, pY, this.getCentralX(), this.getCentralY());
        double cX;
        double cY;

        //kt trường hợp đặc bt
        if (abs(this.getCentralY() - pY)  <= 0.0001
                && abs(this.getCentralX() - pX)  <= 0.0001) {
            return -1;
        } else if (abs(this.getCentralX() - pX)  <= 0.0001) {
            double cY1 = obj.getY() - radius;
            double cY2 = obj.getY() + obj.getHeight() + radius;
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
            double cY1 = obj.getY() - radius;
            double cX1 = (cY1 - motionPath.getB()) / motionPath.getA();
            double cY2 = obj.getY() + obj.getHeight() + radius;
            double cX2 = (cY2 - motionPath.getB()) / motionPath.getA();
            int posibleCase = findPosibleCollisionPoint(pX, pY
                    , cX1, cY1, cX2, cY2);

            if (posibleCase == 1) {
                cX = cX1;
                cY = cY1;
                upcollision = 1;
            } else if (posibleCase == 0) {
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

    /**
     * kiểm tra nằm trong các góc.
     * @param obj vật kiểm tra.
     * @param cX điểm giao X.
     * @param cY điểm giao Y.
     * @param corner số thứ tự
     * 1 là góc trên bên trái, 2 là trên phải
     * 3 là dưới trái, 4 là dưới phải.
     * @return true nếu nằm trong góc.
     */
    public boolean inCorner(GameObject obj, double cX, double cY, int corner) {

        if (corner == 1) {
            return cX < obj.getX() && cY < obj.getY();
        } else if (corner == 2) {
            return cX > obj.getX() + obj.getWidth() && cY < obj.getY();
        } else if (corner == 3) {
            return cX < obj.getX() && cY > obj.getY() +  obj.getHeight();
        } else if (corner == 4) {
            return  cX > obj.getX() + obj.getWidth()
                    && cY > obj.getY() +  obj.getHeight();
        }

        return false;
    }

    /**
     * kiểm tra va chạm góc.
     * @param obj vật kt.
     * @param X0 tọa độ X của góc
     * @param Y0 tọa độ Y góc
     * @param corner stt góc.
     * @return 1 nếu lấy điểm 1, 0 nếu điểm 2;
     * -1 nếu không va chạm.
     */
    public int isCornerCollision_(GameObject obj, double X0, double Y0, int corner) {

        int cornercollision;
        double pY = this.preCentralY;
        double pX = this.preCentralX;
        motionPath.calculateCoefficient(pX, pY, this.getCentralX(), this.getCentralY());
        double cX;
        double cY;

        if (abs(this.getCentralY() - pY)  <= 0.0001
                && abs(this.getCentralX() - pX)  <= 0.0001) {
            return -1;
        } else if (abs(this.getCentralX() - pX)  <= 0.0001) {
            cX = this.getCentralX();
            double cY1;
            double cY2;

            //kiểm tra giao với đường tròn bán kính radius tâm là góc
            if (radius * radius - (cX - X0) * (cX - X0) >= 0) {
                if (abs(cX - X0 + (double) this.getWidth() / 2) <= 0.0001) {
                    //nếu sượt qua 0 va chạm
                    return -1;
                }
                //nếu có giao.
                cY1 = sqrt(radius * radius - (cX - X0) * (cX - X0)) + Y0;
                cY2 = -sqrt(radius * radius - (cX - X0) * (cX - X0)) + Y0;
                if (!inCorner(obj, cX, cY1, corner)
                        && !inCorner(obj, cX, cY2, corner)) {
                    // cả 2 điểm ko thuộc góc
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

            if (radius * radius - (cY - Y0) * (cY - Y0) >= 0) {
                if (abs(cY - Y0 + radius) <= 0.0001) {
                    return -1;
                }
                cX1 = sqrt(radius * radius - (cY - Y0) * (cY - Y0)) + X0;
                cX2 = -sqrt(radius * radius - (cY - Y0) * (cY - Y0)) + X0;
                if (!inCorner(obj, cX1, cY, corner)
                        && !inCorner(obj, cX2, cY, corner)) {
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
            collisionEquation.solve(); //giải pt giao.

            if (collisionEquation.getDelta() < 0) {
                //vô No
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

    /**
     * kiểm tra va chạm cả 4 góc.
     * @param obj vật kt.
     * @return 1 nếu góc 1, 2 góc 2...
     */
    public int isCornerCollision(GameObject obj) {
        if (isCornerCollision_(obj, obj.getX(), obj.getY(),1) != -1) {
            return 1;
        } else if (isCornerCollision_(obj, obj.getX() + obj.getWidth()
                , obj.getY(), 2) != -1) {
            return 2;
        } else if (isCornerCollision_(obj, obj.getX()
                , obj.getY() + obj.getHeight(), 3) != -1) {
            return 3;
        } else if (isCornerCollision_(obj, obj.getX() + obj.getWidth()
                , obj.getY() + obj.getHeight(), 4) != -1) {
            return 4;
        }

        return -1;
    }

    /**
     * xử lý va chạm gạch.
     * @param obj gạch.
     * @return 1 nếu va chạm, 0 nếu ko.
     */
    public int handleBrickCollision(GameObject obj) {

        if (this.isPossibleToCollision(obj)) {
            int upCollisionb = this.isUpCollision(obj);
            int leftCollisionb = this.isLeftCollision(obj);
            int cornerCollisonb = this.isCornerCollision(obj);
            if (upCollisionb != -1) { //va chạm trên
                if (upCollisionb == 1) { //xử lý
                    //this.setY(obj.getY() - this.getWidth());
                    this.setCentral((int) this.collisionCentralX
                            , (int) this.collisionCentralY);
                    //di chuyển bóng về vị trí va chạm

                    if (this.getMotionAngle() >= toRadians(0)
                            && this.getMotionAngle() <= toRadians(90)) {
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    } else if (this.getMotionAngle() >= toRadians(90)
                            && this.getMotionAngle() <= toRadians(180)) {
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    } else { //ngoai le
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    }
                } else if (upCollisionb == 0) {
                    this.setCentral((int) this.collisionCentralX
                            , (int) this.collisionCentralY);

                    if (this.getMotionAngle() >= toRadians(180)
                            && this.getMotionAngle() <= toRadians(270)) {
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    } else if (this.getMotionAngle() >= toRadians(270)
                            && this.getMotionAngle() <= toRadians(360)) {
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    } else {
                        this.setMotionAngle(2*PI-(this.getMotionAngle()));
                    }
                }

                return 1;
            } else if (leftCollisionb != -1) {
                if (leftCollisionb == 1) {
                    //this.setX(obj.getX() - this.getWidth());
                    this.setCentral((int) this.collisionCentralX, (int) this.collisionCentralY);
                    if (this.getMotionAngle() >= toRadians(0)
                            && this.getMotionAngle() <= toRadians(90)) {
                        this.setMotionAngle(PI - this.getMotionAngle());
                    } else if (this.getMotionAngle() >= toRadians(270)
                            && this.getMotionAngle() <= toRadians(360)) {
                        this.setMotionAngle(PI + (aCircle - this.getMotionAngle()));
                    }
                } else if (leftCollisionb == 0) {
                    this.setCentral((int) this.collisionCentralX, (int) this.collisionCentralY);
                    if (this.getMotionAngle() >= toRadians(90)
                            && this.getMotionAngle() <= toRadians(180)) {
                        this.setMotionAngle(PI - (this.getMotionAngle()));
                    } else if (this.getMotionAngle() >= toRadians(180)
                            && this.getMotionAngle() <= toRadians(270)) {
                        this.setMotionAngle(aCircle - (this.getMotionAngle() - toRadians(180)));
                    }
                }

                return 1;
            } else if (cornerCollisonb > 0) {

                this.setCentral((int) this.collisionCentralX,
                        (int) this.collisionCentralY);
                this.motionAngle = this.getMotionAngle() + PI;
                toStandardizeMotionAngle();

                return 1;
            }
        }
        return 0;
    }

    /**
     * kt va chạm.
     * @param obj vật kt.
     * @return true nếu va chạm.
     */
    public boolean isCollision(GameObject obj) {
        if (this.isPossibleToCollision(obj)) {
            return this.isLeftCollision(obj) != -1 || this.isUpCollision(obj) != -1 || this.isCornerCollision(obj) != -1;
        }
        return false;
    }
}