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
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.onebusaway.exceptions.OneBusAwayException;
import fr.dudie.onebusaway.model.Route;
import fr.dudie.onebusaway.model.Stop;
import fr.dudie.onebusaway.model.TripSchedule;
import fr.dudie.onebusaway.model.TripStopTime;

/**
 * Handles responses for a call to the "trip-details" method of the OneBusAway API.
 * 
 * @author Olivier Boudet
 */
public final class TripDetailsHttpResponseHandler implements ResponseHandler<TripSchedule> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TripDetailsHttpResponseHandler.class);

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
     */
    @Override
    public TripSchedule handleResponse(final HttpResponse response) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("handleResponse.start");
        }

        TripSchedule schedule = null;
        String content = null;
        JSONObject data;

        try {
            content = EntityUtils.toString(response.getEntity(), "utf-8");
            data = OneBusAwayUtils.getServiceResponse(content);

            if (data != null) {
                schedule = new TripSchedule();

                final JSONObject entry = data.getJSONObject("entry");
                final Long serviceDate = entry.getLong("serviceDate");
                final JSONObject schedules = entry.getJSONObject("schedule");
                final JSONArray stopTimes = schedules.getJSONArray("stopTimes");

                final JSONObject references = data.getJSONObject("references");

                final HashMap<String, Route> routes = OneBusAwayUtils
                        .getReferencedRoutes(references.getJSONArray("routes"));

                final HashMap<String, Stop> stops = OneBusAwayUtils.getReferencedStops(
                        references.getJSONArray("stops"), routes);

                for (int i = 0; !stopTimes.isNull(i); i++) {
                    schedule.getStopTimes().add(
                            convertJsonObjectToStopTime(stopTimes.optJSONObject(i), serviceDate,
                                    stops));
                }

                schedule.setPreviousTripId(schedules.optString("previousTripId"));
                schedule.setNextTripId(schedules.optString("nextTripId"));

            }
        } catch (final JSONException e) {
            LOGGER.error("error while parsing the response from OneBusAway api", e);
            throw new IOException("error while parsing the response from OneBusAway api\n"
                    + content);
        } catch (final OneBusAwayException e) {
            LOGGER.error("OneBusAway api returned an error", e);
            throw new IOException(e.getMessage());
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("handleResponse.end");
        }
        return schedule;
    }

    /**
     * Converts a json object to a bean representing a stop time.
     * 
     * @param jsonObject
     *            the json object to convert to a stop time
     * @param serviceDate
     *            the service date of the trip for this stop time
     * @param stops
     *            map with stops referenced in the response
     * @return the stop time bean
     */
    private TripStopTime convertJsonObjectToStopTime(final JSONObject jsonObject,
            final Long serviceDate, final HashMap<String, Stop> stops) {

        final TripStopTime stopTime = new TripStopTime();
        stopTime.setStop(stops.get(jsonObject.optString("stopId")));
        stopTime.setArrivalTime(OneBusAwayUtils.dateFromTimestamps(serviceDate,
                jsonObject.optLong("arrivalTime")));
        stopTime.setDepartureTime(OneBusAwayUtils.dateFromTimestamps(serviceDate,
                jsonObject.optLong("departureTime")));
        stopTime.setDistanceAlongTrip(jsonObject.optDouble("distanceAlongTrip"));
        stopTime.setStopHeadsign(jsonObject.optString("stopHeadsign"));

        return stopTime;
    }
}
