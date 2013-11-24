package fr.dudie.onebusaway.client;

import java.io.IOException;
import java.util.Date;

import fr.dudie.onebusaway.model.Agency;
import fr.dudie.onebusaway.model.FeedInfo;
import fr.dudie.onebusaway.model.StopSchedule;
import fr.dudie.onebusaway.model.TripSchedule;

/**
 * Interface to query the Keolis API.
 * 
 * @author Olivier Boudet
 * @author Jérémie Huchet
 */
public interface IOneBusAwayClient {
    
    /**
     * Get details about the feed.
     * @return the feed details
     * @throws IOException
     *             an error occurred
     */
    FeedInfo getFeedInfo() throws IOException;
    
    /**
     * Get details about the agency.
     * @param agencyId the agency identifier
     * @return the agency details
     * @throws IOException
     *             an error occurred
     */
    Agency getAgency(String agencyId) throws IOException;

    /**
     * Gets the trip details.
     * 
     * @param tripId
     *            the identifier of the trip
     * @return the schedule for the given trip
     * @throws IOException
     *             an error occurred
     */
    TripSchedule getTripDetails(final String tripId) throws IOException;

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
    StopSchedule getScheduleForStop(final String stopId, final Date date) throws IOException;

}
