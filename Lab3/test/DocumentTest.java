

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;


public class DocumentTest{
    @org.junit.Test
    public void writeHTML() throws Exception {
        String title = "Tytuł";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        Document document =  new Document(title);

        document.setPhoto("https://www.wykop.pl/cdn/c3201142/comment_7KLkT0B1YlLHigeJ4vvWJFlk8yc9tKrV.jpg"); //trzeba dac setphoto fbo inaczej Nullpointerexception. writeHtml odnosi sie do klasy photo
        document.writeHTML(ps);

        String result = null;
        try{
            result = os.toString("UTF-8");
        }catch (UnsupportedEncodingException exception){
            exception.printStackTrace();
        }

        assertTrue(result.contains("<!DOCTYPE html"));
        assertTrue(result.contains("<html lang="));
        assertTrue(result.contains("<title>CV</title>"));
        assertTrue(result.contains("<head>"));
        assertTrue(result.contains("<meta charset=\"UTF-8\">"));
        assertTrue(result.contains("</head>"));
        assertTrue(result.contains("<h1>" + title + "</h1>"));
        assertTrue(result.contains("</body>" + "</html>"));

    }
}
