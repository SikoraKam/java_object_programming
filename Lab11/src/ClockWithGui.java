import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_MITER;

public class ClockWithGui extends JPanel {
    LocalTime time = LocalTime.now();

    ClockWithGui(){
        setOpaque(false);
        new ClockThread().start();
    }

    public static void main(String [] args){
        JFrame frame = new JFrame("Clock");
        frame.setContentPane(new ClockWithGui());
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        g2d.translate(getWidth()/2,getHeight()/2);

        /*
        tworzymy macierz przekształcenia afinicznego (powinowactwa)
        definiujemy obrót o wielokrotność 360/12 stopni
        wyznaczamy obraz punktu w przekształceniu.
        Ten punkt ma współrzędne (0,-120).
        Wartość 120 to promień, znak minus bo współrzędne y rosną w dół.
        Lokalizacja cyfr nie jest idealna, warto odjąć od x (przed przekształceniem) szerokość tekstu.
         */
        for(int i=1;i<13;i++){
            AffineTransform at = new AffineTransform();
            at.rotate(2*Math.PI/12*i);
            Point2D src = new Point2D.Float(0,-120);
            Point2D trg = new Point2D.Float();
            at.transform(src,trg);
            g2d.drawString(Integer.toString(i),(int)trg.getX()-4,(int)trg.getY());
        }
        //rysowanie kresek
        for (int i=1;i<60;i++){
            AffineTransform saveAT = g2d.getTransform();
            g2d.rotate(i%60*2*Math.PI/60);
            g2d.drawLine(0,-140,0,-142);
            g2d.setTransform(saveAT);

            g2d.rotate(i%12*2*Math.PI/12);
            g2d.drawLine(0,-132,0,-142);
            g2d.setTransform(saveAT);

        }

        //wskazówka godzinowa
        AffineTransform saveAT = g2d.getTransform();
        g2d.rotate(time.getHour()%12*2*Math.PI/12);
        g2d.setStroke(new BasicStroke(5, CAP_ROUND,JOIN_MITER));
        g2d.drawLine(0,0,0,-75);
        g2d.setTransform(saveAT);
        //minutowa
        g2d.rotate(time.getMinute()%60*2*Math.PI/60);
        g2d.setStroke(new BasicStroke(2, CAP_ROUND,JOIN_MITER));
        g2d.drawLine(0,0,0,-100);
        g2d.setTransform(saveAT);
        //sekundowa
        g2d.rotate(time.getSecond()%60*2*Math.PI/60);
        g2d.setStroke(new BasicStroke(1, CAP_ROUND,JOIN_MITER));
        g2d.drawLine(0,0,0,-100);
    }

    class ClockThread extends Thread{

        @Override
        public void run(){
            for(;;){
                time = LocalTime.now();
                System.out.printf("%02d:%02d:%02d\n",time.getHour(),time.getMinute(),time.getSecond());
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }
    }
}
