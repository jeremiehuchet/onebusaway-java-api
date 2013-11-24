package fr.dudie.onebusaway.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time extends Date {

    private static final long serialVersionUID = -985402416765605536L;

    private static final String TIME_FORMAT = "HH:mm";

    public Time(final long date) {
        super(date);
    }

    @Override
    public String toString() {
        return new SimpleDateFormat(TIME_FORMAT).format(this);
    }

    public static Time from(final String s) throws ParseException {
        final Date d = new SimpleDateFormat(TIME_FORMAT).parse(s);
        return new Time(d.getTime());
    }
}
