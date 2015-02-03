package entity.event;

import com.google.common.base.Objects;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import entity.notifications.Notification;
import entity.user.User;

/**
 * Representa um convite Created by marcos on 11/30/14.
 */
@ParseClassName("Invitation")
public class Invitation extends ParseObject {

	public Invitation() {
	}

	public Invitation(User guest, User host, Event event) {
		put("status", new InvitationPending().getName());
		setGuest(guest);
		setHost(host);
		setEvent(event);
	}

	public InvitationState getInvitationState() {
		if (getState().equals("Pending")) {
			return new InvitationPending();
		}
		if (getState().equals("Declined")) {
			return new InvitationDeclined();
		} else {
			return new InvitationConfirmed();
		}
	}

	public User getHost() {
		ParseRelation<User> query = getRelation("userHost");
		try {
			return query.getQuery().find().get(0);
		} catch (ParseException e) {
			return null;
		}
	}

	public void setHost(User host) {
		ParseRelation<User> relat = getRelation("userHost");
		relat.add(host);
	}

	public User getGuest() {
		ParseRelation<User> query = getRelation("userGuest");
		try {
			return query.getQuery().find().get(0);
		} catch (ParseException e) {
			return null;
		}
	}

	public void setGuest(User guest) {
		ParseRelation<User> relat = getRelation("userGuest");
		relat.add(guest);
	}

	public Event getEvent() {
		ParseRelation<Event> query = getRelation("event");
		try {
			return query.getQuery().find().get(0);
		} catch (ParseException e) {
			return null;
		}
	}

	public void setEvent(Event event) {
		ParseRelation<Event> relat = getRelation("event");
		relat.add(event);
	}

	/**
	 * Aceita um convite de um evento dependendo do seu {@code state}
	 */
	public Notification confirm() {
		return getInvitationState().confirm(this);
	}

	/**
	 * Nega o convite para o {@code event}
	 */
	public Notification decline() {
		return getInvitationState().decline(this);
	}

	public String getState() {
		return getString("status");
	}

	public void setState(String state) {
		put("status", state);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getGuest(), getEvent());
	}

	@Override
	public boolean equals(Object otherEvent) {
		if (otherEvent == null || !(otherEvent instanceof Invitation)) {
			return false;
		}
		Invitation other = (Invitation) otherEvent;
		return Objects.equal(getGuest(), other.getGuest())
				&& Objects.equal(getEvent(), other.getEvent());
	}
}
