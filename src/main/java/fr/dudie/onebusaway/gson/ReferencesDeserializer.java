package fr.dudie.onebusaway.gson;

import java.lang.reflect.Type;
import java.util.List;

import org.onebusaway.api.model.transit.AgencyV2Bean;
import org.onebusaway.api.model.transit.RouteV2Bean;
import org.onebusaway.api.model.transit.StopV2Bean;
import org.onebusaway.api.model.transit.TripV2Bean;
import org.onebusaway.api.model.transit.service_alerts.SituationV2Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import fr.dudie.onebusaway.model.v2.References;

/**
 * Deserializes OneBusAway API response references as a {@link References} object.
 * 
 * @author Jérémie Huchet
 */
public final class ReferencesDeserializer implements JsonDeserializer<References> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReferencesDeserializer.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public References deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) {

        final JsonObject jsonReferences = json.getAsJsonObject();
        final List<AgencyV2Bean> agencies;
        final List<StopV2Bean> stops;
        final List<RouteV2Bean> routes;
        final List<TripV2Bean> trips;
        final List<SituationV2Bean> situations;

        // deserializes agencies
        Type t = new TypeToken<List<AgencyV2Bean>>() {
        }.getType();
        agencies = context.deserialize(jsonReferences.get("agencies"), t);

        // deserializes stops
        t = new TypeToken<List<StopV2Bean>>() {
        }.getType();
        stops = context.deserialize(jsonReferences.get("stops"), t);

        // deserializes routes
        t = new TypeToken<List<RouteV2Bean>>() {
        }.getType();
        routes = context.deserialize(jsonReferences.get("routes"), t);

        // deserializes trips
        t = new TypeToken<List<TripV2Bean>>() {
        }.getType();
        trips = context.deserialize(jsonReferences.get("trips"), t);

        // deserializes situations
        t = new TypeToken<List<SituationV2Bean>>() {
        }.getType();
        situations = context.deserialize(jsonReferences.get("situations"), t);

        return new References(agencies, stops, routes, trips, situations);
    }
}
