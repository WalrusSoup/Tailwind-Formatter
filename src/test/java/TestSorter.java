import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;

public class TestSorter {

    @Test
    public void testCanSortVariantsWithRespectToScreenSize()
    {
        String outOfOrderVariants = "mx-auto container py-4 xl:bg-blue-200 md:bg-blue-800 bg-blue-500 dark:bg-blue-600 lg:bg-blue-900 sm:bg-blue-700";
        // This expected order is per the official docs & tested via play.tailwindcss.com
        String expectedOrder = "container mx-auto bg-blue-500 py-4 dark:bg-blue-600 sm:bg-blue-700 md:bg-blue-800 lg:bg-blue-900 xl:bg-blue-200";

        TailwindUtility utility = new TailwindUtility();
        utility.loadDefaultClassOrder();

        TailwindSorter sorter = new TailwindSorter(utility.classOrder, false);
        List<String> classes = Arrays.asList(outOfOrderVariants.split(" "));
        classes.sort(sorter);

        Assert.assertEquals(expectedOrder, String.join(" ", classes));
    }

    @Test
    public void testSortingWithFlex()
    {
        String input = "flex items-end justify-center p-4 text-center sm:items-center sm:p-0 min-h-full";
        String expected = "flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0";

        TailwindUtility utility = new TailwindUtility();
        utility.loadDefaultClassOrder();

        TailwindSorter sorter = new TailwindSorter(utility.classOrder, false);
        List<String> classes = Arrays.asList(input.split(" "));
        classes.sort(sorter);

        Assert.assertEquals(expected, String.join(" ", classes));
    }

    @Test
    public void testSortingVariantsAccordingToPrimaryClass()
    {
        // According to the default order, technically px-0 should be before text-left, not after
        String input = "sm:bg-red-500 sm:text-left sm:px-0 sm:rounded-lg";
        String expected = "sm:rounded-lg sm:bg-red-500 sm:px-0 sm:text-left";

        TailwindUtility utility = new TailwindUtility();
        utility.loadDefaultClassOrder();

        TailwindSorter sorter = new TailwindSorter(utility.classOrder, false);
        List<String> classes = Arrays.asList(input.split(" "));
        classes.sort(sorter);

        Assert.assertEquals(expected, String.join(" ", classes));
    }
}
