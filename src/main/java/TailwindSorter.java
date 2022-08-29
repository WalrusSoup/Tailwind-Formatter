import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TailwindSorter implements Comparator<String> {
    private final List<String> classOrder;
    private final int lastPosition;

    private final List<String> variantOrder = Arrays.asList(
            "first",
            "last",
            "odd",
            "even",
            "visited",
            "checked",
            "empty",
            "read-only",
            "group-hover",
            "group-focus",
            "focus-within",
            "hover",
            "focus",
            "focus-visible",
            "active",
            "disabled",
            "xs",
            "sm",
            "md",
            "lg",
            "xl",
            "2xl",
            "3xl"
    );

    public TailwindSorter(List<String> classOrder)
    {
        this.classOrder = classOrder;
        this.lastPosition = classOrder.size();
    }

    @Override
    public int compare(String s, String t1)
    {
        int positionS = classOrder.indexOf(removeVariant(s)) + offsetVariant(s);
        int positionT1 = classOrder.indexOf(removeVariant(t1)) + offsetVariant(t1);
        if(positionS == -1) {
            positionS = lastPosition;
        }
        if(positionT1 == -1) {
            positionT1 = lastPosition;
        }
        return positionS - positionT1;
    }

    /**
     * Returns true if a variant is detected within this string
     *
     * @param className string class to be checked
     *
     * @return if a variant was detected at the start of the string, returns true
     */
    public int offsetVariant(String className) {
        if(className.contains("dark:")) {
            className = className.replace("dark:", "");
        }

        // If there is still a variant here, pull out the variant and see what natural order it shoudl be included in
        return variantOrder.indexOf(className.substring(0, className.lastIndexOf(":")));
    }

    /**
     * Removes the variant to check the original class location in the output
     *
     * @return string
     */
    public String removeVariant(String originalClassName)
    {
        int trimVariantAt = originalClassName.lastIndexOf(":");
        if(trimVariantAt == -1) {
            return originalClassName;
        }
        return originalClassName.substring(trimVariantAt);
    }
}
