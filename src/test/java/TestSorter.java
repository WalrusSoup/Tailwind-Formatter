import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;

public class TestSorter {

    @Test
    public void testSort()
    {
        HeadwindUtility utility = new HeadwindUtility();
        List<String> originalOrder = new ArrayList<String>(utility.classOrder);
        List<String> shuffledOrder = utility.classOrder;
        Collections.shuffle(shuffledOrder);
        Assert.assertNotEquals(originalOrder, shuffledOrder);
        shuffledOrder.sort(new HeadwindSorter());
        Assert.assertEquals(originalOrder, shuffledOrder);
    }
}
