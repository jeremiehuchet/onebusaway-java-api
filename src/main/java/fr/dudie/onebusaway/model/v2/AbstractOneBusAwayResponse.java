package fr.dudie.onebusaway.model.v2;

public abstract class AbstractOneBusAwayResponse {

    private int version;

    private int code;

    private String text;

    /**
     * Gets the version.
     * 
     * @return the version
     */
    public final int getVersion() {

        return version;
    }

    /**
     * Sets the version.
     * 
     * @param version
     *            the version to set
     */
    public final void setVersion(final int version) {

        this.version = version;
    }

    /**
     * Gets the code.
     * 
     * @return the code
     */
    public final int getCode() {

        return code;
    }

    /**
     * Sets the code.
     * 
     * @param code
     *            the code to set
     */
    public final void setCode(final int code) {

        this.code = code;
    }

    /**
     * Gets the text.
     * 
     * @return the text
     */
    public final String getText() {

        return text;
    }

    /**
     * Sets the text.
     * 
     * @param text
     *            the text to set
     */
    public final void setText(final String text) {

        this.text = text;
    }

}
