
package entity.event;

import entity.notifications.Notification;

/**
 * Representa o estado confirmado de um {@link Invitation} Created by marcos on
 * 11/30/14.
 */
public class InvitationConfirmed implements InvitationState {

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.InvitationState#confirm(entity.event.Invitation)
     */
    @Override
    public final Notification confirm(final Invitation invitation) {
        return new Notification(invitation.getGuest(), invitation.getEvent(),
                String.format("You already registered in %s", invitation
                        .getEvent().getName()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.InvitationState#decline(entity.event.Invitation)
     */
    @Override
    public final Notification decline(final Invitation invitation) {
        invitation.getEvent().removeParticipant(invitation.getGuest());
        return new Notification(invitation.getGuest(), invitation.getEvent(),
                String.format("You leave from this event %s",
                        invitation.getEvent()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.InvitationState#getName()
     */
    @Override
    public final String getName() {
        return "Confirmed";
    }
}
