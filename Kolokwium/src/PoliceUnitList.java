import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class PoliceUnitList {

    List<PoliceUnit> items = new ArrayList<>();
    Map<String,Integer> rodzajCountMap = new HashMap<>();

    public void read(String filename) throws IOException {
        CSVReader reader = null;
        try {
            reader = new CSVReader(filename, ";", true, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (reader.next()){
            PoliceUnit policeUnit = new PoliceUnit();
            policeUnit.id = reader.getInt("id");
            policeUnit.id_gl = reader.getInt("id_gl");
            policeUnit.nazwa = reader.get("nazwa");
            policeUnit.rodzaj = reader.get("rodzaj");
            policeUnit.miasto = reader.get("miasto");
            policeUnit.ulica = reader.get("ulica");
            policeUnit.kier = reader.getInt("kier");
            policeUnit.tel = reader.get("tel");
            policeUnit.fax = reader.get("fax");
            policeUnit.internet = reader.get("internet");
            policeUnit.mail = reader.get("mail");

            items.add(policeUnit);

        }

    }


    public void list(PrintStream out){
        for(PoliceUnit elem : items){
            out.println(elem.toString());
        }
    }

    PoliceUnitList haveWWW(){
        PoliceUnitList temp = new PoliceUnitList();
        for(PoliceUnit elem : items){
            if(!elem.internet.equals(""))
                temp.items.add(elem);
        }
        return temp;
    }

    PoliceUnitList nothaveFax(){
        PoliceUnitList temp = new PoliceUnitList();
        for (PoliceUnit elem : items){
            if (elem.fax.equals(""))
                temp.items.add(elem);
        }
        return temp;
    }

    PoliceUnitList sortInPlaceByMiasto(){
        class NameComparator implements Comparator<PoliceUnit> {
            @Override
            public int compare(PoliceUnit a1, PoliceUnit a2){
                return a1.miasto.compareTo(a2.miasto);
            }
        }

        this.sortInPlace(new NameComparator()); //lub units.sort poprostu
        return this;
    }

    PoliceUnitList sortInPlace(Comparator<PoliceUnit> cmp){
        this.items.sort(cmp);
        return this;
    }

    void countRodzaj(){
        for (PoliceUnit elem : items){
            rodzajCountMap.compute(elem.rodzaj,(key,value)->(value==null) ? 1 : value+1);
        }
    }
}
