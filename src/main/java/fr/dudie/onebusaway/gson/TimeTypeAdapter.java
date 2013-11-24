package fr.dudie.onebusaway.gson;

import java.lang.reflect.Type;
import java.text.ParseException;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.dudie.onebusaway.model.Time;

/**
 * @author Jeremie Huchet
 */
public class TimeTypeAdapter implements JsonSerializer<Time>, JsonDeserializer<Time> {
    
    @Override
    public Time deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final String value = json.getAsString();
        try {
            return Time.from(value);
        } catch (ParseException e) {
            final String msg = String.format("Can't parse time value %s",value);
            throw new JsonParseException(msg,  e);
        }
    }

    @Override
    public JsonElement serialize(final Time src, final Type typeOfSrc, final JsonSerializationContext context) {
        
        return new JsonPrimitive(src.toString());
    }

}
