package repositories;

import models.Appointment;

import java.util.Optional;

public class FileAppointmentRepository implements IAppointmentRepository {

    @Override
    public void save(Appointment appointment) {

    }

    @Override
    public Optional<Appointment> find(int id) {
        return Optional.empty();
    }
}
