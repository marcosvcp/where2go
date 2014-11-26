package entity;

import java.util.List;

/**
 * Created by andersongfs on 25/11/14.
 */
public class User {
    private String mName;
    private int mAge;
    private List<Event> mEvents;
    //falta o atributo que liga o usuario aos dados do facebook: FACEBOOK ID?

    public User(String name, int age){
        this.mName = name;
        this.mAge = age;

    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int mAge) {
        this.mAge = mAge;
    }

    public List<Event> getEvents() {
        return mEvents;
    }

    public void addEvent(Event event){
        mEvents.add(event);
    }

    public void removeEvent(Event event){
        if(mEvents.contains(event)){
            mEvents.remove(event);
        }
    }
}
