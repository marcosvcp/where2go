package persistence;
import java.util.List;
import entity.Event;

public interface StorageSystem {
    public Integer add(Event event);
    public void delete(Integer id);
    public void edit(Event event);
    public Event getEvent(int key);
    public List<Event> getEvents();

}
