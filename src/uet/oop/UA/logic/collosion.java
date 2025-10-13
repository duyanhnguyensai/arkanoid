package uet.oop.UA.logic;

import uet.oop.UA.entites.*;
import uet.oop.UA.WindowFrame_Main;
public class collosion {
    Ball ball = new Ball();
    public void handleWallCollision() {
        // tường 2 bên
        if (ball.getX() <= 0) { //kiểm tra tọa độ x của bóng có vượt qua biên trái không
            ball.setDx(Math.abs(ball.getDx()));
            ball.setX(0);
        } else if (ball.getX() >= WindowFrame_Main.WIN_WIDTH - ball.getWidth()) {
            ball.setDx(-Math.abs(ball.getDx()));
            ball.setX(WindowFrame_Main.WIN_WIDTH - ball.getWidth());
        }
        // tường trên, dưới
        if (ball.getY() <= 0) {
            ball.setDy(Math.abs(ball.getDy()));
            ball.setY(0);
        } else if (ball.getY() >= WindowFrame_Main.WIN_HEIGHT - ball.getHeight()) {
            ball.setDy(-Math.abs(ball.getDy()));
            ball.setY(WindowFrame_Main.WIN_HEIGHT - ball.getHeight());
        }
    }
}