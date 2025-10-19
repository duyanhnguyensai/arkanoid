package uet.oop.UA.entites;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * class GameObject để tạo các đối tượng trong game
 * bao gồm các thuộc tính như vị trí, kích thước, hình ảnh, màu sắc
 * và các phương thức để thiết lập và lấy các thuộc tính đó
 * các phương thức để vẽ hình ảnh của đối tượng (hình chữ nhật, hình tròn, hình ảnh từ file)
 * tên phương thức vẽ là set_Drawed_Paddle_image() và set_Drawed_Ball_image()
 * tên phương thức tải hình ảnh từ file là set_File_image() (tham số là đường dẫn file viết dưới dạng String)
 * các phương thức để lấy vị trí trung tâm của đối tượng: getCentralX() và getCentralY()
 * phương thức đặt màu sắc từ tên màu dưới dạng String: setColor(String colorname) (ví dụ: "red", "blue", "green" (viết thường tiếng anh));

 */
public abstract class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;
    private int centralX;
    private int centralY;
    protected Image image;
    private Color color;
    //constructor
    public GameObject() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.centralX = 0;
        this.centralY = 0;
        this.image = null;
        this.color = Color.WHITE;
    }
    public GameObject(int x, int y, int width, int height, int colorcode) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.centralX = x + width / 2;
        this.centralY = y + height / 2;
        setColorByCode(colorcode);
        this.image = null;
    }
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        updateCentral();
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        updateCentral();
        this.y = y;
    }
    public void setWidth(int width) {
        updateCentral();
        this.width = width;
    }
    public void setHeight(int height) {
        updateCentral();
        this.height = height;
    }
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Color getColor() {
        return this.color;
    }
    
    public void setColorByCode(int colorcode) {
        switch (colorname.toLowerCase()) {
            case 1 -> this.color = Color.BLACK;
            case 2 -> this.color = Color.BLUE;
            case 3 -> this.color = Color.CYAN;
            case 4 -> this.color = Color.DARK_GRAY;
            case 5 -> this.color = Color.GRAY;
            case 6 -> this.color = Color.GREEN;
            case 7 -> this.color = Color.LIGHT_GRAY;
            case 8 -> this.color = Color.MAGENTA;
            case 9 -> this.color = Color.ORANGE;
            case 10 -> this.color = Color.PINK;
            case 11 -> this.color = Color.RED;
            case 12 -> this.color = Color.WHITE;
            case 13 -> this.color = Color.YELLOW;
            default -> {
                System.out.println("Unknown color: " + colorname + ". Defaulting to WHITE.");
                this.color = Color.WHITE;
            }
        }
    }
    public void setColor (Color color) {
        this.color = color;
    }

    public int getCentralX() {
        return centralX;
    }

    public int getCentralY() {
        return centralY;
    }
    public void updateCentral() {
        this.centralX = this.x + this.width / 2;
        this.centralY = this.y + this.height / 2;
    }
    public Image getImage() {
        return this.image;
    }
    public Image set_File_image (String filename){
        Image fileImage = new ImageIcon(filename).getImage();
        if (fileImage == null){
            System.out.println("Image is null");
            return null;
        }
        System.out.println("Image loaded successfully ");
        fileImage.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
        this.image = fileImage;
        return this.image;
    }
    public Image set_Drawed_Paddle_image() {
        BufferedImage paddleImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.image = paddleImage;
        Graphics2D g = paddleImage.createGraphics();
        g.setColor(this.getColor());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.dispose();
        return image;
    }
    public Image set_Drawed_Ball_image() {
        BufferedImage ballimage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.image = ballimage;
        Graphics2D g = ballimage.createGraphics();
        g.setColor(this.getColor());
        g.fillOval(0, 0, this.getWidth(), this.getHeight());
        g.dispose();
        return this.image;
    }
}