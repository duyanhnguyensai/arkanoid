package uet.oop.UA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class WindowFrame_Main extends JFrame
{
    public static final int WIN_WIDTH = 900;
    public static final int WIN_HEIGHT = 800;
    /*
     * để sử dụng các class khác trong project,
     * cần tạo đối tượng của class đó trong class này
     * và add nó vào frame
     * để class này có thể sử dụng các object tham chiếu đó
     * cần phải đặt các class của chúng là fields của class này
     * và khởi tạo chúng trong constructor
     */
    //khởi tạo các fields ở đây
    //ví dụ:
    //private Class_panel panel_ref;

    //constructor
    public WindowFrame_Main()
    {
        //khởi tạo các object tham chiếu ở đây
        //ví dụ:
        //panel_ref = new Class_panel();
        this.setTitle("arkanoid");
        this.setSize(WIN_WIDTH, WIN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        //add các object tham chiếu vào frame ở đây
        //ví dụ:
        //this.add(panel_ref);
    }
    //main method



    public static void main(String[] args)
    {
        new WindowFrame_Main();
    }
}


