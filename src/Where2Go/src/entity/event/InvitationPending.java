package entity.event;

import entity.notifications.Notification;

/**
 * Representa o estado de um {@link Invitation} pendente Created by marcos on
 * 11/30/14.
 */
public class InvitationPending implements InvitationState {

    /**
     * The Constant EVENT_MSG.
     */
    public static final String EVENT_MSG = "The event %s was successfully %s";

    /*
     * (non-Javadoc)
     *
     * @see entity.event.InvitationState#confirm(entity.event.Invitation)
     */
    @Override
    public final Notification confirm(final Invitation invitation) {
        invitation.setState(new InvitationConfirmed().getName());
        return new Notification(invitation.getGuest(), invitation.getEvent(),
                String.format(EVENT_MSG, invitation.getEvent().getName(),
                        "confirmed"));
    }

    /*
     * (non-Javadoc)
     *
     * @see entity.event.InvitationState#decline(entity.event.Invitation)
     */
    @Override
    public final Notification decline(final Invitation invitation) {
        invitation.setState(new InvitationDeclined().getName());
        return new Notification(invitation.getGuest(), invitation.getEvent(),
                String.format(EVENT_MSG, invitation.getEvent().getName(),
                        "declined"));
    }

    /*
     * (non-Javadoc)
     *
     * @see entity.event.InvitationState#getName()
     */
    @Override
    public final String getName() {
        return "Pending";
    }
}
