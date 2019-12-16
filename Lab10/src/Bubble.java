import java.awt.*;


public class Bubble implements XmasShape {
    int x, y;
    double scale;
    Color lineColor;
    Color fillgradient1, fillgradient2;


    Bubble(int x,int y,double scale,Color fillgradient1,Color fillgradient2){
        this.x = x;
        this.y  =y;
        this.scale = scale;
        this.fillgradient1 = fillgradient1;
        this.fillgradient2 = fillgradient2;

    }

    @Override
    public void render (Graphics2D g2d){
        // ustaw kolor wypełnienia
        GradientPaint grad = new GradientPaint(0,0,fillgradient1,0,100, fillgradient2);
        g2d.setPaint(grad);
        g2d.fillOval(0,0,50,50);

        /*// ustaw kolor obramowania
        g2d.setColor(lineColor);
        g2d.drawOval(0,0,100,100);*/
    }

    public void transform(Graphics2D g2d){
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }
}
