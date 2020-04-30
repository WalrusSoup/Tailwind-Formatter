import java.util.Comparator;

public class TailwindSorter implements Comparator<String> {
    private TailwindUtility utility = new TailwindUtility();

    @Override
    public int compare(String s, String t1) {
        int positionS = utility.classOrder.indexOf(s);
        int positionT1 = utility.classOrder.indexOf(t1);
        if(positionS == -1) {
            positionS = 9999;
        }
        if(positionT1 == -1) {
            positionT1 = 9999;
        }
        return positionS - positionT1;
    }
}
