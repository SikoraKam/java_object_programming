import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StockItemList {
    List <StockItem> items = new ArrayList();


    public void read(String filename) throws IOException {
        CSVReader reader = null;
        try {
            reader = new CSVReader(filename, ";", true, "UTF-8");
        }catch (IOException e) {
            e.printStackTrace();
        }

        while (reader.next()){
            StockItem stockItem = new StockItem();
            stockItem.product_code = reader.get("product_code");
            stockItem.name = reader.get("name");
            stockItem.price = reader.getDouble("price");
            stockItem.vat = reader.getInt("vat");
            stockItem.unit = reader.get("unit");
            stockItem.category = reader.get("category");
            stockItem.producer = reader.get("producer");
            stockItem.weight = reader.getDouble("weight");
            stockItem.stock = reader.getInt("stock");
            stockItem.delivery = reader.get("delivery");

            stockItem.categories = stockItem.category.split(">");

            items.add(stockItem);
        }
    }

    public void findMostExpensivefromCategory(String categoryString){
        double price = 0;
        StockItem temp = new StockItem();
        for(StockItem element : items){
            if(element.category.contains(categoryString)){
                if(price < element.price) {
                    price = element.price;
                    temp = element;
                }
            }
        }
        System.out.println(temp.toString());
    }

    public void list(PrintStream out){
        for(StockItem elem : items){
            out.print(elem.toString());
        }
    }

     StockItemList selectByName(String pattern){
        StockItemList resultList  = new StockItemList();
        String convertedPattern = pattern.toLowerCase();
        for (StockItem elem : items){
            String covertedName = elem.name.toLowerCase();
            if(covertedName.contains(convertedPattern)){
                resultList.items.add(elem);
            }
        }
        return resultList;
    }

    StockItemList filter(Predicate<StockItem> predicate){
        StockItemList result  = new StockItemList();
        for (StockItem elem : items){
            if(predicate.test(elem))
                result.items.add(elem);
        }
        return result;
    }

}
