package fr.dudie.onebusaway;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.onebusaway.client.IOneBusAwayClient;
import fr.dudie.onebusaway.client.JsonOneBusAwayClient;
import fr.dudie.onebusaway.client.JsonOneBusAwayClientTest;

/**
 * @author Jérémie Huchet
 */
public class AbstractOneBusAwayTest {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractOneBusAwayTest.class);

    /** Path to the properties file. */
    private static final String PROPS_PATH = "/onebusaway-client-test.properties";

    /** Loaded properties. */
    private static final Properties PROPS = new Properties();

    /** The json OneBusAway client. */
    private static JsonOneBusAwayClient obaClient;

    /**
     * Constructor.
     */
    public AbstractOneBusAwayTest() {

        LOGGER.info("Loading configuration file {}", PROPS_PATH);
        final InputStream in = JsonOneBusAwayClientTest.class.getResourceAsStream(PROPS_PATH);
        try {
            PROPS.load(in);
        } catch (final IOException e) {
            throw new RuntimeException("Can't load properties from " + PROPS_PATH, e);
        }

        LOGGER.info("Preparing http client");
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", new PlainSocketFactory(), 80));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);

        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);

        final String url = PROPS.getProperty("onebusaway.api.url");
        final String key = PROPS.getProperty("onebusaway.api.key");
        obaClient = new JsonOneBusAwayClient(httpClient, url, key);
    }

    /**
     * Gets the OneBusAway json client.
     * 
     * @return the OneBusAway json client
     */
    public final IOneBusAwayClient getObaClient() {

        return obaClient;
    }

}
