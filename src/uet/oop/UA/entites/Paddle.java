package uet.oop.UA.entites;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Paddle extends GameObject {
    public static final double originWidth = 180;
    public Paddle(int x, int y, int width, int height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        // KHÔNG setColor ở đây nữa, để ảnh file quyết định màu
        this.set_File_image("res/paddleImage/pad180.png");
    }

    // GHI ĐÈ phương thức set_Drawed_Paddle_image để KHÔNG thay đổi màu
    @Override
    public Image set_Drawed_Paddle_image() {
        BufferedImage paddleImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = paddleImage.createGraphics();

        // VẼ DỰA TRÊN ẢNH GỐC HOẶC MÀU HIỆN TẠI, KHÔNG THAY ĐỔI MÀU
        if (this.image != null) {
            // Nếu có ảnh từ file, vẽ lại ảnh đó
            g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), null);
        } else {
            // Nếu không có ảnh, dùng màu hiện tại
            g.setColor(this.getColor());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        // Viền đen
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);

        g.dispose();

        // QUAN TRỌNG: KHÔNG setImage ở đây nếu đang dùng ảnh từ file
        // Chỉ set image nếu đang tạo image mới
        if (this.image == null) {
            this.setImage(paddleImage);
        }

        return this.image; // Trả về image hiện tại
    }

    // Tạo hình ảnh paddle từ file - GIỮ NGUYÊN
    @Override
    public Image set_File_image(String filename) {
        ImageIcon imglink = new ImageIcon(filename);
        Image fileImage = imglink.getImage();
        if (imglink.getIconWidth() > 0) {
            System.out.println("Paddle image loaded successfully ");
            // Scale ảnh về kích thước paddle
            Image scaledImage = fileImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            this.image = scaledImage;
        } else {
            System.out.println("Paddle image not found, using default color");
            // Nếu không load được ảnh, mới dùng màu trắng
            this.setColor(Color.WHITE);
            this.set_Drawed_Paddle_image();
        }
        return this.image;
    }
}