package fr.dudie.onebusaway.exceptions;

/**
 * Thrown when a problem occurs while querying the OneBusAway API service.
 * 
 * @author Olivier Boudet
 */
public class OneBusAwayException extends Exception {

    /** Serial version UID. */
    private static final long serialVersionUID = -6552703516738273041L;

    /**
     * Creates a OneBusAway exception.
     * 
     * @param message
     *            the message descriptions
     * @param cause
     *            the optional cause of the error
     */
    public OneBusAwayException(final String message, final Throwable cause) {

        super(message, cause);
    }
}
