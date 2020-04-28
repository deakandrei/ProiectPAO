package repositories;

import models.Appointment;

public interface IAppointmentRepository {
    void save(Appointment appointment);

    Object find(int id);

    static IAppointmentRepository build(RepositoryType type) {
        return new FileAppointmentRepository();
    }
}
