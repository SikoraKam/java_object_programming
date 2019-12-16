import java.awt.*;

public class Lights implements XmasShape {
    int x, y;
    double scale;
    Color fillColor;

    Lights(int x,int y,double scale, Color fillColor){
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.fillColor = fillColor;
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(fillColor);
        g2d.fillOval(x,y,12,3);
    }
    @Override
    public void transform(Graphics2D g2d){
        g2d.translate(x,y/(1.7));
        g2d.scale(scale,scale);
    }

}
