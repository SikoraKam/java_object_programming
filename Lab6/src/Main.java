import java.io.IOException;
import java.util.Locale;

public class Main {
    public static void main (String[]args) throws IOException {
        CSVReader reader = null;
        try {
            reader = new CSVReader("with-header.csv",";",true, "Windows-1250");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(reader.next()) {
            String name = reader.get("nazwisko");
            System.out.printf("%s \n", name);
        }

        CSVReader reader1 = new CSVReader("no-header.csv",";",true, "Windows-1250");
        CSVReader reader2 = new CSVReader("no-header.csv",";",true, "Windows-1250");

        reader1.next();
        String name1 = reader1.get(1);
        reader2.next();
        String name2 = reader2.get(1);

        System.out.printf("%s %s", name1, name2);



    }
}
