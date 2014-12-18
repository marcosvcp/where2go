package persistence;

import java.text.SimpleDateFormat;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import entity.event.Event;

// TODO: Auto-generated Javadoc
/**
 * Classe utilitária de envio de entidade para o servidor.
 *
 * @author marcos
 */
public class ParseUtil {

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
     * Retorna O {@link ParseQuery} do {@link Event}.
     *
     * @return the query event
     */
    public static ParseQuery<Event> getQueryEvent() {
        return ParseQuery.getQuery("Event");
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
    public static void findById(String objectId, GetCallback<Event> callback) {
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
