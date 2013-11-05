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
 * @author Jeremie Huchet
 */
public class TripSchedule {
    
    private String headsign;

    /** List of stop times. */
    private final List<TripStopTime> stopTimes;

    /** The route. */
    private Route route;

    public TripSchedule() {

        stopTimes = new ArrayList<TripStopTime>();
    }

    /**
     * Gets the trip headsign.
     * 
     * @return the headsign
     */
    public String getHeadsign() {
        return headsign;
    }

    /**
     * Sets the trip headsign.
     * 
     * @param headsign
     *            the headsign to set
     */
    public void setHeadsign(String headsign) {
        this.headsign = headsign;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TripSchedule [headsign=");
        builder.append(headsign);
        builder.append(", stopTimes=");
        builder.append(stopTimes);
        builder.append(", route=");
        builder.append(route);
        builder.append("]");
        return builder.toString();
    }

}
