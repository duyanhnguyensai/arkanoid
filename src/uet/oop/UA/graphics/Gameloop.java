package uet.oop.UA.graphics;

import uet.oop.UA.GameManager;
import uet.oop.UA.GamePanel;

/**
 * Lớp Gameloop để quản lí vòng lặp game, cụ thể là
 * - Cập nhật vị trí vật thể theo một tần số cố định (update per second)
 * - Vẽ vật thể (render) theo một tần số cố định (frame per second)
 * (ngoài ra có kiểm tra fps, ups thực tế để debug)
 *
 *skip đến dòng 79 để tìm hàm cập nhật vị trí vật thể, dòng 85 để tìm hàm vẽ
 */

public class Gameloop implements Runnable {
    //biến gameRunning để check xem chương trình có đang chạy không để bật đểm time
    public boolean gameRunning;

    //Tốc độ xử lí và cập nhật vị trí vật thể: update per second
    private final double UPS = 60;

    //Thời gian (chu kì) của mỗi update (tính bằng đơn vị nano giây mỗi update)
    private final double TIME_PER_UPDATE = 1000000000/UPS;

    //Các biến đánh dấu thời điểm bắt đầu của vòng lặp hiện tại và vòng lặp trước nó, trừ đi sẽ biết thời gian vòng lặp trước chạy
    private long last_loop_time;
    private long current_loop_time;

    //Biến lưu số update chạy trong mỗi vòng lặp ngoài, sẽ giải thích cụ thể hơn phía dưới
    private double update_per_loop = 0.0;

    //Các biến kiểm tra thông số chương trình chạy (frame per sec, update per sec)
    private long real_FPS = 0;
    private long real_UPS = 0;

    //Biến này sẽ được sử dụng cho các tác vụ cập nhật theo từng giây, bao gồm cả real UPS, FPS (later)
    private long secondCountingTimer;

    //tạo một tham chiếu đến game để gọi các hàm của nó
    private GameManager game;



    //constructor time:v khởi tạo giá trị thôi
    public Gameloop (GameManager game, GamePanel panel_ref_in_loop) {
        //nhận đầu vào là biến tham chiếu class Game thôi
        this.game = game;
        //một khi đã gọi constructor gameLoop ra, thì tức là bắt đầu chạy được rồi
        this.gameRunning = true;
        //vì vòng lặp đầu (sau đây) không có vòng lặp trước nó, nên tạo một cái lastTime manually ở đây
        this.last_loop_time = System.nanoTime();
        //đặt thời điểm hiện tại cho biến, tính theo ms (giải thích thêm phía dưới)
        this.secondCountingTimer = System.currentTimeMillis();
    }
    @Override
    public void run() {
        while(this.gameRunning){
            //Tính thời điểm bắt đầu vòng lặp hiện tại
            current_loop_time = System.nanoTime();

            // ((mesureable) time per loop) / ((static) time per update) = ((countable) update per loop)
            /*
             * Ý nghĩa của update per loop:
             *
             * ta biết rằng số update mỗi đơn vị thời gian (giây) là giá trị cần phải cố định
             * Nhưng, không phải vòng running nào cũng chạy với thời gian giống nhau và như ý muốn
             * Cách giải quyết là: nếu thời gian chạy một vòng running tăng, thì nó cũng sẽ phải triển khai nhiều update hơn
             * (hoặc nếu thời gian chạy vòng running nhỏ hơn của update, thì số vòng running cần đếm để thực hiện một update giảm đi)
             * Nhờ đó, ta đảm bảo tính nguyên vẹn về thời gian thực hiện một update
             * Đếm thời gian thực hiện một loop, và chia cho thời gian ta muốn thực hiện một update, và ta sẽ có số update mà một loop phải thực hiện
             * (để đảm bảo mỗi update chỉ thực hiện trong một khoảng thời gian nhất định cho phép)
             *
             * note: thật ra thời gian được tính là của loop ngay trước, vì loop hiện tại chưa kết thúc, nên ta sẽ dùng last_loop_time để tính
             * nhưng thời gian chạy của các loop kế nhau là rất nhỏ, nên ta có thể coi như nó là thời gian của loop hiện tại
             */

            update_per_loop += (current_loop_time - last_loop_time)/TIME_PER_UPDATE;

            //khoảnh khắc xác minh (current loop time) của vòng lặp này đã được dùng xong. Giờ thì biến nó thành last_loop_time để dùng cho vòng lặp tới
            last_loop_time = this.current_loop_time;

            //Vậy là ta đã biết số loop cần có để thực hiện một update rồi, giờ thì ta sẽ thực hiện update
            //Lưu ý: vì update_per_loop là số thực, nên có thể nó sẽ lớn hơn 1, nghĩa là một vòng lặp ngoài có thể thực hiện nhiều update
            //Và cũng có thể nó sẽ nhỏ hơn 1, nghĩa là một vòng lặp ngoài không thực hiện update nào cả
            //Vậy nên ta sẽ dùng vòng while để kiểm tra và thực hiện update
            while(update_per_loop >= 1) {

                game.update();
                real_UPS ++; //(mỗi một update là lại cộng một ups)
                update_per_loop --; //giảm số update cần thực hiện đi một, vì đã thực hiện xong một update
            }
            /*
             * Sử dụng method render tại đây
             */
             game.draw();

            real_FPS ++; //(mỗi một loop là lại cộng một fps)


            //giờ là lúc kiểm tra xem cứ mỗi giây thì chương trình chạy bao nhiêu frame/update
            if(System.currentTimeMillis() - secondCountingTimer >=1000) { //thời gian thực được kiểm tra liên tục

                secondCountingTimer += 1000;  //cứ mỗi giây thì cộng thêm 1000ms vào biến đếm thời gian. Mệnh đề if giảm về 0 và lại tiêp tục đếm đến 1000ms tiếp theo
                if(!GamePanel.showMenu) {
                    GamePanel.score = GamePanel.score - 1;
                }
                System.out.println("FPS: " + real_FPS + " | UPS: " + real_UPS);
                //reset lại số fps, ups để đếm cho giây tiếp theo
                real_FPS = 0;
                real_UPS = 0;
            }

            //giờ thì tạm dừng một chút để tránh việc vòng lặp chạy quá nhanh, gây tốn tài nguyên máy
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stop() {
        this.gameRunning = false;
    }

}






























