package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;
import java.util.List;
import java.util.ArrayList;

public class TripleBallPowerUp extends PowerUp {

    public TripleBallPowerUp(int x, int y) {
        super(x, y, 20, 20, Color.MAGENTA);
        this.duration = 0;
    }

    @Override
    public void activateEffect(GameManager gameManager) {
        List<GameObject> objectList = gameManager.getObjectList();

        // Tìm tất cả các bóng hiện có
        List<Ball> existingBalls = new ArrayList<>();
        List<GameObject> currentObjects = new ArrayList<>(objectList);

        for (GameObject obj : currentObjects) {
            if (obj instanceof Ball) {
                existingBalls.add((Ball) obj);
            }
        }

        // Tạo danh sách bóng mới
        List<Ball> newBalls = new ArrayList<>();

        for (Ball originalBall : existingBalls) {
            if (newBalls.size() < 6) { // Giới hạn tối đa
                int newBallY = originalBall.getY() - 50;

                // Bóng thứ 2 - góc 30 độ
                Ball ball2 = new Ball(
                        originalBall.getX(),
                        newBallY,
                        originalBall.getWidth(),
                        originalBall.getHeight()
                );
                // ĐẢM BẢO GÓC VÀ TỐC ĐỘ CHÍNH XÁC
                double originalSpeed = originalBall.getSpeed();
                ball2.setMotionAngle(originalBall.getMotionAngle() + Math.toRadians(30));
                ball2.setSpeed(originalSpeed); // DÙNG ĐÚNG TỐC ĐỘ CỦA BÓNG GỐC
                ball2.set_File_image("res/ballImage/ball30.png");

                // Bóng thứ 3 - góc -30 độ
                Ball ball3 = new Ball(
                        originalBall.getX(),
                        newBallY,
                        originalBall.getWidth(),
                        originalBall.getHeight()
                );
                ball3.setMotionAngle(originalBall.getMotionAngle() - Math.toRadians(30));
                ball3.setSpeed(originalSpeed); // DÙNG ĐÚNG TỐC ĐỘ CỦA BÓNG GỐC
                ball3.set_File_image("res/ballImage/ball30.png");

                newBalls.add(ball2);
                newBalls.add(ball3);
            }
        }

        objectList.addAll(newBalls);
        System.out.println("Triple Ball PowerUp Activated! Created " + newBalls.size() + " new balls!");
    }

    @Override
    public void deactivateEffect(GameManager gameManager) {
        // Không cần deactivate
    }
}