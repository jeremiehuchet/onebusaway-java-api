package fr.dudie.onebusaway.model;

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
