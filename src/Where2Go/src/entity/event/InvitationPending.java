package entity.event;

import entity.notifications.Notification;

/**
 * Representa o estado de um {@link Invitation} pendente Created by marcos on
 * 11/30/14.
 */
public class InvitationPending implements InvitationState {

    public static final String EVENT_MSG = "The event %s was successfully %s";

    @Override
    public Notification confirm(Invitation invitation) {
        if (invitation.getEvent().addParticipant(invitation.getGuest())) {
            invitation.setState(new InvitationConfirmed());
            return new Notification(invitation.getGuest(),
                    invitation.getEvent(), String.format(EVENT_MSG, invitation
                            .getEvent().getName(), "confirmed"));
        }
        return new Notification(invitation.getGuest(), invitation.getEvent(),
                "Sorry, but you don't have access to this event");
    }

    @Override
    public Notification decline(Invitation invitation) {
        invitation.setState(new InvitationDeclined());
        return new Notification(invitation.getGuest(), invitation.getEvent(),
                String.format(EVENT_MSG, invitation.getEvent().getName(),
                        "declined"));
    }
}
