package fr.dudie.onebusaway.model.v2;

public class OneBusAwayResponse<T> extends AbstractOneBusAwayResponse {

    private EntryWithReferences<T> data;

    /**
     * Gets the data.
     * 
     * @return the data
     */
    public final EntryWithReferences<T> getData() {

        return data;
    }

    /**
     * Sets the data.
     * 
     * @param data
     *            the data to set
     */
    public final void setData(final EntryWithReferences<T> data) {

        this.data = data;
    }

}