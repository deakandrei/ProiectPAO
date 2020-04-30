package managers.csvlayout;

import managers.DateFormatManager;
import models.Appointment;

import java.util.List;
import java.util.Optional;

public enum AppointmentLayout {
    ID, TITLE, DESCRIPTION, DATE,
    /* After the date column in the csv file follows an arbitrary number
     * of integers representing contact id's that go in the participants list */
    CONTACTS_START;

    public static List<String> serialize(Appointment appointment) {
        return List.of(appointment.getId().get().toString(),
                appointment.getTitle(),
                appointment.getDescription(),
                DateFormatManager.format(appointment.getDate()));
    }

    public static Appointment deserialize(List<String> line) {
        Appointment appointment = new Appointment(
                line.get(TITLE.ordinal()),
                line.get(DESCRIPTION.ordinal()),
                DateFormatManager.parse(line.get(DATE.ordinal()))
        );
        Integer id = Integer.getInteger(line.get(ID.ordinal()));
        appointment.setId(Optional.of(id));
        return appointment;
    }
}
