
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CSVReaderTest {

    @Test
    public void read() throws IOException {
        CSVReader reader = null;
        try {
            reader = new CSVReader("with-header.csv",";",true, "Windows-1250");
            CSVReader reader2 = new CSVReader("no-header.csv",";",true, "Windows-1250");
            CSVReader reader3 = new CSVReader("elec.csv",";",true, "Windows-1250");
            CSVReader reader4 = new CSVReader("missing-values.csv",";",true, "Windows-1250");
            CSVReader reader5 = new CSVReader("accelerator.csv",";",true, "Windows-1250");
        }
        catch (FileNotFoundException e){
            fail("nie znaleziono pliku");
        }
        catch(IOException e) {
            fail("IO exception");
        }


        List<String> names = new ArrayList<>();
        while (reader.next()){
            names.add(reader.get(1));
        }
        assertEquals("Jan",names.get(0));
        assertEquals("Dominik",names.get(9));
    }

    @Test
    public void getters() throws IOException {
        CSVReader reader = null;
        CSVReader reader2 = null;
        try {
            reader = new CSVReader("with-header.csv",";",true, "Windows-1250");
            reader2 = new CSVReader("no-header.csv",";",false, "Windows-1250");
            CSVReader reader3 = new CSVReader("elec.csv",";",true, "Windows-1250");
            CSVReader reader4 = new CSVReader("missing-values.csv",";",true, "Windows-1250");
            CSVReader reader5 = new CSVReader("accelerator.csv",";",true, "Windows-1250");
        }
        catch (FileNotFoundException e){
            fail("nie znaleziono pliku");
        }
        catch(IOException e) {
            fail("IO exception");
        }

        reader.next();
        String name = reader.get("imię");
        assertEquals("Jan", name);

        reader2.next();
        int id = reader2.getInt(0);
        assertEquals(1,id);

    }
    @Test
    public void missing_values() throws IOException {
        System.out.print("\nMissing values \n");

        CSVReader reader = new CSVReader("missing-values.csv", ";", true, "Windows-1250");
        System.out.println();

        for (int i = 0; i < reader.columnLabels.size(); i++) {
            System.out.print(reader.columnLabels.get(i) + " ");
        }
        System.out.println();
        while (reader.next()) {
            for (int i = 0; i < reader.columnLabels.size(); i++) {
                System.out.print(reader.get(i) + ";");
            }
            System.out.println();
        }

        CSVReader reader2 = new CSVReader("missing-values.csv", ";", true, "Windows-1250");

        reader2.next();
        reader2.next();
        long result = reader2.getLong("population");
        assertEquals(-1,result);
        String result2 = reader2.get("population");
        assertEquals("",result2);

    }

    @Test
    public void missing_columns() throws IOException {
        System.out.print("\nMissing columns \n");
        CSVReader reader = new CSVReader("with-header.csv", ";", true, "Windows-1250");

        reader.next();
        System.out.print(reader.get("helo"));
        String result = reader.get("nazwa_ktorej_nie_ma");
        assertEquals("",result);

        long result2 = reader.getLong(-1);
        assertEquals(-1,result2);


    }
}