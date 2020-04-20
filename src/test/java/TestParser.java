import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class TestParser {

    @Test
    public void testProcessBody() {
        TailwindParser parser = new TailwindParser();

        Arrays.asList("html", "css").forEach(ext -> {
            String input = "";
            String expected = "";

            try {
                input = readFixtureFile("input." + ext);
                expected = readFixtureFile("expected." + ext);
            } catch (IOException | URISyntaxException e) {
                Assert.fail("Could not load fixture file: " + e);
            }

            Assert.assertEquals(expected, parser.processBody(input));
        });
    }

    private String readFixtureFile(String file) throws IOException, URISyntaxException {
        return new String(
                Files.readAllBytes(
                        Paths.get(
                                TestParser.class.getResource("/fixtures/" + file).toURI()
                        )
                )
        );
    }
}
