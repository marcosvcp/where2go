
package entity.event;

import android.util.Log;

import com.google.common.base.Objects;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.notifications.Notification;
import entity.user.User;

/**
 * Representa um evento. Created by andersongfs on 25/11/14.
 */
@ParseClassName("Event")
public class Event extends ParseObject {
    public static final String TAGS = "tags";
    public static final String TAG_SEPARATOR = "@";
    // Mudar para File
    /**
     * The photo.
     */
    private String photo;

    
    private File eventPhoto;
    /**
     * Instantiates a new event.
     */
    public Event() {
    }

    public Event(String eventName, String eventDescription, Date initialDate, Date finalDate, String initialTime, String finalTime, boolean isPublic, User eventOwner){
        put("state", new EventOpened().getName());
        put("name", eventName);
        put("description", eventDescription);
        put("initialDate", initialDate);
        put("finalDate", finalDate);
        put("initialTime", initialTime);
        put("finalTime", finalTime);
        put("isPublic", isPublic);
        put("ownerName", eventOwner.getName());
        put("facebookId", eventOwner.getFacebookId());
        setOwner(eventOwner);
        put(TAGS, "");
    }

    /**
     * Retorna o primeiro usuário da relação entre evento e usuário, ou seja o dono do evento
     */
    public final User getOwner() throws ParseException {
        final ParseRelation<User> a = getRelation("owner");
        // Essa query é síncrona
        return a.getQuery().find().get(0);
    }

    /**
     * Seta o dono do evento
     */
    public final void setOwner(final User owner) {
        final ParseRelation<User> relat = getRelation("owner");
        relat.add(owner);
    }

    /**
     * Gets the owner name.
     *
     * @return the owner name
     */
    public final String getOwnerName() {
        return getString("ownerName");
    }

    /**
     * Sets the owner name.
     *
     * @param ownerName the new owner name
     */
    public final void setOwnerName(final String ownerName) {
        put("ownerName", ownerName);
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
     * Gets the note.
     *
     * @return the note
     */
    public final String getNote() {
        return getString("note");
    }

    /**
     * Sets the note.
     *
     * @param notes the new note
     */
    public final void setNote(final String notes) {
        put("note", notes);
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
     * @param newPhoto the new photo
     */
    public final void setPhoto(final String newPhoto) {
        photo = newPhoto;
    }

    /**
     * Gets the initial date.
     *
     * @return the initial date
     */
    public final Date getInitialDate() {
        return getDate("initialDate");
    }

    /**
     * Sets the initial date.
     *
     * @param initialDate the new initial date
     */
    public final void setInitialDate(final Date initialDate) {
        put("initialDate", initialDate);
    }

    /**
     * Gets the final date.
     *
     * @return the final date
     */
    public final Date getFinalDate() {
        return getDate("finalDate");
    }

    /**
     * Sets the final date.
     *
     * @param finalDate the new final date
     */
    public final void setFinalDate(final Date finalDate) {
        put("finalDate", finalDate);
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public final double getPrice() {
        return getDouble("price");
    }

    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public final void setPrice(final double price) {
        put("price", price);
    }

    /**
     * Gets the outfit.
     *
     * @return the outfit
     */
    public final String getOutfit() {
        return getString("outfit");
    }

    /**
     * Sets the outfit.
     *
     * @param outfit the new outfit
     */
    public final void setOutfit(final String outfit) {
        put("outfit", outfit);
    }

    /**
     * Gets the capacity.
     *
     * @return the capacity
     */
    public final Integer getCapacity() {
        return getInt("capacity");
    }

    /**
     * Sets the capacity.
     *
     * @param capacity the new capacity
     */
    public final void setCapacity(final Integer capacity) {
        put("capacity", capacity);
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
     * Gets the facebook id.
     *
     * @return the facebook id
     */
    public final String getFacebookId() {
        return getString("facebookId");
    }

    /**
     * Sets the facebook id.
     *
     * @param facebookId the new facebook id
     */
    public final void setFacebookId(final String facebookId) {
        put("facebookId", facebookId);
    }

    /**
     * Checks if is public.
     *
     * @return true, if is public
     */
    public final boolean isPublic() {
        return getBoolean("isPublic");
    }

    /**
     * Sets the public.
     *
     * @param isPublic the new public
     */
    public final void setPublic(final boolean isPublic) {
        put("isPublic", isPublic);
    }

    /**
     * @return the tags of event
     */
    public final List<String> getTags() {
        final String tagsString = getString(TAGS);
        final String[] tags = tagsString.split(TAG_SEPARATOR);
        final List<String> finalTags = new ArrayList<String>();
        for (int i = 0; i < tags.length; i++) {
            finalTags.add(tags[i]);
        }
        return finalTags;
    }

    /**
     * Adds the tags.
     *
     * @param tag the tag
     */
    public final void addTags(final String tag) {
        String tags = getString(TAGS);
        tags = tags + TAG_SEPARATOR + tag;
        put(TAGS, tags);
    }

    /**
     * Adiciona um {@code participant} à lista de {@code participants}.
     */
    public final Notification addParticipant(final User guest, final User host) {
        if (isFull()) {
            return new Notification(host, this, "O evento está cheio.");
        }
        return getEventState().addParticipant(this, guest, host);
    }

    /**
     * Remove o {@code participant} da lista de {@code participants}.
     *
     * @param guest O participante a ser removido
     */
    public final Notification removeParticipant(final User guest, final User host) {
        return getEventState().removeParticipant(this, guest, host);
    }

    /**
     * Checks if is full.
     *
     * @return True caso o evento esteja completo
     */
    public final boolean isFull() {
        return getCapacity() <= getParticipants().size();
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
        if (otherEvent == null || !(otherEvent instanceof Event)) {
            return false;
        }
        final Event other = (Event) otherEvent;
        return Objects.equal(getName(), other.getName())
                && Objects.equal(getDescription(), other.getDescription());
    }

    /**
     * Retorna true caso {@code user} é o dono do evento.
     *
     * @param user the user
     * @return true, if is owner
     */
    public final boolean isOwner(final User user) {
        return user.getFacebookId().equals(getFacebookId());
    }

    /**
     * Seta o estado do evento de acordo com o nome.
     *
     * @return the event state
     */
    public final EventState getEventState() {
        if ("Opened".equals(getState())) {
            return new EventOpened();
        } else if ("Finished".equals(getState())) {
            return new EventFinished();
        }
        return new EventCanceled();
    }

    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public final void setState(final String state) {
        put("state", state);
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public final String getState() {
        return getString("state");
    }
    

    /**
     * Gets the participants.
     *
     * @return the participants
     * @throws ParseException 
     */
    public final List<User> getParticipants(){
        final ParseRelation<User> participants = getRelation("participants");
        try {
			return participants.getQuery().find();
		} catch (ParseException e) {
			Log.e("Event", e.getMessage());
		}
        return new ArrayList<User>();
    }
    
    /**
     * Add one participant.
     *
     * @return the participants
     * @throws ParseException 
     */
    public Notification addParticipant(User participant){
    	String msg = "Agora voc� est� participando";
    	if(!isPublic()){
    		msg =  "Voc� n�o tem permiss�o para participar";
    	}
    	if(isFull()){
    		msg =  "Evento lotado";
    	}
    	final ParseRelation<User> participants = getRelation("participants");
    	try {
			if(participants.getQuery().find().contains(participant)){
				msg = "Voc� j� participa deste evento";
			}else{
				participants.add(participant);
			}
		} catch (ParseException e) {
			Log.e("Event", e.getMessage());
		}    	
    	this.saveInBackground();
    	return new Notification(participant, this, msg);
    }

    /**
     * Remove one participant.
     *
     * @return the participants
     * @throws ParseException 
     */
    public Notification removeParticipant(User participant){
    	final ParseRelation<User> participants = getRelation("participants");
    	try {
    		if(!participants.getQuery().find().contains(participant)){
    			return new Notification(participant, this, "Voc� n�o participa deste evento");
    		}
    	} catch (ParseException e) {
    		Log.e("Event", e.getMessage());
    	}
    	participants.remove(participant);
    	this.saveInBackground();
    	return new Notification(participant, this, "Voc� saiu deste evento");
    }

	public File getEventPhoto() {
		return eventPhoto;
	}

	public void setEventPhoto(File eventPhoto) {
		this.eventPhoto = eventPhoto;
	}
}
