package entity.event;

import com.google.common.base.Objects;
import entity.notifications.Notification;
import entity.user.User;


/**
 * Representa um convite
 * Created by marcos on 11/30/14.
 */
public class Invitation {
    private User guest;
    private Event event;
    private InvitationState state;

    public Invitation(User guest, Event event) {
        this.state = new InvitationPending();
        this.guest = guest;
        this.event = event;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Aceita um convite de um evento dependendo do seu {@code state}
     */
    public Notification confirm() {
        return state.confirm(this);
    }

    /**
     * Nega o convite para o {@code event}
     */
    public Notification decline() {
        return state.decline(this);
    }

    public InvitationState getState() {
        return state;
    }

    public void setState(InvitationState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(guest, event);
    }

    @Override
    public boolean equals(Object otherEvent) {
        if (otherEvent == null || !(otherEvent instanceof Invitation)) {
            return false;
        }
        Invitation other = (Invitation) otherEvent;
        return Objects.equal(guest, other.guest) &&
                Objects.equal(event, other.event);
    }
}
