import java.io.IOException;

public class Main {
    public static  void main(String[] args){
        StockItemList stockItemList = new StockItemList();
        try {
            stockItemList.read("poprawkowe_dane.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
       // stockItemList.list(System.out);
        //b)
       // stockItemList.findMostExpensivefromCategory("Pojazdy dla dzieci na akumulator");
        //c)
        //StockItemList keybordList = stockItemList.selectByName("keyboard");
        //keybordList.list(System.out);
        //d)
        StockItemList carsList = stockItemList.filter(a->a.categories[2].equals("Samochody na akumulator"));
        carsList.list(System.out);
    }
}
