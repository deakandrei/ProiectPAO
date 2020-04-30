package managers;

import Utils.CSVColumnComparator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public final class CSVFileManager {
    /* The file this instance manages */
    private Path filename;
    /* in-memory file contents */
    private ArrayList<List<String>> data;

    private CSVFileManager(Path file) {
        this.filename = file;
        this.data = new ArrayList<>();
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(this.filename));
            for(String line : lines) {
                List<String> elements = Arrays.asList(line.split(","));
                data.add(elements);
            }
        } catch (IOException e) {
            System.out.println("Could not open the file " + file + ". Initializing data to empty.");
            e.printStackTrace();
        }
    }

    public static CSVFileManager getInstance(Path filename) {
        CSVFileManager instance = SingletonHolder.openFiles.get(filename);
        if(null != instance) {
            return instance;
        }
        instance = new CSVFileManager(filename);
        SingletonHolder.openFiles.put(filename, instance);
        return instance;
    }

    private static final class SingletonHolder {
        /* Only allow new instances for files that were not opened before.
         * If the constructor is called with the same file argument twice,
         * the second time it just returns the previous instance */
        private static final Map<Path, CSVFileManager> openFiles = new HashMap<>();
    }

    public List<String> getLine(int index) {
        return data.get(index);
    }

    public void addLine(List<String> line) {
        data.add(line);
    }

    /* In case the new line has less columns than the line being
    * replaced, only the columns present in the new line will be updated.
    * This is so that code in the repositories can worry about
    * the columns that make up the object, and safely ignore the
    * trailing columns without losing the information there. */
    public void updateFirstColumns(int index, List<String> line) {
        List<String> oldLine = this.getLine(index);
        if(line.size() < oldLine.size()) {
            List<String> prepareLine = new ArrayList<>(line);
            prepareLine.addAll(oldLine.subList(
                    line.size(), oldLine.size()));
            data.set(index, prepareLine);
            return;
        }
        data.set(index, line);
    }

    public void replaceLine(int index, List<String> line) {
        data.set(index, line);
    }

    public void deleteLine(int index) {
        data.remove(index);
    }

    public String getFilename() { return this.filename.toString(); }

    public Optional<Integer> findFirstMatch(Function<List<String>, Boolean> criteria) {
        for(int i = 0; i < data.size(); i++) {
            if(criteria.apply(data.get(i))) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public List<List<String>> findAllMatches(Function<List<String>, Boolean> criteria) {
        List<List<String>> result = new ArrayList<>();
        for(List<String> line : data) {
            if(criteria.apply(line)) {
                result.add(line);
            }
        }
        return result;
    }

    public void save() {
        try(
            BufferedWriter file = Files.newBufferedWriter(this.filename);
        ) {
            for(List<String> line : data) {
                file.write(line.get(0));
                for(int i = 1; i < line.size(); i++) {
                    file.write(',');
                    file.write(line.get(i));
                }
                file.write('\n');
            }
        } catch (IOException e) {
            System.out.println("Could not save the changes to the file " + this.filename.toString());
            e.printStackTrace();
        }
    }

    /* Returns a value larger than all other values in the file */
    public static int findUnusedId(CSVFileManager file, int column) {
        int freeIndex;
        Optional<Integer> lineIndex = file.max(new CSVColumnComparator(column)
                                                .reversed());
        if(lineIndex.isPresent()) {
            List<String> line = file.getLine(lineIndex.get());
            String value = line.get(column);
            freeIndex = Integer.parseInt(value);
            freeIndex++;
        } else {
            freeIndex = 1;
        }
        return freeIndex;
    }

    public Optional<Integer> max(Comparator<List<String>> comp) {
        int maxIndex = 0;
        for(int i = 1; i < this.data.size(); i++) {
            if(comp.compare(data.get(maxIndex), data.get(i)) > 0) {
                maxIndex = i;
            }
        }
        if(data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(maxIndex);
    }
}
