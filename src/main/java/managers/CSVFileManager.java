package managers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

public class CSVFileManager {
    /* The file this particullar instance manages */
    private String filename;
    /* in-memory file contents */
    private ArrayList<List<String>> data;

    private CSVFileManager(String file) {
        this.filename = file;
        this.data = new ArrayList<>();
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(this.filename)));
            for(String line : lines) {
                List<String> elements = Arrays.asList(line.split(","));
                data.add(elements);
            }
        } catch (IOException e) {
            System.out.println("Could not open the file " + file + ". Initializing data to empty.");
            e.printStackTrace();
        }
    }

    public static CSVFileManager getInstance(String filename) {
        CSVFileManager instance = SingletonHolder.openFiles.get(filename);
        if(instance != null) {
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
        private static final Map<String, CSVFileManager> openFiles = new HashMap<>();
    }

    public List<String> getLine(int index) {
        return data.get(index);
    }

    public Optional<List<String>> searchLine(Function<List<String>, Boolean> criteria) {
        for(List<String> line : data) {
            if(criteria.apply(line)) {
                return Optional.of(line);
            }
        }
        return Optional.empty();
    }

    public void addLine(List<String> line) {
        data.add(line);
    }

    public void replaceLine(int index, List<String> line) {
        data.set(index, line);
    }

    public void save() {
        try(
            BufferedWriter file = Files.newBufferedWriter(Paths.get(this.filename));
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
            System.out.println("Could not save the changes to the file " + this.filename);
            e.printStackTrace();
        }
    }
}
