package entity.event;

/**
 * Representa a visiblidade de um {@link Event} Created by andersongfs on
 * 25/11/14.
 */
public enum EventVisibility {

    /** The public. */
    PUBLIC("Público"),
    /** The private. */
    PRIVATE("Privado");

    /** The visibility. */
    private String visibility;

    /**
     * Instantiates a new event visibility.
     *
     * @param newVisibility
     *            the visibility
     */
    EventVisibility(final String newVisibility) {
        visibility = newVisibility;
    }

    /**
     * Gets the visibility.
     *
     * @return the visibility
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * Sets the visibility.
     *
     * @param newVisibility
     *            the new visibility
     */
    public void setVisibility(final String newVisibility) {
        visibility = newVisibility;
    }
}
