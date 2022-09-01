import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TailwindSorter implements Comparator<String> {
    private final List<String> classOrder;
    private final int variantsStartAt;
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
        "3xl",
        "4xl",
        "5xl"
    );

    public TailwindSorter(List<String> classOrder)
    {
        this.classOrder = classOrder;
        this.variantsStartAt = classOrder.size();
        this.lastPosition = this.variantsStartAt + this.variantOrder.size();
    }

    @Override
    public int compare(String s, String t1)
    {
        int positionS = calculateProperOrder(s);
        int positionT1 = calculateProperOrder(t1);

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
        if(className.contains(":")) {
            return orderByVariant(className);
        }

        return classOrder.indexOf(className);
    }

    /**
     * Variants are strange because they have their own order. Instead of generating an even more massive tailwind file,
     * we can try to detect it and leverage the order of variants given to us in the tailwind config file to sort them.
     *
     * @param className string class to be checked
     */
    public int orderByVariant(String className) {
        int variantLocation = className.lastIndexOf(":");
        // If we see no variants, just return 0
        if(variantLocation == -1) {
            return 0;
        }
        // fish out the first variant to use as an anchor point
        String[] variants = className.split(":");
        if(variants.length >= 1)  {
            return variantsStartAt + variantOrder.indexOf(variants[0]);
        }

        // If we have no ID on the variant, return
        return variantsStartAt;
    }
}
