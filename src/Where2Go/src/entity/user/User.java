
package entity.user;

import com.google.common.base.Objects;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import entity.event.Invitation;

/**
 * The Class User.
 */
@ParseClassName("iUser")
public class User extends ParseObject {

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

    /**
     * Instantiates a new user.
     */
    public User() {
    }

    /**
     * Instantiates a new user.
     * 
     * @param facebookId the facebook id
     */
    public User(final String facebookId) {
        setFacebookId(facebookId);
        setInvitations(new ArrayList<Invitation>());
    }

    /**
     * Gets the invitations.
     * 
     * @return the invitations
     */
    public final List<Invitation> getInvitations() {
        return getList("invitations");
    }

    /**
     * Sets the invitations.
     * 
     * @param invitations the new invitations
     */
    public final void setInvitations(final List<Invitation> invitations) {
        put("invitations", invitations);
    }

    /**
     * Gets the facebook id.
     * 
     * @return the facebook id
     */
    public final String getFacebookId() {
        return getString("facebookId");
    }

    /**
     * Sets the facebook id.
     * 
     * @param facebookId the new facebook id
     */
    public final void setFacebookId(final String facebookId) {
        put("facebookId", facebookId);
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param mName the new name
     */
    public final void setName(final String mName) {
        name = mName;
    }

    /**
     * Gets the birthday.
     * 
     * @return the birthday
     */
    public final String getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday.
     * 
     * @param newBirthday the new birthday
     */
    public final void setBirthday(final String newBirthday) {
        birthday = newBirthday;
    }

    /**
     * Gets the email.
     * 
     * @return the email
     */
    public final String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     * 
     * @param newEmail the new email
     */
    public final void setEmail(final String newEmail) {
        email = newEmail;
    }

    /**
     * Gets the age.
     * 
     * @return the age
     */
    public final int getAge() {
        return age;
    }

    /**
     * Sets the age.
     * 
     * @param mAge the new age
     */
    public final void setAge(final int mAge) {
        age = mAge;
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
     * @param userGender the new gender
     */
    public final void setGender(final String userGender) {
        gender = userGender;
    }

    /**
     * Remove uma {@code invitation} das {@code invitations} do usuário.
     * 
     * @param invitation the invitation
     * @return True, caso seja removida a {@code invitation}
     */
    public final boolean removeInvitation(final Invitation invitation) {
        return getInvitations().remove(invitation);
    }

    /**
     * Adiciona uma {@code invitation} à sua {@code invitations}.
     * 
     * @param invitation a {@code invitation} a ser adicionada
     */
    public final void addInvitation(final Invitation invitation) {
        getInvitations().add(invitation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return Objects.hashCode(name, age);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object therUser) {
        if (therUser == null || !(therUser instanceof User)) {
            return false;
        }
        User other = (User) therUser;
        return Objects.equal(name, other.name) && Objects.equal(age, other.age);
    }
}
