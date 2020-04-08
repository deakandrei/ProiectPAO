package service;

import models.Appointment;
import models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

/*
 * The possible actions are/will be:
 * - list all tasks/appointments/contacts
 * - search through the tasks/appointments
 * - list all tasks in a category
 * - create/update/delete categories
 * - create/update/delete tasks
 * - create/update/delete appointments
 * - create/update/delete contacts
 */
public class OrganizerService {

    private OrganizerService() {
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>();
    }

    public SortedSet<Appointment> getAllAppointments() {
        return null; //new TreeSet<Appointment>();
    }

    public static OrganizerService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        private static OrganizerService INSTANCE = new OrganizerService();
    }
}
