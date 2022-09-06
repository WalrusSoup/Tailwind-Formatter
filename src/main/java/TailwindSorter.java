import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TailwindSorter implements Comparator<String> {
    private final List<String> classOrder;
    private final int variantsStartAt;
    private final int lastPosition;

    private final Boolean isCustomConfiguration;

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
        "dark",
        "xs",
        "sm",
        "md",
        "lg",
        "xl",
        "2xl",
        "3xl",
        "4xl",
        "5xl"
    );

    public TailwindSorter(List<String> classOrder, Boolean isCustomConfiguration)
    {
        // convert all of these to lowercase for comparison later
        this.classOrder = classOrder.stream().map(s -> s.toLowerCase()).collect(Collectors.toList());
        this.variantsStartAt = classOrder.size();
        if(!isCustomConfiguration) {
            this.lastPosition = this.variantsStartAt + this.variantOrder.size();
        } else {
            this.lastPosition = this.variantsStartAt;
        }
        this.isCustomConfiguration = isCustomConfiguration;
    }

    @Override
    public int compare(String s, String t1)
    {
        int positionS = calculateProperOrder(s.toLowerCase());
        int positionT1 = calculateProperOrder(t1.toLowerCase());

        if(positionS == -1) {
            positionS = lastPosition;
        }
        if(positionT1 == -1) {
            positionT1 = lastPosition;
        }
        return positionS - positionT1;
    }

    // If the class contains a variant, use the variant order
    // Otherwise, use the default class order as they appear in the CSS (pre-generated)
    public int calculateProperOrder(String className)
    {
        if(className.contains(":") && !isCustomConfiguration) {
            return orderByVariant(className);
        }

        return classOrder.indexOf(className);
    }

    /**
     * Variants are strange because they have their own order. Instead of generating an even more massive tailwind file,
     * we can try to detect it and leverage the order of variants given to us in the tailwind config file to sort them.
     *
     * Variants also have order within them, so when we fish out a variant also add the original class offset to it
     * This ensures that sm:px-2 is after things such as sm:bg-red-500, which is the way tailwind prefers it.
     * It's a little confusing to add the class order size, but it's the only way to make sure the variants AND the classes don't run over each other.
     * Open to ideas there
     *
     *
     * @param className string class to be checked
     */
    public int orderByVariant(String className)
    {
        int variantLocation = className.lastIndexOf(":");
        // If we see no variants, just return 0
        if(variantLocation == -1) {
            return 0;
        }
        // fish out the first variant to use as an anchor point
        String[] variants = className.split(":");
        if(variants.length >= 1)  {
            return variantsStartAt + (variantOrder.indexOf(variants[0]) * this.classOrder.size()) + calculateProperOrder(variants[1]);
        }

        // If we have no ID on the variant, return
        return variantsStartAt;
    }
}
