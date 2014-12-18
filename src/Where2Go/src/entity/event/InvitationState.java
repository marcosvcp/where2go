package entity.event;

import entity.notifications.Notification;

/**
 * Estado de uma {@link Invitation} Created by marcos on 11/30/14.
 */
public interface InvitationState {

    /**
     * Confirma convite para um {@link Event}
     * <p/>
     * {@see Event}
     *
     * @return Uma notificação com uma mensagem
     */
    Notification confirm(Invitation invitation);

    /**
     * Rejeita convite para um {@link Event}
     * <p/>
     * {@see Event}
     *
     * @return Uma notificação com uma mensagem
     */
    Notification decline(Invitation invitation);
}
