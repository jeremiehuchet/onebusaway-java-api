package fr.dudie.onebusaway.gson;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.onebusaway.api.model.transit.StopRouteDirectionScheduleV2Bean;
import org.onebusaway.api.model.transit.StopRouteScheduleV2Bean;
import org.onebusaway.api.model.transit.StopScheduleV2Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import fr.dudie.onebusaway.model.v2.EntryWithReferences;
import fr.dudie.onebusaway.model.v2.OneBusAwayResponse;
import fr.dudie.onebusaway.model.v2.References;

/**
 * Test Gson deserialization for a schedule-for-stop request.
 * 
 * @author Jérémie Huchet
 */
public class GsonScheduleForStopDeserializationTest {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(GsonScheduleForStopDeserializationTest.class);

    private static Gson gsonInstance;

    @BeforeClass
    public static void setupTest() {

        // prepare gson instance
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(References.class, new ReferencesDeserializer());
        gsonInstance = gsonBuilder.create();
    }

    @Test
    public void testDeserialization() throws JsonIOException, JsonSyntaxException,
            UnsupportedEncodingException {

        final InputStream content = getClass().getResourceAsStream(
                "/api_response_schedule-for-stop.json");

        final Type type = new TypeToken<OneBusAwayResponse<StopScheduleV2Bean>>() {
        }.getType();

        final OneBusAwayResponse<StopScheduleV2Bean> response = gsonInstance.fromJson(
                new InputStreamReader(content, "utf-8"), type);

        assertNotNull(response);
        assertTrue(StringUtils.isNotBlank(response.getText()));
        assertNotNull(response.getCode());
        assertNotNull(response.getVersion());
        assertNotNull(response.getData());

        final EntryWithReferences<StopScheduleV2Bean> data = response.getData();
        assertNotNull(data.getReferences());
        assertNotNull(data.getEntry());

        final StopScheduleV2Bean entry = data.getEntry();
        assertNotNull(entry.getDate());
        assertTrue(StringUtils.isNotBlank(entry.getStopId()));

        final List<StopRouteScheduleV2Bean> schedules = entry.getStopRouteSchedules();
        assertNotNull(schedules);
        assertTrue(schedules.size() > 0);

        for (final StopRouteScheduleV2Bean schedule : schedules) {
            assertTrue(StringUtils.isNotBlank(schedule.getRouteId()));

            final List<StopRouteDirectionScheduleV2Bean> srds = schedule
                    .getStopRouteDirectionSchedules();
            assertNotNull(srds);
            assertTrue(srds.size() > 0);
            for (final StopRouteDirectionScheduleV2Bean s : srds) {
                assertTrue(StringUtils.isNotBlank(s.getTripHeadsign()));
            }
        }
    }
}
