import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_MITER;

public class DrawPanel extends JPanel{

    //Image img = Toolkit.getDefaultToolkit().getImage("img1.jpg");
    List<XmasShape> shapes = new ArrayList<>();

    DrawPanel(){
        setBackground(new Color(53, 56, 133));
        //setOpaque(true);
    }

    DrawPanel addObject(XmasShape object){
        shapes.add(object);
        return this;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(252, 90, 237));
        g.setFont(new Font("Helvetica", Font.BOLD, 30));
        g.drawString("Merry Christmas", 360, 200);

        for (XmasShape s : shapes) {
            s.draw((Graphics2D) g);
        }
    }

}
