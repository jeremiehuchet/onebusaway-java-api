package fr.dudie.onebusaway.gson;

import java.lang.reflect.Type;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author Jeremie Huchet
 */
public class LocaleTypeAdapter implements JsonSerializer<Locale>, JsonDeserializer<Locale> {
    
    @Override
    public Locale deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        return new Locale(json.getAsString());
    }

    @Override
    public JsonElement serialize(final Locale src, final Type typeOfSrc, final JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

}
