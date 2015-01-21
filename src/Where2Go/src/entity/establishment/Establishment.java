package entity.establishment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Objects;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import entity.event.Event;
import entity.user.User;

/**
 * Representa um estabelecimento. Created by diegotavarez on 21/01/15.
 */
@ParseClassName("Establishment")
public class Establishment extends ParseObject {
	// Mudar para File
	private String photo;

	public Establishment() {
	}

	public Establishment(String name, String description, String photo,
			double lat, double lon) {
		put("name", name);
		put("description", description);
		ParseGeoPoint location = new ParseGeoPoint(lat, lon);
		put("location", location);
		this.photo = photo;
	}


	public String getLocation() {
		return getString("location");
	}

	public String getName() {
		return getString("name");
	}

	public void setName(String name) {
		put("name", name);
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

	public List<Event> getEvents() {
		return getList("events");
	}

	public void setParticipants(List<User> participants) {
		put("participants", participants);
	}

	/**
	 * Adiciona um {@code event} da lista de {@code events}
	 * 
	 * @param event
	 *            O evento a ser adicionado
	 * @return True caso ele possa ser associado ao {@code establishment}
	 */
	public boolean addEvent(Event event) {
		return getEvents().add(event);
	}

	/**
	 * Remove o {@code event} da lista de {@code events}
	 * 
	 * @param event
	 *            O evento a ser removido
	 */
	public void removeEvent(Event event) {
		this.getEvents().remove(event);
	}

	@Override
	public int hashCode() {
		// FIXME InitialDate deveria ser do tipo date @author Marcos v. Candeia
		return Objects.hashCode(this.getName(), this.getDescription());
	}

	@Override
	public boolean equals(Object otherEvent) {
		if (otherEvent == null || !(otherEvent instanceof Establishment)) {
			return false;
		}
		Establishment other = (Establishment) otherEvent;
		return Objects.equal(this.getName(), other.getName())
				&& Objects.equal(this.getLocation(), other.getLocation());
	}
}
