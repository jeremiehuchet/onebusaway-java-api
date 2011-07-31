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
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.onebusaway.exceptions.OneBusAwayException;
import fr.dudie.onebusaway.model.ArrivalAndDeparture;

/**
 * Handles responses for a call to the "arrivals-and-departures-for-stop" method of the OneBusAway
 * API.
 * 
 * @author Olivier Boudet
 */
public final class ArrivalsAndDeparturesForStopHttpResponseHandler implements
        ResponseHandler<List<ArrivalAndDeparture>> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ArrivalsAndDeparturesForStopHttpResponseHandler.class);

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
     */
    @Override
    public List<ArrivalAndDeparture> handleResponse(final HttpResponse response) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("handleResponse.start");
        }

        List<ArrivalAndDeparture> arrivalsAndDepartures = null;
        String content = null;
        JSONObject data;

        try {
            content = EntityUtils.toString(response.getEntity(), "utf-8");
            data = OneBusAwayUtils.getServiceResponse(content);

            if (data != null) {
                arrivalsAndDepartures = new ArrayList<ArrivalAndDeparture>();

                final JSONObject jsonEntry = data.getJSONObject("entry");
                final JSONArray jsonArrivalsAndDepartures = jsonEntry
                        .getJSONArray("arrivalsAndDepartures");

                for (int i = 0; !jsonArrivalsAndDepartures.isNull(i); i++) {
                    arrivalsAndDepartures
                            .add(convertJsonObjectToArrivalAndDeparture(jsonArrivalsAndDepartures
                                    .optJSONObject(i)));
                }

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
        return arrivalsAndDepartures;
    }

    /**
     * Converts a JSON object to a ArrivalAndDeparture object.
     * 
     * @param jsonArrivalAndDeparture
     *            JSON Object describing an ArrivalAndDeparture.
     * @return the arrival and departure object
     */
    private ArrivalAndDeparture convertJsonObjectToArrivalAndDeparture(
            final JSONObject jsonArrivalAndDeparture) {

        final ArrivalAndDeparture arrivalAndDeparture = new ArrivalAndDeparture();
        arrivalAndDeparture.setDistanceFromStop(jsonArrivalAndDeparture
                .optDouble("distanceFromStop"));
        arrivalAndDeparture.setScheduledArrivalTime(OneBusAwayUtils
                .dateFromTimestamp(jsonArrivalAndDeparture.optLong("scheduledArrivalTime")));
        arrivalAndDeparture.setScheduledDepartureTime(OneBusAwayUtils
                .dateFromTimestamp(jsonArrivalAndDeparture.optLong("scheduledDepartureTime")));
        arrivalAndDeparture.setTripHeadsign(jsonArrivalAndDeparture.optString("tripHeadsign"));
        arrivalAndDeparture.setTripId(jsonArrivalAndDeparture.optString("tripId"));
        arrivalAndDeparture.setRouteId(jsonArrivalAndDeparture.optString("routeId"));
        arrivalAndDeparture.setRouteLongName(jsonArrivalAndDeparture.optString("routeLongName"));
        arrivalAndDeparture.setRouteShortName(jsonArrivalAndDeparture.optString("routeShortName"));

        arrivalAndDeparture.setServiceDate(OneBusAwayUtils
                .dateFromTimestamp(jsonArrivalAndDeparture.optLong("serviceDate")));
        arrivalAndDeparture.setStopSequence(jsonArrivalAndDeparture.optInt("stopSequence"));
        arrivalAndDeparture.setStopId(jsonArrivalAndDeparture.optString("stopId"));

        return arrivalAndDeparture;
    }
}
