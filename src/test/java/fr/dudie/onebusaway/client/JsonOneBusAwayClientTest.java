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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.junit.rules.RunWithWebServer;
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
public class JsonOneBusAwayClientTest {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonOneBusAwayClientTest.class);

    /** Run a web server. */
    @Rule
    public static final RunWithWebServer SERVER = new RunWithWebServer("/www");

    /** The tested OneBusAway client. */
    private JsonOneBusAwayClient obaClient;

    /**
     * Setup the OneBusAway client.
     */
    @Before
    public void setupOneBusAwayClient() {

        LOGGER.info("Preparing http client");
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", new PlainSocketFactory(), 80));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);

        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);
        obaClient = new JsonOneBusAwayClient(httpClient, SERVER.getUrl().toString(), "test");
    }

    /**
     * Test method for {@link OneBusAwayService#getTripDetails(String)}.
     * 
     * @throws IOException
     *             an error occurred
     */
    @Test
    public final void testGetTripDetails() throws IOException {

        LOGGER.info("testGetTripDetails.start");

        TripSchedule schedule = null;
        schedule = obaClient.getTripDetails("2_10000");

        assertNotNull("no schedule returned by the api", schedule);
        assertEquals("20 stop times should be returned by the api", 20, schedule.getStopTimes()
                .size());
        assertEquals("the first stop of this trip should be Henri Fréville", "Henri Fréville", schedule
                .getStopTimes().get(0).getStop().getName());

        LOGGER.info("testGetTripDetails.end");
    }

    /**
     * Test method for {@link OneBusAwayService#getArrivalsAndDeparturesForStop(String)}.
     * 
     * @throws IOException
     *             an error occurred
     */
    @Test
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
    @Test
    public final void testGetStop() throws IOException {

        LOGGER.info("testGetStop.start");

        Stop stop = null;
        stop = obaClient.getStop("2_1167");

        assertNotNull("no stop returned by the api", stop);
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
    @Test
    public final void testGetScheduleForStop() throws IOException, ParseException {

        LOGGER.info("testGetScheduleForStop.start");

        StopSchedule schedule = null;
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(sdf.parse("2012-03-10"));
        schedule = obaClient.getScheduleForStop("2_1167", calendar.getTime());

        assertNotNull("no schedule returned by the api", schedule);

        assertEquals("172 stop times should be returned by the api", 172, schedule.getStopTimes()
                .size());
        assertEquals("the stop id should be 2_1167", "2_1167", schedule.getStop().getId());
        assertEquals("the stop name should be République Pré Botté", "République Pré Botté",
                schedule.getStop().getName());

        int cpt = 0;
        for (final ScheduleStopTime stopTime : schedule.getStopTimes()) {
            if (stopTime.getRoute().getShortName().equals("53")) {
                cpt++;
            }
        }
        assertEquals("87 stop times should be returned by the api for line 53", 87, cpt);

        LOGGER.info("testGetScheduleForStop.end");
    }

}
