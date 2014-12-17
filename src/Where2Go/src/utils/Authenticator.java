package utils;

import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import entity.user.User;

/**
 * Classe utilitária para o usuário logado Singleton
 */
public class Authenticator {

	private User loggedUser;
	private static Authenticator instance;

	private Authenticator() {
	}

	/**
	 * Seta o usuário logado para o resultado da requisição
	 */
	public void loadLoggedUser(Session session) {
		if (session != null && session.isOpened()) {
			makeMeRequest(session);
		}
	}

	public User getLoggedUser() {
		return this.loggedUser;
	}

	/**
	 * Make me request to get user's data
	 *
	 * @param session
	 *            the session
	 */
	private void makeMeRequest(final Session session) {
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if (session == Session.getActiveSession()) {
					if (user != null) {
						Log.v("USERS", user.asMap().toString());
						loggedUser = new User(user.getName());
						loggedUser.setBirthday(user.asMap().get("birthday").toString());
						loggedUser.setEmail(user.asMap().get("email").toString());
						loggedUser.setGender(user.asMap().get("gender").toString());
						loggedUser.setFacebookId(user.getId());
					}
				}
			}
		});
		request.executeAsync();
	}

	/**
	 * Retorna a única instância da classe
	 */
	public static Authenticator getInstance() {
		if (instance == null) {
			instance = new Authenticator();
		}
		return instance;
	}
}
