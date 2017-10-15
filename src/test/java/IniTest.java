import com.example.ini.parser.Ini;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class IniTest {
    public static final String TEST_DATA =
            "; некоторый комментарий\n" +
                    "# комментарий в стиле Unix\n" +
                    "\n" +
                    "[Section1]\n" +
                    "; комментарий о разделе\n" +
                    "var2=значение_2\n" +
                    "var1=значение_1 ; иногда допускается комментарий к отдельному параметру\n" +
                    "var2=значение_2\n" +
                    "  \n" +
                    "[Section2]\n" +
                    "var1=значение_1\n" +
                    "\n" +
                    "; иногда позволяется перечислять несколько значений через запятую\n" +
                    "[Section3]\n" +
                    "var1=значение_1_1, значение_1_2, значение_1_3\n" +
                    "var2=значение_2\n" +
                    "; в Zend Framework массив задаётся следующим способом\n" +
                    "[Section3.1]\n" +
                    "var1[]=значение_1_1\n" +
                    "var1[]=значение_1_2\n" +
                    "var1[]=значение_1_3\n" +
                    "var2=значение_2\n" +
                    "\n" +
                    "; Иногда значения отсутствуют \n" +
                    "[Section4.0]\n" +
                    "[ViewState]\n" +
                    "Mode=\n" +
                    "Vid=\n" +
                    "FolderType=Generic";

    @Test
    public void parse() throws Exception {
        Ini ini = new Ini();
        InputStream is = new ByteArrayInputStream(TEST_DATA.getBytes("utf-8"));
        ini.parse(is);
    }
}