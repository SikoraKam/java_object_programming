import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Document cv = new Document("Krzysztof Krawczyk");
        cv.setPhoto("https://www.wykop.pl/cdn/c3201142/comment_7KLkT0B1YlLHigeJ4vvWJFlk8yc9tKrV.jpg");
        cv.addSection("Wykształcenie")
                .addParagraph("2000-2005 Przedszkole im. Królewny Snieżki w Krakowie")
                .addParagraph("2006-2012 SP7 im Ronalda Regana w Radomiu")
                .addParagraph("2013-2019 pozostawał na rządowym zasiłku");
        cv.addSection("Umiejetności")
                .addParagraph(new ParagraphWithList().setContent("Poznane języki: ")
                                        .addItemToList("C")
                                        .addItemToList("C++")
                                        .addItemToList("Java")
                )
                .addParagraph("Nabyte podstawowe umiejętności w zawodach: murarz, tynkarz, akrobata");
        cv.addSection("Zainteresowania").addParagraph(new ParagraphWithList().setContent("Zainteresowania: ")
                                        .addItemToList("Dobry film")
                                        .addItemToList("Dobra muzyka")
                                        .addItemToList("Żona somsiada")
                );
        try {
            cv.writeHTML(new PrintStream("cv.html","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
