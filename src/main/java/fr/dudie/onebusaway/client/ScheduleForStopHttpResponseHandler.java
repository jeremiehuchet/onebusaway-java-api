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
import java.util.Collections;
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
import fr.dudie.onebusaway.model.ScheduleStopTime;
import fr.dudie.onebusaway.model.Stop;
import fr.dudie.onebusaway.model.StopSchedule;
import fr.dudie.onebusaway.model.Time;

/**
 * Handles responses for a call to the "schedule-for-stop" method of the OneBusAway API.
 * 
 * @author Olivier Boudet
 */
public final class ScheduleForStopHttpResponseHandler implements ResponseHandler<StopSchedule> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ScheduleForStopHttpResponseHandler.class);

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
     */
    @Override
    public StopSchedule handleResponse(final HttpResponse response) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("handleResponse.start");
        }

        StopSchedule stopSchedule = null;
        String content = null;
        JSONObject data;

        try {
            content = EntityUtils.toString(response.getEntity(), "utf-8");
            data = OneBusAwayUtils.getServiceResponse(content);

            if (data != null) {
                stopSchedule = new StopSchedule();

                final JSONObject references = data.getJSONObject("references");
                final JSONObject jsonEntry = data.getJSONObject("entry");

                final HashMap<String, Route> routes = OneBusAwayUtils
                        .getReferencedRoutes(references.getJSONArray("routes"));

                final HashMap<String, Stop> stops = OneBusAwayUtils.getReferencedStops(
                        references.getJSONArray("stops"), routes);
                stopSchedule.setStop(stops.get(jsonEntry.optString("stopId")));

                final JSONArray jsonStopRouteSchedules = jsonEntry
                        .getJSONArray("stopRouteSchedules");

                for (int i = 0; !jsonStopRouteSchedules.isNull(i); i++) {
                    final JSONObject jsonStopRouteSchedule = jsonStopRouteSchedules
                            .optJSONObject(i);
                    final Route route = routes.get(jsonStopRouteSchedule.optString("routeId"));

                    final JSONArray jsonStopRouteDirectionSchedules = jsonStopRouteSchedule
                            .getJSONArray("stopRouteDirectionSchedules");

                    for (int j = 0; !jsonStopRouteDirectionSchedules.isNull(j); j++) {
                        final JSONObject jsonStopRouteDirectionSchedule = jsonStopRouteDirectionSchedules
                                .optJSONObject(j);
                        final String headSign = jsonStopRouteDirectionSchedule
                                .optString("tripHeadsign");

                        final JSONArray jsonScheduleStopTimes = jsonStopRouteDirectionSchedule
                                .getJSONArray("scheduleStopTimes");

                        for (int k = 0; !jsonScheduleStopTimes.isNull(k); k++) {
                            stopSchedule
                                    .getStopTimes()
                                    .add(convertJsonObjectToScheduleStopTime(
                                            jsonScheduleStopTimes.optJSONObject(k), route, headSign));
                        }

                    }
                }

                Collections.sort(stopSchedule.getStopTimes());

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
        return stopSchedule;
    }

    /**
     * Converts a JSON object to a ScheduleStopTime object.
     * 
     * @param jsonScheduleStopTime
     * @param route
     * @param headSign
     * @return
     */
    private ScheduleStopTime convertJsonObjectToScheduleStopTime(
            final JSONObject jsonScheduleStopTime, final Route route, final String headSign) {

        final ScheduleStopTime scheduleStopTime = new ScheduleStopTime();

        scheduleStopTime.setArrivalTime(new Time(jsonScheduleStopTime
                .optLong("arrivalTime")));
        scheduleStopTime.setDepartureTime(new Time(jsonScheduleStopTime
                .optLong("departureTime")));
        scheduleStopTime.setTripId(jsonScheduleStopTime.optString("tripId"));
        scheduleStopTime.setHeadsign(headSign);
        scheduleStopTime.setRoute(route);
        scheduleStopTime.setServiceId(jsonScheduleStopTime.optString("serviceId"));

        return scheduleStopTime;
    }

}
