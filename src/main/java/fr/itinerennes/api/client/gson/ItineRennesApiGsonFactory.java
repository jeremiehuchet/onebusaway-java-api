package fr.itinerennes.api.client.gson;

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

import java.util.Locale;
import java.util.TimeZone;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import fr.itinerennes.api.client.model.Time;

/**
 * @author Jeremie Huchet
 */
public class ItineRennesApiGsonFactory {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public static Gson newInstance() {
        return newInstance(false);
    }

    public static Gson newInstance(final boolean prettyJson) {
        final GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat(DATE_FORMAT);
        gb.addSerializationExclusionStrategy(new ExcludeAnnotationStrategy());
        gb.registerTypeAdapter(Time.class, new TimeTypeAdapter());
        gb.registerTypeAdapter(Locale.class, new LocaleTypeAdapter());
        gb.registerTypeAdapter(TimeZone.class, new TimeZoneTypeAdapter());
        if (prettyJson) {
            gb.setPrettyPrinting();
        }
        return gb.create();
    }

    private static class ExcludeAnnotationStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(final FieldAttributes f) {
            final Expose expose = f.getAnnotation(Expose.class);
            return null == expose ? false : !expose.serialize();
        }

        @Override
        public boolean shouldSkipClass(final Class<?> clazz) {
            return false;
        }

    }
}
