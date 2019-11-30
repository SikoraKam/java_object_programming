import java.io.IOException;
import java.util.Locale;

public class Main {
    public static void main(String [] args) throws IOException {
       /* CSVReader reader = null;
        try {
            reader = new CSVReader("admin-units.csv",",", true, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i=0;
        while(reader.next() || i<=100){
            if(!reader.isMissing("name"))
                System.out.println(reader.get("name"));
            i++;
        }*/

        AdminUnitList unitList = new AdminUnitList();
        unitList.read("admin-units.csv");
        unitList.list(System.out,2, 5);
        System.out.println();

        AdminUnitList unitExample = unitList.selectByName("^Kraków$", true);

        double t1 = System.nanoTime()/1e6;
        AdminUnitList neighbourList = unitList.getNeighbours(unitExample.getUnits().get(0), 15);
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
        neighbourList.list(System.out);
    }
}
