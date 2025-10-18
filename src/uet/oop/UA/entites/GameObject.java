package uet.oop.UA.entites;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.sqrt;

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
    public void setColor(String colorname) {
        switch (colorname.toLowerCase()) {
            case "black" -> this.color = Color.BLACK;
            case "blue" -> this.color = Color.BLUE;
            case "cyan" -> this.color = Color.CYAN;
            case "dark_gray", "darkgray" -> this.color = Color.DARK_GRAY;
            case "gray" -> this.color = Color.GRAY;
            case "green" -> this.color = Color.GREEN;
            case "light_gray", "lightgray" -> this.color = Color.LIGHT_GRAY;
            case "magenta" -> this.color = Color.MAGENTA;
            case "orange" -> this.color = Color.ORANGE;
            case "pink" -> this.color = Color.PINK;
            case "red" -> this.color = Color.RED;
            case "white" -> this.color = Color.WHITE;
            case "yellow" -> this.color = Color.YELLOW;
            default -> {
                System.out.println("Unknown color: " + colorname + ". Defaulting to WHITE.");
                this.color = Color.WHITE;
            }
        }
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

    public double distant2CentralObj(GameObject obj) {
        updateCentral();
        obj.updateCentral();
        double ObjThisX = this.centralX - obj.centralX;
        double ObjThisY = this.centralY - obj.centralY;
        return sqrt(ObjThisX * ObjThisX + ObjThisY * ObjThisY);
    }
}