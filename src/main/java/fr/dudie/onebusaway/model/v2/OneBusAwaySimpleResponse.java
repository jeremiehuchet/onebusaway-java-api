package fr.dudie.onebusaway.model.v2;

public class OneBusAwaySimpleResponse<T> extends AbstractOneBusAwayResponse {

    private T data;

    /**
     * Gets the data.
     * 
     * @return the data
     */
    public final T getData() {

        return data;
    }

    /**
     * Sets the data.
     * 
     * @param data
     *            the data to set
     */
    public final void setData(final T data) {

        this.data = data;
    }

}
