package teclan.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PdfTest {
    @Test
    public void validPdf() {
        assertTrue("pdf".equals(
                FileUtils.getMediaType(TestSupport.getFile("pdf/teclan.pdf"))));

        assertTrue(FileUtils
                .isSpecialType(TestSupport.getFile("pdf/teclan.pdf"), "pdf"));
    }

    @Test
    public void invalidPdf() {

        assertFalse("pdf".equals(FileUtils
                .getMediaType(TestSupport.getFile("pdf/teclan.png.pdf"))));

        assertFalse(FileUtils.isSpecialType(
                TestSupport.getFile("pdf/teclan.png.pdf"), "pdf"));
    }

    @Test
    public void extensionTest() {
        assertTrue("pdf".equals(FileUtils.getMediaTypeWithExtension(
                TestSupport.getFile("pdf/teclan.pdf"))));
    }

    @Test
    public void supportExtensionTest() {
        FileUtils.getExtensionMatch("pdf");

    }

}
