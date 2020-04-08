package repositories;

import models.Appointment;

import java.util.Optional;

public interface IAppointmentRepository {
    void Save(Appointment appointment);

    Optional<Appointment> Find(int id);

    static IAppointmentRepository build(RepositoryType type) {
        return new FileAppointmentRepository();
    }
}
