package Utils;

import java.util.List;
import java.util.function.Function;

public class CSVFindIdCriteria implements Function<List<String>, Boolean> {
    private int columnNr;
    private int id;

    public CSVFindIdCriteria(Integer columnNr, Integer id) {
        this.columnNr = columnNr;
        this.id = id;
    }
    @Override
    public Boolean apply(List<String> line) {
        return Integer.getInteger(line.get(columnNr)).equals(id);
    }
}
