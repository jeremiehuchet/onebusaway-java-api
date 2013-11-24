package fr.dudie.onebusaway.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a trip schedule.
 * 
 * @author Olivier Boudet
 * @author Jeremie Huchet
 */
public class TripSchedule {

    /** List of stop times. */
    private final List<TripStopTime> stopTimes;

    /** The route. */
    private Route route;

    public TripSchedule() {

        stopTimes = new ArrayList<TripStopTime>();
    }

    /**
     * Gets the stopTimes.
     * 
     * @return the stopTimes
     */
    public List<TripStopTime> getStopTimes() {

        return stopTimes;
    }

    /**
     * Gets the route.
     * 
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets the route.
     * 
     * @param route
     *            the route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TripSchedule [stopTimes=");
        builder.append(stopTimes);
        builder.append(", route=");
        builder.append(route);
        builder.append("]");
        return builder.toString();
    }

}
