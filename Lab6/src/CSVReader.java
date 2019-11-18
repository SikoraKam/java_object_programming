import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;
    String[] current;

    List<String> columnLabels = new ArrayList<>();
    Map<String,Integer> columnLabelsToInt = new HashMap<>();

    //----------------------KONSTRUKTORY-----------------------------------
    public CSVReader(String filename,String delimiter,boolean hasHeader, String charset) throws IOException {
        this(new InputStreamReader(new FileInputStream(filename), Charset.forName(charset)), delimiter, hasHeader, charset);
    }
    public CSVReader(String filename, String delimiter, String charset) throws IOException{
        this(filename, delimiter, false, charset);
    }

    public CSVReader(String filename, String charset) throws IOException{
        this(filename, ",", false, charset);
    }

    public CSVReader(Reader reader, String delimiter, boolean hasHeader, String charset) throws IOException{
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader)
            parseHeader();

    }


//----------------------------------------------------------------------------------
    void parseHeader() throws IOException{
        String line = null;

        line = reader.readLine();
        if(line==null)
            return;
        // podziel na pola
        String[]header = line.split(delimiter);
        // przetwarzaj dane w wierszu
        for(int i=0; i<header.length; i++){
            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i],i);

        }
    }

    boolean next() throws IOException{
            String line = reader.readLine();
            if(line == null)
                return false;
            current = line.split(delimiter);
            return true;
    }



    //----------------------------GETTERY-------------------------------------
    int getInt(int column){
        String value = current[column];
        return Integer.parseInt(value);
    }
    int getInt(String column){
        String value;
        value = current[columnLabelsToInt.get(column)];
        return Integer.parseInt(value);
    }

    String get(String column){
        return current[columnLabelsToInt.get(column)];
    }

    String get(int column){
        return current[column];
    }

    double getDouble(String column){
        String value;
        value = current[columnLabelsToInt.get(column)];
        return Double.parseDouble(value);
    }

    double getDouble(int column){
        String value = current[column];
        return Double.parseDouble(value);
    }

    long getLong(String column){
        String value;
        value = current[columnLabelsToInt.get(column)];
        return Long.parseLong(value);
    }

    long getLong(int column){
        String value = current[column];
        return Long.parseLong(value);
    }


    List<String> getColumnLabels(){
        return columnLabels;
    }

    int getRecordLength(){
        return current.length;
    }


//--------------------------------------------------------------------------------------


    boolean isMissing(int columnIndex) {
        if (columnIndex < 0 || columnIndex > this.getRecordLength() ) {
            return true;
        }
        else if (current[columnIndex].isEmpty()) { //isEmpty wykrywa także spacje jako pusty string
            return true;
        }
        return false;
    }

    boolean isMissing(String columnLabel){
        int columnIndex = columnLabelsToInt.get(columnLabel);
        return this.isMissing(columnIndex);
    }

    //----------------------------Funkcje getTime i getDate----------------------

    LocalTime getTime(int columnIndex, String format){
        LocalTime time = LocalTime.parse(current[columnIndex], DateTimeFormatter.ofPattern(format));
        return time;
    }
    LocalTime getTime(String columnName, String format){
        LocalTime time = LocalTime.parse(get(columnName), DateTimeFormatter.ofPattern(format));
        return time;
    }

    LocalDate getDate(int columnIndex, String format){
        LocalDate date = LocalDate.parse(current[columnIndex], DateTimeFormatter.ofPattern(format));
        return date;
    }

    LocalDate getDate(String columnName, String format){
        LocalDate date = LocalDate.parse(get(columnName), DateTimeFormatter.ofPattern(format));
        return date;
    }

    LocalDateTime getDateTime(int columnIndex, String format){
        LocalDateTime dt = LocalDateTime.parse(current[columnIndex], DateTimeFormatter.ofPattern(format));
        return dt;
    }


}













