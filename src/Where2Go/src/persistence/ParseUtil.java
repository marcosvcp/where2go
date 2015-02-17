
package persistence;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

import java.text.SimpleDateFormat;

/**
 * Classe utilitária de envio de entidade para o servidor.
 * 
 * @author marcos
 */
public final class ParseUtil {

    // FIXME Exemplo de Invitations Query
    // ParseUtil.findInvitationByUserGuest(Authenticator.getInstance().getLoggedUser(),
    // new FindCallback<Invitation>() {
    // @Override
    // public void done(
    // List<Invitation> objects,
    // ParseException e) {
    // Log.v("invts", objects.toString());
    // }
    // });
    /** The ptbr. */
    public final static SimpleDateFormat ptbr = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Instantiates a new parses the util.
     */
    private ParseUtil() {
    }

    /**
     * Salva o {@code event} no Servidor.
     * 
     * @param event the event
     */
    public static void saveEvent(final Event event) {
        event.saveInBackground();
    }

    /**
     * Save invitation.
     * 
     * @param invitation the invitation
     */
    public static void saveInvitation(final Invitation invitation) {
        invitation.saveInBackground();
    }

    /**
     * Retorna O {@link ParseQuery} do {@link Event}.
     * 
     * @return the query event
     */
    public static ParseQuery<Event> getQueryEvent() {
        return ParseQuery.getQuery("Event");
    }

    /**
     * Gets the query user.
     * 
     * @return the query user
     */
    public static ParseQuery<User> getQueryUser() {
        return ParseQuery.getQuery("iUser");
    }

    /**
     * Gets the query invitation.
     * 
     * @return the query invitation
     */
    public static ParseQuery<Invitation> getQueryInvitation() {
        return ParseQuery.getQuery("Invitation");
    }

    /**
     * Find invitation by user guest.
     * 
     * @param from the from
     * @param callback the callback
     */
    public static void findInvitationByUserGuest(final User from,
            final FindCallback<Invitation> callback) {
        final ParseQuery<Invitation> query = getQueryInvitation().whereEqualTo(
                "userGuest", from);
        query.findInBackground(callback);

    }

    /**
     * Find by facebook id.
     * 
     * @param facebookId the facebook id
     * @param callback the callback
     */
    public static void findByFacebookId(final String facebookId,
            final FindCallback<User> callback) {
        final ParseQuery<User> query = getQueryUser().whereEqualTo(
                "facebookId", facebookId);
        query.findInBackground(callback);

    }

    /**
     * Busca o Objeto que tem o {@code objectId} no servidor e executa o
     * {@code callback} quando a requisição for terminada.
     * 
     * @param objectId the object id
     * @param callback the callback
     */
    public static void findEventById(final String objectId,
            final GetCallback<Event> callback) {
        getQueryEvent().getInBackground(objectId, callback);
    }

    /**
     * Retorna todos os {@link Event} do servidor.
     * 
     * @param findCallback the find callback
     */
    public static void findAllEvents(final FindCallback<Event> findCallback) {
        getQueryEvent().findInBackground(findCallback);
    }

    /**
     * Gets the query tag.
     * 
     * @return the query tag
     */
    public static ParseQuery<ParseObject> getQueryTag() {
        return ParseQuery.getQuery("Tag");
    }

    /**
     * Find all tags.
     * 
     * @param findCallback the find callback
     */
    public static void findAllTags(final FindCallback<ParseObject> findCallback) {
        getQueryTag().findInBackground(findCallback);
    }
}
