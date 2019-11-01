import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class ListItemTest {

    @Test
    public void writeHTML() throws Exception{

        String content = "zawartosc";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        new ListItem(content).writeHTML(ps);

        String result = null;
        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        assertTrue(result.contains("<li>" + content + "</li>"));

    }
}