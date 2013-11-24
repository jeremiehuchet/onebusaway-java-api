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
