package utils;

import java.util.List;

import persistence.ParseUtil;
import android.util.Log;
import bolts.Continuation;
import bolts.Task;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.FindCallback;
import com.parse.ParseException;

import entity.event.Invitation;
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
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(final GraphUser user,
							Response response) {
						if (session == Session.getActiveSession()) {
							if (user != null) {
								ParseUtil.findByFacebookId(user.getId(),
										new FindCallback<User>() {
											@Override
											public void done(
													List<User> objects,
													ParseException e) {
												Log.v("USERS", user.asMap()
														.toString());
												loggedUser = new User(user
														.getId());
												loggedUser.setBirthday(user
														.asMap()
														.get("birthday")
														.toString());
												loggedUser.setEmail(user
														.asMap()
														.get("email")
														.toString());
												loggedUser.setGender(user
														.asMap()
														.get("gender")
														.toString());
												loggedUser.setName(user
														.getName());
												if (objects.isEmpty()) {
													loggedUser
															.saveInBackground();
												} else {
													Log.v("TESTE", "entrou aqui");
													// Pega o resultado do usu�rio
													User usuarioParse = objects.get(0);
													loggedUser.setInvitations(usuarioParse.getInvitations());
													loggedUser.setObjectId(usuarioParse.getObjectId());
												}
											}
										});
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
