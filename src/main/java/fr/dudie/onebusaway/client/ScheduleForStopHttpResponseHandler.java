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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.onebusaway.api.model.transit.EntryWithReferencesBean;
import org.onebusaway.api.model.transit.RouteV2Bean;
import org.onebusaway.api.model.transit.ScheduleStopTimeInstanceV2Bean;
import org.onebusaway.api.model.transit.StopRouteDirectionScheduleV2Bean;
import org.onebusaway.api.model.transit.StopRouteScheduleV2Bean;
import org.onebusaway.api.model.transit.StopScheduleV2Bean;
import org.onebusaway.api.model.transit.StopV2Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.dudie.onebusaway.exceptions.OneBusAwayException;
import fr.dudie.onebusaway.model.Route;
import fr.dudie.onebusaway.model.ScheduleStopTime;
import fr.dudie.onebusaway.model.Stop;
import fr.dudie.onebusaway.model.StopSchedule;
import fr.dudie.onebusaway.model.v2.OneBusAwayResponse;

/**
 * Handles responses for a call to the "schedule-for-stop" method of the OneBusAway API.
 * 
 * @author Olivier Boudet
 */
public final class ScheduleForStopHttpResponseHandler implements ResponseHandler<StopSchedule> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ScheduleForStopHttpResponseHandler.class);

    /** The gson instance. */
    private final Gson gsonInstance;

    /**
     * Constructor.
     * 
     * @param gsonInstance
     *            the gson instance
     */
    public ScheduleForStopHttpResponseHandler(final Gson gsonInstance) {

        this.gsonInstance = gsonInstance;
    }

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

        final Map<String, Stop> stopsById = new HashMap<String, Stop>();
        final Map<String, Route> routesById = new HashMap<String, Route>();

        final InputStream content = response.getEntity().getContent();
        final Type type = new TypeToken<OneBusAwayResponse<StopScheduleV2Bean>>() {
        }.getType();

        final StopSchedule stopSchedule = new StopSchedule();

        try {
            final OneBusAwayResponse<StopScheduleV2Bean> obaResponse = gsonInstance.fromJson(
                    new InputStreamReader(content, "utf-8"), type);

            assertResponseOk(obaResponse);

            final EntryWithReferencesBean<StopScheduleV2Bean> data = obaResponse.getData();

            // index references
            for (final RouteV2Bean obaRoute : data.getReferences().getRoutes()) {
                final Route route = new Route();
                route.setId(obaRoute.getId());
                route.setAgencyId(obaRoute.getAgencyId());
                route.setColor(obaRoute.getColor());
                route.setDescription(obaRoute.getDescription());
                route.setLongName(obaRoute.getLongName());
                route.setShortName(obaRoute.getShortName());
                route.setTextColor(obaRoute.getColor());
                route.setType(obaRoute.getType());
                routesById.put(route.getId(), route);
            }
            for (final StopV2Bean obaStop : data.getReferences().getStops()) {
                final Stop stop = new Stop();
                stop.setId(obaStop.getId());
                stop.setCode(Integer.valueOf(obaStop.getCode()));
                stop.setDirection(obaStop.getDirection());
                stop.setLat(obaStop.getLat());
                stop.setLon(obaStop.getLon());
                stop.setName(obaStop.getName());
                for (final String routeId : obaStop.getRouteIds()) {
                    stop.getRoutes().add(routesById.get(routeId));
                }
                stopsById.put(stop.getId(), stop);
            }

            final StopScheduleV2Bean obaStopSchedule = data.getEntry();

            stopSchedule.setStop(stopsById.get(obaStopSchedule.getStopId()));
            stopSchedule.setDate(new Date(obaStopSchedule.getDate()));

            for (final StopRouteScheduleV2Bean srs : obaStopSchedule.getStopRouteSchedules()) {
                for (final StopRouteDirectionScheduleV2Bean srds : srs
                        .getStopRouteDirectionSchedules()) {
                    for (final ScheduleStopTimeInstanceV2Bean ssti : srds.getScheduleStopTimes()) {
                        final ScheduleStopTime sst = new ScheduleStopTime();
                        sst.setArrivalTime(new Date(ssti.getArrivalTime()));
                        sst.setDepartureTime(new Date(ssti.getDepartureTime()));
                        sst.setHeadsign(srds.getTripHeadsign());
                        sst.setServiceId(ssti.getServiceId());
                        sst.setTripId(ssti.getTripId());
                        sst.setRoute(routesById.get(srs.getRouteId()));

                        stopSchedule.getStopTimes().add(sst);
                    }
                }
            }

            Collections.sort(stopSchedule.getStopTimes());

        } catch (final OneBusAwayException e) {
            LOGGER.error("OneBusAway api returned an error", e);
            throw new IOException(e.getMessage());
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("handleResponse.end");
        }
        return stopSchedule;
    }

    private void assertResponseOk(final OneBusAwayResponse<StopScheduleV2Bean> obaResponse)
            throws OneBusAwayException {

        if (2 != obaResponse.getVersion() || 200 != obaResponse.getCode()
                || !"OK".equalsIgnoreCase(obaResponse.getText())) {
            obaResponse.setData(null);
            throw new OneBusAwayException("invalid response: " + gsonInstance.toJson(obaResponse),
                    null);
        }
    }
}
