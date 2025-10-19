package uet.oop.UA.entites;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ExpandPaddlePowerUp extends PowerUp {
    private int originalWidth;
    private final int EXPAND_AMOUNT = 50; // Mở rộng thêm 50 pixels
    
    public ExpandPaddlePowerUp() {
        super();
        setType("EXPAND_PADDLE");
        setDuration(8000); // 8 seconds
        setColor("orange");
        setWidth(30);
        setHeight(15);
        setDy(2); // Tốc độ rơi xuống
    }
    
    @Override
    protected void applyEffect(Paddle paddle, Ball ball) {
        // Lưu lại kích thước gốc
        originalWidth = paddle.getWidth();
        
        // Mở rộng paddle
        paddle.setWidth(originalWidth + EXPAND_AMOUNT);
        
        // Đảm bảo paddle không vượt quá biên
        if (paddle.getX() + paddle.getWidth() > 800) { // Giả sử GAME_WIDTH = 800
            paddle.setX(800 - paddle.getWidth());
        }
        
        // Cập nhật hình ảnh paddle
        if (paddle.getImage() == null) {
            paddle.set_Drawed_Paddle_image();
        }
    }
    
    @Override
    protected void removeEffect(Paddle paddle, Ball ball) {
        // Khôi phục kích thước gốc
        paddle.setWidth(originalWidth);
        
        // Cập nhật hình ảnh paddle
        if (paddle.getImage() == null) {
            paddle.set_Drawed_Paddle_image();
        }
    }
    
    @Override
    public Image set_Drawed_Paddle_image() {
        BufferedImage powerUpImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = powerUpImage.createGraphics();
        
        // Vẽ hình chữ nhật với viền
        g.setColor(getColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        
        // Vẽ chữ "E" ở giữa
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("E", getWidth() / 2 - 3, getHeight() / 2 + 3);
        
        g.dispose();
        setImage(powerUpImage);
        return powerUpImage;
    }
}