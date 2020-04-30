package service;

import managers.DateFormatManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class AuditService {
    private BufferedWriter file;
    private int tick;
    private final int saveInterval = 500;

    private AuditService() {
        try {
            file = new BufferedWriter(new FileWriter("audit.csv", true));
            file.write("Action, Timestamp, Status\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AuditService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void log(String action) {
        getInstance().writeAction(action, "OK");
    }

    public static void log(String action, String status) {
        getInstance().writeAction(action, status);
    }

    private static final class SingletonHolder {
        private static AuditService INSTANCE = new AuditService();
    }

    public void writeAction(String action, String status) {
        if(file == null) {
            return;
        }

        try {
            file.write(action);
            file.write(',');
            Date time = new Date();
            file.write(DateFormatManager.format(time));
            file.write(',');
            file.write(status);
            file.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        tick++;
        if(tick >= saveInterval) {
            tick = 0;
            flush();
        }
    }


    public void close() {
        try {
            file.close();
            file = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush() {
        try {
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
