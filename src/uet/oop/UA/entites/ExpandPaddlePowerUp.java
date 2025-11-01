package uet.oop.UA.entites;

import java.awt.*;
import uet.oop.UA.GameManager;
import java.util.List;

public class ExpandPaddlePowerUp extends PowerUp {
    private static final int EXPAND_AMOUNT = 90;
    private int originalWidth;
    private boolean isActivated = false;
    private static boolean paddleExpanded = false; // Biến static để theo dõi trạng thái toàn cục

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, 20, 20, Color.BLUE);
        this.duration = 900;
    }

    @Override
    public void activateEffect(GameManager gameManager) {
        // Nếu paddle đã được mở rộng, chỉ cần reset timer chứ không mở rộng thêm
        if (paddleExpanded) {
            System.out.println("Paddle already expanded, resetting timer only");
            return;
        }

        if (isActivated) return;

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
                if (newX + newWidth > 1000) {
                    newX = 1000 - newWidth;
                }

                // CẬP NHẬT KÍCH THƯỚC VÀ VỊ TRÍ
                paddle.setWidth(newWidth);
                paddle.setX(newX);

                // Load ảnh paddle mở rộng
                paddle.set_File_image("res/paddleImage/pad270.png");

                // Đảm bảo ảnh được cập nhật
                if (paddle.getImage() == null) {
                    paddle.setColor(Color.BLUE);
                    paddle.set_Drawed_Paddle_image();
                }

                isActivated = true;
                paddleExpanded = true; // Đánh dấu paddle đã được mở rộng
                System.out.println("Expand Paddle PowerUp Activated! New width: " + newWidth + ", image: pad270.png");
                break;
            }
        }
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

                // CẬP NHẬT KÍCH THƯỚC VÀ VỊ TRÍ
                paddle.setWidth(this.originalWidth);
                paddle.setX(newX);

                // Load lại ảnh paddle ban đầu
                paddle.set_File_image("res/paddleImage/pad180.png");

                // Đảm bảo ảnh được cập nhật
                if (paddle.getImage() == null) {
                    paddle.setColor(Color.WHITE);
                    paddle.set_Drawed_Paddle_image();
                }

                isActivated = false;
                paddleExpanded = false; // Reset trạng thái khi deactivate
                System.out.println("Expand Paddle PowerUp Deactivated! Width restored to: " + originalWidth + ", image: pad180.png");
                break;
            }
        }
    }

    // Thêm getter để kiểm tra trạng thái
    public static boolean isPaddleExpanded() {
        return paddleExpanded;
    }
}