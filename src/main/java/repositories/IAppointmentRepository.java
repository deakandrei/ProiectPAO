package repositories;

import models.Appointment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IAppointmentRepository {
    void save(Appointment appointment);

    Optional<Appointment> find(int id);

    public List<Appointment> getAppointmentsAfter(Date date);

    public List<Appointment> readAll();

    static IAppointmentRepository build(RepositoryType type) {
        return new FileAppointmentRepository();
    }
}
