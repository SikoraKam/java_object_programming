import java.awt.*;

public class Trunk implements XmasShape {
    int x,y;
    double scale;
    int width, height;

    Trunk(int x,int y, int width, int height, int scale){
        this.x=x;
        this.y=y;
        this.scale=scale;
        this.width=width;
        this.height=height;
    }

    public void render(Graphics2D g2d){
        g2d.setColor(new Color(110,92,78));
        g2d.fillRect(0,0,width,height);
    }

    @Override
    public void transform(Graphics2D g2d){
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }
}
