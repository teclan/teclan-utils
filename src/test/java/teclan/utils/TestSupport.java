package teclan.utils;

import java.io.File;

public class TestSupport {

    public static File getFile(String fileName) {
        return new File("src/test/resources/files/" + fileName);
    }

}
