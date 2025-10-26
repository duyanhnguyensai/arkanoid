package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;

public class ExpandPaddlePowerUp extends PowerUp {
    private static final int EXPAND_AMOUNT = 60;
    private int originalWidth;

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, 20, 20, Color.BLUE);
        this.duration = 900; // 15 giây với 60 FPS
    }

    @Override
    public void activateEffect(GameManager gameManager) {
        // Tìm paddle và mở rộng nó
        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Paddle) {
                Paddle paddle = (Paddle) obj;
                this.originalWidth = paddle.getWidth();
                paddle.setWidth(paddle.getWidth() + EXPAND_AMOUNT);
                paddle.setX(paddle.getX() - EXPAND_AMOUNT / 2); // Giữ paddle ở giữa
                paddle.set_Drawed_Paddle_image(); // Cập nhật hình ảnh
                break;
            }
        }
        System.out.println("Expand Paddle PowerUp Activated!");
    }

    @Override
    public void deactivateEffect(GameManager gameManager) {
        // Khôi phục kích thước paddle về ban đầu
        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Paddle) {
                Paddle paddle = (Paddle) obj;
                paddle.setWidth(this.originalWidth);
                paddle.setX(paddle.getX() + EXPAND_AMOUNT / 2); // Điều chỉnh vị trí
                paddle.set_Drawed_Paddle_image(); // Cập nhật hình ảnh
                break;
            }
        }
        System.out.println("Expand Paddle PowerUp Deactivated!");
    }
}