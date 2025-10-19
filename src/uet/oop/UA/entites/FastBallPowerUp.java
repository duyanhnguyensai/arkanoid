package uet.oop.UA.entites;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class FastBallPowerUp extends PowerUp {
    private double originalSpeed;
    private final double SPEED_MULTIPLIER = 1.5; // Tăng tốc độ 1.5 lần
    
    public FastBallPowerUp() {
        super();
        setType("FAST_BALL");
        setDuration(6000); // 6 seconds
        setColor("red");
        setWidth(30);
        setHeight(15);
        setDy(2); // Tốc độ rơi xuống
    }
    
    @Override
    protected void applyEffect(Paddle paddle, Ball ball) {
        // Lưu lại tốc độ gốc
        double currentSpeed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());
        originalSpeed = currentSpeed;
        
        // Tăng tốc độ ball
        if (currentSpeed > 0) {
            double ratio = (currentSpeed * SPEED_MULTIPLIER) / currentSpeed;
            ball.setDx(ball.getDx() * ratio);
            ball.setDy(ball.getDy() * ratio);
        }
    }
    
    @Override
    protected void removeEffect(Paddle paddle, Ball ball) {
        // Khôi phục tốc độ gốc
        if (originalSpeed > 0) {
            double currentSpeed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());
            if (currentSpeed > 0) {
                double ratio = originalSpeed / currentSpeed;
                ball.setDx(ball.getDx() * ratio);
                ball.setDy(ball.getDy() * ratio);
            }
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
        
        // Vẽ chữ "F" ở giữa
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("F", getWidth() / 2 - 3, getHeight() / 2 + 3);
        
        g.dispose();
        setImage(powerUpImage);
        return powerUpImage;
    }
}