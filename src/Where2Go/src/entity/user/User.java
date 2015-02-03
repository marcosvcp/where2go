package entity.user;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import entity.event.Invitation;

/**
 * The Class User.
 */
@ParseClassName("iUser")
public class User extends ParseObject{

    /** The name. */
    private String name;

    /** The age. */
    private int age;

    /** The birthday. */
    private String birthday;

    /** The email. */
    private String email;

    /** The gender. */
    private String gender;

    // falta o atributo que liga o usuario aos dados do facebook: FACEBOOK ID?

    public User() {}
    
    public User(String facebookId) {
    	setFacebookId(facebookId);
    	setInvitations(new ArrayList<Invitation>());
    }
    
    public List<Invitation> getInvitations() {
    	return getList("invitations");
    }
    
    public void setInvitations(List<Invitation> invitations) {
    	put("invitations", invitations);
    }
    
    public String getFacebookId() {
    	return getString("facebookId");
    }
    
    public void setFacebookId(String facebookId) {
        put("facebookId", facebookId);
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param mName
     *            the new name
     */
    public void setName(String mName) {
        this.name = mName;
    }

    /**
     * Gets the birthday.
     *
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday.
     *
     * @param birthday
     *            the new birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age.
     *
     * @param mAge
     *            the new age
     */
    public void setAge(int mAge) {
        this.age = mAge;
    }

    /**
     * Gets the gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     *
     * @param gender
     *            the new gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Remove uma {@code invitation} das {@code invitations} do usuário.
     *
     * @param invitation
     *            the invitation
     * @return True, caso seja removida a {@code invitation}
     */
    public boolean removeInvitation(Invitation invitation) {
        return getInvitations().remove(invitation);
    }

    /**
     * Adiciona uma {@code invitation} à sua {@code invitations}.
     *
     * @param invitation
     *            a {@code invitation} a ser adicionada
     */
    public void addInvitation(Invitation invitation) {
        getInvitations().add(invitation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.name, this.age);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object therUser) {
        if (therUser == null || !(therUser instanceof User)) {
            return false;
        }
        User other = (User) therUser;
        return Objects.equal(name, other.name) && Objects.equal(age, other.age);
    }
}
