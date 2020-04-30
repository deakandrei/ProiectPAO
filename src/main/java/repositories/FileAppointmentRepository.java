package repositories;

import Utils.CSVFindIdCriteria;
import managers.CSVFileManager;
import managers.CSVFilesLocationManager;
import managers.csvlayout.AppointmentLayout;
import managers.csvlayout.ContactLayout;
import models.Appointment;
import models.Contact;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FileAppointmentRepository implements IAppointmentRepository {
    private CSVFileManager appointmentFile = CSVFileManager
                                    .getInstance(CSVFilesLocationManager.APPOINTMENTS.getPath());
    private CSVFileManager contactFile = CSVFileManager
            .getInstance(CSVFilesLocationManager.CONTACTS.getPath());

    @Override
    public void save(Appointment appointment) {
        Optional<Integer> id = appointment.getId();
        if(id.isEmpty()) {
            int newId = CSVFileManager.findUnusedId(appointmentFile, AppointmentLayout.ID.ordinal());
            appointment.setId(Optional.of(newId));
            appointmentFile.addLine(createLine(appointment));
        } else {
            Optional<Integer> index = appointmentFile.findFirstMatch(
                    new CSVFindIdCriteria(AppointmentLayout.ID.ordinal(), id.get()));
            if(index.isPresent()) {
                appointmentFile.replaceLine(index.get(), createLine(appointment));
            } else {
                System.out.println("Expected to find id " + id.get().toString()
                                    + " in the file " + appointmentFile.getFilename());
            }
        }
    }

    @Override
    public Optional<Appointment> find(int id) {
        Optional<Integer> index = appointmentFile.findFirstMatch(
                new CSVFindIdCriteria(AppointmentLayout.ID.ordinal(), id));
        if(index.isPresent()) {
            List<String> line = appointmentFile.getLine(index.get());
            Appointment A = AppointmentLayout.deserialize(line);
            /* now also search for the contacts */
            Set<String> contactIds =
                line.subList(AppointmentLayout.CONTACTS_START.ordinal(), line.size())
                    .stream()
                    .collect(Collectors.toSet());
            List<Contact> contactInfo = appointmentFile.findAllMatches(
                            c -> contactIds.contains(
                            c.get(ContactLayout.ID.ordinal())))
                    .stream()
                    .map(contactLine -> ContactLayout.deserialize(contactLine))
                    .collect(Collectors.toList());
            A.setParticipants(contactInfo);
            return Optional.of(A);
        }
        return Optional.empty();
    }

    /* Appends the contact id information at the end of the line */
    private List<String> createLine(Appointment appointment) {
        List<String> firstColumns = AppointmentLayout.serialize(appointment);
        /* This code does not check for missing contact id's.
        * It is assumed that the contact information was saved
        * first. */
        List<String> contacts = appointment.getParticipants().stream()
                .map(a -> a.getId().get().toString())
                .collect(Collectors.toList());
        firstColumns.addAll(contacts);
        return firstColumns;
    }
}
