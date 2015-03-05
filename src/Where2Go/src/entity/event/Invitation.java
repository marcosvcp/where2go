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

    /**
     * Instantiates a new invitation.
     */
    public Invitation() {
    }

    /**
     * Instantiates a new invitation.
     *
     * @param guest
     *            the guest
     * @param host
     *            the host
     * @param event
     *            the event
     */
    public Invitation(final User guest, final User host, final Event event) {
        put("status", new InvitationPending().getName());
        setGuest(guest);
        setHost(host);
        setEvent(event);
    }

    /**
     * Gets the invitation state.
     *
     * @return the invitation state
     */
    public final InvitationState getInvitationState() {
        if ("Pending".equals(getState())) {
            return new InvitationPending();
        }
        if ("Declined".equals(getState())) {
            return new InvitationDeclined();
        } else {
            return new InvitationConfirmed();
        }
    }

    /**
     * Gets the host.
     *
     * @return the host
     */
    public final User getHost() {
        final ParseRelation<User> query = getRelation("userHost");
        try {
            return query.getQuery().find().get(0);
        } catch (final ParseException e) {
            return null;
        }
    }

    /**
     * Sets the host.
     *
     * @param host
     *            the new host
     */
    public final void setHost(final User host) {
        final ParseRelation<User> relat = getRelation("userHost");
        relat.add(host);
    }

    /**
     * Gets the guest.
     *
     * @return the guest
     */
    public final User getGuest() {
        final ParseRelation<User> query = getRelation("userGuest");
        try {
            return query.getQuery().find().get(0);
        } catch (final ParseException e) {
            return null;
        }
    }

    /**
     * Sets the guest.
     *
     * @param guest
     *            the new guest
     */
    public final void setGuest(final User guest) {
        final ParseRelation<User> relat = getRelation("userGuest");
        relat.add(guest);
    }

    /**
     * Gets the event.
     *
     * @return the event
     */
    public final Event getEvent() {
        final ParseRelation<Event> query = getRelation("event");
        try {
            return query.getQuery().find().get(0);
        } catch (final ParseException e) {
            return null;
        }
    }

    /**
     * Sets the event.
     *
     * @param event
     *            the new event
     */
    public final void setEvent(final Event event) {
        final ParseRelation<Event> relat = getRelation("event");
        relat.add(event);
    }

    /**
     * Aceita um convite de um evento dependendo do seu {@code state}.
     *
     * @return the notification
     */
    public final Notification confirm() {
        return getInvitationState().confirm(this);
    }

    /**
     * Nega o convite para o {@code event}.
     *
     * @return the notification
     */
    public final Notification decline() {
        return getInvitationState().decline(this);
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public final String getState() {
        return getString("status");
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the new state
     */
    public final void setState(final String state) {
        put("status", state);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return Objects.hashCode(getGuest(), getEvent());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object otherEvent) {
        if (otherEvent == null || !(otherEvent instanceof Invitation)) {
            return false;
        }
        final Invitation other = (Invitation) otherEvent;
        return Objects.equal(getGuest(), other.getGuest())
                && Objects.equal(getEvent(), other.getEvent());
    }
}
