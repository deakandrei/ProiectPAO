package repositories;

import models.Appointment;

import java.util.Optional;

public class FileAppointmentRepository implements IAppointmentRepository {

    @Override
    public void Save(Appointment appointment) {

    }

    @Override
    public Optional<Appointment> Find(int id) {
        return Optional.empty();
    }
}
