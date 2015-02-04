package persistence;

import java.text.SimpleDateFormat;
import java.util.List;

import utils.Authenticator;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

// TODO: Auto-generated Javadoc
/**
 * Classe utilitária de envio de entidade para o servidor.
 *
 * @author marcos
 */
public class ParseUtil {

//	FIXME Exemplo de Invitations Query
//	ParseUtil.findInvitationByUserGuest(Authenticator.getInstance().getLoggedUser(), new FindCallback<Invitation>() {
//		@Override
//		public void done(
//				List<Invitation> objects,
//				ParseException e) {
//			Log.v("invts", objects.toString());
//		}
//	});
    /** The ptbr. */
    public static SimpleDateFormat ptbr = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Instantiates a new parses the util.
     */
    private ParseUtil() {
    }

    /**
     * Salva o {@code event} no Servidor.
     *
     * @param event
     *            the event
     */
    public static void saveEvent(Event event) {
        event.saveInBackground();
    }
    
    /**
     * Salva o {@code invitation} no Servidor.
     *
     * @param event
     *            the event
     */
    public static void saveInvitation(Invitation invitation) {
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
    
    public static ParseQuery<User> getQueryUser() {
        return ParseQuery.getQuery("iUser");
    }
    
    public static ParseQuery<Invitation> getQueryInvitation() {
        return ParseQuery.getQuery("Invitation");
    }
    
    public static void findInvitationByUserGuest(User from, FindCallback<Invitation> callback) {
        ParseQuery<Invitation> query = getQueryInvitation().whereEqualTo("userGuest", from);
        query.findInBackground(callback);
        
    }
    
    public static void findByFacebookId(String facebookId, FindCallback<User> callback) {
        ParseQuery<User> query = getQueryUser().whereEqualTo("facebookId", facebookId);	
        query.findInBackground(callback);
        
    }
    
    /**
     * Busca o Objeto que tem o {@code objectId} no servidor e executa o
     * {@code callback} quando a requisição for terminada.
     *
     * @param objectId
     *            the object id
     * @param callback
     *            the callback
     */
    public static void findEventById(String objectId, GetCallback<Event> callback) {
        getQueryEvent().getInBackground(objectId, callback);
    }

    /**
     * Retorna todos os {@link Event} do servidor.
     *
     * @param findCallback
     *            the find callback
     */
    public static void findAllEvents(FindCallback<Event> findCallback) {
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

    public static void findAllTags(FindCallback<ParseObject> findCallback) {
        getQueryTag().findInBackground(findCallback);
    }
}
