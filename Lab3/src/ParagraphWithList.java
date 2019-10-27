import java.io.PrintStream;

public class ParagraphWithList extends Paragraph{

    UnorderedList unorderedList;

    ParagraphWithList(){
        super();
        this.unorderedList = new UnorderedList();
    }

    ParagraphWithList(String content){
        super(content);
        this.unorderedList = new UnorderedList();
    }
    ParagraphWithList setContent(String content){
        super.setContent(content);
        return this;
    }
    ParagraphWithList addItemToList(String content){
        unorderedList.addItem(new ListItem(content));
        return this;
    }
    void writeHTML(PrintStream out){
        super.writeHTML(out);
        unorderedList.writeHTML(out);
    }

}

