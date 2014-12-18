package entity.event;

import entity.notifications.Notification;

/**
 * Representa o estado negado de um {@link Invitation}
 * <p/>
 * Created by marcos on 11/30/14.
 */
public class InvitationDeclined implements InvitationState {

    @Override
    public Notification confirm(Invitation invitation) {
        invitation.setState(new InvitationPending());
        return invitation.confirm();
    }

    @Override
    public Notification decline(Invitation invitation) {
        return new Notification(invitation.getGuest(), invitation.getEvent(),
                String.format("You already declined this event %s", invitation
                        .getEvent().getName()));
    }
}
