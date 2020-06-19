import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TestParser {
    private final List<String> classOrder = Arrays.asList(
        "first",
        "sm:first",
        "sec_ond",
        "THIRD",
        "lg:hover:last",
        "group-focus:la-st",
        "last"
    );

    @Test
    public void testProcessBody() {
        TailwindParser parser = new TailwindParser(new TailwindSorter(this.classOrder));

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
