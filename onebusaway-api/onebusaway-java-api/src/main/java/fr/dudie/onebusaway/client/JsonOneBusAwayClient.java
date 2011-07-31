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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.onebusaway.model.ArrivalAndDeparture;
import fr.dudie.onebusaway.model.BusStation;
import fr.dudie.onebusaway.model.Stop;
import fr.dudie.onebusaway.model.StopSchedule;
import fr.dudie.onebusaway.model.TripSchedule;

/**
 * Manage calls to the OneBusAway API.
 * 
 * @author Olivier Boudet
 */
public class JsonOneBusAwayClient implements IOneBusAwayClient {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonOneBusAwayClient.class);

    /** The HTTP Accept header name. */
    private static final String H_ACCEPT = "Accept";

    /** The OneBusAway API version this client use. */
    private static final String API_VERSION = "2";

    /** Parameter used by OneBusAway API to include references or not. */
    private static final String OBA_INCLUDE_REFERENCE = "includeReferences";

    /** Date format for OBA requests. */
    private final SimpleDateFormat obaSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /** The HTTP client. */
    private final HttpClient httpClient;

    /** The OneBusAway API url to use. */
    private final String oneBusAwayApiUrl;

    /** The OneBusAway API key to use. */
    private final String oneBusAwayApiKey;

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
        this.oneBusAwayApiUrl = String.format("%s%s", url, OneBusAwayConstants.OBA_API_PATH);
        this.oneBusAwayApiKey = key;
    }

    /**
     * Creates a generic request to the OneBusAway API. This method will set the request headers.
     * 
     * @param path
     *            the url where to make the call
     * @param parameters
     *            the request parameters
     * @return an {@link HttpGet} to send to execute the request
     */
    private HttpGet createOBARequest(final String path, final List<BasicNameValuePair> parameters) {

        parameters.add(new BasicNameValuePair(OneBusAwayConstants.OBA_API_VERSION, API_VERSION));
        parameters.add(new BasicNameValuePair(OneBusAwayConstants.OBA_API_KEY, oneBusAwayApiKey));

        final StringBuilder params = new StringBuilder();
        params.append("?");
        for (final BasicNameValuePair param : parameters) {
            if (params.length() > 1) {
                params.append("&");
            }
            params.append(param.getName());
            params.append("=").append(param.getValue());
        }

        final HttpGet req = new HttpGet(path.concat(params.toString()));
        req.addHeader(H_ACCEPT, "text/json");
        req.addHeader(H_ACCEPT, "application/json");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("createOBARequest - {}", params.toString());
        }

        return req;
    }

    /**
     * {@inheritDoc}
     * 
     * @see IOneBusAwayClient#getStopsForRoute(String, String)
     */
    @Override
    public final List<BusStation> getStopsForRoute(final String routeId, final String direction) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("stopsForRoute.start - routeId={}", routeId);
        }
        final String urlCall = String.format(oneBusAwayApiUrl, "stops-for-route", routeId);

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(2);
        params.add(new BasicNameValuePair(OBA_INCLUDE_REFERENCE, "false"));

        final HttpGet request = createOBARequest(urlCall, params);

        // final List<BusStation> stops = httpService.execute(request,
        // new StopsForRouteHttpResponseHandler(direction));
        final List<BusStation> stops = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("stopsForRoute.end");
        }
        return stops;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     * @see IOneBusAwayClient#getTripDetails(String)
     */
    @Override
    public final TripSchedule getTripDetails(final String tripId) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getTripDetails.start - tripId={}", tripId);
        }
        final String urlCall = String.format(oneBusAwayApiUrl, "trip-details", tripId);

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(2);
        params.add(new BasicNameValuePair(OBA_INCLUDE_REFERENCE, "true"));

        final TripSchedule schedule = httpClient.execute(createOBARequest(urlCall, params),
                new TripDetailsHttpResponseHandler());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getTripDetails.end");
        }
        return schedule;
    }

    /**
     * {@inheritDoc}
     * 
     * @see IOneBusAwayClient#getArrivalsAndDeparturesForStop(String)
     */
    @Override
    public final List<ArrivalAndDeparture> getArrivalsAndDeparturesForStop(final String stopId)
            throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getArrivalsAndDeparturesForStop.start - stopId={}", stopId);
        }
        final String urlCall = String.format(oneBusAwayApiUrl, "arrivals-and-departures-for-stop",
                stopId);

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(2);
        params.add(new BasicNameValuePair(OBA_INCLUDE_REFERENCE, "false"));

        final List<ArrivalAndDeparture> arrivalsAndDepartures = httpClient.execute(
                createOBARequest(urlCall, params),
                new ArrivalsAndDeparturesForStopHttpResponseHandler());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getArrivalsAndDeparturesForStop.end");
        }
        return arrivalsAndDepartures;
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

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getScheduleForStop.start - stopId={}", stopId);
        }
        final String urlCall = String.format(oneBusAwayApiUrl, "schedule-for-stop", stopId);

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(2);
        params.add(new BasicNameValuePair(OBA_INCLUDE_REFERENCE, "true"));
        params.add(new BasicNameValuePair("date", obaSimpleDateFormat.format(date)));

        final StopSchedule schedule = httpClient.execute(createOBARequest(urlCall, params),
                new ScheduleForStopHttpResponseHandler());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getScheduleForStop.end");
        }
        return schedule;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     * @see IOneBusAwayClient#getStop(String)
     */
    @Override
    public final Stop getStop(final String stopId) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getStop.start - stopId={}", stopId);
        }
        final String urlCall = String.format(oneBusAwayApiUrl, "stop", stopId);

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(1);
        params.add(new BasicNameValuePair(OBA_INCLUDE_REFERENCE, "true"));

        final Stop stop = httpClient.execute(createOBARequest(urlCall, params),
                new StopHttpResponseHandler());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getStop.end");
        }
        return stop;
    }
}
