/* 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.dudie.onebusaway.model;

import java.util.Date;

/**
 * Class representing ArrivalAndDeparture OneBusAway object.
 * 
 * @author Olivier Boudet
 */
public class ArrivalAndDeparture {

    /** The route id for the arriving vehicle. */
    private String routeId;

    /** the Trip id for the arriving vehicle. */
    private String tripId;

    /** The service date (at midnight) for the trip. */
    private Date serviceDate;

    /** The stop id of the stop the vehicle is arriving at. */
    private String stopId;

    /** The index of the stop into the sequence of stops that make up the trip for this arrival. */
    private int stopSequence;

    /**
     * The route short name that potentially overrides the route short name in the referenced route
     * element.
     */
    private String routeShortName;

    /**
     * The route long name that potentially overrides the route long name in the referenced route
     * element.
     */
    private String routeLongName;

    /**
     * The trip headsign that potentially overrides the trip headsign in the referenced trip
     * element.
     */
    private String tripHeadsign;

    /** Scheduled arrival date/time. */
    private Date scheduledArrivalTime;

    /** Scheduled departure date/time. */
    private Date scheduledDepartureTime;

    /** The distance of the arriving transit vehicle from the stop, in meters. */
    private Double distanceFromStop;

    /**
     * Gets the routeId.
     * 
     * @return the routeId
     */
    public final String getRouteId() {

        return routeId;
    }

    /**
     * Sets the routeId.
     * 
     * @param routeId
     *            the routeId to set
     */
    public final void setRouteId(final String routeId) {

        this.routeId = routeId;
    }

    /**
     * Gets the tripId.
     * 
     * @return the tripId
     */
    public final String getTripId() {

        return tripId;
    }

    /**
     * Sets the tripId.
     * 
     * @param tripId
     *            the tripId to set
     */
    public final void setTripId(final String tripId) {

        this.tripId = tripId;
    }

    /**
     * Gets the serviceDate.
     * 
     * @return the serviceDate
     */
    public final Date getServiceDate() {

        return serviceDate;
    }

    /**
     * Sets the serviceDate.
     * 
     * @param serviceDate
     *            the serviceDate to set
     */
    public final void setServiceDate(final Date serviceDate) {

        this.serviceDate = serviceDate;
    }

    /**
     * Gets the stopId.
     * 
     * @return the stopId
     */
    public final String getStopId() {

        return stopId;
    }

    /**
     * Sets the stopId.
     * 
     * @param stopId
     *            the stopId to set
     */
    public final void setStopId(final String stopId) {

        this.stopId = stopId;
    }

    /**
     * Gets the stopSequence.
     * 
     * @return the stopSequence
     */
    public final int getStopSequence() {

        return stopSequence;
    }

    /**
     * Sets the stopSequence.
     * 
     * @param stopSequence
     *            the stopSequence to set
     */
    public final void setStopSequence(final int stopSequence) {

        this.stopSequence = stopSequence;
    }

    /**
     * Gets the routeShortName.
     * 
     * @return the routeShortName
     */
    public final String getRouteShortName() {

        return routeShortName;
    }

    /**
     * Sets the routeShortName.
     * 
     * @param routeShortName
     *            the routeShortName to set
     */
    public final void setRouteShortName(final String routeShortName) {

        this.routeShortName = routeShortName;
    }

    /**
     * Gets the routeLongName.
     * 
     * @return the routeLongName
     */
    public final String getRouteLongName() {

        return routeLongName;
    }

    /**
     * Sets the routeLongName.
     * 
     * @param routeLongName
     *            the routeLongName to set
     */
    public final void setRouteLongName(final String routeLongName) {

        this.routeLongName = routeLongName;
    }

    /**
     * Gets the tripHeadsign.
     * 
     * @return the tripHeadsign
     */
    public final String getTripHeadsign() {

        return tripHeadsign;
    }

    /**
     * Sets the tripHeadsign.
     * 
     * @param tripHeadsign
     *            the tripHeadsign to set
     */
    public final void setTripHeadsign(final String tripHeadsign) {

        this.tripHeadsign = tripHeadsign;
    }

    /**
     * Gets the scheduledArrivalTime.
     * 
     * @return the scheduledArrivalTime
     */
    public final Date getScheduledArrivalTime() {

        return scheduledArrivalTime;
    }

    /**
     * Sets the scheduledArrivalTime.
     * 
     * @param scheduledArrivalTime
     *            the scheduledArrivalTime to set
     */
    public final void setScheduledArrivalTime(final Date scheduledArrivalTime) {

        this.scheduledArrivalTime = scheduledArrivalTime;
    }

    /**
     * Gets the scheduledDepartureTime.
     * 
     * @return the scheduledDepartureTime
     */
    public final Date getScheduledDepartureTime() {

        return scheduledDepartureTime;
    }

    /**
     * Sets the scheduledDepartureTime.
     * 
     * @param scheduledDepartureTime
     *            the scheduledDepartureTime to set
     */
    public final void setScheduledDepartureTime(final Date scheduledDepartureTime) {

        this.scheduledDepartureTime = scheduledDepartureTime;
    }

    /**
     * Gets the distanceFromStop.
     * 
     * @return the distanceFromStop
     */
    public Double getDistanceFromStop() {

        return distanceFromStop;
    }

    /**
     * Sets the distanceFromStop.
     * 
     * @param distanceFromStop
     *            the distanceFromStop to set
     */
    public void setDistanceFromStop(final Double distanceFromStop) {

        this.distanceFromStop = distanceFromStop;
    }

    /*
     * (non-javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder();
        builder.append("ArrivalAndDeparture [routeId=");
        builder.append(routeId);
        builder.append(", tripId=");
        builder.append(tripId);
        builder.append(", serviceDate=");
        builder.append(serviceDate);
        builder.append(", stopId=");
        builder.append(stopId);
        builder.append(", stopSequence=");
        builder.append(stopSequence);
        builder.append(", routeShortName=");
        builder.append(routeShortName);
        builder.append(", routeLongName=");
        builder.append(routeLongName);
        builder.append(", tripHeadsign=");
        builder.append(tripHeadsign);
        builder.append(", scheduledArrivalTime=");
        builder.append(scheduledArrivalTime);
        builder.append(", scheduledDepartureTime=");
        builder.append(scheduledDepartureTime);
        builder.append(", distanceFromStop=");
        builder.append(distanceFromStop);
        builder.append("]");
        return builder.toString();
    }

}
