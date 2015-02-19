
package entity.establishment;

import com.google.common.base.Objects;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.List;

import entity.event.Event;
import entity.user.User;

/**
 * Representa um estabelecimento. Created by diegotavarez on 21/01/15.
 */
@ParseClassName("Establishment")
public class Establishment extends ParseObject {
    // Mudar para File
    /** The photo. */
    private String photo;

    /**
     * Instantiates a new establishment.
     */
    public Establishment() {
    }

    /**
     * Instantiates a new establishment.
     * 
     * @param name the name
     * @param description the description
     * @param photo the photo
     * @param lat the lat
     * @param lon the lon
     */
    public Establishment(final String name, final String description, final String photo,
            final double lat, final double lon) {
        put("name", name);
        put("description", description);
        ParseGeoPoint location = new ParseGeoPoint(lat, lon);
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
     * @param name the new name
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
     * @param description the new description
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
     * @param photo the new photo
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
     * Sets the participants.
     * 
     * @param participants the new participants
     */
    public final void setParticipants(final List<User> participants) {
        put("participants", participants);
    }

    /**
     * Adiciona um {@code event} da lista de {@code events}.
     * 
     * @param event O evento a ser adicionado
     * @return True caso ele possa ser associado ao {@code establishment}
     */
    public final boolean addEvent(final Event event) {
        return getEvents().add(event);
    }

    /**
     * Remove o {@code event} da lista de {@code events}.
     * 
     * @param event O evento a ser removido
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
        return Objects.hashCode(getName(), getDescription());// FIXME InitialDate deveria ser do tipo date @author Marcos v. Candeia
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object otherEvent) {
        if (otherEvent == null || !(otherEvent instanceof Establishment)) {
            return false;
        }
        Establishment other = (Establishment) otherEvent;
        return Objects.equal(getName(), other.getName())
                && Objects.equal(getLocation(), other.getLocation());
    }
}
