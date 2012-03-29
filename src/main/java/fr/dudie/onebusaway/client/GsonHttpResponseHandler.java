package fr.dudie.onebusaway.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import fr.dudie.onebusaway.exceptions.OneBusAwayException;
import fr.dudie.onebusaway.model.v2.AbstractOneBusAwayResponse;

/**
 * @author Jérémie Huchet
 */
public class GsonHttpResponseHandler<T extends AbstractOneBusAwayResponse> implements
        ResponseHandler<T> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GsonHttpResponseHandler.class);

    /** The Gson instance. */
    private final Gson gsonInstance;

    /** The type of the response. */
    private final Type type;

    /**
     * Constructor.
     * 
     * @param gsonInstance
     *            the Gson instance
     * @param type
     *            the type of the response
     */
    public GsonHttpResponseHandler(final Gson gsonInstance, final Type type) {

        this.gsonInstance = gsonInstance;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
     */
    @Override
    public T handleResponse(final HttpResponse response) throws ClientProtocolException,
            IOException {

        // EntityUtils.toString(response.getEntity(), "utf-8")
        final InputStream content = response.getEntity().getContent();
        final T obaResponse = gsonInstance.fromJson(new InputStreamReader(content, "utf-8"), type);
        try {
            assertResponseOk(obaResponse);
        } catch (final OneBusAwayException e) {
            throw new IOException("OneBusAway API returned an error", e);
        }

        return obaResponse;
    }

    /**
     * Ensure the API response code is 200 OK.
     * 
     * @param obaResponse
     *            the parsed response.
     * @throws OneBusAwayException
     */
    private void assertResponseOk(final AbstractOneBusAwayResponse obaResponse)
            throws OneBusAwayException {

        if (200 != obaResponse.getCode() || !"OK".equalsIgnoreCase(obaResponse.getText())) {
            throw new OneBusAwayException("invalid response: " + gsonInstance.toJson(obaResponse),
                    null);
        }
    }
}
