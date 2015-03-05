package utils;

import java.util.List;

import persistence.ParseUtil;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.FindCallback;
import com.parse.ParseException;

import entity.user.User;

/**
 * Classe utilitária para o usuário logado Singleton.
 */
public final class Authenticator {

    /**
     * The logged user.
     */
    private User loggedUser;

    /**
     * The instance.
     */
    private static Authenticator instance;

    /**
     * Instantiates a new authenticator.
     */
    private Authenticator() {
    }

    /**
     * Seta o usuário logado para o resultado da requisição.
     *
     * @param session
     *            the session
     */
    public void loadLoggedUser(final Session session) {
        if (session != null && session.isOpened()) {
            makeMeRequest(session);
        }
    }

    /**
     * Gets the logged user.
     *
     * @return the logged user
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Make me request to get user's data.
     *
     * @param session
     *            the session
     */
    private void makeMeRequest(final Session session) {
        final Request request = Request.newMeRequest(session,
                new GraphUserCallback() {
            @Override
            public void onCompleted(final GraphUser user,
                            final Response response) {
                if (session == Session.getActiveSession()
                                && user != null) {
                    ParseUtil.findByFacebookId(user.getId(),
                            new FindCallback<User>() {
                        @Override
                        public void done(
                                final List<User> objects,
                                final ParseException e) {
                            loggedUser = new User(user.getId());
                            loggedUser
                                                    .setBirthday(user.asMap()
                                                            .get("birthday")
                                                            .toString());
                            loggedUser.setEmail(user.asMap()
                                                    .get("email").toString());
                            loggedUser.setGender(user.asMap()
                                                    .get("gender").toString());
                            loggedUser.setName(user.getName());
                            if (objects.isEmpty()) {
                                loggedUser.saveInBackground();
                            } else {
                                // Pega o resultado do
                                    // usu�rio
                                final User usuarioParse = objects
                                        .get(0);
                                loggedUser
                                .setObjectId(usuarioParse
                                        .getObjectId());
                            }
                        }
                    });
                }
            }
        });
        request.executeAsync();
    }

    /**
     * Retorna a única instância da classe.
     *
     * @return single instance of Authenticator
     */
    public static Authenticator getInstance() {
        if (instance == null) {
            instance = new Authenticator();
        }
        return instance;
    }

    /**
     * Logout from facebook
     */
    public void logout() {
        loggedUser = null;
    }
}
