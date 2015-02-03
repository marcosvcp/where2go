package entity.user;

public class UserFriend {
	
	private String friendName;
	
	private String friendPicture;
	
	private String friendid;

	public UserFriend(String friendName, String friendPicture, String friendid) {
		super();
		this.friendName = friendName;
		this.friendPicture = friendPicture;
		this.friendid = friendid;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getFriendPicture() {
		return friendPicture;
	}

	public void setFriendPicture(String friendPicture) {
		this.friendPicture = friendPicture;
	}

	public String getFriendid() {
		return friendid;
	}

	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}

}
