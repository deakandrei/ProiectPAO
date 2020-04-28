package Utils;

import java.util.Comparator;
import java.util.List;

public class CSVColumnComparator implements Comparator<List<String>> {
    private int column;
    public CSVColumnComparator(int column) {
        this.column = column;
    }

    @Override
    public int compare(List<String> l1, List<String> l2) {
        Integer ia = Integer.getInteger(l1.get(column));
        Integer ib = Integer.getInteger(l2.get(column));
        return ia.compareTo(ib);
    }
}
