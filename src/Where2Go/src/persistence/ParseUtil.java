package persistence;

import java.text.SimpleDateFormat;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseQuery;

import entity.event.Event;

/**
 * Classe utilitária de envio de entidade para o servidor
 * 
 * @author marcos
 */
public class ParseUtil {

	public static SimpleDateFormat ptbr = new SimpleDateFormat("dd/MM/yyyy");

	private ParseUtil() {
	}

	/**
	 * Salva o {@code event} no Servidor
	 */
	public static void saveEvent(Event event) {
		event.saveInBackground();
	}

	/**
	 * Retorna O {@link ParseQuery} do {@link Event}.
	 */
	public static ParseQuery<Event> getQueryEvent() {
		return ParseQuery.getQuery("Event");
	}

	/**
	 * Busca o Objeto que tem o {@code objectId} no servidor e executa o
	 * {@code callback} quando a requisição for terminada
	 */
	public static void findById(String objectId, GetCallback<Event> callback) {
		getQueryEvent().getInBackground(objectId, callback);
	}

	/**
	 * Retorna todos os {@link Event} do servidor 
	 */
	public static void findAllEvents(FindCallback<Event> findCallback) {
		getQueryEvent().findInBackground(findCallback);
	}
}
