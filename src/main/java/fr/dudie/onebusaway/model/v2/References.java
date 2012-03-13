package fr.dudie.onebusaway.model.v2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.onebusaway.api.model.transit.AgencyV2Bean;
import org.onebusaway.api.model.transit.RouteV2Bean;
import org.onebusaway.api.model.transit.StopV2Bean;
import org.onebusaway.api.model.transit.TripV2Bean;
import org.onebusaway.api.model.transit.service_alerts.SituationV2Bean;

public class References {

    private Map<String, AgencyV2Bean> agencies;

    private Map<String, RouteV2Bean> routes;

    private Map<String, StopV2Bean> stops;

    private Map<String, TripV2Bean> trips;

    private Map<String, SituationV2Bean> situations;

    public References(final List<AgencyV2Bean> agencies, final List<StopV2Bean> stops,
            final List<RouteV2Bean> routes, final List<TripV2Bean> trips,
            final List<SituationV2Bean> situations) {

        if (null != agencies && 0 != agencies.size()) {
            this.agencies = new HashMap<String, AgencyV2Bean>();
            for (final AgencyV2Bean agency : agencies) {
                this.agencies.put(agency.getId(), agency);
            }
        }

        if (null != stops && 0 != stops.size()) {
            this.stops = new HashMap<String, StopV2Bean>();
            for (final StopV2Bean stop : stops) {
                this.stops.put(stop.getId(), stop);
            }
        }

        if (null != routes && 0 != routes.size()) {
            this.routes = new HashMap<String, RouteV2Bean>();
            for (final RouteV2Bean route : routes) {
                this.routes.put(route.getId(), route);
            }
        }

        if (null != trips && 0 != trips.size()) {
            this.trips = new HashMap<String, TripV2Bean>();
            for (final TripV2Bean trip : trips) {
                this.trips.put(trip.getId(), trip);
            }
        }

        if (null != situations && 0 != situations.size()) {
            this.situations = new HashMap<String, SituationV2Bean>();
            for (final SituationV2Bean situation : situations) {
                this.situations.put(situation.getId(), situation);
            }
        }
    }

    public AgencyV2Bean getAgency(final String agencyId) {

        if (null != agencies) {
            return agencies.get(agencyId);
        } else {
            return null;
        }
    }

    public Map<String, AgencyV2Bean> getAgencies() {

        return agencies;
    }

    public RouteV2Bean getRoute(final String routeId) {

        if (null != routes) {
            return routes.get(routeId);
        } else {
            return null;
        }
    }

    public Map<String, RouteV2Bean> getRoutes() {

        return routes;
    }

    public StopV2Bean getStop(final String stopId) {

        if (null != stops) {
            return stops.get(stopId);
        } else {
            return null;
        }
    }

    public Map<String, StopV2Bean> getStops() {

        return stops;
    }

    public TripV2Bean getTrip(final String tripId) {

        if (null != trips) {
            return trips.get(tripId);
        } else {
            return null;
        }
    }

    public Map<String, TripV2Bean> getTrips() {

        return trips;
    }

    public SituationV2Bean getSituation(final String situationId) {

        if (null != situations) {
            return situations.get(situationId);
        } else {
            return null;
        }
    }

    public Map<String, SituationV2Bean> getSituations() {

        return situations;
    }
}
