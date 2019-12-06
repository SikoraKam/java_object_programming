import java.io.PrintStream;

public class BoundingBox {
    double xmin;
    double ymin;
    double xmax;
    double ymax;
    boolean empty = true;

    void addPoint(double x, double y){
        if(empty){
            xmin = x;
            xmax = x;
            ymin = y;
            ymax = y;
            empty=false;
        }
        else {
            if (x < xmin)
                xmin = x;
            if( x > xmax)
                xmax = x;
            if(y < ymin)
                ymin = y;
            if(y > ymax)
                ymax = y;
        }
    }

    boolean contains(double x, double y){
        return (x < xmax && x > xmin) && (y < ymax && y>ymin);
    }

    boolean contains(BoundingBox bb){
        return (bb.xmax < xmax && bb.xmin > xmin) && (bb.ymax < ymax && bb.ymax>ymin);
    }

    boolean intersects(BoundingBox bb) {
        if ((bb.xmax < xmin) || (bb.xmin > xmax) || (bb.ymax < ymin) || (bb.ymin > ymax))
            return false;
        else
            return true;
    }

    BoundingBox add(BoundingBox bb){
        if(bb.xmax > xmax)
            xmax = bb.xmax;
        if(bb.xmin < xmin)
            xmin = bb.xmin;
        if(bb.ymin < ymin)
            bb.ymin = ymin;
        if(bb.ymax > ymax)
            ymax=bb.ymax;
        return this;
    }

    boolean isEmpty(){
        return this.empty;
    }

    double getCenterX(){
        if(!isEmpty())
            return (xmax + xmin)/2;
        else
            throw new IllegalCallerException();
    }
    double getCenterY(){
        if(!isEmpty())
            return (ymax + ymin)/2;
        else
            throw new IllegalCallerException();
    }

    /**
     * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
     * @param bbx prostokąt, do którego liczona jest odległość
     * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
     * Ze względu na to, że są to współrzędne geograficzne, zamiast odległosci euklidesowej możesz użyć wzoru haversine
     * (ang. haversine formula)
     */
    double distanceTo(BoundingBox bbx){
        if(!isEmpty()){
            double result = haversine(this.getCenterX(), bbx.getCenterX(), this.getCenterY(), bbx.getCenterY());
            return result;
        }
        else
            throw new IllegalCallerException("psuty bounding box");
    }

    private double haversine(double lat1, double lat2, double lon1, double lon2){
        //distances
        double dLat =  Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("srodek:" + getCenterX() + ";"  + getCenterY() + "\n" );
        return builder.toString();
    }
}
