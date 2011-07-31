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

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a trip schedule.
 * 
 * @author Olivier Boudet
 */
public class TripSchedule {

    /** List of stop times. */
    private final List<TripStopTime> stopTimes;

    /** Id of the previous trip; */
    private String previousTripId;

    /** Id of the next trip. */
    private String nextTripId;

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
     * Gets the previousTripId.
     * 
     * @return the previousTripId
     */
    public String getPreviousTripId() {

        return previousTripId;
    }

    /**
     * Sets the previousTripId.
     * 
     * @param previousTripId
     *            the previousTripId to set
     */
    public void setPreviousTripId(final String previousTripId) {

        this.previousTripId = previousTripId;
    }

    /**
     * Gets the nextTripId.
     * 
     * @return the nextTripId
     */
    public String getNextTripId() {

        return nextTripId;
    }

    /**
     * Sets the nextTripId.
     * 
     * @param nextTripId
     *            the nextTripId to set
     */
    public void setNextTripId(final String nextTripId) {

        this.nextTripId = nextTripId;
    }

}
