package fr.dudie.onebusaway.model.v2;


public class EntryWithReferences<T> {

    private References references;

    private T entry;

    /**
     * Gets the references.
     * 
     * @return the references
     */
    public final References getReferences() {

        return references;
    }

    /**
     * Sets the references.
     * 
     * @param references
     *            the references to set
     */
    public final void setReferences(final References references) {

        this.references = references;
    }

    /**
     * Gets the entry.
     * 
     * @return the entry
     */
    public final T getEntry() {

        return entry;
    }

    /**
     * Sets the entry.
     * 
     * @param entry
     *            the entry to set
     */
    public final void setEntry(final T entry) {

        this.entry = entry;
    }

}
