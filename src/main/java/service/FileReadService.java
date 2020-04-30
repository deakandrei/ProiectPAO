package service;

import managers.CSVFileLocation;
import managers.CSVFileManager;
import models.*;
import repositories.*;

import java.util.List;

public class FileReadService {
    IAppointmentRepository appointmentRepository = IAppointmentRepository.build(RepositoryType.FILE);
    IBoardRepository boardRepository = IBoardRepository.build(RepositoryType.FILE);
    ICategoryRepository categoryRepository = ICategoryRepository.build(RepositoryType.FILE);
    IContactRepository contactRepository = IContactRepository.build(RepositoryType.FILE);
    ITaskRepository taskRepository = ITaskRepository.build(RepositoryType.FILE);
    ITimedTaskRepository timedTaskRepository = ITimedTaskRepository.build(RepositoryType.FILE);


    private FileReadService() {}

    public static FileReadService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        private static FileReadService INSTANCE = new FileReadService();
    }

    public List<Appointment> readAppointments() {
        return appointmentRepository.readAll();
    }

    public List<Board> readBoards() {
        AuditService.log("Read board info");
        return boardRepository.readAll();
    }

    public List<Category> readCategories() {
        AuditService.log("Read categories");
        return categoryRepository.readAll();
    }

    public List<Contact> readContacts() {
        AuditService.log("Read contact info");
        return contactRepository.readAll();
    }

    public List<Task> readTasks() {
        AuditService.log("Read Tasks");
        return taskRepository.readAll();
    }

    public List<TimedTask> readTimedTasks() {
        AuditService.log("Read Tasks with deadline");
        return timedTaskRepository.readAll();
    }

    public void saveAppintments(List<Appointment> list) {
        for(Appointment a : list) {
            appointmentRepository.save(a);
        }
        AuditService.log("Saved appointments");
    }

    public void saveBoards(List<Board> list) {
        for(Board a : list) {
            boardRepository.save(a);
        }
        AuditService.log("Saved boards");
    }

    public void saveCategories(List<Category> list) {
        for(Category a : list) {
            categoryRepository.save(a);
        }
        AuditService.log("Saved categories");
    }

    public void saveContacts(List<Contact> list) {
        for(Contact a : list) {
            contactRepository.save(a);
        }
        AuditService.log("Saved contacts");
    }

    public void saveTasks(List<Task> list) {
        for(Task a : list) {
            taskRepository.save(a);
        }
        AuditService.log("Saved tasks");
    }

    public void saveTimedTasks(List<TimedTask> list) {
        for(TimedTask a : list) {
            timedTaskRepository.save(a);
        }
        AuditService.log("Saved tasks with deadline");
    }
    public IAppointmentRepository getAppointmentRepository() {
        return appointmentRepository;
    }

    public IBoardRepository getBoardRepository() {
        return boardRepository;
    }

    public ICategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public IContactRepository getContactRepository() {
        return contactRepository;
    }

    public ITaskRepository getTaskRepository() {
        return taskRepository;
    }

    public ITimedTaskRepository getTimedTaskRepository() {
        return timedTaskRepository;
    }

    /* Saves changes to disk */
    public void flush() {
        for(CSVFileLocation file : CSVFileLocation.values()) {
            CSVFileManager manager = CSVFileManager.getInstance(file.getPath());
            manager.save();
        }
        AuditService.log("Flush data");
    }
}
