package fr.dudie.onebusaway.utils;

import static org.junit.Assert.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import fr.dudie.onebusaway.model.Route;
import fr.dudie.onebusaway.model.Stop;

public class ObaAssert {

    public static void notEmpty(final String message, final Stop stop) {

        assertNotNull(message, stop.getCode());
        assertTrue(message, StringUtils.isNotBlank(stop.getDirection()));
        assertTrue(message, StringUtils.isNotBlank(stop.getId()));
        assertNotNull(message, stop.getLat());
        assertNotNull(message, stop.getLon());
        assertTrue(message, StringUtils.isNotBlank(stop.getName()));

        assertTrue(message, CollectionUtils.isNotEmpty(stop.getRoutes()));
        for (final Route route : stop.getRoutes()) {
            notEmpty(message, route);
        }
    }

    public static void notEmpty(final String message, final Route route) {

        assertTrue(message, StringUtils.isNotBlank(route.getAgencyId()));
        assertTrue(message, StringUtils.isNotBlank(route.getColor()));
        assertTrue(message, StringUtils.isNotBlank(route.getDescription()));
        assertTrue(message, StringUtils.isNotBlank(route.getId()));
        assertTrue(message, StringUtils.isNotBlank(route.getLongName()));
        assertTrue(message, StringUtils.isNotBlank(route.getShortName()));
        assertTrue(message, StringUtils.isNotBlank(route.getTextColor()));
        assertNotNull(message, route.getType());
    }
}
