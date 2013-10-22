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
import java.util.Map;

import org.apache.http.HttpResponse;

import com.google.gson.Gson;

import fr.dudie.onebusaway.model.Route;
import fr.dudie.onebusaway.model.ScheduleStopTime;
import fr.dudie.onebusaway.model.StopSchedule;

/**
 * @author Jeremie Huchet
 */
public final class StopScheduleHttpResponseHandler extends ApiHttpResponseHandler<StopSchedule> {

    public StopScheduleHttpResponseHandler(final Gson gson) {
        super(StopSchedule.class, gson);
    }

    /* (non-Javadoc)
     * @see fr.dudie.onebusaway.client.ApiHttpResponseHandler#handleResponse(org.apache.http.HttpResponse)
     */
    @Override
    public StopSchedule handleResponse(HttpResponse response) throws IOException {
        final StopSchedule stopSchedule = super.handleResponse(response);
        final Map<String, Route> routes = new HashMap<String, Route>();
        for (final Route route : stopSchedule.getRoutes()) {
            routes.put(route.getId(), route);
        }
        for (final ScheduleStopTime stopTime : stopSchedule.getStopTimes()) {
            stopTime.setRoute(routes.get(stopTime.getRouteId()));
        }
        return stopSchedule;
    }
}
