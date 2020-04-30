import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;

public class TestSorter {

    @Test
    public void testSort()
    {
        TailwindUtility utility = new TailwindUtility();
        List<String> originalOrder = new ArrayList<String>(utility.classOrder);
        List<String> shuffledOrder = utility.classOrder;
        Collections.shuffle(shuffledOrder);
        Assert.assertNotEquals(originalOrder, shuffledOrder);
        shuffledOrder.sort(new TailwindSorter(new TailwindUtility().classOrder));
        Assert.assertEquals(originalOrder, shuffledOrder);
    }
}
