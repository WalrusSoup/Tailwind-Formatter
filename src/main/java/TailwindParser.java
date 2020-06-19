import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TailwindParser {
    private final String classNamesRegex = "[_a-zA-Z0-9\\s-:/]+";
    private final String regex = "\\bclass(?:Name)*\\s*=\\s*(?<quotes>[\"'])(?<classList1>" + classNamesRegex + ")[\"']|@apply (?<classList2>" + classNamesRegex + ");";
    private final TailwindSorter sorter;

    public TailwindParser(TailwindSorter sorter) {
        this.sorter = sorter;
    }

    public String processBody(String body) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(body);

        while (matcher.find()) {
            final boolean isApplyStatement;
            final String originalClassList;
            final String quotes = matcher.group("quotes");
            // Grab the inner contents of the class list for deduplication and arranging
            if (matcher.group("classList1") != null && !matcher.group("classList1").isEmpty()) {
                isApplyStatement = false;
                originalClassList = matcher.group("classList1");
            } else {
                isApplyStatement = true;
                originalClassList = matcher.group("classList2");
            }

            if (originalClassList.contains("\n")) {
                // multiline class lists are currently not supported
                continue;
            }

            final List<String> currentClasses = Arrays.asList(originalClassList.trim().split(" +"));
            // Sort the list of classes
            currentClasses.sort(sorter);
            // Create a linked hash set to remove duplicates
            final LinkedHashSet<String> currentClassesWithoutDuplicates = new LinkedHashSet<String>(currentClasses);
            body = body.replace(
                    encloseClassList(originalClassList, isApplyStatement, quotes),
                    encloseClassList(String.join(" ", currentClassesWithoutDuplicates), isApplyStatement, quotes)
            );
        }

        return body;
    }

    private String encloseClassList(String classList, boolean isApplyStatement, String quotes) {
        if (isApplyStatement) {
            return "@apply " + classList + ";";
        }

        return quotes + classList + quotes;
    }
}
