package Utils;

import managers.CSVFileManager;

import java.util.Optional;

public class CSV {
    /* Returns a value larger than all other values in the file */
    public static int findUnusedId(CSVFileManager file, int column) {
        int freeIndex;
        Optional<Integer> lineIndex = file.max(new CSVColumnComparator(column));
        if(lineIndex.isPresent()) {
            freeIndex = Integer.getInteger(file.getLine(lineIndex.get()).get(column));
            freeIndex++;
        } else {
            freeIndex = 1;
        }
        return freeIndex;
    }
}
