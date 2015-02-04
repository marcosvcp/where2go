package entity.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Objects;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import entity.user.User;

/**
 * Representa um evento. Created by andersongfs on 25/11/14.
 */
@ParseClassName("Event")
public class Event extends ParseObject {
    // Mudar para File
    private String photo;

    public Event() {
    }

    public Event(final String name, final String description,
            final String photo, final String info, final Date initialDate,
            final Date finalDate, final double price, final String outfit,
            final Integer capacity, final boolean isPublic, final User owner) {
        put("state", new EventOpened().getName());
        put("name", name);
        put("ownerName", owner.getName());
        put("description", description);
        // FIXME Isso aqui tem que ser um file
        put("info", info);
        put("initialDate", initialDate);
        put("finalDate", finalDate);
        put("price", price);
        put("outfit", outfit);
        put("capacity", capacity);
        put("note", "");
        setOwner(owner);
        put("facebookId", owner.getFacebookId());
        this.photo = photo;
        put("isPublic", isPublic);
        put("tags", "");
    }

    public User getOwner() throws ParseException {
        final ParseRelation<User> a = getRelation("owner");
        return a.getQuery().find().get(0);
    }

    public void setOwner(final User owner) {
        final ParseRelation<User> relat = getRelation("owner");
        relat.add(owner);
    }

    public String getOwnerName() {
        return getString("ownerName");
    }

    public void setOwnerName(final String ownerName) {
        put("ownerName", ownerName);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(final String name) {
        put("name", name);
    }

    public String getNote() {
        return getString("note");
    }

    public void setNote(final String notes) {
        put("note", notes);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(final String description) {
        put("description", description);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return getString("info");
    }

    public void setInfo(final String info) {
        put("info", info);
    }

    public Date getInitialDate() {
        return getDate("initialDate");
    }

    public void setInitialDate(final Date initialDate) {
        put("initialDate", initialDate);
    }

    public Date getFinalDate() {
        return getDate("finalDate");
    }

    public void setFinalDate(final Date finalDate) {
        put("finalDate", finalDate);
    }

    public double getPrice() {
        return getDouble("price");
    }

    public void setPrice(final double price) {
        put("price", price);
    }

    public String getOutfit() {
        return getString("outfit");
    }

    public void setOutfit(final String outfit) {
        put("outfit", outfit);
    }

    public Integer getCapacity() {
        return getInt("capacity");
    }

    public void setCapacity(final Integer capacity) {
        put("capacity", capacity);
    }

    public List<User> getParticipants() {
        return getList("participants");
    }

    public void setParticipants(final List<User> participants) {
        put("participants", participants);
    }

    public String getFacebookId() {
        return getString("facebookId");
    }

    public void setFacebookId(final String facebookId) {
        put("facebookId", facebookId);
    }

    public boolean isPublic() {
        return getBoolean("isPublic");
    }

    public void setPublic(final boolean isPublic) {
        put("isPublic", isPublic);
    }

    public ArrayList<String> getTags() {
        final String tagsString = getString("tags");
        final String[] tags = tagsString.split("@");
        final ArrayList<String> finalTags = new ArrayList<String>();
        for (int i = 0; i < tags.length; i++) {
            finalTags.add(tags[i]);
        }
        return finalTags;
    }

    public void addTags(final String tag) {
        String tags = getString("tags");
        tags = tags + "@" + tag;
        put("tags", tags);
    }

    /**
     * Adiciona um {@code participant} à lista de {@code participants}
     *
     * @param participant
     *            O participante a ser adicionado
     * @return True caso ele possa participar do evento
     */
    public boolean addParticipant(final User participant) {
        if (isFull()) {
            return false;
        }
        return getParticipants().add(participant);
    }

    /**
     * Remove o {@code participant} da lista de {@code participants}
     *
     * @param participant
     *            O participante a ser removido
     */
    public void removeParticipant(final User participant) {
        this.getParticipants().remove(participant);
    }

    /**
     * @return True caso o evento esteja completo
     */
    public boolean isFull() {
        return getCapacity() <= getParticipants().size();
    }

    @Override
    public int hashCode() {
        // FIXME InitialDate deveria ser do tipo date @author Marcos v. Candeia
        return Objects.hashCode(this.getName(), this.getDescription());
    }

    @Override
    public boolean equals(final Object otherEvent) {
        if (otherEvent == null || !(otherEvent instanceof Event)) {
            return false;
        }
        final Event other = (Event) otherEvent;
        return Objects.equal(this.getName(), other.getName())
                && Objects.equal(this.getDescription(), other.getDescription());
    }

    /**
     * Retorna true caso {@code user} é o dono do evento
     */
    public boolean isOwner(final User user) {
        return user.getFacebookId().equals(getFacebookId());
    }

    /**
     * Seta o estado do evento de acordo com o nome
     */
    public EventState getEventState() {
        if (getState().equals("Opened")) {
            return new EventOpened();
        } else if (getState().equals("Finished")) {
            return new EventFinished();
        }
        return new EventCanceled();
    }

    public void setState(final String state) {
        put("state", state);
    }

    public String getState() {
        return getString("state");
    }
}
