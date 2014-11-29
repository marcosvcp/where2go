package entity;


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
    private String capacity;
    private String timestamp;

    public Event(Integer id, String name, String status, String description,
                 String photo, String info, String initialDate, String finalDate,
                 String price, String outfit, String capacity, String timestamp) {
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
