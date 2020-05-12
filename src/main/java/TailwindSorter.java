import java.util.Comparator;
import java.util.List;

public class TailwindSorter implements Comparator<String> {
    private final List<String> classOrder;
    private final int lastPosition;

    public TailwindSorter(List<String> classOrder) {
        this.classOrder = classOrder;
        this.lastPosition = classOrder.size();
    }

    @Override
    public int compare(String s, String t1) {
        int positionS = classOrder.indexOf(s);
        int positionT1 = classOrder.indexOf(t1);
        if(positionS == -1) {
            positionS = lastPosition;
        }
        if(positionT1 == -1) {
            positionT1 = lastPosition;
        }
        return positionS - positionT1;
    }
}
