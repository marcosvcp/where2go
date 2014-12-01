package entity.event;

import entity.notifications.Notification;

/**
 * Representa o estado confirmado de um {@link Invitation}
 * Created by marcos on 11/30/14.
 */
public class InvitationConfirmed implements InvitationState {

    @Override
    public Notification confirm(Invitation invitation) {
        return new Notification(invitation.getGuest(),
                invitation.getEvent(), String.format("You already registered in %s", invitation.getEvent().getName()));
    }

    @Override
    public Notification decline(Invitation invitation) {
        invitation.getEvent().removeParticipant(invitation.getGuest());
        return new Notification(invitation.getGuest(), invitation.getEvent(),
                String.format("You leave from this event %s", invitation.getEvent()));
    }
}
