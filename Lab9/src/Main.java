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
        //-------------------------
        AdminUnitList unitExample = unitList.selectByName("^Kraków$", true);

        double t1 = System.nanoTime()/1e6;
        AdminUnitList neighbourList = unitList.getNeighbours(unitExample.getUnits().get(0), 15);
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
        neighbourList.list(System.out);
        neighbourList.sortInPlaceByArea();
        //-----------------------------------
        unitList.filter(a->a.name.startsWith("Ż")).sortInPlaceByArea().list(System.out);

        //wybór (i sortowanie) elementów zaczynających się na “K”
        unitList.filter(a->a.name.startsWith("K")).sortInPlaceByArea().list(System.out);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        //wybór jednostek będących powiatami, których parent.name to województwo małopolskie
        unitList.filter(a->a.adminLevel == 6 && a.parent.name.contains("województwo małopolskie"), 5, 3).list(System.out);

        //--------------Query
        System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(unitList)
                .where(a->a.area>1000)
                .or(a->a.name.startsWith("Sz"))
                .sort((a,b)->Double.compare(a.area,b.area))
                .limit(100);
        query.execute().list(System.out);
    }
}
