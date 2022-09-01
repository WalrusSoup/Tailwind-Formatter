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

        TailwindSorter sorter = new TailwindSorter((new TailwindUtility().classOrder));
        List<String> classes = Arrays.asList(outOfOrderVariants.split(" "));
        classes.sort(sorter);

        Assert.assertEquals(expectedOrder, String.join(" ", classes));
    }

    @Test
    public void testSort()
    {
        // Cap this at 400 for test performance
//        int maxListSize = 400;
//        TailwindUtility utility = new TailwindUtility();
//        List<String> originalOrder = new ArrayList<String>(utility.classOrder.subList(0, maxListSize));
//        List<String> shuffledOrder = utility.classOrder.subList(0, maxListSize);
//        Collections.shuffle(shuffledOrder);
//        Assert.assertNotEquals(originalOrder, shuffledOrder);
//        shuffledOrder.sort(new TailwindSorter(new TailwindUtility().classOrder.subList(0, maxListSize)));
//        Assert.assertEquals(originalOrder, shuffledOrder);
    }
}
