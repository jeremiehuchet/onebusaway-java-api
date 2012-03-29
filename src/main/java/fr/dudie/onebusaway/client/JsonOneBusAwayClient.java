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
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.onebusaway.api.model.transit.StopScheduleV2Bean;
import org.onebusaway.api.model.transit.StopV2Bean;
import org.onebusaway.api.model.transit.StopWithArrivalsAndDeparturesV2Bean;
import org.onebusaway.api.model.transit.TripDetailsV2Bean;
import org.onebusaway.transit_data.model.StopBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import fr.dudie.onebusaway.gson.ReferencesDeserializer;
import fr.dudie.onebusaway.model.v2.OneBusAwayResponse;
import fr.dudie.onebusaway.model.v2.OneBusAwaySimpleResponse;
import fr.dudie.onebusaway.model.v2.References;

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
    private final String baseUrl;

    /** The Gson instance. */
    private final Gson gsonInstance;

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
        this.baseUrl = String.format("%s%s?key=%s&includeReferences=%%b", url,
                OneBusAwayConstants.OBA_API_PATH, key);
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(References.class, new ReferencesDeserializer());
        this.gsonInstance = gsonBuilder.create();
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

        final StringBuilder params = new StringBuilder();
        for (final BasicNameValuePair param : parameters) {
            params.append("&");
            params.append(param.getName());
            params.append("=").append(param.getValue());
        }

        LOGGER.info(path.concat(params.toString()));
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
    public final List<StopV2Bean> getStopsForRoute(final String routeId, final String direction)
            throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("stopsForRoute.start - routeId={}", routeId);
        }
        final String urlCall = String.format(baseUrl, "stops-for-route", routeId, false);

        final Type type = new TypeToken<OneBusAwaySimpleResponse<List<StopV2Bean>>>() {
        }.getType();
        final GsonHttpResponseHandler<OneBusAwaySimpleResponse<List<StopV2Bean>>> handler;
        handler = new GsonHttpResponseHandler<OneBusAwaySimpleResponse<List<StopV2Bean>>>(
                gsonInstance, type);

        final OneBusAwaySimpleResponse<List<StopV2Bean>> obaResponse = httpClient.execute(
                new HttpGet(urlCall), handler);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("stopsForRoute.end");
        }
        return obaResponse.getData();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     * @see IOneBusAwayClient#getTripDetails(String)
     */
    @Override
    public final TripDetailsV2Bean getTripDetails(final String tripId) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getTripDetails.start - tripId={}", tripId);
        }
        final String urlCall = String.format(baseUrl, "trip-details", tripId, false);

        final Type type = new TypeToken<OneBusAwayResponse<TripDetailsV2Bean>>() {
        }.getType();
        final GsonHttpResponseHandler<OneBusAwayResponse<TripDetailsV2Bean>> handler;
        handler = new GsonHttpResponseHandler<OneBusAwayResponse<TripDetailsV2Bean>>(gsonInstance,
                type);

        final OneBusAwayResponse<TripDetailsV2Bean> obaResponse = httpClient.execute(new HttpGet(
                urlCall), handler);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getTripDetails.end");
        }
        return obaResponse.getData().getEntry();
    }

    /**
     * {@inheritDoc}
     * 
     * @see IOneBusAwayClient#getArrivalsAndDeparturesForStop(String)
     */
    @Override
    public final StopWithArrivalsAndDeparturesV2Bean getArrivalsAndDeparturesForStop(
            final String stopId) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getArrivalsAndDeparturesForStop.start - stopId={}", stopId);
        }
        final String urlCall = String.format(baseUrl, "arrivals-and-departures-for-stop", stopId,
                false);

        final Type type = new TypeToken<OneBusAwaySimpleResponse<StopWithArrivalsAndDeparturesV2Bean>>() {
        }.getType();
        final GsonHttpResponseHandler<OneBusAwaySimpleResponse<StopWithArrivalsAndDeparturesV2Bean>> handler;
        handler = new GsonHttpResponseHandler<OneBusAwaySimpleResponse<StopWithArrivalsAndDeparturesV2Bean>>(
                gsonInstance, type);

        final OneBusAwaySimpleResponse<StopWithArrivalsAndDeparturesV2Bean> obaResponse = httpClient
                .execute(new HttpGet(urlCall), handler);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getArrivalsAndDeparturesForStop.end");
        }
        return obaResponse.getData();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     * @see IOneBusAwayClient#getScheduleForStop(String, Date)
     */
    @Override
    public final OneBusAwayResponse<StopScheduleV2Bean> getScheduleForStop(final String stopId,
            final Date date) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getScheduleForStop.start - stopId={}", stopId);
        }
        final StringBuilder urlCall = new StringBuilder();
        urlCall.append(String.format(baseUrl, "schedule-for-stop", stopId, true));
        urlCall.append("&date=").append(obaSimpleDateFormat.format(date));

        final Type type = new TypeToken<OneBusAwayResponse<StopScheduleV2Bean>>() {
        }.getType();
        final GsonHttpResponseHandler<OneBusAwayResponse<StopScheduleV2Bean>> handler;
        handler = new GsonHttpResponseHandler<OneBusAwayResponse<StopScheduleV2Bean>>(gsonInstance,
                type);

        final OneBusAwayResponse<StopScheduleV2Bean> obaResponse = httpClient.execute(new HttpGet(
                urlCall.toString()), handler);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getScheduleForStop.end");
        }
        return obaResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     * @see IOneBusAwayClient#getStop(String)
     */
    @Override
    public final StopBean getStop(final String stopId) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getStop.start - stopId={}", stopId);
        }
        final String urlCall = String.format(baseUrl, "stop", stopId, false);

        final Type type = new TypeToken<OneBusAwaySimpleResponse<StopBean>>() {
        }.getType();
        final GsonHttpResponseHandler<OneBusAwaySimpleResponse<StopBean>> handler;
        handler = new GsonHttpResponseHandler<OneBusAwaySimpleResponse<StopBean>>(gsonInstance,
                type);

        final OneBusAwaySimpleResponse<StopBean> obaResponse = httpClient.execute(new HttpGet(
                urlCall), handler);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getStop.end");
        }
        return obaResponse.getData();
    }
}
