import java.awt.*;

public class Star implements XmasShape {
    int midX,midY;
    double scale;

    Star(int midX, int midY, double scale){
        this.midX = midX;
        this.midY = midY;
        this.scale = scale;
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(Color.YELLOW);
        int[] x  = {42,52,72,52,60,42,20,28,8,31,42};
        int [] y = {38,60,65,75,105,85,105,75,65,60,38};
        g2d.fillPolygon(x, y, x.length);
    }

    @Override
    public void transform(Graphics2D g2d){
        g2d.translate(midX,midY);
        g2d.scale(scale,scale);
    }
}
