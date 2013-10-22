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
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import fr.dudie.onebusaway.gson.OneBusAwayGsonFactory;
import fr.dudie.onebusaway.model.StopSchedule;
import fr.dudie.onebusaway.model.TripSchedule;

/**
 * Manage calls to the OneBusAway API.
 * 
 * @author Olivier Boudet
 * @author Jeremie Huchet
 */
public class JsonOneBusAwayClient implements IOneBusAwayClient {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonOneBusAwayClient.class);

    /** The HTTP Accept header name. */
    private static final String H_ACCEPT = "Accept";

    /** The HTTP client. */
    private final HttpClient httpClient;

    /** The OneBusAway API url to use. */
    private final String baseUrl;
    
    /** The gson (de)serializer. */
    private final Gson gson;

    /**
     * Creates a OneBusAway API client.
     * 
     * @param httpClient
     *            an http client
     * @param url
     *            the url of the OneBusAway API
     * @param key
     *            the API key
     */
    public JsonOneBusAwayClient(final HttpClient httpClient, final String url, final String key) {

        this.httpClient = httpClient;
        this.baseUrl = url.replaceAll("/$", "");
        this.gson = OneBusAwayGsonFactory.newInstance();
    }

    /**
     * Creates a generic request to the OneBusAway API. This method will set the request headers.
     * 
     * @param path
     *            the url where to make the call
     * @return an {@link HttpGet} to send to execute the request
     */
    private HttpGet createOBARequest(final String path) {

        final HttpGet req = new HttpGet(path);
        req.addHeader(H_ACCEPT, "text/json");
        req.addHeader(H_ACCEPT, "application/json");

        LOGGER.debug("createOBARequest - {}", path);

        return req;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     * @see IOneBusAwayClient#getTripDetails(String)
     */
    @Override
    public final TripSchedule getTripDetails(final String tripId) throws IOException {

        LOGGER.debug("getTripDetails.start - tripId={}", tripId);

        final String urlCall = String.format("%s/trip-details/%s.json", baseUrl, tripId);
        final TripSchedule schedule = httpClient.execute(createOBARequest(urlCall),
                new ApiHttpResponseHandler<TripSchedule>(TripSchedule.class, gson));

        LOGGER.debug("getTripDetails.end");
        return schedule;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     * @see IOneBusAwayClient#getScheduleForStop(String, Date)
     */
    @Override
    public final StopSchedule getScheduleForStop(final String stopId, final Date date)
            throws IOException {

        LOGGER.debug("getScheduleForStop.start - stopId={}", stopId);

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final String urlCall = String.format("%1$s/schedule-for-stop/%2$tY/%2$tm/%2$td/%3$s.json", baseUrl, date , stopId);
        final StopSchedule schedule = httpClient.execute(createOBARequest(urlCall),
                new StopScheduleHttpResponseHandler(gson));
        schedule.setDate(date);

        LOGGER.debug("getScheduleForStop.end");
        return schedule;
    }
}
