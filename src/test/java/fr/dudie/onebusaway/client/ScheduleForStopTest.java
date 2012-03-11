package fr.dudie.onebusaway.client;

import static fr.dudie.onebusaway.utils.ObaAssert.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.onebusaway.AbstractOneBusAwayTest;
import fr.dudie.onebusaway.model.ScheduleStopTime;
import fr.dudie.onebusaway.model.StopSchedule;

@RunWith(Parameterized.class)
public class ScheduleForStopTest extends AbstractOneBusAwayTest {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleForStopTest.class);

    private static final Date SCHEDULE_DATE;
    static {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse("2012-03-09"));
        } catch (final ParseException e) {
            LOGGER.error("invalid date", e);
        }
        SCHEDULE_DATE = calendar.getTime();
    }

    @Parameters
    public static List<Object[]> data() throws IOException {

        final InputStream stopIds = ScheduleForStopTest.class.getResourceAsStream("/stop_ids.txt");
        final BufferedReader br = new BufferedReader(new InputStreamReader(stopIds));

        final List<Object[]> data = new ArrayList<Object[]>();
        String stopId;
        while ((stopId = br.readLine()) != null) {
            data.add(new Object[] { stopId });
        }

        LOGGER.info("running test for {} stops", data.size());

        return data;
    }

    /** The identifier of the stop to try to retrieve. */
    private final String stopId;

    /**
     * Constructor.
     * 
     * @param stopId
     *            the identifier of the stop to try to retrieve
     */
    public ScheduleForStopTest(final String stopId) {

        this.stopId = stopId;
        LOGGER.info("test initialized for stop id={}", stopId);
    }

    @Test
    public void testGetScheduleForStop() throws IOException {

        final StopSchedule result = getObaClient().getScheduleForStop(stopId, SCHEDULE_DATE);

        assertNotNull("a stop schedule should be retrurned for stop id=" + stopId, result);
        assertEquals("stopId=" + stopId, SCHEDULE_DATE, result.getDate());
        assertNotNull("stopId=" + stopId, result.getStop());

        final List<ScheduleStopTime> stopTimes = result.getStopTimes();
        assertTrue("stopId=" + stopId, CollectionUtils.isNotEmpty(stopTimes));
        for (final ScheduleStopTime stopTime : stopTimes) {
            assertNotNull("stopId=" + stopId, stopTime.getArrivalTime());
            assertNotNull("stopId=" + stopId, stopTime.getDepartureTime());
            assertTrue("stopId=" + stopId, StringUtils.isNotBlank(stopTime.getHeadsign()));
            notEmpty("stopId=" + stopId, stopTime.getRoute());
        }
    }
}
