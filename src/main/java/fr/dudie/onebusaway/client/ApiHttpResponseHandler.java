package fr.dudie.onebusaway.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Handles responses for a call to the "trip-details" method of the OneBusAway API.
 * 
 * @author Olivier Boudet
 * @author Jeremie Huchet
 */
public class ApiHttpResponseHandler<T> implements ResponseHandler<T> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiHttpResponseHandler.class);

    private final Class<? extends T> clazz;

    private final Gson gson;

    public ApiHttpResponseHandler(final Class<? extends T> clazz, final Gson gson) {
        this.clazz = clazz;
        this.gson = gson;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
     */
    @Override
    public T handleResponse(final HttpResponse response) throws IOException {

        LOGGER.debug("handleResponse.start");

        final StatusLine status = response.getStatusLine();
        if (status.getStatusCode() != HttpStatus.SC_OK) {
            throw new IOException(new HttpException(String.format("%s - %s",status.getStatusCode(), status.getReasonPhrase())));
        }

        InputStream in = null;
        T tripSchedule = null;
        try {
            in = response.getEntity().getContent();
            tripSchedule = gson.fromJson(new InputStreamReader(in), clazz);
        } finally {
            if (in != null) {
                in.close();
            }
        }

        LOGGER.debug("handleResponse.end");
        return tripSchedule;
    }
}
