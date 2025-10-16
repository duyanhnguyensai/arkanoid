package uet.oop.UA.entites;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;
    private int centralX;
    private int centralY;
    private Image image;
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
        this.centralX = x + this.width / 2;
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.centralY = y + this.height / 2;
        this.y = y;
    }
    public void setWidth(int width) {
        this.centralX = this.x + width / 2;
        this.width = width;
    }
    public void setHeight(int height) {
        this.centralY = this.y + height / 2;
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
        return this.x + this.width / 2;
    }

    public int getCentralY() {
        return this.y + this.height / 2;
    }
    public void updateCentral() {
        this.centralX = this.x + this.width / 2;
        this.centralY = this.y + this.height / 2;
    }
    public Image set_File_image (String filename){
        this.image = new ImageIcon(filename).getImage();
        return this.image;
    }
    public Image set_Drawed_Paddle_image() {
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        if(this.image instanceof BufferedImage B_image){
            Graphics2D g = B_image.createGraphics();
            g.setColor(this.getColor());
            g.fillRect(this.x, this.y, this.getWidth(), this.getHeight());
            g.dispose();
        }
        else{
            System.out.println("Image is not a BufferedImage");
        }
        return image;
    }
    public Image set_Drawed_Ball_image() {
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        if(this.image instanceof BufferedImage B_image){
            Graphics2D g = B_image.createGraphics();
            g.setColor(this.getColor());
            g.fillOval(this.x, this.y, this.getWidth(), this.getHeight());
            g.dispose();
        }
        else{
            System.out.println("Image is not a BufferedImage");
        }
        return this.image;
    }
}