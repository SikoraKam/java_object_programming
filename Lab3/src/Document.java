import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Document {
    String title;
    Photo photo;
    List<Section> sections = new ArrayList<>();

    //opcjonalny konstruktor
    Document(String title){
        this.title = title;
    }

    Document setTitle(String title){
        this.title = title;
        return this;
    }
    Document setPhoto(String photoUrl){
        this.photo = new Photo(photoUrl);
        return this;
    }
    Section addSection(String sectionTitle){
        // utwórz sekcję o danym tytule i dodaj do sections
        Section newSection = new Section();
        newSection.setTitle(sectionTitle);
        sections.add(newSection);
        return newSection;
    }
    Document addSection(Section s){
        sections.add(s);
        return this;
    }
    void writeHTML(PrintStream out){
        out.printf("<!DOCTYPE html >\n"  +
                "<html lang=\"pl\">\n" +
                "<head>\n" +
                "<title>CV</title>\n" +
                "<meta charset=\"UTF-8\">" +
                "</head>\n" +
                "<body style=\"background-color:powderblue;\">\n"); //znaczniki
        out.printf("<h1>%s</h1>\n", title); //tytuł
        photo.writeHTML(out); // obrazek

        for(Section i : sections){
            i.writeHTML(out);
        }

        out.printf("</body>" + "</html>\n");
    }
}
