package entity.event;


import com.google.common.base.Objects;
import entity.user.User;

import java.util.Collections;
import java.util.List;

/**
 * Representa um evento.
 * Created by andersongfs on 25/11/14.
 */
public class Event {
    private Integer id;
    private String name;
    private String status;
    private String description;
    private String photo;
    private String info;
    private String initialDate;
    private String finalDate;
    private String price;
    private String outfit;
    private Integer capacity;
    private String timestamp;
    private List<User> participants;
    private EventState state;

    public Event(Integer id, String name, String status, String description,
                 String photo, String info, String initialDate, String finalDate,
                 String price, String outfit, Integer capacity, String timestamp) {
        this.state = new EventOpened();
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.photo = photo;
        this.info = info;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.price = price;
        this.outfit = outfit;
        this.capacity = capacity;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOutfit() {
        return outfit;
    }

    public void setOutfit(String outfit) {
        this.outfit = outfit;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<User> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    /**
     * Adiciona um {@code participant} à lista de {@code participants}
     *
     * @param participant O participante a ser adicionado
     * @return True caso ele possa participar do evento
     */
    public boolean addParticipant(User participant) {
        if (isFull()) {
            return false;
        }
        return participants.add(participant);
    }

    /**
     * Remove o {@code participant} da lista de {@code participants}
     *
     * @param participant O participante a ser removido
     */
    public void removeParticipant(User participant) {
        this.participants.remove(participant);
    }

    /**
     * @return True caso o evento esteja completo
     */
    public boolean isFull() {
        return capacity <= participants.size();
    }

    @Override
    public int hashCode() {
        // FIXME InitialDate deveria ser do tipo date @author Marcos v. Candeia
        return Objects.hashCode(this.name, this.description, this.initialDate);
    }

    @Override
    public boolean equals(Object otherEvent) {
        if (otherEvent == null || !(otherEvent instanceof Event)) {
            return false;
        }
        Event other = (Event) otherEvent;
        return Objects.equal(this.name, other.name) &&
                Objects.equal(this.description, other.description) &&
                Objects.equal(this.initialDate, other.initialDate);
    }
}
