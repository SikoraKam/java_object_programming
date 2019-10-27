import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList {
    List<ListItem> itemsList;

    UnorderedList(){
        itemsList = new ArrayList<>();
    }

    void addItem(ListItem item){
        itemsList.add(item);
    }

    void writeHTML(PrintStream out){
        out.printf("<ul>");
        for(ListItem item : itemsList){
            item.writeHTML(out);
        }
        out.printf("</ul>");
    }
}
