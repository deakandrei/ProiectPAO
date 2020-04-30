package repositories;

import Utils.CSVFindIdCriteria;
import managers.CSVFileManager;
import managers.CSVFileLocation;
import managers.DateFormatManager;
import managers.csvlayout.AppointmentLayout;
import managers.csvlayout.ContactLayout;
import models.Appointment;
import models.Contact;

import java.util.*;
import java.util.stream.Collectors;

public class FileAppointmentRepository implements IAppointmentRepository {
    private final CSVFileManager appointmentFile = CSVFileManager
                                    .getInstance(CSVFileLocation.APPOINTMENTS.getPath());
    private final CSVFileManager contactFile = CSVFileManager
            .getInstance(CSVFileLocation.CONTACTS.getPath());

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
                    new HashSet<>(line.subList(AppointmentLayout.CONTACTS_START.ordinal(), line.size()));
            Set<Contact> contactInfo = appointmentFile.findAllMatches(
                            c -> contactIds.contains(
                            c.get(ContactLayout.ID.ordinal())))
                    .stream()
                    .map(ContactLayout::deserialize)
                    .collect(Collectors.toSet());
            A.setParticipants(contactInfo);
            A.setId(Optional.of(id));
            return Optional.of(A);
        }
        return Optional.empty();
    }

    @Override
    public List<Appointment> getAppointmentsAfter(Date date) {
        List<List<String>> list =
            appointmentFile.findAllMatches(
                line -> DateFormatManager.parse(line.get(AppointmentLayout.DATE.ordinal()))
                        .after(date)
            );

        return convertFromCSV(list);
    }

    @Override
    public List<Appointment> readAll() {
        return convertFromCSV(appointmentFile.findAllMatches(line -> true));
    }

    private List<Appointment> convertFromCSV(List<List<String>> list) {
        Set<String> contactIds = new HashSet<>();
        for(List<String> line : list) {
            contactIds.addAll(getContacts(line));
        }

        Map<String, Contact> contacts =
                contactFile.findAllMatches(
                        line -> contactIds.contains(line.get(ContactLayout.ID.ordinal()))
                ).stream()
                        .map(ContactLayout::deserialize)
                        .collect(Collectors.toMap(
                                c -> c.getId().get().toString(),
                                c -> c
                        ));

        return list.stream()
                .map(line -> getAppointment(line, contacts))
                .collect(Collectors.toList());
    }

    private Appointment getAppointment(List<String> line, Map<String, Contact> contacts) {
        Appointment app = AppointmentLayout.deserialize(line);
        List<String> participants = getContacts(line);
        for(String p : participants) {
            app.getParticipants().add(contacts.get(p));
        }
        app.setId(Optional.of(Integer.parseInt(line.get(AppointmentLayout.ID.ordinal()))));
        return app;
    }

    private List<String> getContacts(List<String> line) {
        return line.subList(AppointmentLayout.CONTACTS_START.ordinal(), line.size());
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
        List<String> line = new ArrayList<>();
        line.addAll(firstColumns);
        line.addAll(contacts);
        return line;
    }
}
