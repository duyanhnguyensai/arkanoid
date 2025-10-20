package uet.oop.UA.entites;

import java.awt.*;

public class PowerUp extends MovableObject {
    private String type;
    private int duration;
    private boolean active;
    private long activationTime;
    
    public PowerUp() {
        this.active = false;
        this.duration = 10000; // 10 seconds default
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
        if (active) {
            this.activationTime = System.currentTimeMillis();
        }
    }
    
    public long getActivationTime() {
        return activationTime;
    }
    
    public boolean isExpired() {
        if (!active) return false;
        return (System.currentTimeMillis() - activationTime) > duration;
    }
    
    // Phương thức kích hoạt power-up
    public void activate(Paddle paddle, Ball ball) {
        setActive(true);
        applyEffect(paddle, ball);
    }
    
    // Phương thức hủy power-up
    public void deactivate(Paddle paddle, Ball ball) {
        setActive(false);
        removeEffect(paddle, ball);
    }
    
    // Phương thức áp dụng hiệu ứng (cần override trong class con)
    protected void applyEffect(Paddle paddle, Ball ball) {
        // Override in subclasses
    }
    
    // Phương thức gỡ bỏ hiệu ứng (cần override trong class con)
    protected void removeEffect(Paddle paddle, Ball ball) {
        // Override in subclasses
    }
    
    // Cập nhật trạng thái power-up
    public void update(Paddle paddle, Ball ball) {
        if (active && isExpired()) {
            deactivate(paddle, ball);
        }
    }
    
    public void setColor(String colorName) {
        super.setColor(Color.decode(colorName));
    }
}