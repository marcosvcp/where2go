package entity;

import java.util.Collections;
import java.util.List;

/**
 * Entidade que representa um usuário
 * Created by andersongfs on 25/11/14.
 */
public class User {
    private String name;
    private int age;
    private List<Event> events;
    //falta o atributo que liga o usuario aos dados do facebook: FACEBOOK ID?

    public User(String name, int age) {
        this.name = name;
        this.age = age;
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

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * Adiciona um eveneto à {@code events} do usuário
     *
     * @param event O evento a ser adicionado
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Remove um evento dos {@code events} do usuário
     *
     * @param event O evento a ser removido
     */
    public void removeEvent(Event event) {
        events.remove(event);
    }
}
