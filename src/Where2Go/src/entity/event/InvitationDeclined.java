package entity.event;

import entity.notifications.Notification;

/**
 * Representa o estado negado de um {@link Invitation}
 * <p/>
 * Created by marcos on 11/30/14.
 */
public class InvitationDeclined implements InvitationState {

    /*
     * (non-Javadoc)
     *
     * @see entity.event.InvitationState#confirm(entity.event.Invitation)
     */
    @Override
    public final Notification confirm(final Invitation invitation) {
        invitation.setState(new InvitationPending().getName());
        return invitation.confirm();
    }

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.InvitationState#decline(entity.event.Invitation)
     */
    @Override
    public final Notification decline(final Invitation invitation) {
        return new Notification(invitation.getGuest(), invitation.getEvent(),
                String.format("You already declined this event %s", invitation
                        .getEvent().getName()));
    }

    /*
     * (non-Javadoc)
     *
     * @see entity.event.InvitationState#getName()
     */
    @Override
    public final String getName() {
        return "Declined";
    }
}
