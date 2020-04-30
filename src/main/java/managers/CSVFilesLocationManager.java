package managers;

import java.nio.file.Path;

public enum CSVFilesLocationManager {
    APPOINTMENTS("appointments.csv"),
    BOARDS("boards.csv"),
    CATEGORIES("categories.csv"),
    CONTACTS("contacts.csv"),
    TASKS("tasks.csv"),
    TIMED_TASKS("timedtasks.csv");
    
    private String directory = "csvdata";
    private String file;

    CSVFilesLocationManager(String basename) {
        this.file = basename;
    }

    public Path getPath() {
        return Path.of(directory, file);
    }
}
