package service;

import models.Appointment;
import models.TimedTask;
import repositories.IAppointmentRepository;
import repositories.IContactRepository;
import repositories.ITimedTaskRepository;
import repositories.RepositoryType;

import java.util.*;

/* This service deals with Appointments and with
* TimedTasks */
public class ScheduleService {
    private IAppointmentRepository appRepo;
    private ITimedTaskRepository taskRepo;
    private IContactRepository contactRepository;
    /* Deals with upcoming deadlines and appointments */
    private SortedSet<Appointment> appointments;
    private SortedSet<TimedTask> deadlines;

    private ScheduleService() {
        appointments = new TreeSet<>(Comparator.comparing(Appointment::getDate));
        deadlines = new TreeSet<>(Comparator.comparing(TimedTask::getDueDate));

        Date now = Calendar.getInstance().getTime();

        FileReadService service = FileReadService.getInstance();
        appRepo = service.getAppointmentRepository();
        appointments.addAll(appRepo.getAppointmentsAfter(now));
        taskRepo = service.getTimedTaskRepository();
        deadlines.addAll(taskRepo.getDeadlinesAfter(now));

        contactRepository = service.contactRepository;
    }

    public static ScheduleService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        private static ScheduleService INSTANCE = new ScheduleService();
    }

    public void addAppointment(Appointment appointment) {
        Date today = Calendar.getInstance().getTime();
        if(appointment.getDate().before(today)) {
            System.out.println("Can not add appointments for past days.");
            AuditService.log("Add appointment", "FAIL");
            return;
        }
        appRepo.save(appointment);
        AuditService.log("Add appointment");

        AuditService.log("Add task to board");
    }

    public Optional<Appointment> getFirstAppointment() {
        AuditService.log("Get first upcoming appointment");
        if(appointments.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(appointments.first());
    }

    public Optional<TimedTask> getMostUrgentDeadline() {
        AuditService.log("Get most urgent deadline task");
        if(appointments.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(deadlines.first());
    }

    public SortedSet<Appointment> getAppointments() {
        AuditService.log("Get all upcoming appointments");
        if(appointments.isEmpty()) {
            return new TreeSet<>();
        }
        return appointments;
    }

    public SortedSet<TimedTask> getTasks() {
        AuditService.log("Get all tasks with a deadline");
        if(appointments.isEmpty()) {
            return new TreeSet<>();
        }
        return deadlines;
    }

}
