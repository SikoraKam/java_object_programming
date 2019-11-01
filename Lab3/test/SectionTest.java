

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;


public class SectionTest {

    @Test
    public void writeHTML() throws Exception{

        String sectionTitle = "sekcja";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        Section section = new Section();
        section.setTitle(sectionTitle).addParagraph("paragraph1").writeHTML(ps);

        String result = null;
        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        assertTrue(result.contains("<section>"));
        assertTrue(result.contains("<h2>" + sectionTitle + "</h2>"));
        assertTrue(result.contains("paragraph1"));
        assertTrue(result.contains("</section>"));
    }
}
