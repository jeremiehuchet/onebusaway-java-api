package fr.dudie.onebusaway.client;

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
