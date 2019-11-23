import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
    //mapa owzorowujaca id -> unit
    Map<Long, AdminUnit> idToUnit=new HashMap<>();

    //mapa odwzorowujaca unit -> idRodzica
    Map<AdminUnit,Long> unitToParent = new HashMap<>();

    Map<Long,List<AdminUnit>> parentid2childid = new HashMap<>();

    /**
     * Czyta rekordy pliku i dodaje do listy
     * @param filename nazwa pliku
     */
    public void read(String filename) throws IOException {
        CSVReader reader=null;
        try {
             reader = new CSVReader(filename, ",", true, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (reader.next()){
            AdminUnit aunit = new AdminUnit();
            aunit.name = reader.get("name");
            aunit.adminLevel = reader.getInt("admin_level");
            aunit.population = reader.getDouble("population");
            aunit.area = reader.getDouble("area");
            aunit.density = reader.getDouble("density");

            //parent:
                //przypisanie id węzłom, tylko w mapie
            idToUnit.put(reader.getLong("id"),aunit);

                //prezypisanie rodziców węzłom, i w polu i w mapie
            if(reader.isMissing(2)) {
                unitToParent.put(aunit, null);
                aunit.parent = null;
            }
            else {
                unitToParent.put(aunit, reader.getLong(2));
                //wskazujemy idRodzica i dostajemy odpowiedni wezel
                aunit.parent = this.idToUnit.get(reader.getLong(2)); // mozna tez this.idToUnit.get(this.unitToParent.get(aunit))
            }

            this.fixMissingValues();

            //dodanie do glownej listy
            units.add(aunit);

        }
    }


    /**
     * Wypisuje zawartość korzystając z AdminUnit.toString()
     * @param out strumień wyjścia
     */
    void list(PrintStream out){
        for(AdminUnit element : units){
            out.print(element.toString());
        }
    }

    /**
     * Wypisuje co najwyżej limit elementów począwszy od elementu o indeksie offset
     * @param out - strumień wyjsciowy
     * @param offset - od którego elementu rozpocząć wypisywanie
     * @param limit - ile (maksymalnie) elementów wypisać
     */
    void list(PrintStream out,int offset, int limit ){
        for(int i = offset; i<offset + limit; i++){
            out.print(units.get(i).toString());
        }
    }
    /**
     * Zwraca nową listę zawierającą te obiekty AdminUnit, których nazwa pasuje do wzorca
     * @param pattern - wzorzec dla nazwy
     * @param regex - jeśli regex=true, użyj finkcji String matches(); jeśli false użyj funkcji contains()
     * @return podzbiór elementów, których nazwy spełniają kryterium wyboru
     */
    AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList ret = new AdminUnitList();
        // przeiteruj po zawartości units
        // jeżeli nazwa jednostki pasuje do wzorca dodaj do ret
        if (regex){
            for (AdminUnit element : units){
                if(element.name.matches(pattern))
                    ret.units.add(element);
            }
        }
        if (!regex){
            for (AdminUnit element : units){
                if(element.name.contains(pattern))
                    ret.units.add(element);
            }
        }
        return ret;
    }

    // zastosowane zostały podmetody fixMissingDensity i fixMissingPopulation, aby można było wywoływać rekurencyjnie na rodzicach
    // dzieki temu obsluzymy sytuacje gdy rodzic takze nie bedzie mial pola density
    void fixMissingValues(){
        for(AdminUnit element : units){
            if(element.density == -1){
                try {
                    element.fixMissingDensity();
                }catch (IllegalCallerException e){}

            }
            if(element.population == -1){
                element.fixMissingPopulation();
            }
        }
    }

}
