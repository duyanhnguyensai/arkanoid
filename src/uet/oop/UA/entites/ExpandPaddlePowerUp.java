package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;

public class ExpandPaddlePowerUp extends PowerUp {
    private static final int EXPAND_AMOUNT = 60;
    private int originalWidth;
    private boolean isActivated = false;

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, 20, 20, Color.BLUE);
        this.duration = 900;
    }

    @Override
    public void activateEffect(GameManager gameManager) {
        if (isActivated) return; // TRÁNH KÍCH HOẠT NHIỀU LẦN

        // Tìm paddle và mở rộng nó
        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Paddle) {
                Paddle paddle = (Paddle) obj;
                this.originalWidth = paddle.getWidth();

                // MỞ RỘNG ĐỀU HAI BÊN - GIỮ VỊ TRÍ TRUNG TÂM
                int newWidth = this.originalWidth + EXPAND_AMOUNT;
                int centerX = paddle.getX() + paddle.getWidth() / 2;
                int newX = centerX - newWidth / 2;

                // KIỂM TRA BIÊN
                if (newX < 0) {
                    newX = 0;
                }
                if (newX + newWidth > 1000) { // GAME_WIDTH = 1000
                    newX = 1000 - newWidth;
                }

                paddle.setWidth(newWidth);
                paddle.setX(newX);

                // KHÔNG gọi set_Drawed_Paddle_image() để giữ nguyên ảnh gốc
                // Chỉ cập nhật kích thước, màu sắc giữ nguyên

                isActivated = true;
                break;
            }
        }
        System.out.println("Expand Paddle PowerUp Activated! New width: " + (originalWidth + EXPAND_AMOUNT));
    }

    @Override
    public void deactivateEffect(GameManager gameManager) {
        if (!isActivated) return;

        // Khôi phục kích thước paddle về ban đầu
        for (GameObject obj : gameManager.getObjectList()) {
            if (obj instanceof Paddle) {
                Paddle paddle = (Paddle) obj;

                // KHÔI PHỤC VỊ TRÍ TRUNG TÂM
                int centerX = paddle.getX() + paddle.getWidth() / 2;
                int newX = centerX - this.originalWidth / 2;

                // KIỂM TRA BIÊN KHI THU NHỎ
                if (newX < 0) {
                    newX = 0;
                }
                if (newX + this.originalWidth > 1000) {
                    newX = 1000 - this.originalWidth;
                }

                paddle.setWidth(this.originalWidth);
                paddle.setX(newX);

                isActivated = false;
                break;
            }
        }
        System.out.println("Expand Paddle PowerUp Deactivated! Width restored to: " + originalWidth);
    }
}