package fr.dudie.onebusaway.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing a trip stop time.
 * 
 * @author Olivier Boudet
 */
public class TripStopTime {

    /** Stop. */
    private Stop stop;

    /** Arrival time. */
    @SerializedName("arrival")
    private Time arrivalTime;

    /** Departure time. */
    @SerializedName("departure")
    private Time departureTime;

    /** Stop headsign. */
    private String stopHeadsign;

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
     * Gets the arrivalTime.
     * 
     * @return the arrivalTime
     */
    public Time getArrivalTime() {

        return arrivalTime;
    }

    /**
     * Sets the arrivalTime.
     * 
     * @param arrivalTime
     *            the arrivalTime to set
     */
    public void setArrivalTime(final Time arrivalTime) {

        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the departureTime.
     * 
     * @return the departureTime
     */
    public Time getDepartureTime() {

        return departureTime;
    }

    /**
     * Sets the departureTime.
     * 
     * @param departureTime
     *            the departureTime to set
     */
    public void setDepartureTime(final Time departureTime) {

        this.departureTime = departureTime;
    }

    /**
     * Gets the stopHeadsign.
     * 
     * @return the stopHeadsign
     */
    public String getStopHeadsign() {

        return stopHeadsign;
    }

    /**
     * Sets the stopHeadsign.
     * 
     * @param stopHeadsign
     *            the stopHeadsign to set
     */
    public void setStopHeadsign(final String stopHeadsign) {

        this.stopHeadsign = stopHeadsign;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TripStopTime [stop=");
        builder.append(stop);
        builder.append(", arrivalTime=");
        builder.append(arrivalTime);
        builder.append(", departureTime=");
        builder.append(departureTime);
        builder.append(", stopHeadsign=");
        builder.append(stopHeadsign);
        builder.append("]");
        return builder.toString();
    }

}
