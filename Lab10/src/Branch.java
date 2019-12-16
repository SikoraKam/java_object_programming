import java.awt.*;

public class Branch implements XmasShape {
    int x;
    int y;
    int width;
    int height;
    double scale;
    Color fillColor;

    Branch(int x, int y, int height, int width,double scale, Color fillColor){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.scale = scale;
        this.fillColor = fillColor;
    }
    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(fillColor);

        /* kształt jednego polygona, pierwszy punkt to ten na górze i idzimey w lewo
          o
       o     o
         o o
     o         o
         */

        int [] xCoordinates = {0,-2*width/10, -width/10, -4*width/10, 4*width/10,width/10,2*width/10};
        int[] yCoordinates = {0, height/20,height/18,height/9,height/9,height/18,height/20};
        g2d.fillPolygon(xCoordinates,yCoordinates,xCoordinates.length);

    }

    public void transform(Graphics2D g2d){
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }
}
