import java.io.IOException;

public class Main {
    public static void main(String [] args) throws IOException {
        /*CSVReader reader = null;
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
        AdminUnitList selected = unitList.selectByName("Kawio",false);
        selected.list(System.out);
    }
}
