import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BouncingBallsPanel extends JPanel {

    List<Ball> balls = new ArrayList<>();
    AnimationThread currentThread = new AnimationThread();

    static class Ball {
        int x, y;
        double vx, vy;
        Color color;
        public static int BALL_SIZE = 20;

        public Ball(int x, int y, double vx, double vy, Color color) {
            this.x = x;
            this.y=y;
            this.vx=vx;
            this.vy=vy;
            this.color = color;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Ball b : balls){
            g2.setColor(b.color);
            g2.fillOval(b.x,b.y, Ball.BALL_SIZE, Ball.BALL_SIZE);
        }
    }
    void collision(Ball b1) {
        int radius = Ball.BALL_SIZE/2;
        for(Ball b2:balls) {
            if (b1 != b2) {
                int distanceX = Math.abs((b2.x + radius) - (b1.x + radius));
                int distanceY = Math.abs((b2.y + radius) - (b1.y + radius));
                double distance = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2));
                
                if (distance <= Ball.BALL_SIZE) {
                    double tempx = b1.vx;
                    double tempy = b1.vy;

                    b1.vx = b2.vx;
                    b1.vy = b2.vy;

                    b2.vx = tempx;
                    b2.vy = tempy;

                }

            }
        }
    }


    class AnimationThread extends Thread{

        boolean stopped = true;

        public void run(){
            for(;;){
                for(Ball b : balls){
                    if(b.x + b.vx < 0 || b.x + b.vx + Ball.BALL_SIZE > getWidth())
                        b.vx *= -1;
                    if(b.y + b.vy < 0 || b.y + b.vy + Ball.BALL_SIZE > getHeight())
                        b.vy *= -1;

                    b.x += b.vx;
                    b.y += b.vy;

                    collision(b);
                }
                repaint();

                synchronized (this){
                    if(stopped){
                        System.out.println("suspending");
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //przesuń kulki
                //wykonaj odbicia od ściany
                //wywołaj repaint
                //uśpij
            }
        }
    }

    BouncingBallsPanel(){
        currentThread.start();
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
    }

    private void randomBall(){
        Random random = new Random();
        int vx = (random.nextInt(2) > 0) ? 10 : -10;
        int vy = (random.nextInt(2) > 0) ? 10 : -10;

        int y = random.nextInt((getHeight() - Ball.BALL_SIZE ) + 1) + Ball.BALL_SIZE;
        int x = random.nextInt((getWidth() - Ball.BALL_SIZE ) + 1) + Ball.BALL_SIZE;

        Color color = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));

        balls.add(new Ball(x,y,vx,vy,color));
    }

    void onStart(){

        synchronized (currentThread){
            currentThread.stopped = false;
            currentThread.notifyAll();
            System.out.println("Start or resume animation thread");
        }

    }

    void onStop(){
        synchronized (currentThread){
            currentThread.stopped = true;
            currentThread.notifyAll();
            System.out.println("Suspend animation thread");
        }
    }

    void onPlus(){
        System.out.println("Add a ball");
        randomBall();
        repaint();
    }

    void onMinus(){
        if(balls.isEmpty())
            return;
        balls.remove(balls.size()-1);
        repaint();
        System.out.println("Remove a ball");
    }
}