package Utils;

import java.util.Comparator;

public class CSVColumnComparator {
    static Comparator<Integer> columnComparator(int column) {
        return (a, b) -> {
            Integer ia = Integer.getInteger(a.get(column));
            Integer ib = Integer.getInteger(b.get(column));
            if(ia < ib) {
                return -1;
            }
            if(ia == ib) {
                return 0;
            }
            return 1;
        };


    }
}
