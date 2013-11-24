package fr.itinerennes.api.client;

/*
 * [license]
 * ItineRennes Java API client
 * ----
 * Copyright (C) 2010 - 2013 Dudie
 * ----
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * [/license]
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.itinerennes.api.client.JsonItineRennesApiClient;
import fr.itinerennes.api.client.model.ScheduleStopTime;
import fr.itinerennes.api.client.model.StopSchedule;
import fr.itinerennes.api.client.model.TripSchedule;
import fr.itinerennes.junit.rules.RunWithWebServer;

/**
 * Test class for {@link JsonItineRennesApiClient}.
 * 
 * @author Jérémie Huchet
 * @author Olivier Boudet
 */
public class JsonItineRennesApiClientTest {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonItineRennesApiClientTest.class);

    /** Run a web server. */
    @Rule
    public static final RunWithWebServer SERVER = new RunWithWebServer("/www");
    
    /** The tested ItineRennes client. */
    private JsonItineRennesApiClient obaClient;

    /**
     * Setup the ItineRennes client.
     */
    @Before
    public void setupItineRennesClient() {

        LOGGER.info("Preparing http client");
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", new PlainSocketFactory(), 80));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);

        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);
        obaClient = new JsonItineRennesApiClient(httpClient, SERVER.getUrl().toString(), "test");
    }

    /**
     * Test method for {@link ItineRennesService#getTripDetails(String)}.
     * 
     * @throws IOException
     *             an error occurred
     */
    @Test
    @Ignore
    public final void testGetTripDetails() throws IOException {

        TripSchedule schedule = null;
        schedule = obaClient.getTripDetails("1_1012");

        assertNotNull("no schedule returned by the api", schedule);
        assertEquals("20 stop times should be returned by the api", 20, schedule.getStopTimes()
                .size());
        assertEquals("the first stop of this trip should be Henri Fréville", "Henri Fréville", schedule
                .getStopTimes().get(0).getStop().getName());
    }

    /**
     * Test method for {@link JsonItineRennesApiClient#getScheduleForStop(String)}.
     * 
     * @throws IOException
     *             an error occurred
     * @throws ParseException
     *             The date is not in a valid format
     */
    @Test
    @Ignore
    public final void testGetScheduleForStop() throws IOException, ParseException {

        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(sdf.parse("2013-11-04"));

        final StopSchedule schedule = obaClient.getScheduleForStop("2_1017", calendar.getTime());

        assertNotNull("no schedule returned by the api", schedule);

        assertEquals("378 stop times should be returned by the api", 378, schedule.getStopTimes()
                .size());
        assertEquals("the stop id should be 2_1017", "2_1017", schedule.getStop().getId());
        assertEquals("the stop name should be Les Halles", "Les Halles",
                schedule.getStop().getName());

        int cpt = 0;
        for (final ScheduleStopTime stopTime : schedule.getStopTimes()) {
            if (stopTime.getRoute().getShortName().equals("53")) {
                cpt++;
            }
        }
        assertEquals("87 stop times should be returned by the api for line 53", 87, cpt);
    }

}
