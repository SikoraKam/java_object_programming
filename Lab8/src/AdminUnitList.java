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

        while (reader.next()) {
            AdminUnit aunit = new AdminUnit();
            aunit.name = reader.get("name");
            aunit.adminLevel = reader.getInt("admin_level");
            aunit.population = reader.getDouble("population");
            aunit.area = reader.getDouble("area");
            aunit.density = reader.getDouble("density");
                //------------BoundingBox-------------------
            double x1 = reader.getDouble("x1");
            double x2 = reader.getDouble("x2");
            double x3 = reader.getDouble("x3");
            double x4 = reader.getDouble("x4");
            double y1 = reader.getDouble("y1");
            double y2 = reader.getDouble("y2");
            double y3 = reader.getDouble("y3");
            double y4 = reader.getDouble("y4");
            BoundingBox bbx = new BoundingBox();
            bbx.addPoint(x1,y1);
            bbx.addPoint(x2,y2);
            bbx.addPoint(x3,y3);
            bbx.addPoint(x4,y4);
            aunit.bbox = bbx;
                //-------------------------------------------
            //przypisanie id węzłom w mapie
            idToUnit.put(reader.getLong("id"),aunit);

            // przypisanie id rodzicow
            long idOfParent = reader.getLong("parent");
            unitToParent.put(aunit,idOfParent);

            //przypisanie childrenow do mapy
            long parentId = reader.getLong(1);
            if(parentId != -1){
                if(parentid2childid.containsKey(parentId))
                    parentid2childid.get(parentId).add(aunit);
                else {
                    List<AdminUnit> childrenList = new ArrayList<>();
                    childrenList.add(aunit);
                    parentid2childid.put(parentId,childrenList);
                }
            }
            units.add(aunit);

        }
            //ustawienie relacji
            for(AdminUnit node : units){
                //ustawienie relacji rodzica
                node.parent = idToUnit.getOrDefault(unitToParent.get(node),null);

                //ustawinie relacji dzieci
                long parentId = unitToParent.get(node);
                if(node.parent != null){
                    node.parent.children = parentid2childid.getOrDefault(parentId,new ArrayList<>());
                }
            }

            this.fixMissingValues();

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

    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * Czyli sąsiadami wojweództw są województwa, powiatów - powiaty, gmin - gminy, miejscowości - inne miejscowości
     * @param unit - jednostka, której sąsiedzi mają być wyznaczeni
     * @param maxdistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit,
     *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList getNeighbours(AdminUnit unit, double maxdistance) {
        AdminUnitList result = new AdminUnitList();
        for (AdminUnit node : this.units) {
            if (node.adminLevel == unit.adminLevel && unit != node) {
                if (unit.adminLevel >= 8) {
                    if (unit.bbox.distanceTo(node.bbox) < maxdistance)
                        result.units.add(node);
                } else if (unit.bbox.intersects(node.bbox))
                    result.units.add(node);
            }
        }
        return result;
    }


    List<AdminUnit> getUnits(){
        return units;
    }
}
