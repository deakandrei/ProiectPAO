package repositories;

import models.Appointment;

import java.util.Optional;

public interface IAppointmentRepository {
    void save(Appointment appointment);

    Optional<Appointment> find(int id);

    static IAppointmentRepository build(RepositoryType type) {
        return new FileAppointmentRepository();
    }
}
