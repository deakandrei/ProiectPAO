package repositories;

import Utils.CSV;
import Utils.CSVFindIdCriteria;
import managers.CSVFileManager;
import managers.DateFormatManager;
import models.Appointment;

import java.util.List;
import java.util.Optional;

public class FileAppointmentRepository implements IAppointmentRepository {
    private CSVFileManager file = CSVFileManager
                                    .getInstance("csvdata/appointments.csv");

    @Override
    public void save(Appointment appointment) {
        Optional<Integer> id = appointment.getId();
        if(id.isEmpty()) {
            int newId = CSV.findUnusedId(file, FieldColumn.ID.ordinal());
            appointment.setId(Optional.of(newId));
            file.addLine(serialize(appointment));
        } else {
            Optional<Integer> index = file.findFirstMatch(
                    new CSVFindIdCriteria(FieldColumn.ID.ordinal(), id.get()));
            if(index.isPresent()) {
                file.replaceLine(index.get(), serialize(appointment));
            } else {
                System.out.println("Expected to find id " + id.get().toString()
                                    + " in the file " + file.getFilename());
            }
        }
    }

    @Override
    public Optional<Appointment> find(int id) {
        Optional<Integer> index = file.findFirstMatch(
                new CSVFindIdCriteria(FieldColumn.ID.ordinal(), id));
        if(index.isPresent()) {
            List<String> line = file.getLine(index.get());
            Appointment appointment = new Appointment(
                    line.get(FieldColumn.TITLE.ordinal()),
                    line.get(FieldColumn.DESCRIPTION.ordinal()),
                    DateFormatManager.parse(line.get(FieldColumn.DATE.ordinal()))
            );
            appointment.setId(Optional.of(id));
            return Optional.of(appointment);
        }
        return Optional.empty();
    }

    public enum FieldColumn {
        ID, TITLE, DESCRIPTION, DATE
        /* After the date column in the csv file follows an arbitrary number
        * of integers representing contact id's that go in the participants list */
    }

    private List<String> serialize(Appointment appointment) {
        return List.of(appointment.getId().get().toString(),
                appointment.getTitle(),
                appointment.getDescription(),
                DateFormatManager.format(appointment.getDate()));
    }
}
