package manager;

import persistence.ParseUtil;
import android.content.Context;
import android.util.Log;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

public class InvitationManager {
    /** The m instance. */
    private static InvitationManager mInstance;

    /** The m context. */
    private final Context mContext;

    /**
     * The Constant LOCK.
     */
    private static final Object LOCK = new Object();

    /**
     * Instantiates a new AD notification manager.
     */
    private InvitationManager(final Context context) {
        mContext = context;
    }

    /**
     * Gets the single instance of ADNotificationManager.
     *
     * @return single instance of ADNotificationManager
     */
    public static InvitationManager getInstance(final Context context) {
        synchronized (LOCK) {
            if (mInstance == null) {
                mInstance = new InvitationManager(context);
            }
            return mInstance;
        }
    }

    public void makeInvitation(final User guest, final User host,
            final Event event) {
        final Invitation newInvite = new Invitation(guest, host, event);
        ParseUtil.saveInvitation(newInvite);
        final ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("user", guest.getInstalationId());
        Log.d("Push", guest.getName() + guest.getInstalationId());
        // Send push notification to query
        final ParsePush push = new ParsePush();
        push.setQuery(pushQuery); // Set our Installation query
        push.setMessage("Voce tem um novo convite!");
        push.sendInBackground();
        Log.d("Push", "Sended");
    }

    public void makeInvitationNotification() {
        // final User guest = objects.get(0);
        // final Notification notification = eventToInvite
        // .addParticipant(guest, host);
    }
}
