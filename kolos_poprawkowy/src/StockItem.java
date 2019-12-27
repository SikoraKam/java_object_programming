import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockItem {
    String product_code;
    //boolean active;
    String name;
    double price;
    int vat;
    String unit;
    String category;
    String producer;
    double weight;
    int stock;
    //int ean;
    String delivery;

    String [] categories;

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append(product_code + ";" + name + ";" + price + ";" + vat + ";" + unit
                + ";" + category + ";"+ producer + ";" + weight + ";" + stock + ";" + delivery + "\n" );
        return buf.toString();
    }

}
