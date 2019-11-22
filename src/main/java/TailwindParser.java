import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TailwindParser {
    private final String regex = "\\bclass(?:Name)*\\s*=\\s*([\\\\\"\\']([_a-zA-Z0-9\\s\\-\\:]+)[\\\\\"\\'])";
    private HeadwindSorter headwindSorter = new HeadwindSorter();

    public String processBody(String body)
    {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(body);

        while(matcher.find()) {
            // Grab the original entry class="container mx-auto bg-black"...
            String targetReplacement = matcher.group(1);
            // Grab the inner contents of the class list for deduplication and arranging
            String originalClassList = matcher.group(2);
            List<String> currentClasses = Arrays.asList(originalClassList.split(" "));
            // Sort the list of classes
            currentClasses.sort(headwindSorter);
            // Create a linked hash set to remove duplicates
            LinkedHashSet<String> currentClassesWithoutDuplicates = new LinkedHashSet<String>(currentClasses);
            body = body.replace(targetReplacement, "\"" + String.join(" ", currentClassesWithoutDuplicates) + "\"");
        }
        return body;
    }
}
