package fr.itinerennes.api.client.model;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Olivier Boudet
 * @author Jeremie Huchet
 */
public class StopSchedule {

    private Date date;

    private Stop stop;

    /** List of routes for the stop times. */
    private final List<Route> routes;

    /** List of stop times for the route and the stop. */
    private final List<ScheduleStopTime> stopTimes;

    public StopSchedule() {

        routes = new ArrayList<Route>();
        stopTimes = new ArrayList<ScheduleStopTime>();
    }

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public Date getDate() {

        return date;
    }

    /**
     * Sets the date.
     * 
     * @param date
     *            the date to set
     */
    public void setDate(final Date date) {

        this.date = date;
    }

    /**
     * Gets the stop.
     * 
     * @return the stop
     */
    public Stop getStop() {

        return stop;
    }

    /**
     * Sets the stop.
     * 
     * @param stop
     *            the stop to set
     */
    public void setStop(final Stop stop) {

        this.stop = stop;
    }

    /**
     * Gets the routes.
     * 
     * @return the routes
     */
    public List<Route> getRoutes() {

        return routes;
    }

    /**
     * Gets the stopTimes.
     * 
     * @return the stopTimes
     */
    public List<ScheduleStopTime> getStopTimes() {

        return stopTimes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StopSchedule [date=");
        builder.append(date);
        builder.append(", stop=");
        builder.append(stop);
        builder.append(", routes=");
        builder.append(routes);
        builder.append(", stopTimes=");
        builder.append(stopTimes);
        builder.append("]");
        return builder.toString();
    }
}
