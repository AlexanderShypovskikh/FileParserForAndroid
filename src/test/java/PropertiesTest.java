import com.example.ini.parser.Properties;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class PropertiesTest {
    private static final String TEST_DATA = ""
            + "# comment 1\n"
            + "! comment 2\n"
            + "key1:value\n"
            + "key2=value\n"
            + "key3=multiline \\\n"
            + "\tvalue\n";

    private static final String TEST_ERRONEUS_DATA = ""
            + "# comment 1\n"
            + "! comment 2\n"
            + "key1:value\n"
            + "key2=value\n"
            + "ahahah, me break file\n"
            + "kev3=multiline \\\n"
            + "\tvalue\n";


    @Test
    public void parse() throws Exception {
        Properties testProps = new Properties();
        InputStream is = new ByteArrayInputStream(TEST_DATA.getBytes("utf-8"));
        testProps.parse(is);
        //System.out.println("test");
    }

    @Test
    public void parseWithError() throws Exception {
        Properties testProps = new Properties();
        boolean exceptionThrown = false;
        InputStream is = new ByteArrayInputStream(TEST_ERRONEUS_DATA.getBytes("utf-8"));
        try {
            testProps.parse(is);
        } catch(RuntimeException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);
    }

    public static Properties getTestData() throws UnsupportedEncodingException {
        Properties testProps = new Properties();
        InputStream is = new ByteArrayInputStream(TEST_DATA.getBytes("utf-8"));
        testProps.parse(is);
        return testProps;
    }

    @Test
    public void getMultilineString() throws Exception {
        Properties testProps = getTestData();
        String result = testProps.getString("key3");
        Assert.assertNotNull(result);
        Assert.assertEquals("multiline value", result);
    }


    @Test
    public void get() throws Exception {
        Properties testProps = getTestData();
        String result = testProps.getString("key1");
        Assert.assertNotNull(result);
        Assert.assertEquals("value", result);
    }

    @Test
    public void getString() throws Exception {
        Properties testProps = getTestData();
    }

    @Test
    public void getInt() throws Exception {
        Properties testProps = getTestData();
    }

}