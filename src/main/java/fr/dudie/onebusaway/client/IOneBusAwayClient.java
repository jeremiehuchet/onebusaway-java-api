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
package fr.dudie.onebusaway.client;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.onebusaway.api.model.transit.StopScheduleV2Bean;
import org.onebusaway.api.model.transit.StopV2Bean;
import org.onebusaway.api.model.transit.StopWithArrivalsAndDeparturesV2Bean;
import org.onebusaway.api.model.transit.TripDetailsV2Bean;
import org.onebusaway.transit_data.model.StopBean;

import fr.dudie.onebusaway.model.v2.OneBusAwayResponse;

/**
 * Interface to query the Keolis API.
 * 
 * @author Olivier Boudet
 * @author Jérémie Huchet
 */
public interface IOneBusAwayClient {

    /**
     * Gets the bus stations for the given route.
     * 
     * @param routeId
     *            the identifier of the route you wants the stops
     * @param direction
     *            direction of the route to get stops
     * @return the list of stations for the given route
     * @throws IOException
     *             an error occurred
     */
    List<StopV2Bean> getStopsForRoute(final String routeId, final String direction)
            throws IOException;

    /**
     * Gets the trip details.
     * 
     * @param tripId
     *            the identifier of the trip
     * @return the schedule for the given trip
     * @throws IOException
     *             an error occurred
     */
    TripDetailsV2Bean getTripDetails(final String tripId) throws IOException;

    /**
     * Gets arrivals and departures for stop.
     * 
     * @param stopId
     *            id of the stop to get arrivals and departures
     * @return the list of arrivals and departures
     * @throws IOException
     *             an error occurred
     */
    StopWithArrivalsAndDeparturesV2Bean getArrivalsAndDeparturesForStop(final String stopId)
            throws IOException;

    /**
     * Gets schedule for a stop.
     * 
     * @param stopId
     *            id of the stop to get schedule
     * @param date
     *            the date of the day you want the schedule
     * @return the full schedule
     * @throws IOException
     *             an error occurred
     */
    OneBusAwayResponse<StopScheduleV2Bean> getScheduleForStop(final String stopId, final Date date)
            throws IOException;

    /**
     * Gets schedule for a stop.
     * 
     * @param stopId
     *            id of the stop to retrieve
     * @return the stop with routes
     * @throws IOException
     *             an error occurred
     */
    StopBean getStop(final String stopId) throws IOException;
}
