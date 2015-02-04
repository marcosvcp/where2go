package entity.user;

public class UserFriend {

    private String friendName;

    private String friendPicture;

    private String friendid;

    public UserFriend(final String friendName, final String friendPicture,
            final String friendid) {
        super();
        this.friendName = friendName;
        this.friendPicture = friendPicture;
        this.friendid = friendid;
    }

    public UserFriend(final String friendName, final String friendid) {
        super();
        this.friendName = friendName;
        this.friendPicture = "";
        this.friendid = friendid;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(final String friendName) {
        this.friendName = friendName;
    }

    public String getFriendPicture() {
        return friendPicture;
    }

    public void setFriendPicture(final String friendPicture) {
        this.friendPicture = friendPicture;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(final String friendid) {
        this.friendid = friendid;
    }

}
