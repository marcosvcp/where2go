package entity.establishment;

import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import entity.event.Event;

/**
 * Representa um estabelecimento. Created by diegotavarez on 21/01/15.
 */
@ParseClassName("Establishment")
public class Establishment extends ParseObject {
    // Mudar para File
    /** The photo. */
    private String photo;

    public Establishment() {
    }

    /**
     * Instantiates a new establishment.
     *
     * @param name
     *            the name
     * @param description
     *            the description
     * @param photo
     *            the photo
     * @param lat
     *            the lat
     * @param lon
     *            the lon
     */
    public Establishment(final String name, final String description,
            final String photo, final double lat, final double lon) {
        put("name", name);
        put("description", description);
        final ParseGeoPoint location = new ParseGeoPoint(lat, lon);
        put("location", location);
        this.photo = photo;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public final String getLocation() {
        return getString("location");
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public final String getName() {
        return getString("name");
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public final void setName(final String name) {
        put("name", name);
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public final String getDescription() {
        return getString("description");
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public final void setDescription(final String description) {
        put("description", description);
    }

    /**
     * Gets the photo.
     *
     * @return the photo
     */
    public final String getPhoto() {
        return photo;
    }

    /**
     * Sets the photo.
     *
     * @param photo
     *            the new photo
     */
    public final void setPhoto(final String photo) {
        this.photo = photo;
    }

    /**
     * Gets the events.
     *
     * @return the events
     */
    public final List<Event> getEvents() {
        return getList("events");
    }

    /**
     * Adiciona um {@code event} da lista de {@code events}.
     *
     * @param event
     *            O evento a ser adicionado
     * @return True caso ele possa ser associado ao {@code establishment}
     */
    public final boolean addEvent(final Event event) {
        return getEvents().add(event);
    }

    /**
     * Remove o {@code event} da lista de {@code events}.
     *
     * @param event
     *            O evento a ser removido
     */
    public final void removeEvent(final Event event) {
        getEvents().remove(event);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return (getName().length() + getDescription().length()) * 8;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object otherEstablishment) {
        if (otherEstablishment == null
                || !(otherEstablishment instanceof Establishment)) {
            return false;
        }
        final Establishment other = (Establishment) otherEstablishment;
        if (getName().equals(other.getName())) {
            return true;
        } else {
            return false;
        }
    }
}
