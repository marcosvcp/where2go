package entity.event;

/**
 * Representa a visiblidade de um {@link Event}
 * Created by andersongfs on 25/11/14.
 */
public enum EventVisibility {
    PUBLIC("Público"), PRIVATE("Privado");
    private String visibility;

    EventVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
