package fr.dudie.onebusaway.model;

/*
 * [license]
 * ItineRennes Java API client
 * ----
 * Copyright (C) 2010 - 2013 Dudie
 * ----
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * [/license]
 */

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
