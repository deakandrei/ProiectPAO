package managers;

import java.nio.file.Path;

public enum CSVFileLocation {
    APPOINTMENTS("appointments.csv"),
    BOARDS("boards.csv"),
    CATEGORIES("categories.csv"),
    CONTACTS("contacts.csv"),
    TASKS("tasks.csv"),
    TIMED_TASKS("timedtasks.csv");
    
    private String directory = "csvdata";
    private String file;

    CSVFileLocation(String basename) {
        this.file = basename;
    }

    public Path getPath() {
        return Path.of(directory, file);
    }
}
