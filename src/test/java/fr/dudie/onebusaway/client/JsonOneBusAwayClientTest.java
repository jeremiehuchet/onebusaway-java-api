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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.onebusaway.model.ArrivalAndDeparture;
import fr.dudie.onebusaway.model.ScheduleStopTime;
import fr.dudie.onebusaway.model.Stop;
import fr.dudie.onebusaway.model.StopSchedule;
import fr.dudie.onebusaway.model.TripSchedule;

/**
 * Test class for {@link OneBusAwayService}.
 * 
 * @author Jérémie Huchet
 * @author Olivier Boudet
 */
public class JsonOneBusAwayClientTest extends TestCase {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonOneBusAwayClientTest.class);

    /** The tested OneBusAway client. */
    private static JsonOneBusAwayClient obaClient;

    /** Path to the properties file. */
    private static final String PROPS_PATH = "/onebusaway-client-test.properties";

    /** Loaded properties. */
    private static final Properties PROPS = new Properties();

    /**
     * Instantiates the test.
     * 
     * @param name
     *            the test name
     * @throws IOException
     *             an error occurred during initialization
     */
    public JsonOneBusAwayClientTest(final String name) throws IOException {

        super(name);

        LOGGER.info("Loading configuration file {}", PROPS_PATH);
        final InputStream in = JsonOneBusAwayClientTest.class.getResourceAsStream(PROPS_PATH);
        PROPS.load(in);

        LOGGER.info("Preparing http client");
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", new PlainSocketFactory(), 80));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);

        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);

        final String url = PROPS.getProperty("onebusaway.api.url");
        final String key = PROPS.getProperty("onebusaway.api.key");
        obaClient = new JsonOneBusAwayClient(httpClient, url, key);

    }

    /**
     * Test method for {@link OneBusAwayService#getTripDetails(String)}.
     * 
     * @throws IOException
     *             an error occurred
     */
    public final void testGetTripDetails() throws IOException {

        LOGGER.info("testGetTripDetails.start");

        TripSchedule schedule = null;
        schedule = obaClient.getTripDetails("2_10000");

        assertNotNull("no schedule returned by the api", schedule);
        assertEquals("27 stop times should be returned by the api", 27, schedule.getStopTimes()
                .size());
        assertEquals("the first stop of this trip should be Saint Saëns", "Saint Saëns", schedule
                .getStopTimes().get(0).getStop().getName());

        LOGGER.info("testGetTripDetails.end");
    }

    /**
     * Test method for {@link OneBusAwayService#getArrivalsAndDeparturesForStop(String)}.
     * 
     * @throws IOException
     *             an error occurred
     */
    public final void testGetArrivalsAndDeparturesForStop() throws IOException {

        LOGGER.info("testGetArrivalsAndDeparturesForStop.start");

        List<ArrivalAndDeparture> arrivalsAndDepartures = null;
        arrivalsAndDepartures = obaClient.getArrivalsAndDeparturesForStop("2_1212");

        assertNotNull("no arrivals and departures returned by the api", arrivalsAndDepartures);
        for (final ArrivalAndDeparture aad : arrivalsAndDepartures) {
            assertTrue(StringUtils.isNotBlank(aad.getRouteId()));
            assertNotNull(aad.getDistanceFromStop());
            assertTrue(StringUtils.isNotBlank(aad.getRouteLongName()));
            assertTrue(StringUtils.isNotBlank(aad.getRouteShortName()));
            assertNotNull(aad.getScheduledArrivalTime());
            assertNotNull(aad.getServiceDate());
            assertTrue(StringUtils.isNotBlank(aad.getStopId()));
            assertTrue(StringUtils.isNotBlank(aad.getTripHeadsign()));
            assertNotNull(aad.getStopSequence());
        }

        LOGGER.info("testGetArrivalsAndDeparturesForStop.end");
    }

    /**
     * Test method for {@link OneBusAwayService#getStop(String)}.
     * 
     * @throws IOException
     *             an error occurred
     */
    public final void testGetStop() throws IOException {

        LOGGER.info("testGetStop.start");

        Stop stop = null;
        stop = obaClient.getStop("2_1167");

        assertNotNull("no stop returned by the api", stop);
        assertEquals("5 routes should be returned for the stop repbottb", 5, stop.getRoutes()
                .size());
        assertEquals("the name of the stop repbottb should be République Pré Botté",
                "République Pré Botté", stop.getName());
        assertEquals("the code of the stop repbottb should be 1167", 1167, stop.getCode());

        LOGGER.info("testGetStop.end");
    }

    /**
     * Test method for {@link OneBusAwayService#getScheduleForStop(String)}.
     * 
     * @throws IOException
     *             an error occurred
     * @throws ParseException
     *             The date is not in a valid format
     */
    public final void testGetScheduleForStop() throws IOException, ParseException {

        LOGGER.info("testGetScheduleForStop.start");

        StopSchedule schedule = null;
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(sdf.parse("2012-03-10"));
        schedule = obaClient.getScheduleForStop("2_1167", calendar.getTime());

        assertNotNull("no schedule returned by the api", schedule);

        assertEquals("79 stop times should be returned by the api", 79, schedule.getStopTimes()
                .size());
        assertEquals("the stop id should be 2_1167", "2_1167", schedule.getStop().getId());
        assertEquals("the stop name should be République Pré Botté", "République Pré Botté",
                schedule.getStop().getName());
        assertEquals("5 lines should be return for this stop", 5, schedule.getStop().getRoutes()
                .size());

        int cpt = 0;
        for (final ScheduleStopTime stopTime : schedule.getStopTimes()) {
            if (stopTime.getRoute().getShortName().equals("53")) {
                cpt++;
            }
        }
        assertEquals("31 stop times should be returned by the api for line 53", 31, cpt);

        LOGGER.info("testGetScheduleForStop.end");
    }

}
