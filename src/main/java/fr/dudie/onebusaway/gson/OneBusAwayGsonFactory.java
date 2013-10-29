package fr.dudie.onebusaway.gson;

import java.util.Locale;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import fr.dudie.onebusaway.model.Time;

/**
 * @author Jeremie Huchet
 */
public class OneBusAwayGsonFactory {

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
