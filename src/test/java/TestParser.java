import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TestParser {
    private final List<String> customClassOrder = Arrays.asList(
        "first",
        "sm:first",
        "second",
        "THIRD",
        "fourth",
        "group-focus:fifth",
        "last"
    );

    private final List<String> cssClassOrder = Arrays.asList(
        "first",
        "sec_ond",
        "THIRD",
        "group-focus:la-st",
        "lg:hover:last",
        "last",
        "sm:first"
    );

    @Test
    public void testProcessingCustomConfigurationOnCss()
    {
        TailwindParser parser = new TailwindParser(new TailwindSorter(cssClassOrder, true));
        String input = "";
        String expected = "";

        try {
            input = readFixtureFile("input.css");
            expected = readFixtureFile("expected.css");
        } catch (IOException | URISyntaxException e) {
            Assert.fail("Could not load fixture file: " + e);
        }

        Assert.assertEquals(expected, parser.processBody(input));
    }

    @Test
    public void testProcessingCustomConfigurationOnRegularFile()
    {
        TailwindParser parser = new TailwindParser(new TailwindSorter(customClassOrder, true));
        String input = "";
        String expected = "";

        try {
            input = readFixtureFile("custom.html");
            expected = readFixtureFile("custom_expected.html");
        } catch (IOException | URISyntaxException e) {
            Assert.fail("Could not load fixture file: " + e);
        }

        Assert.assertEquals(expected, parser.processBody(input));
    }

    @Test
    public void testProcessingVue()
    {
        TailwindUtility tailwindUtility = new TailwindUtility();
        tailwindUtility.loadDefaultClassOrder();

        TailwindParser parser = new TailwindParser(new TailwindSorter(tailwindUtility.classOrder, false));
        String input = "";
        String expected = "";

        try {
            input = readFixtureFile("input.vue");
            expected = readFixtureFile("expected.vue");
        } catch (IOException | URISyntaxException e) {
            Assert.fail("Could not load fixture file: " + e);
        }

        Assert.assertEquals(expected, parser.processBody(input));
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
