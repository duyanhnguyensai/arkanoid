package uet.oop.UA;

import uet.oop.UA.entites.Brick;
import uet.oop.UA.entites.GameObject;
import uet.oop.UA.entites.Paddle;
import uet.oop.UA.entites.Ball;
import uet.oop.UA.entites.SoundManager;
// load ảnh
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Lớp GamePanel để vẽ game
 * - bao gồm các thuộc tính như vị trí paddle, ảnh paddle, di chuyển paddle
 * - thiết kế màn chơi (hiển thị brick, paddle, score, lives, level, game over)
 */
public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
    //Danh sách các vật thể trong một panel
    private List<GameObject> objectList;

    //thêm/bỏ vật thể ra khỏi danh sách
    public void addGameObject(GameObject gameObject){
        this.objectList.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject) {
        this.objectList.remove(gameObject);
    }
    public static boolean showMenu = true;
    private boolean inPlayButton = false;
    private boolean inHighScoreButton =  false;
    private boolean inHighScoreExitButton = false;
    private boolean inHighScore = false;
    public boolean isGameOver = false;
    //private boolean gameStartFlag = true;
    private Image menuImage;
    private Image backgroundImage;
    private Image gameoverImage;

    private Image Menu() {
        ImageIcon menuImage = new ImageIcon("res/menuImage/menu.png"); // đường dẫn tới ảnh menu
        return menuImage.getImage();
    }

    private Image GameOver() {
        ImageIcon gameoverImage = new ImageIcon("res/menuImage/gameover.png"); // đường dẫn tới ảnh game over
        return gameoverImage.getImage();
    }

    private Image backgroundImage() {
        ImageIcon backgroundImage = new ImageIcon("res/backgroundImage/background.png"); // đường dẫn tới ảnh background
        return backgroundImage.getImage();
    }

    // vẽ menu

    private void drawMenu(Graphics g) {
        g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), this);

        // vẽ Start
        if (inPlayButton) {
            g.setColor(Color.YELLOW);
        }
        else {
            g.setColor(Color.CYAN);
        }
        g.fillRect(getWidth()/2 - 80 - 150, getHeight()/2 - 40, 170, 45);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("START", getWidth()/2 - 80 - 150, getHeight()/2);

        //vẽ highscore
        if (inHighScoreButton) {
            g.setColor(Color.ORANGE);
        }
        else {
            g.setColor(Color.GREEN);
        }
        g.fillRect(getWidth()/2 - 80 + 150, getHeight()/2 - 40, 200, 45);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("H.score", getWidth()/2 - 80 +150, getHeight()/2);
    }


    public void drawHighScores(Graphics g) {
        //tô nền
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());
        //viết highscore
        g.setFont(new Font("Arial", Font.BOLD, 80));
        g.setColor(Color.YELLOW);
        g.drawString("High Scores", getWidth()/2 - 250, getHeight()/2 -200);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        int[] highScores = takeScore();
        for (int i=1; i<=5 ;i++) {
            g.setColor(Color.RED);
            g.drawString(Integer.toString(i),getWidth()/2 - 175, getHeight()/2 - 200 + i*70);
            g.setColor(Color.GREEN);
            g.drawString(Integer.toString(highScores[i-1]), getWidth()/2 +105 , getHeight()/2 - 200 + i*70);
        }
        //lệch 20 pix về bên trái để cho cân
        if(inHighScoreExitButton) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.YELLOW);
        }
        g.fillRect(getWidth()/2 - 190, getHeight()/2 + 200, 340, 45);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Back to menu", getWidth()/2 - 190, getHeight()/2 + 200 +40);
    }

    //hàm vẽ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showMenu && !inHighScore) {
            drawMenu(g);
        }
        else if (inHighScore) {
            drawHighScores(g);
        }
        else if (isGameOver) {
            g.drawImage(gameoverImage, 0, 0, getWidth(), getHeight(), this);
        }
        else {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g.setColor(Color.green);
            g.drawRect(0, 0, 1000, 800);
            for (GameObject obj : objectList) {  //vẽ gameObject trong list
                if (obj != null && obj.getImage() != null) {
                    g.drawImage(obj.getImage(), obj.getX(), obj.getY(), this);
                }
            }
            drawGameInfo(g);  //vẽ thông tin game

            //kiểm tra game over
            if(lives <= 0) {
                isGameOver = true;
                repaint();
            }
        }
    }
    private void drawGameInfo(Graphics g) {

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Lives: " + lives, GAME_WIDTH / 2 - 40, 30);
        g.drawString("Level: " + level, GAME_WIDTH - 150, 30);

        // THÊM MỚI: Hiển thị power-up active
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("Power-ups:", 20, 50);
        // Ở đây có thể thêm logic để hiển thị các power-up đang active
    }

    //Vẽ Game Over
    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", GAME_WIDTH / 2 - 150, GAME_HEIGHT / 2 - 50);

    }


    //drawgameInfo và drawgameOver được tôi tích hợp vào paintComponent mới của tôi
    //bên trên là phần tôi bổ sung . Bn dưới là phần cũ của ông, tôi đã xóa 1 phần ve obj
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 800;
    private final int PADDLE_WIDTH = 180;
    private final int PADDLE_HEIGHT = 30;

    // Game state
    //vì gamestate không cần gắn vào class nào, không có method riêng, và cần thay đổi trong nhiểu trường hợp
    //nên biến nó thành static và public
    public static int score = 500;
    public static int lives = 3;
    public static int level = 1;


    //Khởi tạo Game
    public GamePanel(List<GameObject> objects) {
        this.objectList = objects;
        this.backgroundImage = backgroundImage();
        this.menuImage = Menu();
        this.gameoverImage = GameOver();
        setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        //initializeBricks(); //vẽ Bricks
        //loadPaddleImage(); //load ảnh paddle

        // Thêm paddle vào danh sách vật thể
        Paddle paddle = new Paddle(
                GAME_WIDTH / 2 - PADDLE_WIDTH / 2,
                GAME_HEIGHT - PADDLE_HEIGHT ,
                PADDLE_WIDTH,
                PADDLE_HEIGHT
        );
        this.objectList.add(paddle);


        //thêm các method xử lí chuột và bàn phím
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }


    //Vẽ thông tin game (score, lives, level)
    //Nhận phím
    @Override
    public void keyPressed(KeyEvent e) {
        Paddle paddle = (Paddle)objectList.get(0);
        if(!showMenu) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                GameManager.gameStarted = true;
            }

            // Di chuyển sang trái - THÊM KIỂM TRA CHẶT CHẼ
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                int newX = paddle.getX() - 20;
                    if (newX < 0) {
                        newX = 0; // KHÔNG CHO VƯỢT QUÁ BIÊN
                    }
                    paddle.setX(newX);
                    repaint();
            }
            // Di chuyển sang phải
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                int newX = paddle.getX() + 20;
                    int maxX = GAME_WIDTH - paddle.getWidth();
                    if (newX > maxX) {
                        newX = maxX; // KHÔNG CHO VƯỢT QUÁ BIÊN
                    }
                    paddle.setX(newX);
                    repaint();
            }
        }


        // Restart game
        if (e.getKeyCode() == KeyEvent.VK_R && isGameOver) {
            // THÊM SOUND EFFECT CHO RESTART GAME
            SoundManager.getInstance().stopAllSounds();
            SoundManager.getInstance().playSound("game_start");
            //saveScore(score);
            //System.out.println(Arrays.toString(takeScore()));
            restartGame();
        }

        // THÊM ĐIỀU KHIỂN ÂM LƯỢNG
        if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
            SoundManager.getInstance().setVolume(SoundManager.getInstance().getVolume() + 0.1f);
        } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
            SoundManager.getInstance().setVolume(SoundManager.getInstance().getVolume() - 0.1f);
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            SoundManager.getInstance().stopAllSounds();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (showMenu) {
            int x = e.getX();
            int y = e.getY();
            if (x > getWidth()/2 - 80 -150 && x < getWidth()/2 - 80 + 20
                    && y > getHeight()/2 - 40 && y < getHeight()/2 - 40 + 45) {
                // THÊM SOUND EFFECT CHO BẮT ĐẦU GAME
                SoundManager.getInstance().playSound("game_start");
                SoundManager.getInstance().playSound("background", true);
                showMenu = false;
                repaint();
            }
            if (x > getWidth()/2 - 80 +150 && x < getWidth()/2 - 80 + 170 +150
                    && y > getHeight()/2 - 40 && y < getHeight()/2 - 40 + 45) {
                inHighScore = true;
                repaint();
            }
        }
        if(inHighScore) {
            int x = e.getX();
            int y = e.getY();
            if(x > getWidth()/2 - 190 && x< getWidth()/2 -190+ 340 && y > getHeight()/2 +200 &&
                    y < getHeight()/2 + 200+45) {
                inHighScore = false;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        if (showMenu) {
            int x = e.getX();
            int y = e.getY();
            if(x > getWidth()/2 - 80 -150 && x < getWidth()/2 - 80 + 20
                    && y > getHeight()/2 - 40 && y < getHeight()/2 - 40 + 45) {
                inPlayButton = true;
            }
            else {
                inPlayButton = false;
            }
            if(x > getWidth()/2 - 80 +150 && x < getWidth()/2 - 80 + 170 +150
                    && y > getHeight()/2 - 40 && y < getHeight()/2 - 40 + 45) {
                inHighScoreButton = true;
            }
            else {
                inHighScoreButton = false;
            }
        }
        if(inHighScore) {
            int x = e.getX();
            int y = e.getY();
            if(x > getWidth()/2 - 190 && x< getWidth()/2 -190+ 340 && y > getHeight()/2 +200 &&
                    y < getHeight()/2 + 200+45) {
                inHighScoreExitButton = true;
            }
            else {
                inHighScoreExitButton = false;
            }
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    public static void saveScore (int score) {
        //Obj kiểu FileWriter dùng để mở file. True dùng để ghi thêm vào file ,false thì ghi lại
        //Obj writeHere kiểu BufferedWriter dùng để ghi vào file

        try (BufferedWriter writeHere = new BufferedWriter(new FileWriter("res/Score.txt", true))) {
            writeHere.write(Integer.toString(score));
            //newline thay cho '\n' để xuống dòng trong window
            writeHere.newLine();
            System.out.println("new score added");
        }
        catch (IOException e) {
            //lệnh in lỗi
            e.printStackTrace();
        }
    }
    public static int[] takeScore () {
        int[] highscore = new int[5];
        for (int i = 0; i < 5; i++) {
            highscore[i] = 0;
        }
        List<Integer> scores = new ArrayList<>();
        //scoreRead đọc file
        try (BufferedReader scoreRead = new BufferedReader(new FileReader("res/Score.txt"))) {
            String line;
            //đọc file đến khi file = null --> hết file
            while ((line = scoreRead.readLine()) != null) {
                //nếu gặp dòng trống, bỏ qua
                if(line.isEmpty()) {
                    continue;
                }
                scores.add(Integer.parseInt(line));
            }
            Collections.sort(scores);
            Collections.reverse(scores);
            if(scores.isEmpty()) {
                return null;
            }
            if (scores.size() <= 5) {
                for (int i = 0; i < scores.size(); i++) {
                    highscore[i] = scores.get(i);
                }
            }
            else {
                for (int i = 0; i < 5; i++) {
                    highscore[i] = scores.get(i);
                }
            }
            return highscore;
        } catch (IOException e) {
            e.printStackTrace();
            return highscore;
        }
    }


    //Khởi tạo lại Game sau khi Game Over
    private void restartGame() {
        score = 500;
        lives = 3;
        level = 1;
        isGameOver = false;
        GameManager.gameStarted = false;
        objectList.clear(); // xóa hết objects khi restart game


        // tạo lại paddle
        Paddle paddle = new Paddle(
                GAME_WIDTH / 2 - PADDLE_WIDTH / 2,
                GAME_HEIGHT - PADDLE_HEIGHT,
                PADDLE_WIDTH,
                PADDLE_HEIGHT
        );
        objectList.add(paddle);

        Brick.createBrickGrid(objectList); // tạo lại bricks

        // tạo lại ball
        Ball ball = new Ball(objectList.get(0).getX()+objectList.get(0).getWidth()/2-15,
                objectList.get(0).getY()-30, 30 , 30);
        objectList.add(ball);
        repaint();
    }
}