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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.onebusaway.exceptions.OneBusAwayException;
import fr.dudie.onebusaway.model.Route;
import fr.dudie.onebusaway.model.Stop;
import fr.dudie.onebusaway.model.Time;

/**
 * @author Olivier Boudet
 */
public final class OneBusAwayUtils {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(OneBusAwayUtils.class);

    private final static int RESPONSE_CODE_SUCCESS = 200;

    /**
     * Constructor.
     */
    private OneBusAwayUtils() {

    }

    /**
     * Checks the response code of the OneBusAway API and return the JSON Object named "data".
     * 
     * @param reponse
     *            the reponse of the OneBusAway API
     * @return the JSON Object named "data"
     * @throws JSONException
     *             the response does not contains valid data
     * @throws OneBusAwayException
     *             the API returned an error code
     */
    public static JSONObject getServiceResponse(final String reponse) throws JSONException,
            OneBusAwayException {

        final JSONObject jsonResult = new JSONObject(reponse);

        final int code = jsonResult.getInt("code");

        if (code != RESPONSE_CODE_SUCCESS) {
            final String message = String.format("OneBusAway API error : code=%S, %s ", code,
                    jsonResult.getString("text"));
            throw new OneBusAwayException(message, null);
        }
        return jsonResult.getJSONObject("data");
    }

    /**
     * Gets stops from the "references" part of OneBusAway response.
     * 
     * @param jsonArray
     *            jsonArray containing the references part of the responses.
     * @param routes
     *            list of routes @link {@link #getReferencedRoutes(JSONArray)}
     * @return the list of stops
     */
    public static HashMap<String, Stop> getReferencedStops(final JSONArray jsonArray,
            final HashMap<String, Route> routes) {

        HashMap<String, Stop> stops = null;

        if (jsonArray != null) {
            stops = new HashMap<String, Stop>();
            for (int i = 0; !jsonArray.isNull(i); i++) {
                final JSONObject jsonStop = jsonArray.optJSONObject(i);
                stops.put(jsonStop.optString("id"), convertJsonObjectToStop(jsonStop, routes));
            }
        }
        return stops;
    }

    /**
     * Gets routes from the "references" part of OneBusAway response.
     * 
     * @param jsonArray
     *            jsonArray containing the references part of the responses.
     * @return the list of routes
     */
    public static HashMap<String, Route> getReferencedRoutes(final JSONArray jsonArray) {

        HashMap<String, Route> routes = null;

        if (jsonArray != null) {
            routes = new HashMap<String, Route>();
            for (int i = 0; !jsonArray.isNull(i); i++) {
                final JSONObject jsonRoute = jsonArray.optJSONObject(i);
                routes.put(jsonRoute.optString("id"), convertJsonObjectToRoute(jsonRoute));
            }
        }
        return routes;
    }

    /**
     * Converts a JSONObject to a Stop object.
     * 
     * @param jsonStop
     *            JSONObject describing a Stop
     * @param routes
     *            list of routes @link {@link #getReferencedRoutes(JSONArray)}
     * @return the Stop object
     * @throws JSONException
     */
    public static Stop convertJsonObjectToStop(final JSONObject jsonStop,
            final HashMap<String, Route> routes) {

        final Stop stop = new Stop();
        stop.setId(jsonStop.optString("id"));
        stop.setLon(jsonStop.optDouble("lon"));
        stop.setLat(jsonStop.optDouble("lat"));
        stop.setName(jsonStop.optString("name"));
        stop.setCode(jsonStop.optInt("code"));

        return stop;
    }

    /**
     * Converts a JSONObject to a route object.
     * 
     * @param jsonRoute
     *            JSONObject describing a Route
     * @return the Route object
     */
    public static Route convertJsonObjectToRoute(final JSONObject jsonRoute) {

        final Route route = new Route();
        route.setId(jsonRoute.optString("id"));
        route.setLongName(jsonRoute.optString("longName"));
        route.setShortName(jsonRoute.optString("shortName"));
        route.setTextColor(jsonRoute.optString("textColor"));
        route.setColor(jsonRoute.optString("color"));
        route.setAgencyId(jsonRoute.optString("agencyId"));
        route.setDescription(jsonRoute.optString("description"));
        route.setType(jsonRoute.optInt("type"));

        return route;
    }
}
