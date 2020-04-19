import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String [] args) throws IOException {

        PoliceUnitList policeUnitList = new PoliceUnitList();
        policeUnitList.read("policja.csv");

        //a;
        PoliceUnitList sortedList = new PoliceUnitList();
        policeUnitList.items.stream().sorted(Comparator.comparing((PoliceUnit unit) -> unit.miasto).thenComparing(unit -> unit.ulica));

        //b
        PoliceUnitList wwwList = policeUnitList.haveWWW();
        wwwList.sortInPlaceByMiasto().list(System.out);

        //c
        PoliceUnitList notFaxList = policeUnitList.nothaveFax();
        notFaxList.list(System.out);

        //d
        policeUnitList.countRodzaj();
        System.out.println(policeUnitList.rodzajCountMap);
        Arrays.toString(policeUnitList.rodzajCountMap.entrySet().toArray());


    }
}
