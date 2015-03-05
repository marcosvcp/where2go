package entity.user;

/**
 * The Class UserFriend.
 */
public class UserFriend {

    /** The friend name. */
    private String mFriendName;

    /** The friend picture. */
    private String mFriendPicture;

    /** The friendid. */
    private String mFriendId;

    /**
     * Instantiates a new user friend.
     *
     * @param friendName
     *            the friend name
     * @param friendPicture
     *            the friend picture
     * @param friendid
     *            the friendid
     */
    public UserFriend(final String friendName, final String friendPicture,
            final String friendid) {
        super();
        mFriendName = friendName;
        mFriendPicture = friendPicture;
        mFriendId = friendid;
    }

    /**
     * Instantiates a new user friend.
     *
     * @param friendName
     *            the friend name
     * @param friendid
     *            the friendid
     */
    public UserFriend(final String friendName, final String friendid) {
        super();
        mFriendName = friendName;
        mFriendPicture = "";
        mFriendId = friendid;
    }

    /**
     * Gets the friend name.
     *
     * @return the friend name
     */
    public final String getFriendName() {
        return mFriendName;
    }

    /**
     * Sets the friend name.
     *
     * @param friendName
     *            the new friend name
     */
    public final void setFriendName(final String friendName) {
        mFriendName = friendName;
    }

    /**
     * Gets the friend picture.
     *
     * @return the friend picture
     */
    public final String getFriendPicture() {
        return mFriendPicture;
    }

    /**
     * Sets the friend picture.
     *
     * @param friendPicture
     *            the new friend picture
     */
    public final void setFriendPicture(final String friendPicture) {
        mFriendPicture = friendPicture;
    }

    /**
     * Gets the friendid.
     *
     * @return the friendid
     */
    public final String getFriendid() {
        return mFriendId;
    }

    /**
     * Sets the friendid.
     *
     * @param friendid
     *            the new friendid
     */
    public final void setFriendid(final String friendid) {
        mFriendId = friendid;
    }

}
