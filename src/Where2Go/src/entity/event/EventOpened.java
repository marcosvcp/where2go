
package entity.event;

import android.util.Log;

import com.parse.ParseException;

import entity.notifications.Notification;
import entity.user.User;
import persistence.ParseUtil;

/**
 * Created by marcos on 11/30/14.
 */
public class EventOpened implements EventState {

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.EventState#removeParticipant()
     */
    @Override
    public final Notification removeParticipant(Event evt, User guest, User host) {// TODO Ainda falta implementar
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.EventState#addParticipant()
     */
    @Override
    public final Notification addParticipant(Event evt, User guest, User host) {
        try {
            if(!evt.isPublic() && !evt.getOwner().equals(host)){
                return new Notification(host, evt, "Apenas o dono do evento - " + evt.getOwner() +" pode adicionar pessoas");
            }
        } catch (ParseException e) {
            Log.e("Event State", e.getMessage());
        }
        final Invitation invite = new Invitation(
                guest, host, evt);
        ParseUtil.saveInvitation(invite);
        return new Notification(guest, evt, guest.getName() + " foi adicionado ao evento " + evt.getName() + " por " + host.getName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.EventState#getName()
     */
    @Override
    public final String getName() {
        return "Opened";
    }
}
