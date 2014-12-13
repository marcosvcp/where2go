package entity.user;

import com.google.common.base.Objects;

import entity.event.Invitation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidade que representa um usuário
 * Created by andersongfs on 25/11/14.
 */
public class User {
    private String name;
    private int age;
    private String gender;

    private List<Invitation> invitations;
    //falta o atributo que liga o usuario aos dados do facebook: FACEBOOK ID?

    public User(String name, int age) {
        this.name = name;
        this.age = age;
        this.invitations = new ArrayList<Invitation>();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String mName) {
        this.name = mName;
    }
  
    public int getAge() {
        return age;
    }
  
    public void setAge(int mAge) {
        this.age = mAge;
    }
  
    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Invitation> getInvitations() {
        return Collections.unmodifiableList(invitations);
    }
  
    /**
     * Remove uma {@code invitation} das {@code invitations} do usuário.
     *
     * @return True, caso seja removida a {@code invitation}
     */
    public boolean removeInvitation(Invitation invitation) {
        return invitations.remove(invitation);
    }
  
    /**
     * Adiciona uma {@code invitation} à sua {@code invitations}
     *
     * @param invitation a {@code invitation} a ser adicionada
     */
    public void addInvitation(Invitation invitation) {
        invitations.add(invitation);
    }
  
    @Override
    public int hashCode() {
        return Objects.hashCode(this.name, this.age);
    }
  
    @Override
    public boolean equals(Object therUser) {
        if (therUser == null || !(therUser instanceof User)) {
            return false;
        }
        User other = (User) therUser;
        return Objects.equal(name, other.name)
                && Objects.equal(age, other.age);
    }
}
