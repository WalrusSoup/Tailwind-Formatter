import java.util.Comparator;
import java.util.List;

public class TailwindSorter implements Comparator<String> {
    private final List<String> classOrder;

    public TailwindSorter(List<String> classOrder) {
        this.classOrder = classOrder;
    }

    @Override
    public int compare(String s, String t1) {
        int positionS = classOrder.indexOf(s);
        int positionT1 = classOrder.indexOf(t1);
        if(positionS == -1) {
            positionS = 9999;
        }
        if(positionT1 == -1) {
            positionT1 = 9999;
        }
        return positionS - positionT1;
    }
}
