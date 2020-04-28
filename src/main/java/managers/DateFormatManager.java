package managers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatManager {
    private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static String format(Date d) {
        return df.format(d);
    }

    public static Date parse(String str) {
        try {
            return df.parse(str);
        } catch (ParseException e) {
            System.out.println("Could not parse the date " + str);
            e.printStackTrace();
        }

        return Calendar.getInstance().getTime();
    }
}
