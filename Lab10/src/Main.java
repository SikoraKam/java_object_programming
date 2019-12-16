import javax.swing.*;
import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        DrawPanel christmasTree = new DrawPanel();

        for(int i=1;i<7;i++) {
            christmasTree.addObject(new Branch(480, 200 + 50 * i, 400 + i * 20, 200 + 50 * i, 1.5, new Color(17, 190, 39)));
        }
        for (int i=1;i<13;i++) {
            christmasTree.addObject(new Lights(240+i*5, 202+i*2, 0.8, new Color(254, 255, 5)));
        }
        for(int i=1; i<20; i++){
            christmasTree.addObject(new Lights(300-i*5, 226+i*2, 0.8, new Color(254, 255, 5)));
        }
        for(int i=1;i<22;i++){
            christmasTree.addObject(new Lights(210+i*5, 270+i*2, 0.8, new Color(254, 255, 5)));
        }
        for(int i=1;i<19;i++){
            christmasTree.addObject(new Lights(310-i*5, 315+i*2, 0.8, new Color(254, 255, 5)));
        }
        for(int i=1;i<19;i++){
            christmasTree.addObject(new Lights(220+i*5, 350+i*2, 0.8, new Color(254, 255, 5)));
        }
        for(int i=1;i<27;i++){
            christmasTree.addObject(new Lights(315-i*5, (int) Math.round(390+i*(1.05)), 0.8, new Color(254, 255, 5)));
        }
        christmasTree.addObject(new Bubble(480,350,0.4,new Color(45,252,217),new Color(255,121,101)))
                .addObject(new Bubble(500,470,0.4,new Color(252,90,237),new Color(254,255,5)))
                .addObject(new Bubble(400,300,0.4,new Color(252,26,28),new Color(255,70,166)))
                .addObject(new Bubble(380,420,0.4,new Color(252,130,32),new Color(252,239,208)))
                .addObject(new Bubble(570,350,0.4,new Color(252,26,28),new Color(255,116,116)))
                .addObject(new Bubble(440,520,0.4,new Color(0,232,252),new Color(11,38,175)))

                .addObject(new Star(440,180,1))
                .addObject(new Trunk(455,585,50,100,1));

        JFrame frame = new JFrame("Choinka");
        frame.setContentPane(christmasTree);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

    }
}
