package entity.event;

import java.util.Date;
import java.util.List;

import com.google.common.base.Objects;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import entity.user.User;

/**
 * Representa um evento. Created by andersongfs on 25/11/14.
 */
@ParseClassName("Event")
public class Event extends ParseObject{
	private User owner;
	// Mudar para File
	private String photo;

	public Event() {}
	public Event(String name, String description, String photo, String info, Date initialDate, Date finalDate,
			double price, String outfit, Integer capacity, boolean isPublic, User owner) {
		put("state", new EventOpened().getName());
		put("name", name);
		put("description", description);
		// FIXME Isso aqui tem que ser um file
		put("info", info);
		put("initialDate", initialDate);
		put("finalDate", finalDate);
		put("price", price);
		put("outfit", outfit);
		put("capacity", capacity);
		put("note", "");
		this.photo = photo;
		this.owner = owner;
		put("isPublic", isPublic);
	}

	public String getName() {
		return getString("name");
	}

	public void setName(String name) {
		put("name", name);
	}

	public String getNote() {
		return getString("note");
	}

	public void setNote(String notes) {
		put("note", notes);
	}
	
	public String getDescription() {
		return getString("description");
	}

	public void setDescription(String description) {
		put("description", description);
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getInfo() {
		return getString("info");
	}

	public void setInfo(String info) {
		put("info", info);
	}

	public Date getInitialDate() {
		return getDate("initialDate");
	}

	public void setInitialDate(Date initialDate) {
		put("initialDate", initialDate);
	}

	public Date getFinalDate() {
		return getDate("finalDate");
	}

	public void setFinalDate(Date finalDate) {
		put("finalDate", finalDate);
	}

	public double getPrice() {
		return getDouble("price");
	}

	public void setPrice(double price) {
		put("price", price);
	}

	public String getOutfit() {
		return getString("outfit");
	}

	public void setOutfit(String outfit) {
		put("outfit", outfit);
	}

	public Integer getCapacity() {
		return getInt("capacity");
	}

	public void setCapacity(Integer capacity) {
		put("capacity", capacity);
	}

	public List<User> getParticipants() {
		return getList("participants");
	}

	public void setParticipants(List<User> participants) {
		put("participants", participants);
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isPublic() {
		return getBoolean("isPublic");
	}

	public void setPublic(boolean isPublic) {
		put("isPublic", isPublic);
	}

	/**
	 * Adiciona um {@code participant} à lista de {@code participants}
	 *
	 * @param participant
	 *            O participante a ser adicionado
	 * @return True caso ele possa participar do evento
	 */
	public boolean addParticipant(User participant) {
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
	public void removeParticipant(User participant) {
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
	public boolean equals(Object otherEvent) {
		if (otherEvent == null || !(otherEvent instanceof Event)) {
			return false;
		}
		Event other = (Event) otherEvent;
		return Objects.equal(this.getName(), other.getName()) && Objects.equal(this.getDescription(), other.getDescription());
	}

	/**
	 * Retorna true caso {@code user} é o dono do evento
	 */
	public boolean isOwner(User user) {
		return user.equals(owner);
	}
	
	/**
	 * Seta o estado do evento de acordo com o nome
	 */
	public EventState getEventState() {
		if(getState().equals("Opened")) {
			return new EventOpened();
		}else if (getState().equals("Finished")) {
			return new EventFinished();
		}
		return new EventCanceled();
	}

	public void setState(String state) {
		put("state", state);
	}
	
	public String getState() {
		return getString("state");
	}
}
