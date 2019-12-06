import java.util.List;

public class AdminUnit {
    String name;
    int adminLevel;
    double population;
    double area;
    double density;
    AdminUnit parent;
    BoundingBox bbox = new BoundingBox();
    List<AdminUnit> children;

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append(name + "," + adminLevel + "," + population + "," + area + "," + density + "," + bbox.toString() +"\n");
        return buf.toString();
    }

    void fixMissingDensity() throws IllegalCallerException{
        if(parent!=null){
            if(parent.density != -1)
                this.density = parent.density;
            else
                parent.fixMissingDensity();
        }
        else{
            throw new IllegalCallerException("nie można przypisać density rodzica, ponieważ ten obiekt unit nie posiada rodzica");
        }

    }

    void fixMissingPopulation(){
        population = area * density;
    }


}
