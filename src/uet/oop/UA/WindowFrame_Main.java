package uet.oop.UA;

import uet.oop.UA.entites.Ball;
import uet.oop.UA.entites.GameObject;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
class Main_Panel extends JPanel {
    private List<GameObject> objectList;

    public Main_Panel  (List<GameObject> objects){
        this.objectList = objects;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);
        this.setBounds(0, 0, WindowFrame_Main.WIN_WIDTH, WindowFrame_Main.WIN_HEIGHT);
    }
    public void addGameObject(GameObject gameObject){
        this.objectList.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject){
        this.objectList.remove(gameObject);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GameObject obj : objectList) {
            if (obj.getImage() != null) {
                g.drawImage(obj.getImage(), obj.getX(), obj.getY(), this);
            }
        }
    }
}


public class WindowFrame_Main extends JFrame
{
    public static final int WIN_WIDTH = 900;
    public static final int WIN_HEIGHT = 800;
    /*
     * để sử dụng các class khác trong project,
     * cần tạo đối tượng của các class đó trong class này
     * và add nó vào frame
     *
     * để class này có thể sử dụng các object tham chiếu đó
     * cần phải đặt các class của chúng là fields của class này
     * và khởi tạo chúng trong constructor
     */
    //khởi tạo các fields ở đây
    //ví dụ:
    //private Class_panel panel_ref;
    private Main_Panel mainPanel;

    //constructor
    public WindowFrame_Main()
    {
        //tạo list chứa game object
        //tạo object panel chứa list game object
        List<GameObject> Objects = new ArrayList<>();
        Main_Panel mainPanel = new Main_Panel(Objects);
        //tạo và add các game object vào game object list ở đây
        //GameObject obj1 = ...
        //main_Panel.addGameObject( obj1 );
        //main_Panel.removeGameObject ( obj1 );


        this.add(mainPanel);
        this.pack();

        this.setTitle("arkanoid");
        this.setSize(WIN_WIDTH, WIN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);

    }
    //main method



    public static void main(String[] args)
    {
        //SwingUtilities.invokeLater(() -> new WindowFrame_Main());
        new WindowFrame_Main();
    }
}


